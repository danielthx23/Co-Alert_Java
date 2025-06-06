package br.com.fiap.CoAlert.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocalizacaoResponseDto {

    private Long idLocalizacao;
    private String nmBairro;
    private String nmLogradouro;
    private String nrNumero;
    private String nmCidade;
    private String nmEstado;
    private String nrCep;
    private String nmPais;
    private String dsComplemento;
}
