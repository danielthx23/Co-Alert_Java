package br.com.fiap.CoAlert.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoriaDesastreResponseDto {

    private Long idCategoriaDesastre;
    private String nmTitulo;
    private String dsCategoria;
    private String nmTipo;
}
