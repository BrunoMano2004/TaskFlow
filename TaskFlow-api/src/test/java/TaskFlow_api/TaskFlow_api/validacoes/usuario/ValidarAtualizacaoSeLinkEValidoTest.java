package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.usuario.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.UrlNotValidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidarAtualizacaoSeLinkEValidoTest {

    @InjectMocks
    private ValidarAtualizacaoSeLinkEValido validador;

    @Test
    void deveriaCairNaExcecaoComLinkSemImagem(){
        AtualizacaoUsuarioDto atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2004",
                "https://www.google.com.br/"
        );

        assertThrows(UrlNotValidException.class, () -> {
            validador.validar(atualizacaoUsuario);
        });
    }

    @Test
    void deveriaCairNaExcecaoComLinkInvalido(){
        AtualizacaoUsuarioDto atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2004",
                "fefeg"
        );

        assertThrows(UrlNotValidException.class, () -> {
            validador.validar(atualizacaoUsuario);
        });
    }

    @Test
    void naoDeveriaCairNaExcecaoComLinkValido(){
        AtualizacaoUsuarioDto atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2004",
                "https://i.pinimg.com/236x/5f/33/c7/5f33c741560bb71ebedb831267603c1b.jpg"
        );

        assertDoesNotThrow(() -> {
            validador.validar(atualizacaoUsuario);
        });
    }
}