package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.Postagem;
import br.com.fiap.CoAlert.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
    
    @Procedure(procedureName = "inserir_postagem")
    void inserirPostagem(
        @Param("n_titulo") String titulo,
        @Param("n_conteudo") String conteudo,
        @Param("n_data") LocalDateTime data,
        @Param("n_likes") Integer likes,
        @Param("n_id_usuario") Long idUsuario,
        @Param("n_id_categoria") Long idCategoria,
        @Param("n_id_localizacao") Long idLocalizacao
    );

    @Procedure(procedureName = "deletar_postagem")
    void deletarPostagem(@Param("p_id_postagem") Long idPostagem);

    @Procedure(procedureName = "atualizar_postagem")
    void atualizarPostagem(
        @Param("p_id_postagem") Long idPostagem,
        @Param("p_titulo") String titulo,
        @Param("p_conteudo") String conteudo,
        @Param("p_data") LocalDateTime data,
        @Param("p_likes") Integer likes,
        @Param("p_id_usuario") Long idUsuario,
        @Param("p_id_categoria") Long idCategoria,
        @Param("p_id_localizacao") Long idLocalizacao
    );

    Optional<Postagem> findByNmTituloAndNmConteudoAndUsuarioAndDtEnvio(
            String nmTitulo,
            String nmConteudo,
            Usuario usuario,
            LocalDateTime dtEnvio
    );
}