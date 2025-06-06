package br.com.fiap.CoAlert.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ComentarioResponseDto {

    private Long idComentario;
    private Long idComentarioParente;
    private String nmConteudo;
    private LocalDateTime dtEnvio;
    private Long nrLikes;

    private String nomeUsuario;
    private Long idPostagem;
}
