package br.com.fiap.CoAlert.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LikeResponseDto {
    private Long idLike;
    private String nomeUsuario;
    private Long idPostagem;
    private Long idComentario;
    private LocalDateTime dtLike;
} 