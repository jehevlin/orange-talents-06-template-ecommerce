package br.com.zupacademy.jessica.mercadolivre.repository;

import br.com.zupacademy.jessica.mercadolivre.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
}
