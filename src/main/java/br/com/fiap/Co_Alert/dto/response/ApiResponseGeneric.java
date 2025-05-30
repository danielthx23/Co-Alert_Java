package br.com.fiap.Co_Alert.dto.response;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ApiResponseGeneric<T> {
    private String mensagem;
    private T dados;
}
