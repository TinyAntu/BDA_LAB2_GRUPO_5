package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.Direccion;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

public interface DireccionRepository {

    Direccion getDireccionById(Integer id);

    Direccion createDireccion(Double latitud, Double longitud);

    void updateDireccion(Integer id, Double latitud, Double longitud);

    void deleteDireccion(Integer id);
}
