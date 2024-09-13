package TaskFlow_api.TaskFlow_api.dto;

import TaskFlow_api.TaskFlow_api.model.Etiqueta;

public record ListagemEtiquetaDto(
    String nome,
    String cor
) {
    public ListagemEtiquetaDto(Etiqueta etiqueta){
        this(etiqueta.getNome(), etiqueta.getCor());
    }
}
