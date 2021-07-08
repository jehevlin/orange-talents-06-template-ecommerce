package br.com.zupacademy.jessica.mercadolivre.request;

import br.com.zupacademy.jessica.mercadolivre.model.Categoria;
import br.com.zupacademy.jessica.mercadolivre.request.validator.MustBeUnique;

import javax.validation.constraints.NotBlank;

public class CadastrarCategoriaRequest {

    @NotBlank
    @MustBeUnique(domainClass = Categoria.class, fieldName = "nome", message = "categoria já cadastrada")
    private String nome;

    private Categoria categoriaMae;

    public Categoria toModel(){
        return new Categoria(nome, categoriaMae);
    }

    public String getNome() {
        return nome;
    }

    public Categoria getCategoriaMae() {
        return categoriaMae;
    }
}
