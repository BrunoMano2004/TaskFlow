package TaskFlow_api.TaskFlow_api.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(

        @NotBlank(message = "Email não pode estar vazio!")
        @Email(message = "Deve estar no formato de email! email@email.com")
        String username,

        @NotBlank(message = "Senha não pode estar vazia!")
        String password
) {
}
