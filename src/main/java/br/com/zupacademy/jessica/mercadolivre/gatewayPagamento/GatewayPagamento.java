package br.com.zupacademy.jessica.mercadolivre.gatewayPagamento;

import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;

@Component
public class GatewayPagamento implements GatewayPagamentoCompra {

    private final PaypalGateway paypalGateway;
    private final PagSeguroGateway pagSeguroGateway;

    public GatewayPagamento(PaypalGateway paypalGateway, PagSeguroGateway pagSeguroGateway) {
        this.paypalGateway = paypalGateway;
        this.pagSeguroGateway = pagSeguroGateway;
    }

    public String gerarUrlPagamento(Compra compra, UriComponentsBuilder uriBuilder) throws Exception {

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

        return gateway.gerarUrlPagamento(compra, uriBuilder);
    }

    public String recuperarStatus(String gateway, String status) throws Exception {
        switch (gateway)
        {
            case "paypal":
                if (status.equals("1"))
                    return "sucesso";
                return "erro";

            case "pagseguro":
                if (status.equals("SUCESSO"))
                    return "sucesso";
                return "erro";

            default:
                throw new Exception("Gateway não suportado: " + gateway);
        }
    }
}
