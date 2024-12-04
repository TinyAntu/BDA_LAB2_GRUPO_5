package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.PriceHistory;
import Laboratorio1BdaGrupo5.BackendLab1.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Connection;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepositoryImp implements ProductoRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Producto> getProductos(int limit, int offset) {
        String queryText = "SELECT id_producto AS idProducto, nombre, descripcion, precio, stock, estado, id_categoria AS idCategoria FROM producto LIMIT :limit OFFSET :offset";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            return connection.createQuery(queryText)
                    .addParameter("limit", limit)
                    .addParameter("offset", offset)
                    .executeAndFetch(Producto.class);
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener productos", e);
        }
    }

    @Override
    public List<Producto> getProductosSearch(int limit, int offset, String search) {
        search = search.toLowerCase();
        String queryText = "SELECT id_producto AS idProducto, nombre, descripcion, precio, stock, estado, id_categoria AS idCategoria " +
                "FROM producto WHERE LOWER(nombre) LIKE CONCAT('%', :search, '%') LIMIT :limit OFFSET :offset";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            return connection.createQuery(queryText)
                    .addParameter("limit", limit)
                    .addParameter("offset", offset)
                    .addParameter("search", search)
                    .executeAndFetch(Producto.class);
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener productos", e);
        }
    }

    @Override
    public Producto getProductoById(Integer idProducto) {
        String queryText = "SELECT id_producto AS idProducto, nombre, descripcion, precio, stock, estado, id_categoria AS idCategoria FROM producto WHERE id_producto = :idProducto";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            Producto producto = connection.createQuery(queryText)
                    .addParameter("idProducto", idProducto)
                    .executeAndFetchFirst(Producto.class);
            return producto;
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener el producto por ID", e);
        }
    }
    @Override
    public List<Producto> getProductosPorCategoria(Integer idCategoria) {
        String query = "SELECT  id_producto AS idProducto, nombre, descripcion, precio, stock, estado, id_categoria AS idCategoria FROM producto WHERE id_categoria = :idCategoria";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(query)
                    .addParameter("idCategoria", idCategoria)
                    .executeAndFetch(Producto.class);
        }
    }

    @Override
    public List<Producto> getProductosPorCategoria(String nombre) {
        nombre = nombre.toLowerCase();
        String query = "SELECT  id_producto AS idProducto, producto.nombre, descripcion, precio, stock, estado, producto.id_categoria AS idCategoria " +
        "FROM producto, categoria WHERE producto.id_categoria = categoria.id_categoria AND (LOWER(categoria.nombre) LIKE :nombre)";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(query)
                    .addParameter("nombre", "%" + nombre + "%")
                    .executeAndFetch(Producto.class);
        }
    }


    @Override
    public void createProducto(Producto producto) {
        String queryText = "INSERT INTO producto (nombre, descripcion, precio, stock, estado) " +
                "VALUES (:nombre, :descripcion, :precio, :stock, :estado)";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            producto.setEstado(producto.getEstado().toLowerCase());
            connection.createQuery(queryText)
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("descripcion", producto.getDescripcion())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("estado", producto.getEstado())
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al crear el producto", e);
        }
    }

    @Override
    public void updateProducto(Producto producto) {
        String queryText = "UPDATE producto SET nombre = :nombre, descripcion = :descripcion, " +
                "precio = :precio, stock = :stock, estado = :estado WHERE id_producto = :idProducto";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            connection.createQuery(queryText)
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("descripcion", producto.getDescripcion())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("estado", producto.getEstado())
                    .addParameter("idProducto", producto.getIdProducto())
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el producto", e);
        }
    }

    @Override
    public void deleteProducto(Integer idProducto) {
        String queryText = "DELETE FROM producto WHERE id_producto = :idProducto";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            connection.createQuery(queryText)
                    .addParameter("idProducto", idProducto)
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al eliminar el producto", e);
        }
    }

    @Override
    public long getTotalCount() {
        String queryText = "SELECT COUNT(*) FROM producto";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            return connection.createQuery(queryText)
                    .executeScalar(Long.class);
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener el total de productos", e);
        }
    }

    @Override
    public String getProductoName(Integer idProducto) {
        String queryText = "SELECT producto.nombre FROM producto WHERE id_producto = :idProducto";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(queryText)
                    .addParameter("idProducto", idProducto)
                    .executeScalar(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el nombre del producto", e);
        }
    }

    public Producto getMostVariablePriceProduct() {
        String queryText = "WITH last_orders AS (" +
                "    SELECT id_orden " +
                "    FROM orden " +
                "    ORDER BY fecha_orden DESC " +
                "    LIMIT 100" +
                "), " +
                "price_variability AS (" +
                "    SELECT " +
                "        d.id_producto, " +
                "        STDDEV(d.precio_unitario) AS price_variability " +
                "    FROM detalle_orden d " +
                "    JOIN last_orders l ON d.id_orden = l.id_orden " +
                "    GROUP BY d.id_producto " +
                "    HAVING STDDEV(d.precio_unitario) IS NOT NULL " +
                ") " +
                "SELECT p.id_producto AS idProducto, p.nombre, p.descripcion, " +
                "p.precio, p.stock, p.estado, p.id_categoria AS idCategoria " +
                "FROM producto p " +
                "JOIN price_variability v ON p.id_producto = v.id_producto " +
                "ORDER BY v.price_variability DESC " +
                "LIMIT 1;";
        try (Connection connection = sql2o.open()) {
            Producto product = connection.createQuery(queryText)
                    .executeAndFetchFirst(Producto.class);
            if (product == null) {
                throw new RuntimeException("No product with price variability found.");
            }
            return product;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching product with most variable price", e);
        }
    }

    @Override
    public List<PriceHistory> getPriceHistory(Integer productoId) {
        String queryText = "SELECT DISTINCT d.precio_unitario AS precio, o.fecha_orden AS fecha " +
                "FROM orden o JOIN detalle_orden d ON o.id_orden = d.id_orden " +
                "WHERE d.id_producto = :productoId";
        try (Connection connection = sql2o.open()) {
            return connection.createQuery(queryText)
                    .addParameter("productoId", productoId)
                    .executeAndFetch(PriceHistory.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener historial de precios");
        }
    }

    @Override
    public Producto getFirstProducto() {
        String queryText = "SELECT id_producto AS idProducto, nombre, descripcion, precio, stock, estado, id_categoria AS idCategoria FROM producto";
        try (Connection connection = sql2o.open()) {
            Producto producto = connection.createQuery(queryText)
                    .executeAndFetchFirst(Producto.class);
            return producto;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el producto por ID", e);
        }
    }
}