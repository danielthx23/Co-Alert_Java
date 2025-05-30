package br.com.fiap.Co_Alert.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ComentarioEditRequestDto {

    @NotNull(message = "O ID do comentário é obrigatório")
    private Long idComentario;

    private Long idComentarioParente;

    @Size(max = 2000, message = "O conteúdo deve ter no máximo 2000 caracteres")
    private String nmConteudo;

    @PastOrPresent(message = "A data de envio não pode ser futura")
    private LocalDate dtEnvio;

    @Min(value = 0, message = "O número de likes deve ser no mínimo 0")
    private Long nrLikes;

    private Long idPostagem;

    private Long idUsuario;
}
