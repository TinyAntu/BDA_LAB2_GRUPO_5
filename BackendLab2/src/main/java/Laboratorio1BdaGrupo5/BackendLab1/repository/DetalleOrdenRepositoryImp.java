package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.DetalleOrden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class DetalleOrdenRepositoryImp implements DetalleOrdenRepository {

    @Autowired
    private Sql2o sql2o;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void createDetalleOrden(DetalleOrden detalleOrden, Integer idCliente) {
        clienteRepository.registerSessionUserAndInsertOperation(idCliente, () -> {
            String queryText = "INSERT INTO session_users (session_id, id_cliente)\n" +
                    "VALUES (pg_backend_pid(), :id_cliente)\n"+
                    "ON CONFLICT (session_id) DO UPDATE SET id_cliente = :id_cliente";
            try (Connection connection = sql2o.open()) {
                connection.createQuery(queryText)
                        .addParameter("id_cliente", idCliente)
                        .executeUpdate();
            } catch (Exception e) {
                System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
                throw new RuntimeException("No se pudo crear el Cliente", e);
            }
            queryText = "INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) " +
                    "VALUES (:idOrden, :idProducto, :cantidad, :precioUnitario)";
            try (Connection connection = sql2o.open()) {
                connection.createQuery(queryText)
                        .addParameter("idOrden", detalleOrden.getIdOrden())
                        .addParameter("idProducto", detalleOrden.getIdProducto())
                        .addParameter("cantidad", detalleOrden.getCantidad())
                        .addParameter("precioUnitario", detalleOrden.getPrecioUnitario())
                        .executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException("No se pudo crear el DetalleOrden", e);
            }
            return null;
        });
    }

    @Override
    public DetalleOrden getDetalleOrden(Integer idDetalleOrden) {
        String queryText = "SELECT * FROM detalle_orden WHERE id_detalle = :idDetalle";
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(queryText)
                    .addParameter("idDetalle", idDetalleOrden)
                    .addColumnMapping("ID_DETALLE", "idDetalle")
                    .addColumnMapping("ID_ORDEN", "idOrden")
                    .addColumnMapping("ID_PRODUCTO", "idProducto")
                    .addColumnMapping("CANTIDAD", "cantidad")
                    .addColumnMapping("PRECIO_UNITARIO", "precioUnitario");
            return query.executeAndFetchFirst(DetalleOrden.class);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el DetalleOrden", e);
        }
    }

    @Override
    public List<DetalleOrden> getDetalleOrdenByOrdenId(Integer idOrden) {
        String queryText = "SELECT * FROM detalle_orden WHERE id_orden = :idOrden";
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery(queryText)
                    .addParameter("idOrden", idOrden)
                    .addColumnMapping("ID_DETALLE", "idDetalle")
                    .addColumnMapping("ID_ORDEN", "idOrden")
                    .addColumnMapping("ID_PRODUCTO", "idProducto")
                    .addColumnMapping("CANTIDAD", "cantidad")
                    .addColumnMapping("PRECIO_UNITARIO", "precioUnitario");
            return query.executeAndFetch(DetalleOrden.class);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el DetalleOrden", e);
        }
    }

    @Override
    public List<DetalleOrden> getAllDetalleOrden(){
        String queryText = "SELECT * FROM detalle_orden";
        try(Connection connection =sql2o.open()){
            Query query = connection.createQuery(queryText)
                    .addColumnMapping("ID_DETALLE", "idDetalle")
                    .addColumnMapping("ID_ORDEN", "idOrden")
                    .addColumnMapping("ID_PRODUCTO", "idProducto")
                    .addColumnMapping("CANTIDAD", "cantidad")
                    .addColumnMapping("PRECIO_UNITARIO", "precioUnitario");
            List<DetalleOrden> detalleOrden = query.executeAndFetch(DetalleOrden.class);
            return detalleOrden;
        }
        catch (Exception e){
            throw new RuntimeException("No se logro obtener todos los DetalleOrden");
        }
    }

    @Override
    public void updateDetalleOrden(DetalleOrden detalleOrden) {
        String queryText = "UPDATE detalle_orden SET id_orden = :idOrden, id_producto = :idProducto, " +
                "cantidad = :cantidad, precio_unitario = :precioUnitario WHERE id_detalle = :idDetalle";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(queryText)
                    .addParameter("idDetalle", detalleOrden.getIdDetalle())
                    .addParameter("idOrden", detalleOrden.getIdOrden())
                    .addParameter("idProducto", detalleOrden.getIdProducto())
                    .addParameter("cantidad", detalleOrden.getCantidad())
                    .addParameter("precioUnitario", detalleOrden.getPrecioUnitario())
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo actualizar el DetalleOrden", e);
        }
    }

    @Override
    public void deleteDetalleOrden(Integer idDetalleOrden) {
        String queryText = "DELETE FROM detalle_orden WHERE id_detalle = :idDetalle";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(queryText)
                    .addParameter("idDetalle", idDetalleOrden)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo eliminar el DetalleOrden", e);
        }
    }
}

