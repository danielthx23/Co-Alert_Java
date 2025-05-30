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
@Table(name = "CA_COMENTARIO")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComentario;

    private Long idComentarioParente;

    @NotNull
    private String nmConteudo;

    @NotNull
    private LocalDate dtEnvio;

    @NotNull
    private Long nrLikes;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "CA_POSTAGEM_ID_POSTAGEM")
    private Postagem postagem;
}
