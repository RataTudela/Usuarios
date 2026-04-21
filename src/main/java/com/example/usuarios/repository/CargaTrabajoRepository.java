package com.example.usuarios.repository;

import com.example.usuarios.model.CargaTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CargaTrabajoRepository extends JpaRepository<CargaTrabajo, Long> {
    
    @Query("SELECT c FROM CargaTrabajo c WHERE c.usuario.id_usuario = :id")
    List<CargaTrabajo> findByUsuarioId(@Param("id") Long id);

    @Query("SELECT c FROM CargaTrabajo c WHERE c.usuario.id_usuario = :id AND c.periodo = :periodo")
    CargaTrabajo findByUsuarioIdAndPeriodo(@Param("id") Long id, @Param("periodo") String periodo);

    List<CargaTrabajo> findByPeriodo(String periodo);
}