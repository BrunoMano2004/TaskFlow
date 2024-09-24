package TaskFlow_api.TaskFlow_api.validacoes.tarefa;

import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ValidarSeDataDeExpiracaoEMaiorQueDataAtual implements ValidacoesTarefa{

    @Override
    public void validar(CadastroTarefaDto cadastroTarefa) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime dataExpiracao = LocalDateTime.parse(cadastroTarefa.dataExpiracao(), dtf);
        LocalDateTime dataAtual = LocalDateTime.now();

        if (dataExpiracao.isBefore(dataAtual)){
            throw new InvalidDataException("Data de expiração não deve ser anterior a data atual!");
        }
    }
}
