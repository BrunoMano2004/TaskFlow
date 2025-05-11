package TaskFlow_api.TaskFlow_api.validacoes.estatistica;

import TaskFlow_api.TaskFlow_api.dto.estatistica.DadosPeriodoDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ValidarSeDataFinalEMenorOuIgualADataAtual implements ValidacoesEstatistica{

    @Override
    public void validar(DadosPeriodoDto dadosPeriodo) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataFinal = LocalDate.parse(dadosPeriodo.dataFinal(), dtf);

        if (dataFinal.isAfter(LocalDate.now())){
            throw new InvalidDataException("Data final não pode ser maior do que a data atual!");
        }
    }
}
