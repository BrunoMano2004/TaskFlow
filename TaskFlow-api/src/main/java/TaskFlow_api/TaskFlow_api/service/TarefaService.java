package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.ListagemTarefaDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.exception.TaskAlreadyMadeException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Status;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.EtiquetaRepository;
import TaskFlow_api.TaskFlow_api.repository.TarefaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import TaskFlow_api.TaskFlow_api.validacoes.tarefa.ValidacoesTarefa;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TarefaService {

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Autowired
    private List<ValidacoesTarefa> validadores;

    public ListagemTarefaDto buscarTarefaPeloId(Long idTarefa) {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        Tarefa tarefa = tarefaRepository
                .retornarTarefaDeUmUsuarioPeloId(idTarefa, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        return new ListagemTarefaDto(tarefa);
    }

    public List<ListagemTarefaDto> buscarTodasTarefasDeUmUsuario() {

        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        List<Tarefa> tarefaList = tarefaRepository.retornarListaDeTarefasPorUsuario(usuario);

        List<ListagemTarefaDto> listagemTarefas = tarefaList.stream()
                .map(ListagemTarefaDto::new)
                .toList();

        return listagemTarefas;
    }


    public List<ListagemTarefaDto> buscarTodasTarefasPorEtiqueta(String nomeEtiqueta) {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        List<Etiqueta> etiquetasEncontradas = etiquetaRepository
                .retornarEtiquetaComNomeEUsuario(nomeEtiqueta, usuario);

        List<Tarefa> tarefas =
                etiquetasEncontradas.stream()
                        .flatMap(e -> tarefaRepository.retornarListaDeTarefasPorEtiqueta(e).stream())
                        .toList();

        List<ListagemTarefaDto> listagemTarefas = tarefas.stream()
                .map(ListagemTarefaDto::new)
                .toList();

        return listagemTarefas;
    }

    public void criarTarefa(CadastroTarefaDto cadastroTarefa) {

        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        Etiqueta etiqueta = etiquetaRepository.findById(cadastroTarefa.idEtiqueta())
                        .orElseThrow(() -> new ResourceNotFoundException("Etiqueta não encontrada!"));

        validadores.forEach(v -> v.validar(cadastroTarefa));

        tarefaRepository.save(new Tarefa(cadastroTarefa, etiqueta, usuario));
    }

    public void concluirTarefa(Long idTarefa) {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        Tarefa tarefa = tarefaRepository
                .retornarTarefaDeUmUsuarioPeloId(idTarefa, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        if (tarefa.getStatus() == Status.ABERTA){
            tarefa.setStatus(Status.FECHADA);
            tarefa.setDataFinalizacao(LocalDateTime.now());
        } else {
            throw new TaskAlreadyMadeException("Tarefa já concluída!");
        }
    }

    @Scheduled(cron = "0 * * * * ?")
    private void verificarDataDeExpiracaoTarefa(){

        List<Tarefa> tarefas = tarefaRepository.findAll();

        tarefas.forEach(tarefa -> {
            LocalDateTime dataAtual = LocalDateTime.now();

            if ((tarefa.getDataExpiracao().isEqual(dataAtual) || tarefa.getDataExpiracao().isBefore(dataAtual)) && tarefa.getStatus() == Status.ABERTA){
                tarefa.setStatus(Status.EXPIRADA);
                tarefa.setDataFinalizacao(tarefa.getDataExpiracao());
            }
        });
    }
}
