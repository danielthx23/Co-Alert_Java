package br.com.fiap.Co_Alert.repository;

import br.com.fiap.Co_Alert.model.CategoriaDesastre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDesastreRepository extends JpaRepository<CategoriaDesastre, Long> {
}
