package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUsuario_IdUsuarioAndPostagem_IdPostagemAndComentarioIsNull(Long idUsuario, Long idPostagem);

    Optional<Like> findByUsuario_IdUsuarioAndComentario_IdComentarioAndPostagemIsNull(Long idUsuario, Long idComentario);

    long countByPostagem_IdPostagem(Long idPostagem);

    long countByComentario_IdComentario(Long idComentario);

    @Procedure(procedureName = "inserir_like")
    void inserirLike(
            @Param("n_id_usuario") Long idUsuario,
            @Param("n_id_postagem") Long idPostagem,
            @Param("n_id_comentario") Long idComentario
    );

    @Procedure(procedureName = "deletar_like")
    void deletarLike(@Param("p_id_like") Long idLike);
}
