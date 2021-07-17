package br.com.zupacademy.jessica.mercadolivre.controller;

import br.com.zupacademy.jessica.mercadolivre.enviadorEmails.EnviarEmailParaVendedor;
import br.com.zupacademy.jessica.mercadolivre.model.Imagem;
import br.com.zupacademy.jessica.mercadolivre.model.Opiniao;
import br.com.zupacademy.jessica.mercadolivre.model.Pergunta;
import br.com.zupacademy.jessica.mercadolivre.model.Produto;
import br.com.zupacademy.jessica.mercadolivre.repository.OpiniaoRepository;
import br.com.zupacademy.jessica.mercadolivre.repository.PerguntaRepository;
import br.com.zupacademy.jessica.mercadolivre.repository.ProdutoRepository;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarImagemRequest;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarOpiniaoRequest;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarPerguntaRequest;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarProdutoRequest;
import br.com.zupacademy.jessica.mercadolivre.enviadorEmails.EnviadorEmail;
import br.com.zupacademy.jessica.mercadolivre.response.CaracteristicaResponse;
import br.com.zupacademy.jessica.mercadolivre.response.DetalheProdutoResponse;
import br.com.zupacademy.jessica.mercadolivre.response.OpiniaoResponse;
import br.com.zupacademy.jessica.mercadolivre.response.PerguntaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    private final OpiniaoRepository opiniaoRepository;

    private final PerguntaRepository perguntaRepository;

    private final EnviarEmailParaVendedor enviarEmailParaVendedor;

    public ProdutoController(ProdutoRepository produtoRepository, OpiniaoRepository opiniaoRepository, PerguntaRepository perguntaRepository, EnviarEmailParaVendedor enviarEmailParaVendedor) {
        this.produtoRepository = produtoRepository;
        this.opiniaoRepository = opiniaoRepository;
        this.perguntaRepository = perguntaRepository;
        this.enviarEmailParaVendedor = enviarEmailParaVendedor;
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

    @PostMapping(path = "/{id}/pergunta")
    public ResponseEntity<?> cadastrarPergunta(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "id") long idProduto,
            @Valid @RequestBody CadastrarPerguntaRequest request) {

        Optional<Produto> buscaProduto = produtoRepository.findById(idProduto);
        if (!buscaProduto.isPresent()) {
            return new ResponseEntity<>("Produto com id " + idProduto + " não existe.", HttpStatus.NOT_FOUND);
        }

        Produto produto = buscaProduto.get();
        Pergunta pergunta = perguntaRepository.save(request.toModel(user.getUsername(), idProduto));
        enviarEmailParaVendedor.enviarPergunta(pergunta, produto.getUsuario().getLogin());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/detalhe")
    public ResponseEntity<?> detalhe(
            @PathVariable(name = "id") long idProduto
    ) {
        Optional<Produto> buscaProduto = produtoRepository.findById(idProduto);
        if (!buscaProduto.isPresent()) {
            return new ResponseEntity<>("Produto com id " + idProduto + " não existe.", HttpStatus.NOT_FOUND);
        }

        Produto produto = buscaProduto.get();
        List<Opiniao> opinioes = opiniaoRepository.findAllByProdutoId(idProduto);
        List<Pergunta> perguntas = perguntaRepository.findAllByProdutoId(idProduto);
        int totalNotas = opinioes.size();
        int somaNotas = opinioes.stream()
                .map(o -> (int) o.getAvaliacao())
                .reduce(0, Integer::sum);
        double mediaNotas = 0;

        if (totalNotas > 0) {
            mediaNotas = 1.0 * somaNotas / totalNotas;
        }

        DetalheProdutoResponse response = new DetalheProdutoResponse(
                produto.getImagens().stream()
                        .map(img -> img.getArquivoImagem())
                        .collect(Collectors.toSet()),
                produto.getNome(),
                produto.getValor(),
                produto.getCaracteristicas().stream()
                        .map(c -> new CaracteristicaResponse(c.getNome(), c.getDescricao()))
                        .collect(Collectors.toSet()),
                produto.getDescricao(),
                mediaNotas,
                totalNotas,
                opinioes.stream()
                        .map(o -> new OpiniaoResponse(o.getAvaliacao(), o.getTitulo(), o.getDescricao()))
                        .collect(Collectors.toSet()),
                perguntas.stream()
                        .map(p -> new PerguntaResponse(p.getTitulo(), p.getInstanteCriacao()))
                        .collect(Collectors.toSet())
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

//Des​crição do produto
//Média de notas do produto
//Número total de notas do produto
//Opiniões sobre o produto
//Perguntas para aquele produto