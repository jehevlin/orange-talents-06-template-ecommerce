package br.com.zupacademy.jessica.mercadolivre.gatewayPagamento;

import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PaypalGateway implements GatewayPagamentoCompra {

    @Override
    public String gerarUrlPagamento(Compra compra, UriComponentsBuilder uriBuilder) {

        String urlRetorno = uriBuilder
                .path("/retorno-pagamento")
                .queryParam("idCompra", compra.getId())
                .queryParam("gateway", "paypal")
                .buildAndExpand()
                .toString();

        return "http://paypal.com?buyerId=" + compra.getId() + "&redirectUrl=" + urlRetorno;
    }
}
