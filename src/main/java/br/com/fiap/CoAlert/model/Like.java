package br.com.fiap.CoAlert.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "CA_LIKE")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLike;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_POSTAGEM")
    private Postagem postagem;

    @ManyToOne
    @JoinColumn(name = "ID_COMENTARIO")
    private Comentario comentario;

    @NotNull
    private LocalDate dtLike;
} 