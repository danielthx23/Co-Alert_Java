package br.com.fiap.CoAlert.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PostagemSaveRequestDto {

    @NotBlank(message = "O título não pode estar em branco")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    private String nmTitulo;

    @NotBlank(message = "O conteúdo não pode estar em branco")
    @Size(max = 5000, message = "O conteúdo deve ter no máximo 5000 caracteres")
    private String nmConteudo;

    @NotNull(message = "A data de envio é obrigatória")
    @PastOrPresent(message = "A data de envio não pode ser futura")
    private LocalDate dtEnvio;

    @NotNull(message = "O número de likes é obrigatório")
    @Min(value = 0, message = "O número de likes deve ser no mínimo 0")
    private Integer nrLikes;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long idUsuario;

    @NotNull(message = "O ID da categoria de desastre é obrigatório")
    private Long idCategoriaDesastre;

    @NotNull(message = "O ID da localização é obrigatório")
    private Long idLocalizacao;

}