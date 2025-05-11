package TaskFlow_api.TaskFlow_api.validacoes.tarefa;

import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;

public interface ValidacoesTarefa {

    void validar(CadastroTarefaDto cadastroTarefa);
}
