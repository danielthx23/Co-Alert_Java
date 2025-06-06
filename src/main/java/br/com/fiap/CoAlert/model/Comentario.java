package br.com.fiap.CoAlert.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "CA_COMENTARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_comentario_id", sequenceName = "seq_comentario_id", allocationSize = 1)
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comentario_id")
    @Column(name = "ID_COMENTARIO")
    private Long idComentario;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_POSTAGEM", nullable = false)
    private Postagem postagem;

    @Column(name = "NM_CONTEUDO", nullable = false)
    private String nmConteudo;

    @Column(name = "DT_ENVIO", nullable = false)
    private LocalDateTime dtEnvio;

    @Column(name = "NR_LIKES")
    private Long nrLikes;

    @ManyToOne
    @JoinColumn(name = "ID_COMENTARIO_PARENTE")
    private Comentario comentarioParente;
}
