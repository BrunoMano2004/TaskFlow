package TaskFlow_api.TaskFlow_api.model;

import TaskFlow_api.TaskFlow_api.dto.CadastroUsuarioDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nomeCompleto;

    private Date dataNascimento;

    private boolean temaEscuro;

    private String imgPerfil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isTemaEscuro() {
        return temaEscuro;
    }

    public void setTemaEscuro(boolean temaEscuro) {
        this.temaEscuro = temaEscuro;
    }

    public String getImgPerfil() {
        return imgPerfil;
    }

    public void setImgPerfil(String imgPerfil) {
        this.imgPerfil = imgPerfil;
    }

    public Usuario() {
    }

    public Usuario(CadastroUsuarioDto usuarioDto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        this.email = usuarioDto.email();
        this.nomeCompleto = usuarioDto.nomeCompleto();
        this.dataNascimento = sdf.parse(usuarioDto.dataNascimento());
        this.imgPerfil = usuarioDto.imgPerfil();
    }
}
