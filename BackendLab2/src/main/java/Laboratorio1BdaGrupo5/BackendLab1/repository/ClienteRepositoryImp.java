package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.function.Supplier;

@Repository
public class ClienteRepositoryImp implements ClienteRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public Cliente getClienteById(Integer id_cliente) {
        String queryText = "SELECT * FROM cliente WHERE id_cliente = :id_cliente";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            Cliente cliente = connection.createQuery(queryText)
                    .addParameter("id_cliente", id_cliente)
                    .executeAndFetchFirst(Cliente.class);
            return cliente;
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener el cliente por Id", e);
        }
    }

    @Override
    public void createCliente(Cliente cliente) {

        String queryText = "INSERT INTO cliente ( nombre,  email, telefono, password, id_direccion) " +
                "VALUES (:nombre, :email, :telefono, :password, :id_direccion)";
        try (Connection connection = sql2o.open()) {
            connection.createQuery(queryText)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("password", cliente.getPassword())
                    .addParameter("id_direccion", cliente.getId_direccion())
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo crear el Cliente", e);
        }
    }

    @Override
    public void updateCliente(Cliente cliente) {
        String queryText = "UPDATE cliente SET nombre = :nombre, id_direccion = :direccion, " +
                "email = :email, telefono = :telefono WHERE id_cliente = :id_cliente";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            connection.createQuery(queryText)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("id_direccion", cliente.getId_direccion())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("id_cliente", cliente.getId_cliente())
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el Cliente", e);
        }
    }

    @Override
    public void deleteCliente(Integer id_cliente) {
        String queryText = "DELETE FROM cliente WHERE id_cliente = :id_cliente";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            connection.createQuery(queryText)
                    .addParameter("id_cliente", id_cliente)
                    .executeUpdate();
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al eliminar el Cliente", e);
        }
    }

    @Override
    public Cliente findByEmail(String email) {
        String queryText = "SELECT * FROM cliente WHERE email = :email";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            Cliente cliente = connection.createQuery(queryText)
                    .addParameter("email", email)
                    .executeAndFetchFirst(Cliente.class);
            return cliente;
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener el cliente por email", e);
        }
    }

    @Override
    public Cliente getClienteByEmail(String email) {
        String queryText = "SELECT * FROM cliente WHERE email = :email";
        try (Connection connection = sql2o.open()) {
            System.out.println("Conexión exitosa a la base de datos");
            Cliente cliente = connection.createQuery(queryText)
                    .addParameter("email", email)
                    .executeAndFetchFirst(Cliente.class);
            return cliente;
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener el cliente por email", e);
        }
    }

    @Override
    public <T> T registerSessionUserAndInsertOperation(Integer id_cliente, Supplier<T> operation) {
        String sessionQuery = "INSERT INTO session_users (session_id, id_cliente) " +
                "VALUES (pg_backend_pid(), :id_cliente) " +
                "ON CONFLICT (session_id) DO UPDATE SET id_cliente = :id_cliente";
        try (Connection connection = sql2o.beginTransaction()) {
            // Registrar usuario en session_users
            connection.createQuery(sessionQuery)
                    .addParameter("id_cliente", id_cliente)
                    .executeUpdate();

            // Ejecutar la operación y obtener el resultado
            T result = operation.get();

            // Confirmar la transacción
            connection.commit();

            return result;
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("No se pudo completar la operación", e);
        }
    }



}
