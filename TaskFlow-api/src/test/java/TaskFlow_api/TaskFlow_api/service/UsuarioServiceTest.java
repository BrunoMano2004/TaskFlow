package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveriaRetornarUmUsuario(){
        Usuario usuario = new Usuario("email@email.com", "Usuario", new Date(), "imagem");
        ListagemUsuarioDto usuarioDto = new ListagemUsuarioDto(usuario);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        ListagemUsuarioDto listagemUsuarioDto = usuarioService.retornarUsuario("email@email.com");

        assertEquals(listagemUsuarioDto, usuarioDto);
    }

    @Test
    public void naoDeveriaLancarExcecao(){
        Usuario usuario = new Usuario("email@email.com", "Usuario", new Date(), "imagem");
        ListagemUsuarioDto usuarioDto = new ListagemUsuarioDto(usuario);
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> {
            usuarioService.retornarUsuario("email@email.com");
        });
    }

    @Test
    public void deveriaLancarExcecao(){
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.retornarUsuario("email@email.com");
        });
    }
}