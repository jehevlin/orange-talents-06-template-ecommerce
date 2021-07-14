package br.com.zupacademy.jessica.mercadolivre.enviadorEmails;

import org.springframework.stereotype.Component;

@Component
public class EnviadorEmail {

    public void enviarEmail(String titulo, String destinatario, String corpo){
        System.out.println(corpo);
    }
}
