package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.Cliente;

import java.util.function.Supplier;

public interface ClienteRepository {

    Cliente getClienteById(Integer idCliente);

    void createCliente(Cliente cliente);

    void updateCliente(Cliente cliente);

    void deleteCliente(Integer idCliente);

    Cliente findByEmail(String email);

    Cliente getClienteByEmail(String email);

    public <T> T registerSessionUserAndInsertOperation(Integer id_cliente, Supplier<T> operation);
}
