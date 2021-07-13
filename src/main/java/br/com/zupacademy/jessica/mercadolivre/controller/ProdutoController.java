package br.com.zupacademy.jessica.mercadolivre.controller;

import br.com.zupacademy.jessica.mercadolivre.model.Imagem;
import br.com.zupacademy.jessica.mercadolivre.model.Produto;
import br.com.zupacademy.jessica.mercadolivre.repository.OpiniaoRepository;
import br.com.zupacademy.jessica.mercadolivre.repository.ProdutoRepository;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarImagemRequest;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarOpiniaoRequest;
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

    private final ProdutoRepository produtoRepository;

    private final OpiniaoRepository opiniaoRepository;

    public ProdutoController(ProdutoRepository produtoRepository, OpiniaoRepository opiniaoRepository) {
        this.produtoRepository = produtoRepository;
        this.opiniaoRepository = opiniaoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrarProduto(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CadastrarProdutoRequest request) {

        Produto produto = produtoRepository.save(request.toModel(user.getUsername()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/imagens")
    @Transactional
    public ResponseEntity<?> cadastrarImagem(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "id") long idProduto,
            @Valid @RequestBody CadastrarImagemRequest request) {

        Optional<Produto> buscaProduto = produtoRepository.findById(idProduto);
        if (!buscaProduto.isPresent()) {
            return new ResponseEntity<>("Produto com id " + idProduto + " não existe.", HttpStatus.NOT_FOUND);
        }

        Produto produto = buscaProduto.get();
        if (!produto.getUsuario().getLogin().equals(user.getUsername())) {
            return new ResponseEntity<>("Esse não é seu produto animal", HttpStatus.FORBIDDEN);
        }

        produto.getImagens().add(new Imagem(request.getArquivoImagem()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/opiniao")
    public ResponseEntity<?> cadastrarOpiniao(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "id") long idProduto,
            @Valid @RequestBody CadastrarOpiniaoRequest request) {

        if (!produtoRepository.existsById(idProduto)) {
            return new ResponseEntity<>("Produto com id " + idProduto + " não existe.", HttpStatus.NOT_FOUND);
        }

        opiniaoRepository.save(request.toModel(user.getUsername(), idProduto));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}