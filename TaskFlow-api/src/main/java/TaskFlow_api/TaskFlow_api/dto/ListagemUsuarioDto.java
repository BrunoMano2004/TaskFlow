package TaskFlow_api.TaskFlow_api.dto;

import TaskFlow_api.TaskFlow_api.model.Usuario;

import java.util.Date;

public record ListagemUsuarioDto(
        String email,
        String nomeCompleto,
        Date dataNascimento,
        String imgPerfil
) {
    public ListagemUsuarioDto(Usuario usuario){
        this(usuario.getEmail(), usuario.getNomeCompleto(), usuario.getDataNascimento(), usuario.getImgPerfil());
    }
}
