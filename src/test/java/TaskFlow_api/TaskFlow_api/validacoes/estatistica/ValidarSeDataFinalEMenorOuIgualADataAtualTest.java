package TaskFlow_api.TaskFlow_api.validacoes.estatistica;

import TaskFlow_api.TaskFlow_api.dto.estatistica.DadosPeriodoDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidarSeDataFinalEMenorOuIgualADataAtualTest {

    @InjectMocks
    private ValidarSeDataFinalEMenorOuIgualADataAtual validador;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    void naoDeveriaCairNaExcecaoComDataMenorOuIgualDoQueDataAtual(){

        DadosPeriodoDto dadosPeriodo = new DadosPeriodoDto(
                "01/01/2020",
                LocalDate.now().minusDays(1L).format(dtf));

        assertDoesNotThrow(() -> {
            validador.validar(dadosPeriodo);
        });
    }

    @Test
    void deveriaCairNaExcecaoComDataMaiorDoQueDataAtual(){

        DadosPeriodoDto dadosPeriodo = new DadosPeriodoDto(
                "01/01/2020",
                LocalDate.now().plusDays(1L).format(dtf));

        assertThrows(InvalidDataException.class, () -> {
            validador.validar(dadosPeriodo);
        });
    }
}