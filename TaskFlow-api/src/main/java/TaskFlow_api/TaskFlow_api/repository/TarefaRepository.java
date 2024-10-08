package TaskFlow_api.TaskFlow_api.repository;

import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Tarefa;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t FROM Tarefa t WHERE t.id = :idTarefa AND t.usuario = :usuario")
    Optional<Tarefa> retornarTarefaDeUmUsuarioPeloId(Long idTarefa, Usuario usuario);

    @Query("SELECT t FROM Tarefa t WHERE t.usuario = :usuario")
    List<Tarefa> retornarListaDeTarefasPorUsuario(Usuario usuario);

    @Query("SELECT t FROM Tarefa t WHERE t.etiqueta = :etiqueta")
    List<Tarefa> retornarListaDeTarefasPorEtiqueta(Etiqueta etiqueta);

    @Query("SELECT COUNT(t) " +
            "FROM Tarefa t " +
            "WHERE t.usuario = :usuario " +
            "AND t.status = 'FECHADA' " +
            "AND t.dataFinalizacao BETWEEN :dataInicial AND :dataFinal")
    Long retornaNumeroDeTarefasConluidasNoPeriodo(Usuario usuario, LocalDateTime dataInicial, LocalDateTime dataFinal);

    @Query("SELECT COUNT(t) " +
            "FROM Tarefa t " +
            "WHERE t.usuario = :usuario " +
            "AND t.status = 'EXPIRADA' " +
            "AND t.dataFinalizacao BETWEEN :dataInicial AND :dataFinal")
    Long retornaNumeroDeTarefasExpiradasNoPeriodo(Usuario usuario, LocalDateTime dataInicial, LocalDateTime dataFinal);

    @Query("SELECT COUNT(t) " +
            "FROM Tarefa t " +
            "WHERE t.usuario = :usuario " +
            "AND t.dataFinalizacao BETWEEN :dataInicial AND :dataFinal")
    Long retornaNumeroDeTarefasNoPeriodo(Usuario usuario, LocalDateTime dataInicial, LocalDateTime dataFinal);
}
