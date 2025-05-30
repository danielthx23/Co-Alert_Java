package br.com.fiap.Co_Alert.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoriaDesastreSaveRequestDto {

    @NotBlank(message = "O título não pode estar em branco")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    private String nmTitulo;

    @NotBlank(message = "A descrição não pode estar em branco")
    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
    private String dsCategoria;

    @Size(max = 255, message = "O tipo deve ter no máximo 255 caracteres")
    private String nmTipo;
}
