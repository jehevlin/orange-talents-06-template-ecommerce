package br.com.zupacademy.jessica.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String titulo;

    private LocalDateTime instanteCriacao;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @ManyToOne
    private Produto produto;

    @Deprecated
    public Pergunta() {
    }

    public Pergunta(String titulo, LocalDateTime instanteCriacao, Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.instanteCriacao = instanteCriacao;
        this.usuario = usuario;
        this.produto = produto;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }
}

//O vendedor recebe um email com a pergunta nova
//O email não precisa ser de verdade. Pode ser apenas um print no console do servidor com o corpo.
