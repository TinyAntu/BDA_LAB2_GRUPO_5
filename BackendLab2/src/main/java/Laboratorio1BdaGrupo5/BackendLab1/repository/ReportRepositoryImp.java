package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.Producto;
import Laboratorio1BdaGrupo5.BackendLab1.models.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ReportRepositoryImp implements ReportRepository{
    @Autowired
    Sql2o sql2o;

    @Override
    public List<Report> getReport() {
        String queryText = "SELECT usuario, inserts, updates, deletes, total " +
                "FROM reporte_act_client()";
        try (Connection connection = sql2o.open()) {
            System.out.println("3");
            System.out.println("Conexión exitosa a la base de datos");
            return connection.createQuery(queryText)
                    .executeAndFetch(Report.class);
        } catch (Exception e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al obtener reporte", e);
        }
    }
}
