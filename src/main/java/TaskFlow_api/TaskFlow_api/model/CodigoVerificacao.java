package TaskFlow_api.TaskFlow_api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CodigoVerificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataExpiracao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_login")
    private Login login;

    public CodigoVerificacao() {
    }

    public CodigoVerificacao(Long id, String codigo, LocalDateTime dataCriacao, LocalDateTime dataExpiracao, Login login) {
        this.id = id;
        this.codigo = codigo;
        this.dataCriacao = dataCriacao;
        this.dataExpiracao = dataExpiracao;
        this.login = login;
    }

    public CodigoVerificacao(String codigo, LocalDateTime dataExpiracao, Login login) {
        this.codigo = codigo;
        this.dataExpiracao = dataExpiracao;
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
