package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.DataAlreadyExistException;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidarCadastroEmailJaCadastradoTest {

    @InjectMocks
    private ValidarCadastroEmailJaCadastrado validarCadastroEmailJaCadastrado;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioDto cadastroUsuario;

    @Autowired
    private Usuario usuario;

    @BeforeEach
    void setUp(){

        cadastroUsuario = new CadastroUsuarioDto(
                "emailemail@email.com",
                "senha123",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        usuario = new Usuario(cadastroUsuario);
    }

    @Test
    void deveriaCairNaExcecaoDeDadosErradosComEmailJaExistente(){

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        DataAlreadyExistException ex = assertThrows(DataAlreadyExistException.class, () -> {
            validarCadastroEmailJaCadastrado.validar(cadastroUsuario);
        });

        assertEquals("Email já cadastrado!", ex.getMessage());
    }

    @Test
    void naoDeveriaCairNaExcecaoDeDadosErradosComEmailNaoCadastrado(){

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            validarCadastroEmailJaCadastrado.validar(cadastroUsuario);
        });
    }
}