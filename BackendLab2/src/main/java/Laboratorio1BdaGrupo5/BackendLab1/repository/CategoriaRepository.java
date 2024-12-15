package Laboratorio1BdaGrupo5.BackendLab1.repository;

import Laboratorio1BdaGrupo5.BackendLab1.models.Categoria;

import java.util.List;

public interface CategoriaRepository {
    Categoria getCategoriaById(Integer idCategoria);

    void createCategoria(Categoria categoria);

    void updateCategoria(Categoria categoria);

    void deleteCategoria(Integer id_categoria);

    List<Categoria> getAllCategorias();

    List<Categoria> searchCategoria(String name);
}
