package br.com.zupacademy.jessica.mercadolivre.response;

public class OpiniaoResponse {

    private short avaliacao;

    private String titulo;

    private String descricao;

    public OpiniaoResponse(short avaliacao, String titulo, String descricao) {
        this.avaliacao = avaliacao;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public short getAvaliacao() {
        return avaliacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
