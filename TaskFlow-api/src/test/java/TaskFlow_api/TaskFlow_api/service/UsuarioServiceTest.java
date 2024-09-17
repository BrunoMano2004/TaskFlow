package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import TaskFlow_api.TaskFlow_api.validacoes.usuario.ValidacoesUsuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void deveriaRetornarUmUsuario(){
        Usuario usuario = new Usuario("email@email.com", "Usuario", LocalDate.now(), "imagem");
        ListagemUsuarioDto usuarioDto = new ListagemUsuarioDto(usuario);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        ListagemUsuarioDto listagemUsuarioDto = usuarioService.retornarUsuario("email@email.com");

        assertEquals(listagemUsuarioDto, usuarioDto);
    }

    @Test
    void naoDeveriaLancarExcecao(){
        Usuario usuario = new Usuario("email@email.com", "Usuario", LocalDate.now(), "imagem");
        ListagemUsuarioDto usuarioDto = new ListagemUsuarioDto(usuario);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> {
            usuarioService.retornarUsuario("email@email.com");
        });
    }

    @Test
    void deveriaLancarExcecao(){
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.retornarUsuario("email@email.com");
        });
    }

    @Test
    void deveriaChamarMetodoSalvar(){
        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario usuario",
                "10/10/2010",
                "imagem");

        Usuario usuario = new Usuario(cadastroUsuario);

        usuarioService.cadastrarUsuario(cadastroUsuario);

        then(usuarioRepository).should().save(usuarioCaptor.capture());
        Usuario usuario1 = usuarioCaptor.getValue();
        assertEquals(usuario, usuario1);
    }

    @Test
    void deveriaAlterarDadosDoUsuario(){
        AtualizacaoUsuarioDto atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "email@gmail.com",
                "Usuario",
                "10/10/2004",
                "imagem"
        );

        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        Usuario usuario = new Usuario(cadastroUsuario);

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        usuario.atualizarUsuario(atualizacaoUsuario);
        ListagemUsuarioDto listagemUsuarioAtualizado = new ListagemUsuarioDto(usuario);

        ListagemUsuarioDto listagemUsuario = usuarioService.atualizarUsuario("email@email.com", atualizacaoUsuario);

        assertEquals(listagemUsuarioAtualizado, listagemUsuario);
    }
}