package br.com.zupacademy.jessica.mercadolivre.repository;

import br.com.zupacademy.jessica.mercadolivre.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findAllByIdTransacao(String idTransacao);
}
