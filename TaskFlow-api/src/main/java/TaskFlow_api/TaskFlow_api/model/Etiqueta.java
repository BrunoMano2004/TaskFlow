package TaskFlow_api.TaskFlow_api.model;


import TaskFlow_api.TaskFlow_api.dto.etiqueta.AtualizacaoEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import jakarta.persistence.*;

@Entity
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cor;

    @JoinColumn(name = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
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

    public void atualizar(AtualizacaoEtiquetaDto atualizacaoEtiqueta){

        if (!(atualizacaoEtiqueta.nome().isBlank())){
            this.nome = atualizacaoEtiqueta.nome();
        }

        if (!(atualizacaoEtiqueta.cor().isBlank())){
            this.nome = atualizacaoEtiqueta.cor();
        }
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
}
