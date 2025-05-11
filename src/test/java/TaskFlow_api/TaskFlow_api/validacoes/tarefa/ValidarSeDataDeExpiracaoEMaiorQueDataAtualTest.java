package TaskFlow_api.TaskFlow_api.validacoes.tarefa;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.TarefaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidarSeDataDeExpiracaoEMaiorQueDataAtualTest {

    @InjectMocks
    private ValidarSeDataDeExpiracaoEMaiorQueDataAtual validacao;

    @Test
    void deveriaCairNaExcecaoComDataDeExpiracaoMenorDoQueDataAtual(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        CadastroTarefaDto cadastroTarefa = new CadastroTarefaDto(
                "Criar curriculo",
                "Criar curriculo",
                LocalDateTime.now().minusDays(2L).format(dtf),
                1L
        );

        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> {
            validacao.validar(cadastroTarefa);
        });

        assertEquals("Data de expiração não deve ser anterior a data atual!", ex.getMessage());
    }

    @Test
    void naoDeveriaCairNaExcecaoComDataDeExpiracaoMaiorDoQueDataAtual(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        CadastroTarefaDto cadastroTarefa = new CadastroTarefaDto(
                "Criar curriculo",
                "Criar curriculo",
                LocalDateTime.now().plusDays(2L).format(dtf),
                1L
        );

        assertDoesNotThrow(() -> {
            validacao.validar(cadastroTarefa);
        });
    }
}