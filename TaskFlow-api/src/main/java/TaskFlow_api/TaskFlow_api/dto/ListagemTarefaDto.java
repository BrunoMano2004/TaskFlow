package TaskFlow_api.TaskFlow_api.dto;

import TaskFlow_api.TaskFlow_api.model.Status;
import TaskFlow_api.TaskFlow_api.model.Tarefa;

import java.util.List;

public record ListagemTarefaDto(
        String nome,
        String descricao,
        String status,
        ListagemEtiquetaDto etiqueta,
        ListagemUsuarioDto usuario
) {
    public ListagemTarefaDto(Tarefa tarefa){
        this(tarefa.getNome(), tarefa.getDescricao(), tarefa.getStatus().toString(), new ListagemEtiquetaDto(tarefa.getEtiqueta()), new ListagemUsuarioDto(tarefa.getUsuario()));
    }
}
