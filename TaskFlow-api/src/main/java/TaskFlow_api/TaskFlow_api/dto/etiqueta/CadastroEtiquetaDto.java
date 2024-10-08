package TaskFlow_api.TaskFlow_api.dto.etiqueta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadastroEtiquetaDto(

        @Pattern(regexp = "^.{0,20}$", message = "Nome deve conter no máximo 20 caracteres!")
        @NotBlank(message = "Nome não pode estar em branco!")
        String nome,

        @Pattern(regexp = "^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})$", message = "A cor deve seguir o padrão de hashcode!")
        @NotBlank(message = "Cor não pode estar em branco!")
        String cor
) {
}
