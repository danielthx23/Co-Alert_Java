package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.Comentario;
import br.com.fiap.CoAlert.model.Postagem;
import br.com.fiap.CoAlert.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
    @Procedure(procedureName = "inserir_comentario")
    void inserirComentario(
        @Param("n_id_usuario") Long idUsuario,
        @Param("n_id_postagem") Long idPostagem,
        @Param("n_conteudo") String conteudo,
        @Param("n_data") LocalDateTime data,
        @Param("n_likes") Long likes,
        @Param("n_id_coment_p") Long idComentarioParente
    );

    @Procedure(procedureName = "deletar_comentario")
    void deletarComentario(@Param("p_id_comentario") Long idComentario);

    @Procedure(procedureName = "atualizar_comentario")
    void atualizarComentario(
        @Param("p_id_comentario") Long idComentario,
        @Param("p_id_usuario") Long idUsuario,
        @Param("p_id_postagem") Long idPostagem,
        @Param("p_conteudo") String conteudo,
        @Param("p_data") LocalDateTime data,
        @Param("p_likes") Long likes,
        @Param("p_id_comentario_pai") Long idComentarioParente
    );

    Optional<Comentario> findByNmConteudoAndUsuarioAndPostagemAndDtEnvio(
            String nmConteudo,
            Usuario usuario,
            Postagem postagem,
            LocalDateTime dtEnvio
    );
}