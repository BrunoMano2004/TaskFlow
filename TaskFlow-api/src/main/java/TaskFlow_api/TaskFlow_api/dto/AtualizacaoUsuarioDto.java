package TaskFlow_api.TaskFlow_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AtualizacaoUsuarioDto(

        @Email(message = "O email deve estar no formato correto! email@email.com")
        String email,

        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Nome deve conter somente letras!")
        String nomeCompleto,

        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$", message = "Data deve seguir o formato correto! DD/MM/YYYY")
        String dataNascimento,

        String imgPerfil
) {
}
