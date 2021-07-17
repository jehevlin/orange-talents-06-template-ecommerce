package br.com.zupacademy.jessica.mercadolivre.response;

import java.math.BigDecimal;
import java.util.Set;

public class DetalheProdutoResponse {

    private Set<String> imagens;

    private String nome;

    private BigDecimal preco;

    private Set<CaracteristicaResponse> caracteristicas;

    private String descricao;

    private Double mediaNotas;

    private int totalNotas;

    private Set<OpiniaoResponse> opinioes;

    private Set<PerguntaResponse> perguntas;

    public DetalheProdutoResponse(Set<String> imagens, String nome, BigDecimal preco, Set<CaracteristicaResponse> caracteristicas,
                                  String descricao, Double mediaNotas, int totalNotas,
                                  Set<OpiniaoResponse> opinioes, Set<PerguntaResponse> perguntas) {
        this.imagens = imagens;
        this.nome = nome;
        this.preco = preco;
        this.caracteristicas = caracteristicas;
        this.descricao = descricao;
        this.mediaNotas = mediaNotas;
        this.totalNotas = totalNotas;
        this.opinioes = opinioes;
        this.perguntas = perguntas;
    }

    public Set<String> getImagens() {
        return imagens;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Set<CaracteristicaResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getMediaNotas() {
        return mediaNotas;
    }

    public int getTotalNotas() {
        return totalNotas;
    }

    public Set<OpiniaoResponse> getOpinioes() {
        return opinioes;
    }

    public Set<PerguntaResponse> getPerguntas() {
        return perguntas;
    }
}