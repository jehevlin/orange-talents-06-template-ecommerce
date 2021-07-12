package br.com.zupacademy.jessica.mercadolivre.controller;

import br.com.zupacademy.jessica.mercadolivre.model.Produto;
import br.com.zupacademy.jessica.mercadolivre.repository.ProdutoRepository;
import br.com.zupacademy.jessica.mercadolivre.repository.UsuarioRepository;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarProdutoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController (ProdutoRepository repository) { this.repository = repository; }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarProduto(@Valid @RequestBody CadastrarProdutoRequest request) {
        Produto produto = repository.save(request.toModel());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}