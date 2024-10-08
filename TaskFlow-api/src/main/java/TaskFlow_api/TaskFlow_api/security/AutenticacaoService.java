package TaskFlow_api.TaskFlow_api.security;

import TaskFlow_api.TaskFlow_api.model.Login;
import TaskFlow_api.TaskFlow_api.repository.LoginReposiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private LoginReposiory loginReposiory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginReposiory.findByUsername(username);
    }
}
