package TaskFlow_api.TaskFlow_api.repository;

import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {

    @Query("SELECT e FROM Etiqueta e JOIN e.usuario u WHERE e.nome LIKE CONCAT('%', :nomeEtiqueta, '%') AND u.email = :emailUsuario\n")
    Optional<Etiqueta> retornarEtiquetaComNomeEEmailUsuario(String nomeEtiqueta, String emailUsuario);
}
