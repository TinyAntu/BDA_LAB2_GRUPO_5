package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.DetalleOrden;

import java.util.List;

public interface DetalleOrdenRepository {
    // Create
    void createDetalleOrden(DetalleOrden detalleOrden, Integer idCliente);

    // Read
    DetalleOrden getDetalleOrden(Integer idDetalleOrden);

    List<DetalleOrden> getDetalleOrdenByOrdenId(Integer idOrden);

    List<DetalleOrden> getAllDetalleOrden();

    // Update
    void updateDetalleOrden(DetalleOrden detalleOrden);

    // Delete
    void deleteDetalleOrden(Integer idDetalleOrden);


}
