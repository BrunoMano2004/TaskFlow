package TaskFlow_api.TaskFlow_api.dto.etiqueta;

import jakarta.validation.constraints.Pattern;

public record AtualizacaoEtiquetaDto(

        @Pattern(regexp = "^.{20}$", message = "Nome deve conter no máximo 20 caracteres!")
        String nome,

        @Pattern(regexp = "const regex = /^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})$/", message = "A cor deve seguir o padrão de hashcode!")
        String cor
) {
}
