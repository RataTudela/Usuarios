package com.example.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuarios.model.CargaTrabajo;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.repository.CargaTrabajoRepository;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CargaTrabajoService {

    @Autowired
    private CargaTrabajoRepository repository;

    public List<CargaTrabajo> listarTodas() {
        return repository.findAll(); 
    }

    public CargaTrabajo guardar(CargaTrabajo c) {
        return repository.save(c); 
    }

    public List<CargaTrabajo> buscarPorUsuario(Long id) {
        return repository.findByUsuarioId(id); 
    }

    public CargaTrabajo buscarPorId(Long id) {
        return repository.findById(id).orElse(null); 
    }

    @Transactional
    public void eliminar(Long id) {
    CargaTrabajo carga = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("ID no encontrado"));
    Usuario usuario = carga.getUsuario();
    if (usuario != null) {
        usuario.getCargasTrabajo().remove(carga);
    }
    repository.delete(carga);
    }
    
    public List<CargaTrabajo> buscarPorNombreTarea(String nombreTarea) {
        return repository.findByNombreTarea(nombreTarea);
    }

    public CargaTrabajo sumarHoras(Long idUsuario, String nombreTarea, Integer nuevasHoras) {
        CargaTrabajo carga = repository.findByUsuarioIdAndNombreTarea(idUsuario, nombreTarea);
        if (carga != null) {
            carga.setHoras_asignadas(carga.getHoras_asignadas() + nuevasHoras);
            return repository.save(carga);
        }
        return null; 
    }
}