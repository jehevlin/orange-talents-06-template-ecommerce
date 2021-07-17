package br.com.zupacademy.jessica.mercadolivre.response;

public class CaracteristicaResponse {

    private String nome;

    private String descicao;

    public CaracteristicaResponse(String nome, String descicao) {
        this.nome = nome;
        this.descicao = descicao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescicao() {
        return descicao;
    }
}
