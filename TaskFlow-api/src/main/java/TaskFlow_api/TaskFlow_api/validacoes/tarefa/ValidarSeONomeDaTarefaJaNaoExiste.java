package TaskFlow_api.TaskFlow_api.validacoes.tarefa;

import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.exception.DataAlreadyExistException;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.TarefaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidarSeONomeDaTarefaJaNaoExiste implements ValidacoesTarefa{

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(CadastroTarefaDto cadastroTarefa) {
        Usuario usuario = usuarioRepository.findById(cadastroTarefa.idUsuario()).get();

        List<Tarefa> tarefas = tarefaRepository.retornarListaDeTarefasPorUsuario(usuario);

        tarefas.forEach(t -> {
            if (t.getNome().equals(cadastroTarefa.nome())){
                throw new DataAlreadyExistException("Nome da tarefa já existente!");
            }
        });
    }
}
