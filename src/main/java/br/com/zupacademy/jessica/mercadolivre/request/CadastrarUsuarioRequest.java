package br.com.zupacademy.jessica.mercadolivre.request;

import br.com.zupacademy.jessica.mercadolivre.model.Usuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CadastrarUsuarioRequest {

    @Email
    @NotBlank
    //unico
    private String login;

    @NotBlank
    @Size(min = 6)
    private String senha;

    public Usuario toModel() {
        return new Usuario(login, senha);
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
