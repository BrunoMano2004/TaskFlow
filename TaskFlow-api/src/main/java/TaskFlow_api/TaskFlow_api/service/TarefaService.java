package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.tarefa.ListagemTarefaDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.TarefaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ListagemTarefaDto buscarTarefaPeloId(Long idTarefa) {
        Tarefa tarefa = tarefaRepository
                .findById(idTarefa)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        return new ListagemTarefaDto(tarefa);
    }

    public List<ListagemTarefaDto> buscarTodasTarefasDeUmUsuario(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        List<Tarefa> tarefaList = tarefaRepository.retornarListaDeTarefasPorUsuario(usuario);

        List<ListagemTarefaDto> listagemTarefas = tarefaList.stream()
                .map(ListagemTarefaDto::new)
                .toList();

        return listagemTarefas;
    }


}
