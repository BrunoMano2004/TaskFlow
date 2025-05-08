package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidarCadastroSeUsuarioEMaiorDeIdadeTest {

    @InjectMocks
    private ValidarCadastroSeUsuarioEMaiorDeIdade validador;

    @Test
    void deveriaCairNaExcecaoDeDadosIncorretosComDataDeNascimentoMaiorDoQueDataAtual(){
        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "emailemail@email.com",
                "senha123",
                "Usuario Usuario",
                "10/10/2050",
                "imagem"
        );

        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> {
            validador.validar(cadastroUsuario);
        });

        assertEquals("Data de nascimento não deve ser maior do que a data atual!", ex.getMessage());
    }

    @Test
    void deveriaCairNaExcecaoDeDadosIncorretosComIdadeMenorDoQue12(){

        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataPassado = LocalDate.now().minusYears(11L);
        String data = dataPassado.format(sdf);

        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "emailemail@email.com",
                "senha123",
                "Usuario Usuario",
                data,
                "imagem"
        );

        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> {
            validador.validar(cadastroUsuario);
        });

        assertEquals("A idade mínima para utilização é de 12 anos!", ex.getMessage());
    }

    @Test
    void naoDeveriaCairNaExcecaoDeDadosIncorretosComDadosCorretos(){
        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "emailemail@email.com",
                "senha123",
                "Usuario Usuario",
                "10/10/2004",
                "imagem"
        );

        assertDoesNotThrow(() -> {
            validador.validar(cadastroUsuario);
        });
    }

}