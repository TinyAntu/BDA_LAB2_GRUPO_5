package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.Direccion;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Map;

@Repository
public class DireccionRepositoryImp implements DireccionRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public Direccion getDireccionById(Integer id) {
        String queryText = "SELECT id_direccion, ST_AsText(geom) AS geom FROM direccion WHERE id_direccion = :id";

        try (Connection connection = sql2o.open()) {
            Map<String, Object> row = connection.createQuery(queryText)
                    .addParameter("id", id)
                    .executeAndFetchTable()
                    .asList()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró la dirección con ID: " + id));

            // Convert WKT to Geometry
            String wkt = (String) row.get("geom");
            GeometryFactory geometryFactory = new GeometryFactory();
            WKTReader reader = new WKTReader(geometryFactory);
            Geometry geom = reader.read(wkt);

            // Return the Direccion object
            return new Direccion(
                    (Integer) row.get("id_direccion"),
                    geom,
                    (String) row.get("formatted_address")
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la dirección con ID: " + id, e);
        }
    }

    @Override
    public Direccion createDireccion(Double latitud, Double longitud, String formatted_address) {
        String queryText = "INSERT INTO direccion(geom, formatted_address) " +
                "VALUES (ST_SetSRID(ST_Point(:longitud, :latitud), 4326), :formatted_address)";

        try (Connection connection = sql2o.beginTransaction()) {
            // Insert into the database and fetch the generated ID
            Integer id = connection.createQuery(queryText)
                    .addParameter("latitud", latitud)
                    .addParameter("longitud", longitud)
                    .addParameter("formatted_address", formatted_address)
                    .executeUpdate()
                    .getKey(Integer.class);

            if (id == null) {
                throw new RuntimeException("No se pudo obtener la ID de la dirección ingresada.");
            }

            // Create the Geometry object in Java
            GeometryFactory geometryFactory = new GeometryFactory();
            Geometry geom = geometryFactory.createPoint(new Coordinate(longitud, latitud));
            geom.setSRID(4326);

            // Commit the transaction
            connection.commit();

            // Return the created Direccion object
            return new Direccion(id, geom, formatted_address);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo registrar la dirección", e);
        }
    }

    @Override
    public void updateDireccion(Integer id, Double latitud, Double longitud, String formatted_address) {
        String queryText = "UPDATE direccion " +
                "SET geom = ST_SetSRID(ST_Point(:longitud, :latitud), 4326) " +
                "SET formatted_address = :formatted_address " +
                "WHERE id_direccion = :id_direccion";
        try (Connection connection = sql2o.beginTransaction()) {
            connection.createQuery(queryText)
                    .addParameter("latitud", latitud)
                    .addParameter("longitud", longitud)
                    .addParameter("id_direccion", id)
                    .executeUpdate();
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la direccion.", e);
        }
    }

    @Override
    public void deleteDireccion(Integer id) {
        String queryText = "DELETE FROM direccion WHERE id_direccion = :id_direccion";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(queryText)
                    .addParameter("id_direccion", id)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la direccion", e);
        }
    }

    public String findFormattedAddressByClienteId(Integer id_cliente) {
        String sql = """
            SELECT d.formatted_address
            FROM cliente c
            JOIN direccion d ON c.id_direccion = d.id_direccion
            WHERE c.id_cliente = :id_cliente;
        """;

        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .addParameter("id_cliente", id_cliente)
                    .executeAndFetchFirst(String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la dirección formateada", e);
        }
    }
}
