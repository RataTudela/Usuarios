package com.example.usuarios.service;

import com.example.usuarios.model.Disponibilidad;
import com.example.usuarios.repository.DisponibilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DisponibilidadService {
    @Autowired
    private DisponibilidadRepository repository;

    public List<Disponibilidad> listarTodas() {
         return repository.findAll(); 
        }

    public Disponibilidad guardar(Disponibilidad d) {
         return repository.save(d); 
        }

    public List<Disponibilidad> buscarPorUsuario(Long id) {
    return repository.buscarPorUsuarioId(id); 
    }

    public Disponibilidad buscarPorId(Long id) {
         return repository.findById(id).orElse(null); 
        }

    public void eliminar(Long id) {
         repository.deleteById(id); 
        }
    
    public List<Disponibilidad> buscarReal(String motivo) {
        return repository.findByMotivoContaining(motivo);
    }
}