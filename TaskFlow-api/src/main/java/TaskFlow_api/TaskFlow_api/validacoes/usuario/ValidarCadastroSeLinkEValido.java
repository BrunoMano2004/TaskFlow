package TaskFlow_api.TaskFlow_api.validacoes.usuario;

import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.UrlNotValidException;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class ValidarCadastroSeLinkEValido implements ValidacoesUsuario<CadastroUsuarioDto>{

    @Override
    public void validar(CadastroUsuarioDto cadastroUsuario) {
        if (cadastroUsuario.imgPerfil() != null){
            if (!isUrlValid(cadastroUsuario.imgPerfil())){
                throw new UrlNotValidException("Esta URL não é válida!");
            }

            try{
                HttpURLConnection connector = (HttpURLConnection) new URL(cadastroUsuario.imgPerfil()).openConnection();
                connector.setRequestMethod("HEAD");
                connector.setConnectTimeout(5000);
                connector.setReadTimeout(5000);
                connector.connect();

                String contentType = connector.getContentType();
                if (!(contentType != null && contentType.startsWith("image/"))){
                    throw new UrlNotValidException("URL nâo é uma iamgem válida!");
                }
            }catch (Exception ex){
                throw new UrlNotValidException("Não foi possível acessar a url!");
            }
        }
    }

    private boolean isUrlValid(String url){
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
