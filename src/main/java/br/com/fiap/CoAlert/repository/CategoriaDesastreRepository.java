package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.CategoriaDesastre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaDesastreRepository extends JpaRepository<CategoriaDesastre, Long> {
    
    @Procedure(procedureName = "inserir_categoria")
    void inserirCategoria(
        @Param("n_titulo") String titulo,
        @Param("n_categ") String categoria,
        @Param("n_tipo") String tipo
    );

    @Procedure(procedureName = "deletar_categoria")
    void deletarCategoria(@Param("p_id_categoria") Long idCategoria);

    @Procedure(procedureName = "atualizar_categoria")
    void atualizarCategoria(
        @Param("p_id_categoria") Long idCategoria,
        @Param("p_titulo") String titulo,
        @Param("p_categ") String categoria,
        @Param("p_tipo") String tipo
    );

    Optional<CategoriaDesastre> findByNmTitulo(String titulo);
}
