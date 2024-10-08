package TaskFlow_api.TaskFlow_api.repository;

import TaskFlow_api.TaskFlow_api.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginReposiory extends JpaRepository<Login, Long> {

    UserDetails findByUsername(String username);
}
