package TaskFlow_api.TaskFlow_api.dto.estatistica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosPeriodoDto(

        @NotNull(message = "Id do usuário não pode estar vazio")
        Long idUsuario,

        @NotBlank(message = "Data inicio não pode estar vazia")
        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$", message = "Data deve seguir o formato correto! DD/MM/YYYY")
        String dataInicio,

        @NotBlank(message = "Data fim não pode estar vazia")
        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$", message = "Data deve seguir o formato correto! DD/MM/YYYY")
        String dataFinal
) {
}
