package br.com.fiap.CoAlert.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CA_POSTAGEM")
@SequenceGenerator(name = "seq_postagem_id", sequenceName = "seq_postagem_id", allocationSize = 1)
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_postagem_id")
    @Column(name = "ID_POSTAGEM")
    private Long idPostagem;

    @Column(name = "NM_TITULO", nullable = false)
    private String nmTitulo;

    @Column(name = "NM_CONTEUDO", nullable = false)
    private String nmConteudo;

    @Column(name = "DT_ENVIO", nullable = false)
    private LocalDateTime dtEnvio;

    @Column(name = "NR_LIKES")
    private Integer nrLikes;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA_DESASTRE", nullable = false)
    private CategoriaDesastre categoriaDesastre;

    @ManyToOne
    @JoinColumn(name = "ID_LOCALIZACAO", nullable = false)
    private Localizacao localizacao;

    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("postagem")
    private List<Comentario> comentarios;
}
