package br.com.zupacademy.jessica.mercadolivre.request;

import br.com.zupacademy.jessica.mercadolivre.model.Opiniao;
import br.com.zupacademy.jessica.mercadolivre.model.Produto;
import br.com.zupacademy.jessica.mercadolivre.model.Usuario;

import javax.validation.constraints.*;

public class CadastrarOpiniaoRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private short avaliacao;

    @NotBlank
    private String titulo;

    @NotBlank
    @Size(max = 500)
    private String descricao;

    public Opiniao toModel(String login, Long idProduto){
        return new Opiniao(avaliacao, titulo, descricao, new Usuario(login), new Produto(idProduto));
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