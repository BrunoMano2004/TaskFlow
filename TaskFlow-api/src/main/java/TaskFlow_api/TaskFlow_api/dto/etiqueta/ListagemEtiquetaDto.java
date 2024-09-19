package TaskFlow_api.TaskFlow_api.dto.etiqueta;

import TaskFlow_api.TaskFlow_api.model.Etiqueta;

public record ListagemEtiquetaDto(
    String nome,
    String cor,
    Long usuarioId
) {
    public ListagemEtiquetaDto(Etiqueta etiqueta){
        this(etiqueta.getNome(), etiqueta.getCor(), etiqueta.getUsuario().getId());
    }
}
