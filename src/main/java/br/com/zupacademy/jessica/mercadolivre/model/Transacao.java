package br.com.zupacademy.jessica.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String gateway;

    @NotNull
    private LocalDateTime dataProcessamento;

    @NotBlank
    private String status;

    @NotNull
    @ManyToOne
    private Compra compra;

    @NotBlank
    private String idTransacao;

    public Transacao(String gateway, String status, Compra compra, String idTransacao) {
        this.gateway = gateway;
        this.dataProcessamento = LocalDateTime.now();
        this.status = status;
        this.compra = compra;
        this.idTransacao = idTransacao;
    }

    @Deprecated
    public Transacao() {}

    public Long getId() {
        return id;
    }

    public String getGateway() {
        return gateway;
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public String getStatus() {
        return status;
    }

    public Compra getCompra() {
        return compra;
    }

    public String getIdTransacao() {
        return idTransacao;
    }
}
