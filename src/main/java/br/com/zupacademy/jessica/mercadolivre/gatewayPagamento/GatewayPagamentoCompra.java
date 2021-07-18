package br.com.zupacademy.jessica.mercadolivre.gatewayPagamento;

import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import org.springframework.web.util.UriComponentsBuilder;

public interface GatewayPagamentoCompra {
    String gerarUrlPagamento(Compra compra, UriComponentsBuilder uriBuilder) throws Exception;
}
