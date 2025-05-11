package TaskFlow_api.TaskFlow_api.dto.estatistica;

public record EstatisticaDto(

        String dataInical,
        String dataFinal,
        Long quantidadeTarefasConcluidas,
        Long quantidadeTarefasExpiradas,
        Integer percentualTarefasConcluidas,
        Integer percentualTarefasExpiradas
) {
}
