package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.usuario.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import TaskFlow_api.TaskFlow_api.validacoes.usuario.ValidacoesUsuario;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Captor
    private ArgumentCaptor<Usuario> usuarioCaptor;

    @Spy
    private List<ValidacoesUsuario<CadastroUsuarioDto>> validacoesUsuariosCadastro = new ArrayList<>();

    @Spy
    private List<ValidacoesUsuario<AtualizacaoUsuarioDto>> validacoesUsuariosAtualizacao = new ArrayList<>();

    @Autowired
    private Usuario usuario;

    @Autowired
    private CadastroUsuarioDto cadastroUsuario;

    @Autowired
    private AtualizacaoUsuarioDto atualizacaoUsuario;

    @Autowired
    private ListagemUsuarioDto listagemUsuario;

    @BeforeEach
    void setUp(){
        cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario usuario",
                "10/10/2010",
                "imagem");

        usuario = new Usuario(cadastroUsuario);

        atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "email@gmail.com",
                "Usuario",
                "10/10/2004",
                "imagem"
        );

        listagemUsuario = new ListagemUsuarioDto(usuario);
    }

    @Test
    void deveriaRetornarSomenteUmUsuarioPeloEmail(){

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        ListagemUsuarioDto listagemUsuarioDto = usuarioService.retornarUsuarioPeloEmail("email@email.com");

        assertEquals(listagemUsuarioDto, listagemUsuario);
    }

    @Test
    void naoDeveriaLancarExcecaoAoBuscarUsuarioPeloEmail(){

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> {
            usuarioService.retornarUsuarioPeloEmail("email@email.com");
        });
    }

    @Test
    void deveriaLancarExcecaoAoNaoEncontrarUsuario(){
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.retornarUsuarioPeloEmail("email@email.com");
        });
    }

    @Test
    void deveriaChamarMetodoSalvarAoCadastrarUsuario() throws MessagingException, IOException {

        usuarioService.cadastrarUsuario(cadastroUsuario);

        then(usuarioRepository).should().save(usuarioCaptor.capture());
        Usuario usuario1 = usuarioCaptor.getValue();
        assertEquals(usuario, usuario1);
    }

    @Test
    void deveriaAlterarDadosDoUsuario(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        usuario.atualizarUsuario(atualizacaoUsuario);
        ListagemUsuarioDto listagemUsuarioAtualizado = new ListagemUsuarioDto(usuario);

        ListagemUsuarioDto listagemUsuario = usuarioService.atualizarUsuario(1L, atualizacaoUsuario);

        assertEquals(listagemUsuarioAtualizado, listagemUsuario);
    }

    @Test
    void deveriaAcessarOMetodoExcluirDoRepositoryAoExcluirUsuario(){

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuario(1L);

        then(usuarioRepository).should().delete(usuarioCaptor.capture());
        assertEquals(usuario, usuarioCaptor.getValue());
    }

    @Test
    void deveriaRetornarCairNaExcecaoComUsuarioNaoEncontrado(){

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.deletarUsuario(1L);
        });

        assertEquals(ex.getMessage(), "Usuário não encontrado");
    }

    @Test
    void naoDeveriaLancarExcecaoAoBuscarUsuarioPeloId(){

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> {
            usuarioService.retornarUsuarioPeloId(1L);
        });
    }

    @Test
    void deveriaLancarExcecaoAoNaoEncontrarUsuarioPeloId(){
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.retornarUsuarioPeloId(1L);
        });
    }
}