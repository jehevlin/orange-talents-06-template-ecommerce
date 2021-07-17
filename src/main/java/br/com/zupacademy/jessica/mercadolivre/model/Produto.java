package br.com.zupacademy.jessica.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String nome;

    @NotNull
    @Min(1)
    private BigDecimal valor;

    @NotNull
    @Min(0)
    private int quantidade;

    @OneToMany(cascade = CascadeType.PERSIST)
    @Size(min = 3)
    private Set<Caracteristica> caracteristicas;

    @NotBlank
    @Size(min = 1000)
    @Column(length = 5000)
    private String descricao;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Imagem> imagens;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    private final LocalDateTime instanteCadastro = LocalDateTime.now();

    @Deprecated
    public Produto() {
    }

    public Produto(Long id) {
        this.id = id;
    }

    public Produto(String nome, BigDecimal valor, int quantidade, Set<Caracteristica> caracteristicas, String descricao, Categoria categoria, Usuario usuario) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDateTime getInstanteCadastro() {
        return instanteCadastro;
    }

    public Set<Imagem> getImagens() {
        return imagens;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void atualizarEstoque(Compra compra) {
        this.quantidade -= compra.getQuantidade();
    }
}