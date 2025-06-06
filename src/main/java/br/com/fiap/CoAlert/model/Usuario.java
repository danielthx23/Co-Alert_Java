package br.com.fiap.CoAlert.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "CA_USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_usuario_id", sequenceName = "seq_usuario_id", allocationSize = 1)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario_id")
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NM_USUARIO", nullable = false)
    private String nmUsuario;

    @Column(name = "NR_SENHA", nullable = false)
    private String nrSenha;

    @Column(name = "NM_EMAIL", nullable = false, unique = true)
    private String nmEmail;
}
