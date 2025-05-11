package TaskFlow_api.TaskFlow_api.dto.tarefa;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.ListagemUsuarioDto;
import TaskFlow_api.TaskFlow_api.model.Tarefa;

public record ListagemTarefaDto(
        Long id,
        String nome,
        String nomeEtiqueta,
        String corEtiqueta,
        String descricao,
        String status,
        String dataExpiracao,
        String dataFinalizacao
) {
    public ListagemTarefaDto(Tarefa tarefa){
        this(tarefa.getId(),
                tarefa.getNome(),
                tarefa.getEtiqueta().getNome(),
                tarefa.getEtiqueta().getCor(),
                tarefa.getDescricao(),
                tarefa.getStatus().toString(),
                tarefa.getDataExpiracao().toString(),
                tarefa.getDataFinalizacao() != null ? tarefa.getDataFinalizacao().toString() : null);
    }
}
