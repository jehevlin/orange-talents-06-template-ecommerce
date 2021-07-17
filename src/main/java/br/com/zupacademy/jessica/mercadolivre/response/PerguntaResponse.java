package br.com.zupacademy.jessica.mercadolivre.response;

import java.time.LocalDateTime;

public class PerguntaResponse {

    private String titulo;

    private LocalDateTime instanteCriacao;

    public PerguntaResponse(String titulo, LocalDateTime instanteCriacao) {
        this.titulo = titulo;
        this.instanteCriacao = instanteCriacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }
}
