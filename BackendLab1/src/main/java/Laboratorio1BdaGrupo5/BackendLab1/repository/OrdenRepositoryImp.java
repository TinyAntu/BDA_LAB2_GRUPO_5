package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class OrdenRepositoryImp implements OrdenRepository {
    @Autowired
    private Sql2o sql2o;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Orden> getOrdenes(int limit, int offset) {
        String queryText = "SELECT * FROM orden LIMIT :limit OFFSET :offset";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            return connection.createQuery(queryText)
                    .addParameter("limit", limit)
                    .addParameter("offset", offset)
                    .executeAndFetch(Orden.class);
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener ordenes", e);
        }
    }

    @Override
    public List<Orden> getOrdenesById(int limit, int offset, int idCliente) {
        String queryText = "SELECT id_orden AS idOrden, fecha_orden AS fechaOrden, estado, id_cliente AS idCliente, total " +
                "FROM orden " +
                "WHERE id_cliente = :idCliente " +
                "LIMIT :limit OFFSET :offset";

        try (Connection connection = sql2o.open()) {
            System.out.println("Conexion exitosa a la base de datos");
            return connection.createQuery(queryText)
                    .addParameter("limit", limit)
                    .addParameter("offset", offset)
                    .addParameter("idCliente", idCliente) // Cambiado a coincidir con el alias
                    .executeAndFetch(Orden.class);
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener ordenes", e);
        }
    }

    @Override
    public Orden getOrdenById(Integer idOrden) {
        String queryText = "SELECT * FROM orden WHERE id_orden = :idOrden";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            Orden orden = connection.createQuery(queryText)
                    .addParameter("idOrden", idOrden)
                    .executeAndFetchFirst(Orden.class);
            return orden;
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener la orden por ID", e);
        }
    }

    @Override
    public Orden createOrden(Orden orden) {
        clienteRepository.registerSessionUserAndInsertOperation(orden.getIdCliente(), () -> {
            String queryText = "INSERT INTO orden (fecha_orden, estado, id_cliente, total) " +
                    "VALUES (:fecha_orden, :estado, :id_cliente, :total)";
            try (Connection connection = sql2o.open()) {
                System.out.println("Conexión exitosa a la base de datos");

                // Convertir estado a minúsculas
                orden.setEstado(orden.getEstado().toLowerCase());

                // Insertar la orden
                connection.createQuery(queryText)
                        .addParameter("fecha_orden", orden.getFechaOrden())
                        .addParameter("estado", orden.getEstado())
                        .addParameter("id_cliente", orden.getIdCliente())
                        .addParameter("total", orden.getTotal())
                        .executeUpdate();

                // Recuperar el id generado para la nueva orden
                String queryGetId = "SELECT currval('orden_id_orden_seq')";  // Cambia el nombre de la secuencia según tu base de datos
                Integer idGenerado = connection.createQuery(queryGetId)
                        .executeScalar(Integer.class);

                // Asignar el id generado a la orden
                orden.setIdOrden(idGenerado);

                return orden; // Devuelve la orden creada
            } catch (Exception e) {
                System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
                throw new RuntimeException("Error al crear la orden", e);
            }
        }

        );
        return orden;
    }


    @Override
    public void updateOrden(Orden orden) {
        String queryText = "UPDATE orden SET fecha_orden = :fecha_orden, estado = :estado, " +
                "id_cliente = :id_cliente, total = :total WHERE id_orden = :idOrden";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            connection.createQuery(queryText)
                    .addParameter("fecha_orden", orden.getFechaOrden())
                    .addParameter("estado", orden.getEstado())
                    .addParameter("id_cliente", orden.getIdCliente())
                    .addParameter("total", orden.getTotal())
                    .addParameter("idOrden", orden.getIdOrden())
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el orden", e);
        }
    }

    @Override
    public void deleteOrden(Integer idOrden) {
        String queryText = "DELETE FROM orden WHERE id_orden = :idOrden";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            connection.createQuery(queryText)
                    .addParameter("idOrden", idOrden)
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al eliminar la orden", e);
        }
    }

    @Override
    public List<Orden> filtrarOrdenesEnviadasDentro10km(int idAlmacen){
        String query = """
                SELECT o.*
                FROM orden o
                JOIN cliente c ON o.id_cliente = c.id_cliente
                JOIN direccion d_cliente ON c.id_direccion = d_cliente.id_direccion
                JOIN almacen a ON a.id_almacen = :idAlmacen  -- Cambia 1 por el ID del almacén que quieras probar
                JOIN direccion d_almacen ON a.id_direccion = d_almacen.id_direccion
                WHERE
                    ST_DWithin(
                        d_cliente.geom::geography,   -- Transformación a SRID métrico
                        d_almacen.geom::geography,  -- Transformación a SRID métrico
                        10000                                -- Radio en metros (10 km)
                    )
                    AND o.estado = 'enviada';  -- Filtra solo órdenes con estado 'enviada'         
                """;
        try {
            return sql2o.open()
                    .createQuery(query)
                    .addParameter("idAlmacen", idAlmacen)
                    .executeAndFetch(Orden.class);
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al filtrar órdenes", e);
        }
    }
}
