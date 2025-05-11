package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.exception.InvalidValidationCodeException;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.CodigoVerificacao;
import TaskFlow_api.TaskFlow_api.model.Login;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.CodigoVerificacaoRepository;
import TaskFlow_api.TaskFlow_api.repository.LoginRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class CodigoVerificacaoService {

    private final String HTML_PATH = "src/main/resources/templates/emailTemplate.html";

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int CODE_LENGTH = 6;

    @Autowired
    private CodigoVerificacaoRepository repository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private EmailService emailService;

    public void gerarCodigo(String username) throws MessagingException, IOException {

        Login login = loginRepository.encontrarPeloUsernameRetornandoLogin(username)
                .orElseThrow(() -> new ResourceNotFoundException("Login não encontrado!"));

        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++){
            int index = random.nextInt(CHARACTERS.length());
            codigo.append(CHARACTERS.charAt(index));
        }

        emailService.sendHtmlEmail(
                login.getUsuario().getEmail(),
                "Verificação email TaskFlow",
                codigo.toString(), HTML_PATH);

        CodigoVerificacao codigoVerifi = new CodigoVerificacao(
                codigo.toString(),
                getData(),
                login
        );

        repository.save(codigoVerifi);
    }

    @Transactional
    public void validarCodigo(String codigo){
        CodigoVerificacao codigoVerificacao = repository.findByCodigo(codigo)
                .orElseThrow(() -> new InvalidValidationCodeException("Código digitado incorreto ou inválido!"));

        if (codigoVerificacao.getDataExpiracao().isBefore(LocalDateTime.now())){
            throw new InvalidValidationCodeException("Código vencido! Favor solicitar novo código");
        } else {
            codigoVerificacao.getLogin().setAtivo(true);
        }
    }

    private LocalDateTime getData(){
        return LocalDateTime.now().plusHours(1);
    }
}
