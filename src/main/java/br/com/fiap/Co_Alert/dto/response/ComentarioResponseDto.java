package br.com.fiap.Co_Alert.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ComentarioResponseDto {

    private Long idComentario;
    private Long idComentarioParente;
    private String nmConteudo;
    private LocalDate dtEnvio;
    private Long nrLikes;

    private String nomeUsuario;
    private Long idPostagem;
}
