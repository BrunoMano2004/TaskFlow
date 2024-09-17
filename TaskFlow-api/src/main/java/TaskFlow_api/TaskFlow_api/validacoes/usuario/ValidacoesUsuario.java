package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import org.springframework.stereotype.Component;

@Component
public interface ValidacoesUsuario<T> {

    public void validar(T o);
}
