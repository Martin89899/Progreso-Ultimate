package com.progreso.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.progreso.dto.Progreso;
import com.progreso.dto.ProgresoModel;
import com.progreso.assembler.ProgresoModelAssembler;
import com.progreso.services.ProgresoService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v0/progreso")
@Tag(name = "Progreso", description = "Operacion relacionado con el progreso")
public class ProgresoController {

    @Autowired
    private ProgresoService progresoService;

    @Autowired
    private ProgresoModelAssembler assembler;

    // GET: todos los progresos
    @GetMapping("")
    public ResponseEntity<?> obtenerProgreso() {
        List<Progreso> progresos = progresoService.obtenerProgreso();
        if (progresos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay registros de progreso.");
        }
        return ResponseEntity.ok(progresos);
    }

    //GET:por ID (renombrado a buscaProgreso)
    @GetMapping("/{idEstudiante}")
    public ResponseEntity<?> buscaProgreso(@PathVariable Integer idEstudiante) {
        Progreso progreso = progresoService.getProgresos(idEstudiante);
        if (progreso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso no encontrado");
        }
        ProgresoModel model = assembler.toModel(progreso);
        return ResponseEntity.ok(model);
    }

    // POST:crear progreso
    @PostMapping("")
    public ResponseEntity<?> guardar(@RequestBody Progreso pro) {
        Progreso guardado = progresoService.guardaProgreso(pro);
        ProgresoModel model = assembler.toModel(guardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    //PUT: actualizar progreso (renombrado a actualizarProgreso)
    @PutMapping("/{idEstudiante}")
    public ResponseEntity<?> actualizarProgreso(@RequestBody Progreso pro, @PathVariable Integer idEstudiante) {
        Progreso actualizado = progresoService.actualizarProgreso(pro, idEstudiante);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo actualizar: no existe el ID.");
        }
        ProgresoModel model = assembler.toModel(actualizado);
        return ResponseEntity.ok(model);
    }

    // DELETE: eliminar progreso
    @DeleteMapping("/{idEstudiante}")
    public ResponseEntity<Void> eliminarProgreso(@PathVariable Integer idEstudiante) {
        boolean eliminado = progresoService.eliminarProgreso(idEstudiante);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        }
    }

    // GET: mejor estudiante
    @GetMapping("/mejor-estudiante")
    public ResponseEntity<?> mejorEstudiante() {
        Progreso mejor = progresoService.obtenerTopNota();
        if (mejor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay estudiantes registrados.");
        }
        ProgresoModel model = assembler.toModel(mejor);
        return ResponseEntity.ok(model);
    }
}

