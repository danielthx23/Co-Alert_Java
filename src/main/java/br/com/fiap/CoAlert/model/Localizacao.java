package br.com.fiap.CoAlert.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "CA_LOCALIZACAO")
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocalizacao;

    @NotNull
    private String nmBairro;

    @NotNull
    private String nmLogradouro;

    @NotNull
    private Integer nrNumero;

    @NotNull
    private String nmCidade;

    @NotNull
    private String nmEstado;

    @NotNull
    private String nrCep;

    @NotNull
    private String nmPais;

    private String dsComplemento;
}
