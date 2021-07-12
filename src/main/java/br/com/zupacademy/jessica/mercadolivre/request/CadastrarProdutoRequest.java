package br.com.zupacademy.jessica.mercadolivre.request;

import br.com.zupacademy.jessica.mercadolivre.model.Caracteristica;
import br.com.zupacademy.jessica.mercadolivre.model.Categoria;
import br.com.zupacademy.jessica.mercadolivre.model.Produto;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class CadastrarProdutoRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Min(1)
    private BigDecimal valor;

    @NotNull
    @Min(0)
    private int quantidade;

    @OneToMany
    @Size(min = 3)
    private Set<CadastrarCaracteristicaRequest> caracteristicas;

    @NotBlank
    @Size(min = 1000)
    private String descricao;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    private final LocalDateTime instanteCadastro = LocalDateTime.now();

    public Produto toModel() {
        Set<Caracteristica> caracteristicasModel = caracteristicas.stream()
                .map(c -> c.toModel())
                .collect(Collectors.toSet());

        return new Produto(nome, valor, quantidade, caracteristicasModel, descricao, categoria);
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

    public Set<CadastrarCaracteristicaRequest> getCaracteristicas() {
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
}