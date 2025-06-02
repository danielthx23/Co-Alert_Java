package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUsuarioIdUsuarioAndPostagemIdPostagemAndComentarioIsNull(Long usuarioId, Long postagemId);
    Optional<Like> findByUsuarioIdUsuarioAndComentarioIdComentarioAndPostagemIsNull(Long usuarioId, Long comentarioId);
    long countByPostagemIdPostagem(Long postagemId);
    long countByComentarioIdComentario(Long comentarioId);
} 