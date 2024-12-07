package Laboratorio1BdaGrupo5.BackendLab1.controller;

import Laboratorio1BdaGrupo5.BackendLab1.models.Almacen;
import Laboratorio1BdaGrupo5.BackendLab1.service.AlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacen")
public class AlmacenController {
    @Autowired
    AlmacenService almacenService;

    @GetMapping
    public ResponseEntity<List<Almacen>> getAllAlmacen(){
        try{
            return ResponseEntity.ok(almacenService.getAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Almacen> getAlmacenById(@PathVariable Integer id){
        try{
            return ResponseEntity.ok(almacenService.getAlmacenById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> createAlmacen(@RequestBody Almacen almacen){
        try{
            almacenService.createAlmacen(almacen);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Almacen creado exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear almacen");
        }
    }

    @PostMapping("/update")
    ResponseEntity<String> updateAlmacen(@RequestBody Almacen almacen){
        try{
            almacenService.updateAlmacen(almacen);
            return ResponseEntity.ok("Almacen actualizado exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el Almacen");
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteAlmacenById(@PathVariable Integer id){
        try{
            almacenService.deleteAlmacen(id);
            return ResponseEntity.ok("Almacen eliminado exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar el almacen");
        }
    }
}
