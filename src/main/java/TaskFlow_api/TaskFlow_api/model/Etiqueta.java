package TaskFlow_api.TaskFlow_api.model;


import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cor;

    @JoinColumn(name = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Usuario usuario;

    public Etiqueta() {
    }

    public Etiqueta(String nome, String cor, Usuario usuario) {
        this.nome = nome;
        this.cor = cor;
        this.usuario = usuario;
    }

    public Etiqueta(CadastroEtiquetaDto cadastroEtiqueta, Usuario usuario) {
        this.nome = cadastroEtiqueta.nome();
        this.cor = cadastroEtiqueta.cor();
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etiqueta etiqueta = (Etiqueta) o;
        return Objects.equals(id, etiqueta.id) && Objects.equals(nome, etiqueta.nome) && Objects.equals(cor, etiqueta.cor) && Objects.equals(usuario, etiqueta.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cor, usuario);
    }
}
