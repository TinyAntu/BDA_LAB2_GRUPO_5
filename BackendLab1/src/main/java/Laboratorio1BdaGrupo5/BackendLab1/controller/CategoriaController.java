package Laboratorio1BdaGrupo5.BackendLab1.controller;

import Laboratorio1BdaGrupo5.BackendLab1.models.Categoria;
import Laboratorio1BdaGrupo5.BackendLab1.models.Producto;
import Laboratorio1BdaGrupo5.BackendLab1.service.CategoriaService;
import Laboratorio1BdaGrupo5.BackendLab1.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @Autowired
    ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> getAllCategorias() {
        try {
            return ResponseEntity.ok(categoriaService.getAllCategorias());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Producto>> getProductosByCategoria(@PathVariable("id") Integer idCategoria) {
        try {
            List<Producto> categoria = productoService.getProductosPorCategoria(idCategoria);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> createCategoria(@RequestBody Categoria categoria) {
        try {
            categoriaService.createCategoria(categoria);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Categoria creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear la Categoria");
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updateCategoria(@RequestBody Categoria categoria) {
        try {
            categoriaService.updateCategoria(categoria);
            return ResponseEntity.ok("Categoria actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar la Categoria");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoria(@PathVariable Integer id) {
        try {
            categoriaService.deleteCategoria(id);
            return ResponseEntity.ok("Categoria eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar la Categoria");
        }
    }
}
