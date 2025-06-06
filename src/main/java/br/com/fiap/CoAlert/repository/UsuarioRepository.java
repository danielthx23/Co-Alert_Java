package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNmEmail(String nmEmail);
    boolean existsByNmEmail(String nmEmail);

    @Procedure(procedureName = "inserir_usuario")
    void inserirUsuario(
        @Param("n_nome") String nome,
        @Param("n_senha") String senha,
        @Param("n_email") String email
    );

    @Procedure(procedureName = "deletar_usuario")
    void deletarUsuario(@Param("p_id_usuario") Long idUsuario);

    @Procedure(procedureName = "atualizar_usuario")
    void atualizarUsuario(
        @Param("p_id_usuario") Long idUsuario,
        @Param("p_nome") String nome,
        @Param("p_senha") String senha,
        @Param("p_email") String email
    );
}
