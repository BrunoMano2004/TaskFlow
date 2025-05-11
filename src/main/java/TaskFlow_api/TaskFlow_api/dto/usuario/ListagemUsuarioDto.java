package TaskFlow_api.TaskFlow_api.dto.usuario;

import TaskFlow_api.TaskFlow_api.model.Usuario;

import java.time.LocalDate;

public record ListagemUsuarioDto(
        Long id,
        String email,
        String nomeCompleto,
        LocalDate dataNascimento,
        String imgPerfil
) {
    public ListagemUsuarioDto(Usuario usuario){
        this(usuario.getId(), usuario.getEmail(), usuario.getNomeCompleto(), usuario.getDataNascimento(), usuario.getImgPerfil());
    }
}
