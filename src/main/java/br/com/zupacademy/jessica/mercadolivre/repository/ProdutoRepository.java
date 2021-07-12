package br.com.zupacademy.jessica.mercadolivre.repository;

import br.com.zupacademy.jessica.mercadolivre.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
}
