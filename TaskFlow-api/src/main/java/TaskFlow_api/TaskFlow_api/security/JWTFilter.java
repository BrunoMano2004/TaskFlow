package TaskFlow_api.TaskFlow_api.security;

import TaskFlow_api.TaskFlow_api.exception.InvalidJwtTokenException;
import TaskFlow_api.TaskFlow_api.model.Login;
import TaskFlow_api.TaskFlow_api.repository.LoginReposiory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LoginReposiory loginReposiory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);

        if (token != null){
            String username = jwtService.getSubject(token);

            var login = loginReposiory.findByUsername(username);

            var authentication = new UsernamePasswordAuthenticationToken(login, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        doFilter(request, response, filterChain);
    }

    private String recuperarToken(HttpServletRequest request){
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
