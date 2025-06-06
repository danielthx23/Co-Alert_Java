package br.com.fiap.CoAlert.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "CA_CATEGORIA_DESASTRE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_categoria_id", sequenceName = "seq_categoria_id", allocationSize = 1)
public class CategoriaDesastre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categoria_id")
    @Column(name = "ID_CATEGORIA_DESASTRE")
    private Long id;

    @Column(name = "NM_TITULO", nullable = false)
    private String titulo;

    @Column(name = "DS_CATEGORIA", nullable = false)
    private String categoria;

    @Column(name = "NM_TIPO", nullable = false)
    private String tipo;
}
