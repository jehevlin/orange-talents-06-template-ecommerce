package br.com.zupacademy.jessica.mercadolivre.enviadorEmails;

import br.com.zupacademy.jessica.mercadolivre.model.Pergunta;
import org.springframework.stereotype.Component;

@Component
public class EnviarEmailParaVendedor {

    private final EnviadorEmail enviadorEmail;

    public EnviarEmailParaVendedor(EnviadorEmail enviadorEmail) {
        this.enviadorEmail = enviadorEmail;
    }

    public void enviarPergunta(Pergunta pergunta, String destinatario) {
        enviadorEmail.enviarEmail("Email da pergunta", destinatario, pergunta.getTitulo());
    }
}
