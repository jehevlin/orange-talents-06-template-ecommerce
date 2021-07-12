package br.com.zupacademy.jessica.mercadolivre.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String arquivoImagem;

    @Deprecated
    public Imagem() {
    }

    public Imagem(String arquivoImagem) {
        this.arquivoImagem = arquivoImagem;
    }

    public String getArquivoImagem() {
        return arquivoImagem;
    }

    public long getId() {
        return id;
    }
}
