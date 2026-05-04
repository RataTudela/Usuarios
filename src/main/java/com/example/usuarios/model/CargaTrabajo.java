package com.example.usuarios.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carga")
@Data
public class CargaTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_carga;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false) 
    private Usuario usuario;    
    private Integer horas_asignadas;
    private String nombreTarea;
}