package br.com.fiap.CoAlert.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "CA_LIKE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_like_id", sequenceName = "seq_like_id", allocationSize = 1)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_like_id")
    @Column(name = "ID_LIKE")
    private Long id;

    @Column(name = "DT_LIKE", nullable = false)
    private LocalDateTime dataLike;

    @ManyToOne
    @JoinColumn(name = "ID_POSTAGEM")
    private Postagem postagem;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_COMENTARIO")
    private Comentario comentario;
} 