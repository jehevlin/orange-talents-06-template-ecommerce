package br.com.zupacademy.jessica.mercadolivre.gatewayPagamento;

import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PagSeguroGateway implements GatewayPagamentoCompra {

    @Override
    public String gerarUrlPagamento(Compra compra, UriComponentsBuilder uriBuilder) {

        String urlRetorno = uriBuilder
                .path("/retorno-pagamento")
                .queryParam("idCompra", compra.getId())
                .queryParam("gateway", "pagseguro")
                .buildAndExpand()
                .toString();

        return "http://pagseguro.com?returnId=" + compra.getId() + "&redirectUrl=" + urlRetorno;
    }
}
