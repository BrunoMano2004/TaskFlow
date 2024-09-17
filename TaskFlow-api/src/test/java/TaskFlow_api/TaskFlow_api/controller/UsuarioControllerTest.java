package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void deveriaRetornarUsuario() throws Exception {

        Usuario usuario = new Usuario("email@email.com", "Usuario usuario", LocalDate.now(), "imagem");
        ListagemUsuarioDto usuarioDto = new ListagemUsuarioDto(usuario);

        when(usuarioService.retornarUsuario(anyString())).thenReturn(usuarioDto);

        System.out.println(LocalDate.now());

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String json = om.writeValueAsString(usuarioDto);

        mvc.perform(get("/usuario/{email}", "brunomano2004@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(content().json(json))
                .andExpect(status().isOk());

    }

    @Test
    void deveriaRetornarErro404() throws Exception {

        when(usuarioService.retornarUsuario(anyString())).thenThrow(new ResourceNotFoundException("Usuário não encontrado!"));

        mvc.perform(get("/usuario/{email}", "brunomano2004@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound());
    }

    @Test
    void deveriaRetornarCreated() throws Exception {
        CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto(
                "usuario@usuario.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(cadastroUsuarioDto);

        mvc.perform(post("/usuario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(content().string("Usuário criado com sucesso!"))
                .andExpect(status().isCreated());
    }

    @Test
    void deveriaRetornarErro400AoInserirEmailNoFormatoIncorreto() throws Exception {
        CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto(
                "usuariousuario.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        List<TratamentoErroBeanValidation> listaErros = new ArrayList<>();
        TratamentoErroBeanValidation erroDto = new TratamentoErroBeanValidation("email", "O email deve estar no formato correto! email@email.com");
        listaErros.add(erroDto);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(cadastroUsuarioDto);
        String json1 = om.writeValueAsString(listaErros);

        mvc.perform(post("/usuario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(content().json(json1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarErro400AoDeixarEmailEmBranco() throws Exception {
        CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto(
                "",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        List<TratamentoErroBeanValidation> listaErros = new ArrayList<>();
        TratamentoErroBeanValidation erroDto = new TratamentoErroBeanValidation("email", "Email não deve estar vazio!");
        listaErros.add(erroDto);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(cadastroUsuarioDto);
        String json1 = om.writeValueAsString(listaErros);

        mvc.perform(post("/usuario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(content().json(json1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarErro400AoDeixarDataNoFormatoErrado() throws Exception {
        CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "1mfrg",
                "imagem"
        );

        List<TratamentoErroBeanValidation> listaErros = new ArrayList<>();
        TratamentoErroBeanValidation erroDto = new TratamentoErroBeanValidation("dataNascimento", "Data deve seguir o formato correto! DD/MM/YYYY");
        listaErros.add(erroDto);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(cadastroUsuarioDto);
        String json1 = om.writeValueAsString(listaErros);

        mvc.perform(post("/usuario")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(content().json(json1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveriaRetornarUsuarioAtualizadoEStatus200() throws Exception {
        CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto(
                "email@email",
                "Usuario usuario",
                "10/10/2004",
                "imagem"
        );

        AtualizacaoUsuarioDto atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "email@email",
                "Usuario",
                "10/10/2004",
                "imagem"
        );

        Usuario usuario = new Usuario(cadastroUsuarioDto);

        usuario.atualizarUsuario(atualizacaoUsuario);

        ListagemUsuarioDto listagemUsuario = new ListagemUsuarioDto(usuario);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonAtualizacao = om.writeValueAsString(atualizacaoUsuario);
        String jsonListagem = om.writeValueAsString(listagemUsuario);

        when(usuarioService.atualizarUsuario("email@email.com", atualizacaoUsuario)).thenReturn(listagemUsuario);

        mvc.perform(patch("/usuario/{email}", "email@email.com")
                .content(jsonAtualizacao)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().json(jsonListagem))
                .andExpect(status().isOk());

    }

    @Test
    void deveriaRetornarCodigo400ComAlgumDadoInvalido() throws Exception {
        AtualizacaoUsuarioDto atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "emailemail.com",
                "Usuario 24",
                "967fj",
                "imagem"
        );

        List<TratamentoErroBeanValidation> listaErros = new ArrayList<>();
        listaErros.add(new TratamentoErroBeanValidation("email", "O email deve estar no formato correto! email@email.com"));
        listaErros.add(new TratamentoErroBeanValidation("nomeCompleto", "Nome deve conter somente letras!"));
        listaErros.add(new TratamentoErroBeanValidation("dataNascimento", "Data deve seguir o formato correto! DD/MM/YYYY"));

        ObjectMapper om = new ObjectMapper();
        String jsonErros = om.writeValueAsString(listaErros);
        String jsonAtualizacao = om.writeValueAsString(atualizacaoUsuario);

        mvc.perform(patch("/usuario/{email}", "email@email.com")
                .content(jsonAtualizacao)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().json(jsonErros))
                .andExpect(status().isBadRequest());
    }

    public record TratamentoErroBeanValidation(
            String campo,
            String mensagem
    ){
    }
}