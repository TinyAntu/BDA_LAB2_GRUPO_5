package Laboratorio1BdaGrupo5.BackendLab1.models;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrden {

    @Column(name = "id_detalle")
    private Integer idDetalle;

    @Column(name = "id_orden")
    private Integer idOrden;

    @Column(name = "id_producto")
    private Integer idProducto;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    public DetalleOrden(Integer idOrden, Integer idProducto){
        this.idOrden = idOrden;
        this.idProducto = idProducto;
    }
}
