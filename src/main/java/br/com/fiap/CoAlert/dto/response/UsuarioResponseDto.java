package br.com.fiap.CoAlert.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioResponseDto {
    private Long idUsuario;
    private String nmUsuario;
    private String nmEmail;
}