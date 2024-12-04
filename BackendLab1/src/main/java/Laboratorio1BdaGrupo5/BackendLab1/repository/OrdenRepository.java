package Laboratorio1BdaGrupo5.BackendLab1.repository;


import Laboratorio1BdaGrupo5.BackendLab1.models.Orden;

import java.util.List;

public interface OrdenRepository {
    List<Orden> getOrdenes(int limit, int offset);
    Orden getOrdenById(Integer id);
    List<Orden> getOrdenesById(int limit, int offset, int idCliente);
    Orden createOrden(Orden orden);
    void updateOrden(Orden orden);
    void deleteOrden(Integer idOrden);
}
