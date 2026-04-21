package com.example.usuarios.controller;

import com.example.usuarios.model.CargaTrabajo;
import com.example.usuarios.service.CargaTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios/carga_trabajo")
public class CargaTrabajoController {

    @Autowired
    private CargaTrabajoService service;

    @GetMapping
    public List<CargaTrabajo> listarTodo() {
        return service.listarTodas();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CargaTrabajo>> buscarPorUsuario(@PathVariable Long usuarioId) {
        List<CargaTrabajo> lista = service.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public CargaTrabajo crear(@RequestBody CargaTrabajo c) {
        return service.guardar(c);
    }

    

   @PutMapping("/{id}")
    public ResponseEntity<CargaTrabajo> modificar(@PathVariable Long id, @RequestBody CargaTrabajo c) {
        CargaTrabajo existente = service.buscarPorId(id);
        if (existente != null) {
            existente.setHoras_asignadas(c.getHoras_asignadas());
            existente.setPeriodo(c.getPeriodo());        
            if (c.getUsuario() != null) {
                existente.setUsuario(c.getUsuario());
            }
            return ResponseEntity.ok(service.guardar(existente));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/validar/{usuarioId}")
    public ResponseEntity<Boolean> validarCapacidad(
            @PathVariable Long usuarioId, 
            @RequestParam Integer nuevasHoras) {
        List<CargaTrabajo> cargas = service.buscarPorUsuario(usuarioId);
        int totalActual = cargas.stream().mapToInt(CargaTrabajo::getHoras_asignadas).sum();
                boolean esValido = (totalActual + nuevasHoras) <= 40;
        return ResponseEntity.ok(esValido);
    }

 @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public List<CargaTrabajo> consultarReal(@RequestParam String periodo) {
        return service.buscarMotivo(periodo);
    }
}
