package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
}