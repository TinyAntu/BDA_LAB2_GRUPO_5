package Laboratorio1BdaGrupo5.BackendLab1.models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private Integer id_cliente;

    private String nombre;
    private String email;
    private String telefono;
    private String password; //Encriptada con BCrypt
    @Column(name = "id_direccion")
    private String idDireccion;

}
