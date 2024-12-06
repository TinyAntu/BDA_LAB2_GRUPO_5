package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.Direccion;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

public interface DireccionRepository {

    Direccion getDireccionById(Integer id);

    Direccion createDireccion(String tipo, Double latitud, Double longitud);

    void updateDireccion(Integer id, String tipo, Double latitud, Double longitud);

    void deleteDireccion(Integer id);
}
