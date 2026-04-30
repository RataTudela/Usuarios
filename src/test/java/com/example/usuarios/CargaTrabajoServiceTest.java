package com.example.usuarios; 

import com.example.usuarios.model.CargaTrabajo;
import com.example.usuarios.service.CargaTrabajoService; 
import com.example.usuarios.repository.CargaTrabajoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CargaTrabajoServiceTest {

    @Mock
    private CargaTrabajoRepository repository;

    @InjectMocks
    private CargaTrabajoService service;

    private CargaTrabajo cargaPrueba;

    @BeforeEach
    void setUp() {
        cargaPrueba = new CargaTrabajo();
        cargaPrueba.setId_carga(1L); 
        cargaPrueba.setHoras_asignadas(20);
        cargaPrueba.setPeriodo("2024-Q1");
    }

    @Test
    void testSumarHoras_Exito() {
        Mockito.when(repository.findByUsuarioIdAndPeriodo(1L, "2024-Q1"))
               .thenReturn(cargaPrueba);
        
        Mockito.when(repository.save(any(CargaTrabajo.class)))
               .thenAnswer(i -> i.getArguments()[0]);

        CargaTrabajo resultado = service.sumarHoras(1L, "2024-Q1", 10);

        assertNotNull(resultado);
        assertEquals(30, resultado.getHoras_asignadas());
        Mockito.verify(repository).save(cargaPrueba);
    }

    @Test
    void testSumarHoras_NoExiste() {
        Mockito.when(repository.findByUsuarioIdAndPeriodo(1L, "2024-Q2"))
               .thenReturn(null);

        CargaTrabajo resultado = service.sumarHoras(1L, "2024-Q2", 10);

        assertNull(resultado);
        Mockito.verify(repository, Mockito.never()).save(any());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());
        CargaTrabajo resultado = service.buscarPorId(99L);
        assertNull(resultado);
    }
}