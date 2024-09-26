package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.estatistica.DadosPeriodoDto;
import TaskFlow_api.TaskFlow_api.dto.estatistica.EstatisticaDto;
import TaskFlow_api.TaskFlow_api.service.EstatisticaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    @Autowired
    private EstatisticaService estatisticaService;

    @Operation(summary = "Retorna algumas estatisticas",
            description = "Retorna algumas estatisticas com base no número de tarefas fechadas e expiradas em um período",
            responses = {
                @ApiResponse(responseCode = "200",
                            description = "Retorna estatísticas com sucesso",
                            content = @Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "404",
                            description = "Sem tarefa finalizada ou expirada no período, ou usuário não encontrado",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/{idUsuario}/periodo")
    public ResponseEntity<EstatisticaDto> estatisticaPorPeriodo(@RequestBody @Valid DadosPeriodoDto dadosPeriodo){
        EstatisticaDto estatisticas = estatisticaService.gerarEstatisticasPorPeriodo(dadosPeriodo);
        return ResponseEntity.ok(estatisticas);
    }
}
