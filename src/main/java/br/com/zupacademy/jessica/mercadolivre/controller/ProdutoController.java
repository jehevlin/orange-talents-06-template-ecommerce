package br.com.zupacademy.jessica.mercadolivre.controller;

import br.com.zupacademy.jessica.mercadolivre.model.Imagem;
import br.com.zupacademy.jessica.mercadolivre.model.Produto;
import br.com.zupacademy.jessica.mercadolivre.repository.ProdutoRepository;
import br.com.zupacademy.jessica.mercadolivre.repository.UsuarioRepository;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarImagemRequest;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarProdutoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "produtos")
public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarProduto(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CadastrarProdutoRequest request) {

        Produto produto = repository.save(request.toModel(user.getUsername()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/imagens")
    @Transactional
    public ResponseEntity<?> cadastrarImagem(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "id") long idProduto,
            @Valid @RequestBody CadastrarImagemRequest request) {

        Optional<Produto> buscaProduto = repository.findById(idProduto);
        if (buscaProduto.isPresent()) {
            Produto produto = buscaProduto.get();

            if (!produto.getUsuario().getLogin().equals(user.getUsername())) {
                return new ResponseEntity<>("Esse não é seu produto animal", HttpStatus.FORBIDDEN);
            }

            produto.getImagens().add(new Imagem(request.getArquivoImagem()));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Produto com id " + idProduto + " não existe.", HttpStatus.NOT_FOUND);
    }
}