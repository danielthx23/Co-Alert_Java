package br.com.fiap.CoAlert.dto.response;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ApiResponseGeneric<T> {
    private String mensagem;
    private T dados;
}
