package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.service.EtiquetaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EtiquetaController.class)
class EtiquetaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EtiquetaService etiquetaService;

    @MockBean
    private Etiqueta etiqueta;

    @Autowired
    private Etiqueta etiqueta1;

    @MockBean
    private CadastroEtiquetaDto cadastroEtiqueta;

    @BeforeEach
    void setUp(){
        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        Usuario usuario = new Usuario(cadastroUsuario);

        cadastroEtiqueta = new CadastroEtiquetaDto(
                "Trabalho",
                "#950345",
                1L
        );

        etiqueta = new Etiqueta(cadastroEtiqueta, usuario);

        CadastroEtiquetaDto cadastroEtiqueta1 = new CadastroEtiquetaDto(
                "Casa",
                "#513684",
                1L
        );

        etiqueta1 = new Etiqueta(cadastroEtiqueta1, usuario);
    }

    @Test
    void deveriaRetornarEtiquetaQuandoEncontrada() throws Exception {

        ListagemEtiquetaDto listagemEtiqueta = new ListagemEtiquetaDto(etiqueta);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(listagemEtiqueta);

        when(etiquetaService.buscarEtiqueta("Trabalho", "email@email.com"))
                .thenReturn(listagemEtiqueta);

        mvc.perform(get("/etiqueta/{nomeEtiqueta}/{emailUsuario}", "Trabalho", "email@email.com"))
                .andExpect(content().json(json))
                .andExpect(status().isOk());
    }

    @Test
    void deveriaCairNaExcecaoComUsuarioOuEtiquetaNaoEncontrados() throws Exception {
        when(etiquetaService.buscarEtiqueta("Trabalho", "email@email.com"))
                .thenThrow(ResourceNotFoundException.class);

        mvc.perform(get("/etiqueta/{nomeEtiqueta}/{emailUsuario}", "Trabalho", "email@email.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveriaRetornarListaDeEtiquetasDeUmUsuario() throws Exception {
        List<Etiqueta> etiquetas = Arrays.asList(etiqueta, etiqueta1);
        List<ListagemEtiquetaDto> listagemEtiqueta = etiquetas.stream()
                .map(ListagemEtiquetaDto::new)
                .toList();

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(listagemEtiqueta);

        when(etiquetaService.retornarTodasEtiquetasPorUsuario(anyString())).thenReturn(listagemEtiqueta);

        mvc.perform(get("/etiqueta/{emailUsuario}", "email@email.com"))
                .andExpect(content().json(json))
                .andExpect(status().isOk());
    }

    @Test
    void deveriaRetornarErro404ComEtiquetaNaoEncontrado() throws Exception {
        when(etiquetaService.retornarTodasEtiquetasPorUsuario(anyString()))
                .thenThrow(new ResourceNotFoundException("Usuário não encontrado!"));

        mvc.perform(get("/etiqueta/{emailUsuario}", "email@email"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado!"));
    }

    @Test
    void deveriaRetornar201ComEtiquetaCriadoComSucesso() throws Exception{

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(cadastroEtiqueta);

        mvc.perform(post("/etiqueta")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andExpect(content().string("Usuario criado com sucesso!"));
    }

    @Test
    void deveriaRetornar400ComDadosDeCadastroDeEtiquetaErrados() throws Exception{

        CadastroEtiquetaDto cadastroErrado = new CadastroEtiquetaDto(
                "TarefasDeCasaEDoTrabalho",
                "#42359415151fdef",
                1L
        );

        TratamentoErroBeanValidation erroNome =
                new TratamentoErroBeanValidation("nome", "Nome deve conter no máximo 20 caracteres!");

        TratamentoErroBeanValidation erroCor =
                new TratamentoErroBeanValidation("cor", "A cor deve seguir o padrão de hashcode!");

        List<TratamentoErroBeanValidation> listaErros = Arrays.asList(erroNome, erroCor);

        ObjectMapper om = new ObjectMapper();
        String jsonCadastro = om.writeValueAsString(cadastroErrado);
        String jsonErros = om.writeValueAsString(listaErros);

        mvc.perform(post("/etiqueta")
                .content(jsonCadastro)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErros));
    }

    @Test
    void deveriaRetornarErro404ComUsuarioInexistenteNoCadastroDaEtiqueta() throws Exception{

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(cadastroEtiqueta);

        doThrow(new ResourceNotFoundException("Usuário não encontrado!"))
                .when(etiquetaService).cadastrarEtiqueta(cadastroEtiqueta);

        mvc.perform(post("/etiqueta")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado!"));
    }

    @Test
    void deveriaRetornarErro404ComEtiquetaInexistenteNaExclusaoDaEtiqueta() throws Exception{

        doThrow(new ResourceNotFoundException("Usuário não encontrado!"))
                .when(etiquetaService).excluirEtiqueta(etiqueta.getId());

        mvc.perform(delete("/etiqueta/{idEtiqueta}")
                ).andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado!"));
    }

    public record TratamentoErroBeanValidation(
            String campo,
            String mensagem
    ){
    }
}