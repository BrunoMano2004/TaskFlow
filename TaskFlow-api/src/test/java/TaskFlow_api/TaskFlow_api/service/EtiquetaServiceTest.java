package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.EtiquetaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EtiquetaServiceTest {

    @InjectMocks
    private EtiquetaService etiquetaService;

    @Mock
    private EtiquetaRepository etiquetaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroEtiquetaDto cadastroEtiqueta;

    @Autowired
    private CadastroEtiquetaDto cadastroEtiqueta1;

    @Autowired
    private CadastroUsuarioDto cadastroUsuario;

    @Autowired
    private Usuario usuario;

    @Autowired
    private Etiqueta etiqueta;

    @Autowired
    private Etiqueta etiqueta1;

    @Captor
    private ArgumentCaptor<Etiqueta> etiquetaCaptor;

    @BeforeEach
    void setUp(){
        cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        usuario = new Usuario(cadastroUsuario);

        cadastroEtiqueta = new CadastroEtiquetaDto(
                "Trabalho",
                "#950345",
                1L
        );

        etiqueta = new Etiqueta(cadastroEtiqueta, usuario);

        cadastroEtiqueta1 = new CadastroEtiquetaDto(
                "Trabalho",
                "#950345",
                1L
        );

        etiqueta1 = new Etiqueta(cadastroEtiqueta1, usuario);
    }

    @Test
    void deveriaRetornarEtiquetaComNomeEEmailCorretos(){

        List<Etiqueta> etiquetas = Arrays.asList(etiqueta, etiqueta1);
        List<ListagemEtiquetaDto> listagemEtiquetas = etiquetas.stream()
                .map(ListagemEtiquetaDto::new)
                .toList();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        when(etiquetaRepository.retornarEtiquetaComNomeEUsuario("Trabalho", usuario))
                .thenReturn(etiquetas);

        List<ListagemEtiquetaDto> listagemEtiquetaResultado = etiquetaService.buscarEtiqueta("Trabalho", 1L);

        assertEquals(listagemEtiquetas, listagemEtiquetaResultado);
    }

    @Test
    void deveriaRetornarListaDeEtiquetasDeUmUsuario(){

        ListagemEtiquetaDto listagemEtiqueta = new ListagemEtiquetaDto(etiqueta);
        ListagemEtiquetaDto listagemEtiqueta1 = new ListagemEtiquetaDto(etiqueta1);

        List<Etiqueta> etiquetas = Arrays.asList(etiqueta, etiqueta1);
        List<ListagemEtiquetaDto>  listagemEtiquetaDtos = Arrays.asList(listagemEtiqueta, listagemEtiqueta1);

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));
        when(etiquetaRepository.retornarEtiquetaPorUsuario(usuario.getId())).thenReturn(etiquetas);

        List<ListagemEtiquetaDto> listaResul = etiquetaService
                .retornarTodasEtiquetasPorUsuario("email@email.com");

        assertEquals(listagemEtiquetaDtos, listaResul);
    }

    @Test
    void deveriaCairNaExcecaoComUsuarioNaoEncontrado(){

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            etiquetaService.retornarTodasEtiquetasPorUsuario("emailemail@emai.com");
        });

        assertEquals(ex.getMessage(), "Usuário não encontrado!");
    }

    @Test
    void deveriaCairNaExcecaoComUsuarioNaoEncontradoAoCadastrar(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            etiquetaService.cadastrarEtiqueta(cadastroEtiqueta);
        });

        assertEquals(ex.getMessage(), "Usuário não encontrado!");
    }

    @Test
    void deveriaCadastrarEtiqueta(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        etiquetaService.cadastrarEtiqueta(cadastroEtiqueta);

        then(etiquetaRepository).should().save(etiquetaCaptor.capture());
        Etiqueta etiquetaCapturada = etiquetaCaptor.getValue();

        assertEquals(etiqueta, etiquetaCapturada);
    }

    @Test
    void deveriaCairNaExcecaoComEtiquetaNaoEncontrada(){

        when(etiquetaRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            etiquetaService.excluirEtiqueta(1L);
        });

        assertEquals(ex.getMessage(), "Etiqueta não encontrada!");
    }

    @Test
    void deveriaDeletarAEtiqueta(){

        when(etiquetaRepository.findById(1L)).thenReturn(Optional.of(etiqueta));

        etiquetaService.excluirEtiqueta(1L);

        then(etiquetaRepository).should().delete(etiquetaCaptor.capture());
        assertEquals(etiqueta, etiquetaCaptor.getValue());
    }
}