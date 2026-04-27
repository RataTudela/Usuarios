package com.example.usuarios.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "disponibilidades")
@Data
public class Disponibilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_disponibilidad;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false) 
    private Usuario usuario;    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String motivo;
}