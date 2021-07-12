package br.com.zupacademy.jessica.mercadolivre.request;

import br.com.zupacademy.jessica.mercadolivre.model.Caracteristica;

import javax.validation.constraints.NotBlank;

public class CadastrarCaracteristicaRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    public Caracteristica toModel() {
        return new Caracteristica(nome, descricao);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
