package TaskFlow_api.TaskFlow_api.dto.tarefa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AtualizacaoTarefaDto(

        String nome,

        String descricao,

        Long idEtiqueta
) {
}
