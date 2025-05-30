package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
}