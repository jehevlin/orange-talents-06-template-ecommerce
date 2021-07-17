package br.com.zupacademy.jessica.mercadolivre.gatewayPagamento;

import br.com.zupacademy.jessica.mercadolivre.model.Compra;

public interface GatewayPagamentoCompra {
    String GerarUrlPagamento(Compra compra) throws Exception;
}
