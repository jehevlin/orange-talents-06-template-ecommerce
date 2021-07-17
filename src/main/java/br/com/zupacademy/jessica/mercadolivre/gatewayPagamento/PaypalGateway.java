package br.com.zupacademy.jessica.mercadolivre.gatewayPagamento;

import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import org.springframework.stereotype.Component;

@Component
public class PaypalGateway implements GatewayPagamentoCompra {
    @Override
    public String GerarUrlPagamento(Compra compra) {
        return "paypal.com?buyerId=" + compra.getId() + "&redirectUrl=meuapp.com/pagamentoRealizado";
    }
}
