package TaskFlow_api.TaskFlow_api.dto.tarefa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadastroTarefaDto(

        @NotBlank(message = "Nome não pode estar vazio!")
        String nome,

        String descricao,

        @NotBlank(message = "Data de expiração não pode estar vazia!")
        @Pattern(
                regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$",
                message = "Data e hora inválidas! Formato correto: dd/MM/yyyy HH:mm."
        )
        String dataExpiracao,

        @NotNull
        Long idEtiqueta,

        @NotNull
        Long idUsuario
) {
}
