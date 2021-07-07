package br.com.zupacademy.jessica.mercadolivre.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @Email
    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 6)
    //@Length(min = 6) ?
    //A senha deve ser guardada usando algum algoritmo de hash da sua escolha.
    private String senha;

    @NotNull
    private LocalDateTime instanteCadastro = LocalDateTime.now();

    @Deprecated
    public Usuario() { }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }

    public String getLogin() {
        return login;
    }
}

//Resultado esperado
//O usuário precisa estar criado no sistema
//O cliente que fez a requisição precisa saber que o usuário foi criado. Apenas um retorno com status 200 está suficente.
//Em caso de falha de validação status 400