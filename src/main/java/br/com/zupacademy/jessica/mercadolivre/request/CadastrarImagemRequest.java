package br.com.zupacademy.jessica.mercadolivre.request;

import javax.validation.constraints.NotBlank;

public class CadastrarImagemRequest {

    @NotBlank
    private String arquivoImagem;

    public String getArquivoImagem() {
        return arquivoImagem;
    }
}
