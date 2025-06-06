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
    private Long id;

    @Column(name = "NM_BAIRRO", nullable = false)
    private String bairro;

    @Column(name = "NM_LOGRADOURO", nullable = false)
    private String logradouro;

    @Column(name = "NR_NUMERO")
    private String numero;

    @Column(name = "NM_CIDADE", nullable = false)
    private String cidade;

    @Column(name = "NM_ESTADO", nullable = false)
    private String estado;

    @Column(name = "NR_CEP", nullable = false)
    private String cep;

    @Column(name = "NM_PAIS", nullable = false)
    private String pais;

    @Column(name = "DS_COMPLEMENTO")
    private String complemento;
}
