package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.EtiquetaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtiquetaServiceTest {

    @InjectMocks
    private EtiquetaService etiquetaService;

    @Mock
    private EtiquetaRepository etiquetaRepository;

    @Test
    void deveriaRetornarEtiquetaComNomeEEmailCorretos(){
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

        ListagemEtiquetaDto listagemEtiquetaEsperado = new ListagemEtiquetaDto(etiqueta);

        when(etiquetaRepository.retornarEtiquetaComNomeEEmailUsuario("Trabalho", "email@email.com"))
                .thenReturn(Optional.of(etiqueta));

        ListagemEtiquetaDto listagemEtiquetaResultado = etiquetaService.buscarEtiqueta("Trabalho", "email@email.com");

        assertEquals(listagemEtiquetaEsperado, listagemEtiquetaResultado);
    }

    @Test
    void deveriaCairNaExcecaoComDadosNaoEncontrados(){
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

        when(etiquetaRepository.retornarEtiquetaComNomeEEmailUsuario("Casa", "emailemail@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            etiquetaService.buscarEtiqueta("Casa", "emailemail@email.com");
        });
    }
}