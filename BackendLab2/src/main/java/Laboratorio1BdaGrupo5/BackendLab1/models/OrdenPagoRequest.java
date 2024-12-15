package Laboratorio1BdaGrupo5.BackendLab1.models;

import lombok.Data;

import java.util.List;

@Data
public class OrdenPagoRequest {
    private List<DetalleOrden> detalles;
    private Orden orden;
}

