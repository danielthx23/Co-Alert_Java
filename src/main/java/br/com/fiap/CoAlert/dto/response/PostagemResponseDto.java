package br.com.fiap.CoAlert.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostagemResponseDto {

    private Long idPostagem;
    private String nmTitulo;
    private String nmConteudo;
    private LocalDateTime dtEnvio;
    private Integer nrLikes;

    private String nomeUsuario;
    private String nomeCategoriaDesastre;
    private String nomeLocalizacao;

    private List<ComentarioResponseDto> comentarios;
}