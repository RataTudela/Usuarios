package com.example.usuarios;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.usuarios.service.DisponibilidadService;
import com.example.usuarios.model.Disponibilidad;
import com.example.usuarios.repository.DisponibilidadRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class DisponibilidadServiceTest {

    @Mock
    private DisponibilidadRepository repository;

    @InjectMocks
    private DisponibilidadService service;

    private Disponibilidad disp;

    @BeforeEach
    void setUp() {
        disp = new Disponibilidad();
        disp.setId_disponibilidad(1L);
        disp.setMotivo("Permiso Médico");
    }

    @Test
    void testListarTodas() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(disp));
        
        List<Disponibilidad> resultado = service.listarTodas();
        
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testBuscarPorId_Encontrado() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(disp));

        Disponibilidad resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Permiso Médico", resultado.getMotivo());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());

        Disponibilidad resultado = service.buscarPorId(99L);

        assertNull(resultado);
    }

    @Test
    void testGuardar() {
        Mockito.when(repository.save(any(Disponibilidad.class))).thenReturn(disp);

        Disponibilidad guardada = service.guardar(new Disponibilidad());

        assertNotNull(guardada);
        Mockito.verify(repository).save(any(Disponibilidad.class));
    }

    @Test
    void testBuscarReal() {
        Mockito.when(repository.findByMotivoContaining("Médico")).thenReturn(Arrays.asList(disp));

        List<Disponibilidad> resultado = service.buscarReal("Médico");

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getMotivo().contains("Médico"));
    }
}
