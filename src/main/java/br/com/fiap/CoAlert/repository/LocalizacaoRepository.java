package br.com.fiap.CoAlert.repository;

import br.com.fiap.CoAlert.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
    
    @Procedure(procedureName = "inserir_localizacao")
    void inserirLocalizacao(
        @Param("n_bairro") String bairro,
        @Param("n_logradouro") String logradouro,
        @Param("n_numero") String numero,
        @Param("n_cidade") String cidade,
        @Param("n_estado") String estado,
        @Param("n_cep") String cep,
        @Param("n_pais") String pais,
        @Param("n_complemento") String complemento
    );

    @Procedure(procedureName = "deletar_localizacao")
    void deletarLocalizacao(@Param("p_id_localizacao") Long idLocalizacao);

    @Procedure(procedureName = "atualizar_localizacao")
    void atualizarLocalizacao(
        @Param("p_id_localizacao") Long idLocalizacao,
        @Param("p_bairro") String bairro,
        @Param("p_logradouro") String logradouro,
        @Param("p_numero") String numero,
        @Param("p_cidade") String cidade,
        @Param("p_estado") String estado,
        @Param("p_cep") String cep,
        @Param("p_pais") String pais,
        @Param("p_complemento") String complemento
    );

    Optional<Localizacao> findByBairroAndLogradouroAndNumeroAndCidadeAndEstadoAndCepAndPais(
        String bairro,
        String logradouro,
        String numero,
        String cidade,
        String estado,
        String cep,
        String pais
    );
}