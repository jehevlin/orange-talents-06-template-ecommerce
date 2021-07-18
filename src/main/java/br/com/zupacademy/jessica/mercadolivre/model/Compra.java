package br.com.zupacademy.jessica.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @Min(1)
    private int quantidade; // estoque abatido

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal valor; //valor da compra , no instante da compra

    @NotNull
    @ManyToOne
    private Usuario comprador;

    @ManyToOne
    private Transacao transacao;

    @NotBlank
    private String gatewayPagamento;

    @NotBlank
    private String status;

    @Deprecated
    public Compra() {
    }

    public Compra(Produto produto, int quantidade, Usuario comprador, String gatewayPagamento) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.comprador = comprador;
        this.gatewayPagamento = gatewayPagamento;
        this.valor = produto.getValor();
        this.status = "INICIADA";
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public String getGatewayPagamento() {
        return gatewayPagamento;
    }

    public String getStatus() {
        return status;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void atualizarStatusParaTransacao(Transacao transacao) {

        this.transacao = transacao;

        if (transacao.getStatus().equals("sucesso")) {
            this.status = "PAGAMENTO_EFETUADO";
        } else {
            this.status = "FALHA_PAGAMENTO";
        }
    }
}
