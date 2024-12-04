package Laboratorio1BdaGrupo5.BackendLab1.service;


import Laboratorio1BdaGrupo5.BackendLab1.models.DetalleOrden;
import Laboratorio1BdaGrupo5.BackendLab1.repository.DetalleOrdenRepository;
import Laboratorio1BdaGrupo5.BackendLab1.repository.DetalleOrdenRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenService {

    @Autowired
    DetalleOrdenRepositoryImp detalleOrdenRepository;

    public void createDetalleOrden(DetalleOrden detalleOrden, Integer idCliente){
        detalleOrdenRepository.createDetalleOrden(detalleOrden, idCliente);
    }

    public DetalleOrden getDetalleOrdenById(Integer idDetalleOrden){
        try{
            return detalleOrdenRepository.getDetalleOrden(idDetalleOrden);
        }catch (Exception e){
            throw new RuntimeException("No se encuentra este DetalleOrden en la base de datos");
        }
    }

    public List<DetalleOrden> getDetalleOrdenByOrdenId(Integer idOrden) {
        try {
            return detalleOrdenRepository.getDetalleOrdenByOrdenId(idOrden);
        } catch (Exception e) {
            throw new RuntimeException("No hay detalles asociados a esta Orden en la base de datos");
        }
    }

    public List<DetalleOrden> getAllDetalleOrden(){
        return detalleOrdenRepository.getAllDetalleOrden();
    }

    public void updateDetalleOrden(Integer idDetalle, DetalleOrden detalleOrden){
        DetalleOrden exist = detalleOrdenRepository.getDetalleOrden(idDetalle);
        if (exist == null) {
            throw new RuntimeException("El DetalleOrden con ID " + idDetalle + " no existe.");
        }
        detalleOrden.setIdDetalle(idDetalle);

        detalleOrdenRepository.updateDetalleOrden(detalleOrden);
    }

    public void deleteDetalleOrden(Integer idDetalleOrden){

        detalleOrdenRepository.deleteDetalleOrden(idDetalleOrden);
    }
}
