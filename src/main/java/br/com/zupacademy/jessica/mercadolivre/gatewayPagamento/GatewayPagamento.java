package br.com.zupacademy.jessica.mercadolivre.gatewayPagamento;

import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class GatewayPagamento implements GatewayPagamentoCompra {

    private final PaypalGateway paypalGateway;
    private final PagSeguroGateway pagSeguroGateway;

    public GatewayPagamento(PaypalGateway paypalGateway, PagSeguroGateway pagSeguroGateway) {
        this.paypalGateway = paypalGateway;
        this.pagSeguroGateway = pagSeguroGateway;
    }

    public String GerarUrlPagamento(Compra compra) throws Exception {

        GatewayPagamentoCompra gateway;

        switch (compra.getGatewayPagamento().toLowerCase(Locale.ROOT))
        {
            case "paypal":
                gateway = paypalGateway;
                break;

            case "pagseguro":
                gateway = pagSeguroGateway;
                break;

            default:
                throw new Exception("Gateway não suportado: " + compra.getGatewayPagamento());
        }

        return gateway.GerarUrlPagamento(compra);
    }
}
