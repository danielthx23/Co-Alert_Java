package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.CategoriaDesastre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDesastreRepository extends JpaRepository<CategoriaDesastre, Long> {
}
