package Laboratorio1BdaGrupo5.BackendLab1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Asegúrate de que la anotación @Entity esté presente
@Table(name = "categoria") // Sin comillas dobles
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria") // Sin comillas dobles
    private Integer idCategoria;

    private String nombre;
}

