package br.com.fiap.CoAlert.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "CA_POSTAGEM")
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPostagem;

    @NotNull
    private String nmTitulo;

    @NotNull
    private String nmConteudo;

    @NotNull
    private LocalDate dtEnvio;

    @NotNull
    private Integer nrLikes;

    @ManyToOne
    @JoinColumn(name = "CA_USUARIO_ID_USUARIO")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "CA_CATEGORIA_DESASTRE_ID_CATEGORIA_DESASTRE")
    private CategoriaDesastre categoriaDesastre;

    @ManyToOne
    @JoinColumn(name = "CA_LOCALIZACAO_ID_LOCALIZACAO")
    private Localizacao localizacao;

    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("postagem")
    private List<Comentario> comentarios;
}
