package br.com.zupacademy.jessica.mercadolivre.request;

import br.com.zupacademy.jessica.mercadolivre.model.Pergunta;
import br.com.zupacademy.jessica.mercadolivre.model.Produto;
import br.com.zupacademy.jessica.mercadolivre.model.Usuario;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CadastrarPerguntaRequest {

    @NotBlank
    private String titulo;

    private final LocalDateTime instanteCriacao = LocalDateTime.now();

    public Pergunta toModel(String login, Long idProduto){
        return new Pergunta(titulo, instanteCriacao, new Usuario(login), new Produto(idProduto));
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }
}
