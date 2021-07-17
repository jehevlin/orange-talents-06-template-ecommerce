package br.com.zupacademy.jessica.mercadolivre.request;

import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import br.com.zupacademy.jessica.mercadolivre.model.Produto;
import br.com.zupacademy.jessica.mercadolivre.model.Usuario;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CadastrarCompraRequest {
    @NotNull
    private long idProduto;

    @NotNull
    @Min(1)
    private int quantidade;

    @NotBlank
    private String gatewayPagamento;

    public long getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getGatewayPagamento() {
        return gatewayPagamento;
    }

    public Compra toModel(Produto produto, Usuario usuario) {
        return new Compra(produto, quantidade, usuario, gatewayPagamento);
    }
}
