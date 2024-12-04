package Laboratorio1BdaGrupo5.BackendLab1.service;

import Laboratorio1BdaGrupo5.BackendLab1.models.Categoria;
import Laboratorio1BdaGrupo5.BackendLab1.repository.CategoriaRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    CategoriaRepositoryImp categoriaRepository;

    public List<Categoria> getAllCategorias() {
        try {
            return categoriaRepository.getAllCategorias();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de categorias", e);
        }
    }

    public Categoria getCategoriaById(Integer idCategoria) {
        try {
            Categoria categoria = categoriaRepository.getCategoriaById(idCategoria);
            if (categoria != null) {
                return categoria;
            } else {
                throw new RuntimeException("Categoria no encontrada");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la Categoria", e);
        }
    }

    public void createCategoria(Categoria categoria) {
        try {
            categoriaRepository.createCategoria(categoria);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la Categoria", e);
        }
    }

    public void updateCategoria(Categoria categoria) {
        try {
            if (getCategoriaById(categoria.getIdCategoria()) != null) {
                categoriaRepository.updateCategoria(categoria);
            } else {
                throw new RuntimeException("La categoria no existe en la base de datos");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la Categoria", e);
        }
    }

    public void deleteCategoria(Integer idCategoria) {
        try {
            if (getCategoriaById(idCategoria) != null) {
                categoriaRepository.deleteCategoria(idCategoria);
            } else {
                throw new RuntimeException("La categoria no existe en la base de datos");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la categoria", e);
        }
    }

    public List<Categoria> searchCategoria(String categoria) {
        try {
            return categoriaRepository.searchCategoria(categoria);
        } catch (Exception e){
            throw new RuntimeException("Error al buscar la categoria", e);
        }
    }
}
