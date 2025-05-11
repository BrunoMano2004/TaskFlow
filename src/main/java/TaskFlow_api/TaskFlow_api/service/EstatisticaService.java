package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.estatistica.DadosPeriodoDto;
import TaskFlow_api.TaskFlow_api.dto.estatistica.EstatisticaDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.TarefaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import TaskFlow_api.TaskFlow_api.validacoes.estatistica.ValidacoesEstatistica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class EstatisticaService {

    @Autowired
    private List<ValidacoesEstatistica> validacoes;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    private LocalDateTime dataInicialParse;

    private LocalDateTime dataFinalParse;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public EstatisticaDto gerarEstatisticasPorPeriodo(DadosPeriodoDto dadosPeriodo) {

        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        try {
            dataInicialParse = LocalDate.parse(dadosPeriodo.dataInicio(), dtf).atStartOfDay();
            dataFinalParse = LocalDate.parse(dadosPeriodo.dataFinal(), dtf).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new InvalidDataException("Data inválida ou digitada de forma inválida!");
        }

        validacoes.forEach(v -> v.validar(dadosPeriodo));

        Long numTarefasConcluidas = tarefaRepository
                .retornaNumeroDeTarefasConluidasNoPeriodo(usuario, dataInicialParse, dataFinalParse);
        Long numTarefasExpiradas = tarefaRepository
                .retornaNumeroDeTarefasExpiradasNoPeriodo(usuario, dataInicialParse, dataFinalParse);
        Long numTarefasNoPeriodo = tarefaRepository
                .retornaNumeroDeTarefasNoPeriodo(usuario, dataInicialParse, dataFinalParse);

        Integer porcentagemTarefasConcluidas = calcularPorcentagem(numTarefasNoPeriodo, numTarefasConcluidas);
        Integer porcentagemTarefasExpiradas = calcularPorcentagem(numTarefasNoPeriodo, numTarefasExpiradas);

        return new EstatisticaDto(dadosPeriodo.dataInicio(),
                dadosPeriodo.dataFinal(),
                numTarefasConcluidas,
                numTarefasExpiradas,
                porcentagemTarefasConcluidas,
                porcentagemTarefasExpiradas);
    }

    private Integer calcularPorcentagem(Long valorTotal, Long valorParcial) {
        if (valorTotal > 0) {
            return (int) Math.floor((valorParcial * 100.0f) / valorTotal);
        } else {
            throw new ResourceNotFoundException("Nenhuma tarefa encontrada no período");
        }
    }
}
