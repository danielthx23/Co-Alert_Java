package br.com.fiap.CoAlert.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocalizacaoSaveRequestDto {

    @NotBlank(message = "O bairro não pode estar em branco")
    private String nmBairro;

    @NotBlank(message = "O logradouro não pode estar em branco")
    private String nmLogradouro;

    @NotBlank(message = "O número é obrigatório")
    private String  nrNumero;

    @NotBlank(message = "A cidade não pode estar em branco")
    private String nmCidade;

    @NotBlank(message = "O estado não pode estar em branco")
    private String nmEstado;

    @NotBlank(message = "O CEP não pode estar em branco")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Formato de CEP inválido")
    private String nrCep;

    @NotBlank(message = "O país não pode estar em branco")
    private String nmPais;

    private String dsComplemento;
}
