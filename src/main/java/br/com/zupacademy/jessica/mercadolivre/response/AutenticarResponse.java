package br.com.zupacademy.jessica.mercadolivre.response;

public class AutenticarResponse {
    private final String jwt;

    public AutenticarResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
