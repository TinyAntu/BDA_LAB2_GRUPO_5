package Laboratorio1BdaGrupo5.BackendLab1.service;

import Laboratorio1BdaGrupo5.BackendLab1.models.Almacen;
import Laboratorio1BdaGrupo5.BackendLab1.repository.AlmacenRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlmacenService {
    @Autowired
    AlmacenRepositoryImp almacenRepository;

    public List<Almacen> getAll() {
        return almacenRepository.getAll();
    }

    public void createAlmacen(Almacen almacen) {
        try{
            almacenRepository.createAlmacen(almacen);
        }catch (Exception e){
            throw new RuntimeException("Error al crear el almacen", e);
        }
    }

    public void updateAlmacen(Almacen almacen){
        try{
            almacenRepository.updateAlmacen(almacen);
        }catch (Exception e){
            throw new RuntimeException("Error al actualizar el almacen", e);
        }
    }

    public void deleteAlmacen(Integer id) {
        try{
            if(getAlmacenById(id) != null){
                almacenRepository.deleteAlmacen(id);
            }else {
                throw new RuntimeException("El almacen no existe en la base de datos");
            }
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el almacen", e);
        }
    }

    public Almacen getAlmacenById(Integer id) {
        try{
            Almacen almacen = almacenRepository.getAlmacenById(id);
            if(almacen != null){
                return almacen;
            }else{
                throw new RuntimeException("Almacen no encontrado");
            }
        }catch (Exception e){
            throw new RuntimeException("Error al obtener el Almacen", e);
        }
    }

    public Almacen getAlmacenMasCercano(Integer idCliente) {
        return almacenRepository.findAlmacenMasCercano(idCliente);
    }
}
