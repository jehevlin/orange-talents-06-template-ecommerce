package br.com.zupacademy.jessica.mercadolivre.response;

import br.com.zupacademy.jessica.mercadolivre.model.Usuario;

public class UsuarioResponse {

    private String login;

    public UsuarioResponse(Usuario usuario) {
        this.login = usuario.getLogin();
    }

    public String getLogin() {
        return login;
    }
}
