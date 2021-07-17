package br.com.zupacademy.jessica.mercadolivre.repository;

import br.com.zupacademy.jessica.mercadolivre.model.Opiniao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpiniaoRepository extends JpaRepository<Opiniao, Long> {
    List<Opiniao> findAllByProdutoId(long produtoId);
}
