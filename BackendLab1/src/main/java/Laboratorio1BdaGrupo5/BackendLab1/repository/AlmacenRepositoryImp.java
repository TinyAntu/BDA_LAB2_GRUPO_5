package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.Almacen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class AlmacenRepositoryImp implements AlmacenRepository{
    @Autowired
    Sql2o sql2o;

    @Override
    public List<Almacen> getAll() {
        String queryText = "SELECT * FROM almacen";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            return connection.createQuery(queryText)
                    .executeAndFetch(Almacen.class);
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la lista de almacenes", e);
        }
    }
    @Override
    public void createAlmacen(Almacen almacen) {
        String queryText = "INSERT INTO almacen (nombre, id_direccion, capacidad, estado) " +
                "VALUES (:nombre, :idDireccion, :capacidad, :estado)";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(queryText)
                    .addParameter("nombre", almacen.getNombre())
                    .addParameter("idDireccion", almacen.getId_direccion())
                    .addParameter("capacidad", almacen.getCapacidad())
                    .addParameter("estado", almacen.getEstado())
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo crear el Almacén", e);
        }
    }

    @Override
    public void updateAlmacen(Almacen almacen){
        String queryText = "UPDATE almacen SET nombre = :nombre, id_direccion = :idDireccion, " +
                "capacidad = :capacidad, estado = :estado" +
                " WHERE id_almacen = :id_almacen";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(queryText)
                    .addParameter("id_almacen", almacen.getId_almacen())
                    .addParameter("nombre", almacen.getNombre())
                    .addParameter("idDireccion", almacen.getId_direccion())
                    .addParameter("capacidad", almacen.getCapacidad())
                    .addParameter("estado", almacen.getEstado())
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo actualizar el Almacen", e);
        }
    }
    @Override
    public void deleteAlmacen(Integer id) {
        String queryText = "DELETE FROM almacen WHERE id_almacen = :idAlmacen";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(queryText)
                    .addParameter("idAlmacen", id)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo eliminar el Almacen", e);
        }
    }
    @Override
    public Almacen getAlmacenById(Integer id) {
        String queryText = "SELECT * FROM almacen WHERE id_almacen = :idAlmacen";
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(queryText)
                    .addParameter("idAlmacen", id);
            return query.executeAndFetchFirst(Almacen.class);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el Almacen", e);
        }
    }
}
