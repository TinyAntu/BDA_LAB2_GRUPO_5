package Laboratorio1BdaGrupo5.BackendLab1.service;

import Laboratorio1BdaGrupo5.BackendLab1.models.Direccion;
import Laboratorio1BdaGrupo5.BackendLab1.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionService {
    @Autowired
    DireccionRepository direccionRepository;

    public Direccion getDireccionById(Integer id) {
        try {
            Direccion direccion = direccionRepository.getDireccionById(id);
            if (direccion != null) {
                return direccion;
            } else {
                throw new RuntimeException("Direccion no encontrada");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la direccion", e);
        }
    }

    public Direccion createDireccion(String tipo, Double latitud, Double longitud) {
        try {
            return direccionRepository.createDireccion(tipo, latitud, longitud);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la direccion", e);
        }
    }

    public void updateDireccion(Integer id, String tipo, Double latitud, Double longitud) {
        try {
            direccionRepository.updateDireccion(id, tipo, latitud, longitud);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la direccion", e);
        }
    }

    public void deleteDireccion(Integer id) {
        try {
            if (getDireccionById(id) != null) {
                direccionRepository.deleteDireccion(id);
            } else {
                throw new RuntimeException("La direccion no existe en la base de datos.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la direccion.");
        }
    }
}
