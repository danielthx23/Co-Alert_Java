package br.com.fiap.CoAlert.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class LikeSaveRequestDto {

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long idUsuario;

    private Long idPostagem;

    private Long idComentario;

    @NotNull(message = "A data do like é obrigatória")
    private LocalDate dtLike;
} 