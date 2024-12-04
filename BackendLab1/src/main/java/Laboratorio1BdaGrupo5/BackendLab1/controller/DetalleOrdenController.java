package Laboratorio1BdaGrupo5.BackendLab1.controller;


import Laboratorio1BdaGrupo5.BackendLab1.models.DetalleOrden;
import Laboratorio1BdaGrupo5.BackendLab1.service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalleorden")
public class DetalleOrdenController {
    @Autowired
    DetalleOrdenService detalleOrdenService;
/*
    @PostMapping("/create")
    public ResponseEntity createDetalleOrden(@RequestBody DetalleOrden detalleOrden) {
        detalleOrdenService.createDetalleOrden(detalleOrden);
        return ResponseEntity.ok(null);
    }
*/
    @GetMapping("/get/{id}")
    public ResponseEntity<DetalleOrden> getDetalleOrdenById(@PathVariable Integer id) {
        return ResponseEntity.ok(detalleOrdenService.getDetalleOrdenById(id));
    }

    @GetMapping("/getByOrderId/{id}")
    public ResponseEntity<List<DetalleOrden>> getDetalleOrdenByOrdenId(@PathVariable Integer id) {
        return ResponseEntity.ok(detalleOrdenService.getDetalleOrdenByOrdenId(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DetalleOrden>> getAllDetalleOrden(){
        return ResponseEntity.ok(detalleOrdenService.getAllDetalleOrden());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateDetalleOrden(@PathVariable Integer id, @RequestBody DetalleOrden detalleOrden) {
        detalleOrdenService.updateDetalleOrden(id, detalleOrden);
        return ResponseEntity.ok(null);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDetalleOrden(@PathVariable Integer id) {
        detalleOrdenService.deleteDetalleOrden(id);
        return ResponseEntity.ok(null);
    }

}
