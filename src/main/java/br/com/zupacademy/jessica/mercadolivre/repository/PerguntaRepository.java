package br.com.zupacademy.jessica.mercadolivre.repository;

import br.com.zupacademy.jessica.mercadolivre.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    List<Pergunta> findAllByProdutoId(long idProduto);
}
