package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.usuario.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidarAtualizacaoSeUsuarioEMaiorDeIdadeTest {

    @InjectMocks
    private ValidarAtualizacaoSeUsuarioEMaiorDeIdade validador;

    @Autowired
    private Usuario usuario;

    @BeforeEach
    void setUp(){

        CadastroUsuarioDto cadastroUsuario = new CadastroUsuarioDto(
                "emailemail@email.com",
                "Usuario Usuario",
                "10/10/2000",
                "imagem"
        );

        usuario = new Usuario(cadastroUsuario);
    }

    @Test
    void deveriaCairNaExcecaoDeDadosInvalidosComDataDeNascimentoMaiorDoQueDataAtual(){

        AtualizacaoUsuarioDto atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2027",
                "imagem"
        );

        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> {
            validador.validar(atualizacaoUsuario);
        });

        assertEquals("Data de nascimento não deve ser maior do que a data atual!", ex.getMessage());
    }

    @Test
    void deveriaCairNaExcecaoDeDadosInvalidosComIdadeMenorDoQue12(){

        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataPassado = LocalDate.now().minusYears(11L);
        String data = dataPassado.format(sdf);

        AtualizacaoUsuarioDto atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                data,
                "imagem"
        );

        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> {
            validador.validar(atualizacaoUsuario);
        });

        assertEquals("A idade mínima para utilização é de 12 anos!", ex.getMessage());
    }

    @Test
    void naoDeveriaCairEmNenhumaExcecao(){
        AtualizacaoUsuarioDto atualizacaoUsuario = new AtualizacaoUsuarioDto(
                "email@email.com",
                "Usuario Usuario",
                "10/10/2003",
                "imagem"
        );

        assertDoesNotThrow(() -> {
            validador.validar(atualizacaoUsuario);
        });
    }
}