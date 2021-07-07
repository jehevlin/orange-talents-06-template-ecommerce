package br.com.zupacademy.jessica.mercadolivre.controller;

import br.com.zupacademy.jessica.mercadolivre.model.Usuario;
import br.com.zupacademy.jessica.mercadolivre.repository.UsuarioRepository;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarUsuarioRequest;
import br.com.zupacademy.jessica.mercadolivre.response.UsuarioResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "usuario")
public class UsuarioController {

    private final UsuarioRepository repository;

    public UsuarioController (UsuarioRepository repository) {this.repository = repository;}

    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody CadastrarUsuarioRequest request) {
        Usuario usuario = repository.save(request.toModel());
        UsuarioResponse response = new UsuarioResponse(usuario);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
