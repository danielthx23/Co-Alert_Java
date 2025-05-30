package br.com.fiap.CoAlert.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "CA_CATEGORIA_DESASTRE")
public class CategoriaDesastre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoriaDesastre;

    @NotNull
    private String nmTitulo;

    @NotNull
    private String dsCategoria;

    private String nmTipo;
}
