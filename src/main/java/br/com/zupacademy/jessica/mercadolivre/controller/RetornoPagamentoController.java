package br.com.zupacademy.jessica.mercadolivre.controller;

import br.com.zupacademy.jessica.mercadolivre.enviadorEmails.EnviarEmailParaComprador;
import br.com.zupacademy.jessica.mercadolivre.gatewayPagamento.GatewayPagamento;
import br.com.zupacademy.jessica.mercadolivre.model.Compra;
import br.com.zupacademy.jessica.mercadolivre.model.Transacao;
import br.com.zupacademy.jessica.mercadolivre.repository.CompraRepository;
import br.com.zupacademy.jessica.mercadolivre.repository.TransacaoRepository;
import br.com.zupacademy.jessica.mercadolivre.request.RetornoPagamentoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(path = "retorno-pagamento")
public class RetornoPagamentoController {

    private final TransacaoRepository transacaoRepository;
    private final CompraRepository compraRepository;
    private final GatewayPagamento gatewayPagamento;
    private final EnviarEmailParaComprador emailParaComprador;

    public RetornoPagamentoController(TransacaoRepository transacaoRepository, CompraRepository compraRepository, GatewayPagamento gatewayPagamento, EnviarEmailParaComprador emailParaComprador) {
        this.transacaoRepository = transacaoRepository;
        this.compraRepository = compraRepository;
        this.gatewayPagamento = gatewayPagamento;
        this.emailParaComprador = emailParaComprador;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> retornoPagamento(
            @RequestParam(name = "idCompra") long idCompra,
            @RequestParam(name = "gateway") String gateway,
            @RequestBody RetornoPagamentoRequest request,
            UriComponentsBuilder uriBuilder) throws Exception {

        // verifica se para esse gateway, esse id ja foi processado
        List<Transacao> transacoes = transacaoRepository.findAllByIdTransacao(request.getIdTransacao());
        boolean transacaoJaProcessada = transacoes.stream()
                .anyMatch(t -> t.getGateway().equals(gateway));

        if (transacaoJaProcessada) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        Compra compra = compraRepository.getById(idCompra);
        String status = gatewayPagamento.recuperarStatus(gateway, request.getStatus());

        Transacao transacao = new Transacao(gateway, status, compra, request.getIdTransacao());
        transacaoRepository.save(transacao);

        ProcessarTransacao(transacao, uriBuilder);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void ProcessarTransacao(Transacao transacao, UriComponentsBuilder uriBuilder) {

        Compra compra = transacao.getCompra();
        compra.atualizarStatusParaTransacao(transacao);
        compra = compraRepository.save(compra);

        if (transacao.getStatus().equals("sucesso")) {
            FluxoSucesso(compra);
        } else {
            FluxoErro(compra, uriBuilder);
        }
    }

    private void FluxoSucesso(Compra compra) {

        // Precisamos nos comunicar com o setor de nota fiscal que é um outro sistema. Ele precisa receber apenas receber o id da compra e o id do usuário que fez a compra.
        long idCompra = compra.getId();
        String comprador = compra.getComprador().getLogin();
        ComunicarSetorNotaFiscal(idCompra, comprador);

        // Também precisamos nos comunicar com o sistema de ranking dos vendedores. Esse sistema recebe o id da compra e o id do vendedor.
        String vendedor = compra.getProduto().getUsuario().getLogin();
        ComunicarSistemaRankingVendedores(idCompra, vendedor);

        // Para fechar precisamos mandar um email para quem comprou avisando da conclusão com sucesso. Pode colocar o máximo de informações da compra que puder.
        emailParaComprador.EnviarEmailSucessoCompra(compra);
    }

    private void ComunicarSistemaRankingVendedores(long idCompra, String vendedor) {
    }

    private void ComunicarSetorNotaFiscal(long idCompra, String comprador) {
    }

    private void FluxoErro(Compra compra, UriComponentsBuilder uriBuilder) {
        // Enviar um email para o usuário informando que o pagamento falhou com o link para que a pessoa possa tentar de novo.
        try {
            String urlPagamento = gatewayPagamento.gerarUrlPagamento(compra, uriBuilder);
            emailParaComprador.EnviarEmailFalhaCompra(compra, urlPagamento);
        } catch (Exception ignored) {
        }
    }
}
