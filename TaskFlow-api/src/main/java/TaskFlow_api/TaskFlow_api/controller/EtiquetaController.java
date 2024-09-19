package TaskFlow_api.TaskFlow_api.controller;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.service.EtiquetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/etiqueta")
public class EtiquetaController {

    @Autowired
    private EtiquetaService etiquetaService;

    @Operation(summary = "Retorna etiqueta", description = "Retorna etiqueta única de um usuário pelo nome e pelo email do usuário", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna etiqueta com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                    description = "Etiqueta ou usuario não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{nomeEtiqueta}/{emailUsuario}")
    public ResponseEntity<ListagemEtiquetaDto> bucarEtiquetaPeloNome(@PathVariable String nomeEtiqueta,
                                                                     @PathVariable String emailUsuario){
        ListagemEtiquetaDto listagemEtiqueta = etiquetaService.buscarEtiqueta(nomeEtiqueta, emailUsuario);
        return ResponseEntity.ok(listagemEtiqueta);
    }

    @Operation(summary = "Retorna lista de etiquetas", description = "Retorna a lista de etiquetas que um usuário possui", responses = {
            @ApiResponse(responseCode = "200",
                        description = "Retorna lista de etiquetas, mesmo estando vazia",
                        content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404",
                        description = "Usuário não encontrado",
                        content = @Content(mediaType = "Application/json"))
    })
    @GetMapping("/{emailUsuario}")
    public ResponseEntity<List<ListagemEtiquetaDto>> listarTodasEtiquetasPorUsuario(@PathVariable String emailUsuario){
        List<ListagemEtiquetaDto> listaEtiquetas = etiquetaService.retornarTodasEtiquetasPorUsuario(emailUsuario);
        return ResponseEntity.ok(listaEtiquetas);
    }
}
