package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

    public static Login retornarLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Login) authentication.getPrincipal();
    }
}
