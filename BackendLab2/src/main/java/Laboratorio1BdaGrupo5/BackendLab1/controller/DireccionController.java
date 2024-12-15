package Laboratorio1BdaGrupo5.BackendLab1.controller;

import Laboratorio1BdaGrupo5.BackendLab1.models.Direccion;
import Laboratorio1BdaGrupo5.BackendLab1.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/direccion")
public class DireccionController {

    @Autowired
    DireccionService direccionService;

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> getDireccionById(@PathVariable Integer id) {
        try {
            Direccion direccion = direccionService.getDireccionById(id);
            return ResponseEntity.ok(direccion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Direccion> createDireccion(
            @RequestParam Double latitud,
            @RequestParam Double longitud) {
        try {
            Direccion direccion = direccionService.createDireccion(latitud, longitud);
            return ResponseEntity.ok(direccion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<String> updateDireccion(
            @RequestParam Integer id,
            @RequestParam Double latitud,
            @RequestParam Double longitud) {
        try {
            direccionService.updateDireccion(id, latitud, longitud);
            return ResponseEntity.ok("Direccion actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDireccion(@PathVariable Integer id) {
        try {
            direccionService.deleteDireccion(id);
            return ResponseEntity.ok("Direccion eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar la direccion.");
        }
    }
}
