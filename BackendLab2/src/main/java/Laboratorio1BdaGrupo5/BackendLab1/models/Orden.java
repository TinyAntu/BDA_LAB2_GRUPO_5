package Laboratorio1BdaGrupo5.BackendLab1.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orden {

    @Column(name = "id_orden")
    private Integer id_orden;

    @Column(name = "fecha_orden")
    private Date fecha_orden;

    private String estado;

    @Column(name = "id_cliente")
    private Integer id_cliente;

    private Float total;
}
