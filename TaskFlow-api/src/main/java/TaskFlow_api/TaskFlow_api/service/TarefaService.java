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
        Tarefa tarefa = tarefaRepository
                .findById(idTarefa)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        verificarDataDeExpiracaoTarefa(tarefa);

        return new ListagemTarefaDto(tarefa);
    }

    public List<ListagemTarefaDto> buscarTodasTarefasDeUmUsuario(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        List<Tarefa> tarefaList = tarefaRepository.retornarListaDeTarefasPorUsuario(usuario);

        tarefaList.forEach(this::verificarDataDeExpiracaoTarefa);

        List<ListagemTarefaDto> listagemTarefas = tarefaList.stream()
                .map(ListagemTarefaDto::new)
                .toList();

        return listagemTarefas;
    }


    public List<ListagemTarefaDto> buscarTodasTarefasPorEtiqueta(String nomeEtiqueta, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        List<Etiqueta> etiquetasEncontradas = etiquetaRepository
                .retornarEtiquetaComNomeEUsuario(nomeEtiqueta, usuario);

        List<Tarefa> tarefas =
                etiquetasEncontradas.stream()
                        .flatMap(e -> tarefaRepository.retornarListaDeTarefasPorEtiqueta(e).stream())
                        .toList();

        tarefas.forEach(this::verificarDataDeExpiracaoTarefa);

        List<ListagemTarefaDto> listagemTarefas = tarefas.stream()
                .map(ListagemTarefaDto::new)
                .toList();

        return listagemTarefas;
    }

    public void criarTarefa(CadastroTarefaDto cadastroTarefa) {

        Usuario usuario = usuarioRepository.findById(cadastroTarefa.idUsuario())
                        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        Etiqueta etiqueta = etiquetaRepository.findById(cadastroTarefa.idEtiqueta())
                        .orElseThrow(() -> new ResourceNotFoundException("Etiqueta não encontrada!"));

        validadores.forEach(v -> v.validar(cadastroTarefa));

        tarefaRepository.save(new Tarefa(cadastroTarefa, etiqueta, usuario));
    }

    private void verificarDataDeExpiracaoTarefa(Tarefa tarefa){
        LocalDateTime dataAtual = LocalDateTime.now();

        if (tarefa.getDataExpiracao().isEqual(dataAtual) || tarefa.getDataExpiracao().isBefore(dataAtual)){
            tarefa.setStatus(Status.EXPIRADA);
        }
    }

    public void concluirTarefa(Long idTarefa) {

        Tarefa tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        if (tarefa.getStatus() == Status.ABERTA){
            tarefa.setStatus(Status.FECHADA);
            tarefa.setDataFinalizacao(LocalDateTime.now());
        } else {
            throw new TaskAlreadyMadeException("Tarefa já concluída!");
        }
    }
}
