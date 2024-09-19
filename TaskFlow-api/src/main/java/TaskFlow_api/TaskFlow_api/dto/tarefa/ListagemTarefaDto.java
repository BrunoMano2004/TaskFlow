package TaskFlow_api.TaskFlow_api.dto.tarefa;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.model.Tarefa;

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
