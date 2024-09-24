package TaskFlow_api.TaskFlow_api.repository;

import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa t WHERE t.usuario = :usuario")
    List<Tarefa> retornarListaDeTarefasPorUsuario(Usuario usuario);

    @Query("SELECT t FROM Tarefa t WHERE t.etiqueta = :etiqueta")
    List<Tarefa> retornarListaDeTarefasPorEtiqueta(Etiqueta etiqueta);
}
