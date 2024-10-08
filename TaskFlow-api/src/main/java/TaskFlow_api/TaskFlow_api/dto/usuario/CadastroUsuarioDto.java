package TaskFlow_api.TaskFlow_api.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CadastroUsuarioDto(

        @Email(message = "O email deve estar no formato correto! email@email.com")
        @NotBlank(message = "Email não deve estar vazio!")
        String email,

        @NotBlank
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}:\"<>?\\[\\]\\\\;',./`~\\-])(?=.*[0-9])(?=.*[A-Z]).{5,}$",
                message = "A senha deve conter pelo menos um caractere especial, um número, uma letra maiúscula e ter pelo menos 5 caracteres.")
        String senha,

        @NotBlank(message = "Nome não deve estar vazio!")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Nome deve conter somente letras!")
        String nomeCompleto,

        @NotBlank(message = "Data do nascimento não deve estar vazia!")
        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/([0-9]{4})$", message = "Data deve seguir o formato correto! DD/MM/YYYY")
        String dataNascimento,

        String imgPerfil
) {
}
