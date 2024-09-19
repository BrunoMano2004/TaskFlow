package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.service.EtiquetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EtiquetaController.class)
class EtiquetaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EtiquetaService etiquetaService;

    @Test
    void deveriaRetornarEtiquetaQuandoEncontrada() throws Exception {

        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        Usuario usuario = new Usuario(cadastroUsuario);

        CadastroEtiquetaDto cadastroEtiqueta = new CadastroEtiquetaDto(
                "Trabalho",
                "#950345",
                1L
        );

        Etiqueta etiqueta = new Etiqueta(cadastroEtiqueta, usuario);

        ListagemEtiquetaDto listagemEtiqueta = new ListagemEtiquetaDto(etiqueta);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(listagemEtiqueta);

        when(etiquetaService.buscarEtiqueta("Trabalho", "email@email.com"))
                .thenReturn(listagemEtiqueta);

        mvc.perform(get("/etiqueta/{nomeEtiqueta}/{emailUsuario}", "Trabalho", "email@email.com"))
                .andExpect(content().json(json))
                .andExpect(status().isOk());
    }
}