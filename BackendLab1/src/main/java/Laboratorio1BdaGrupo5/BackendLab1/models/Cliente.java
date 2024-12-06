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
    private String direccion; //TODO : lo cambie en frontend a id_direccion ahora es foreign key (aprovecho de probar el todo)
    private String email;
    private String telefono;
    private String password; //Encriptada con BCrypt
}
