package TaskFlow_api.TaskFlow_api.model;

import TaskFlow_api.TaskFlow_api.dto.usuario.AtualizacaoUsuarioDto;
import TaskFlow_api.TaskFlow_api.dto.usuario.CadastroUsuarioDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String nomeCompleto;

    private LocalDate dataNascimento;

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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return temaEscuro == usuario.temaEscuro && Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email) && Objects.equals(nomeCompleto, usuario.nomeCompleto) && Objects.equals(dataNascimento, usuario.dataNascimento) && Objects.equals(imgPerfil, usuario.imgPerfil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nomeCompleto, dataNascimento, temaEscuro, imgPerfil);
    }

    public Usuario() {
    }

    public Usuario(String email, String nomeCompleto, LocalDate dataNascimento, String imgPerfil) {
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.temaEscuro = false;
        this.imgPerfil = imgPerfil;
    }

    public Usuario(CadastroUsuarioDto usuarioDto) {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.email = usuarioDto.email();
        this.nomeCompleto = usuarioDto.nomeCompleto();
        this.imgPerfil = usuarioDto.imgPerfil();
        this.dataNascimento = LocalDate.parse(usuarioDto.dataNascimento(), sdf);
        this.temaEscuro = false;
    }

    public void atualizarUsuario(AtualizacaoUsuarioDto atualizacaoUsuario){
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (!(atualizacaoUsuario.email() == null || atualizacaoUsuario.email().isBlank())){
            this.email = atualizacaoUsuario.email();
        }
        if (!(atualizacaoUsuario.nomeCompleto() == null || atualizacaoUsuario.nomeCompleto().isBlank())){
            this.nomeCompleto = atualizacaoUsuario.nomeCompleto();
        }
        if (!(atualizacaoUsuario.dataNascimento() == null || atualizacaoUsuario.dataNascimento().isBlank())){
            try {
                this.dataNascimento = LocalDate.parse(atualizacaoUsuario.dataNascimento(), sdf);
            } catch (DateTimeParseException e) {
                throw new InvalidDataException("Data digitada está incorreta!");
            }
        }
        if (!(atualizacaoUsuario.imgPerfil() == null || atualizacaoUsuario.imgPerfil().isBlank())){
            this.imgPerfil = atualizacaoUsuario.imgPerfil();
        }
    }
}
