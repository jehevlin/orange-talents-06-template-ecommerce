package br.com.zupacademy.jessica.mercadolivre.controller;

import br.com.zupacademy.jessica.mercadolivre.enviadorEmails.EnviarEmailParaVendedor;
import br.com.zupacademy.jessica.mercadolivre.gatewayPagamento.GatewayPagamento;
import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import br.com.zupacademy.jessica.mercadolivre.model.Produto;
import br.com.zupacademy.jessica.mercadolivre.model.Usuario;
import br.com.zupacademy.jessica.mercadolivre.repository.CompraRepository;
import br.com.zupacademy.jessica.mercadolivre.repository.ProdutoRepository;
import br.com.zupacademy.jessica.mercadolivre.request.CadastrarCompraRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping(path = "compra")
public class CompraController {

    private final CompraRepository compraRepository;
    private final ProdutoRepository produtoRepository;
    private final EnviarEmailParaVendedor emailParaVendedor;
    private final GatewayPagamento gatewayPagamento;

    public CompraController(CompraRepository compraRepository, ProdutoRepository produtoRepository,
                            EnviarEmailParaVendedor emailParaVendedor, GatewayPagamento gatewayPagamento) {
        this.compraRepository = compraRepository;
        this.produtoRepository = produtoRepository;
        this.emailParaVendedor = emailParaVendedor;
        this.gatewayPagamento = gatewayPagamento;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrarCompra(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CadastrarCompraRequest request) throws Exception {

        if (!request.getGatewayPagamento().toLowerCase(Locale.ROOT).equals("paypal")
                && !request.getGatewayPagamento().toLowerCase(Locale.ROOT).equals("pagseguro")) {
            return new ResponseEntity<>("Gateway de pagamento não suportado: " + request.getGatewayPagamento(),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Produto> buscaProduto = produtoRepository.findById(request.getIdProduto());
        if (buscaProduto.isEmpty()) {
            return new ResponseEntity<>("Produto com id " + request.getIdProduto() + " não existe.",
                    HttpStatus.BAD_REQUEST);
        }

        Produto produto = buscaProduto.get();

        if (produto.getQuantidade() < request.getQuantidade()) {
            return new ResponseEntity<>("Produto não possui quantidade solicitada em estoque.",
                    HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario(user.getUsername());
        Compra compra = request.toModel(produto, usuario);

        produto.atualizarEstoque(compra);

        produtoRepository.save(produto);
        compra = compraRepository.save(compra);

        emailParaVendedor.enviarCompra(compra);

        String urlPagamento = gatewayPagamento.GerarUrlPagamento(compra);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", urlPagamento)
                .build();
    }
}
