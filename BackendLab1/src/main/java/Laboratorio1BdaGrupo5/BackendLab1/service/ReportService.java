package Laboratorio1BdaGrupo5.BackendLab1.service;

import Laboratorio1BdaGrupo5.BackendLab1.models.Report;
import Laboratorio1BdaGrupo5.BackendLab1.repository.ReportRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepositoryImp reportRepository;

    public List<Report> getReport() {
        try {
            System.out.println("2");
            return reportRepository.getReport();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de categorias", e);
        }
    }
}
