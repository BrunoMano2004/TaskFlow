package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.UrlNotValidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidarCadastroSeLinkEValidoTest {

    @Autowired
    private ValidarCadastroSeLinkEValido validador;

    @Test
    void deveriaCairNaExcecaoComLinkSemImagem(){
        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2004",
                "https://www.google.com.br/"
        );

        assertThrows(UrlNotValidException.class, () -> {
            validador.validar(cadastroUsuario);
        });
    }

}