package br.com.fiap.CoAlert.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "CA_LOCALIZACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_localizacao_id", sequenceName = "seq_localizacao_id", allocationSize = 1)
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_localizacao_id")
    @Column(name = "ID_LOCALIZACAO")
    private Long idLocalizacao;

    @Column(name = "NM_BAIRRO", nullable = false)
    private String nmBairro;

    @Column(name = "NM_LOGRADOURO", nullable = false)
    private String nmLogradouro;

    @Column(name = "NR_NUMERO")
    private String nrNumero;

    @Column(name = "NM_CIDADE", nullable = false)
    private String nmCidade;

    @Column(name = "NM_ESTADO", nullable = false)
    private String nmEstado;

    @Column(name = "NR_CEP", nullable = false)
    private String nrCep;

    @Column(name = "NM_PAIS", nullable = false)
    private String nmPais;

    @Column(name = "DS_COMPLEMENTO")
    private String dsComplemento;
}
