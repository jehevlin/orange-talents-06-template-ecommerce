package br.com.zupacademy.jessica.mercadolivre.enviadorEmails;

import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import org.springframework.stereotype.Component;

@Component
public class EnviarEmailParaComprador {
    private final EnviadorEmail enviadorEmail;

    public EnviarEmailParaComprador(EnviadorEmail enviadorEmail) {
        this.enviadorEmail = enviadorEmail;
    }

    public void EnviarEmailSucessoCompra(Compra compra) {
        enviadorEmail.enviarEmail(
                "Compra realizada com sucesso",
                compra.getComprador().getLogin(),
                "Detalhes da compra: " + compra
        );
    }

    public void EnviarEmailFalhaCompra(Compra compra, String urlPagamento) {
        enviadorEmail.enviarEmail(
                "Houve um problema seu pagamento",
                compra.getComprador().getLogin(),
                "Tente realizar o pagamento novamente pelo link: " + urlPagamento
        );
    }
}
