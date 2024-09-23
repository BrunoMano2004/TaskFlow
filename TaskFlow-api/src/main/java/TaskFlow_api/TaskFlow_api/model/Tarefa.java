package TaskFlow_api.TaskFlow_api.model;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.AtualizacaoTarefaDto;
import TaskFlow_api.TaskFlow_api.dto.tarefa.CadastroTarefaDto;
import TaskFlow_api.TaskFlow_api.exception.InvalidDataException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private LocalDateTime dataExpiracao;

    private LocalDateTime dataFinalizacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Etiqueta etiqueta;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Usuario usuario;

    public Tarefa() {
    }

    public Tarefa(String nome, String descricao, LocalDateTime dataExpiracao, LocalDateTime dataFinalizacao, Etiqueta etiqueta, Usuario usuario) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataExpiracao = dataExpiracao;
        this.dataFinalizacao = dataFinalizacao;
        this.status = Status.ABERTA;
        this.etiqueta = etiqueta;
        this.usuario = usuario;
    }

    public Tarefa(CadastroTarefaDto cadastroTarefa, Etiqueta etiqueta, Usuario usuario) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        this.nome = cadastroTarefa.nome();
        this.descricao = cadastroTarefa.descricao();
        this.status = Status.ABERTA;
        this.etiqueta = etiqueta;
        this.usuario = usuario;

        try {
            this.dataExpiracao = LocalDateTime.parse(cadastroTarefa.dataExpiracao(), dtf);
        } catch (DateTimeParseException ex){
            throw new InvalidDataException("Data ou horário digitado é inválido!");
        }
    }

    public void concluirTarefa(){
        this.dataFinalizacao = LocalDateTime.now();
        this.status = Status.FECHADA;
    }

    public void atualizarTarefa(AtualizacaoTarefaDto atualizacaoTarefa, Etiqueta etiqueta){
        if (atualizacaoTarefa.nome() != null){
            this.nome = atualizacaoTarefa.nome();
        }

        if (atualizacaoTarefa.descricao() != null){
            this.descricao = atualizacaoTarefa.descricao();
        }

        if (etiqueta != null){
            this.etiqueta = etiqueta;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Etiqueta getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Etiqueta etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public LocalDateTime getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(LocalDateTime dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(id, tarefa.id) && Objects.equals(nome, tarefa.nome) && Objects.equals(descricao, tarefa.descricao) && Objects.equals(dataExpiracao, tarefa.dataExpiracao) && Objects.equals(dataFinalizacao, tarefa.dataFinalizacao) && status == tarefa.status && Objects.equals(etiqueta, tarefa.etiqueta) && Objects.equals(usuario, tarefa.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, dataExpiracao, dataFinalizacao, status, etiqueta, usuario);
    }
}
