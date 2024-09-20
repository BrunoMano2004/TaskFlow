package TaskFlow_api.TaskFlow_api.dto.etiqueta;

import TaskFlow_api.TaskFlow_api.model.Etiqueta;

public record ListagemEtiquetaDto(
        Long id,
    String nome,
    String cor,
    Long usuarioId
) {
    public ListagemEtiquetaDto(Etiqueta etiqueta){
        this(etiqueta.getId(), etiqueta.getNome(), etiqueta.getCor(), etiqueta.getUsuario().getId());
    }
}
