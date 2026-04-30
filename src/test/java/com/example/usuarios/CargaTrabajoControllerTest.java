package com.example.usuarios;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.usuarios.controller.CargaTrabajoController;
import com.example.usuarios.model.CargaTrabajo;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.service.CargaTrabajoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CargaTrabajoController.class)
public class CargaTrabajoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CargaTrabajoService cargaTrabajoService;

    @Autowired
    private ObjectMapper objectMapper;

    private CargaTrabajo cargaPrueba;
    private Usuario usuarioPrueba;

    @BeforeEach
    void setUp() {
        usuarioPrueba = new Usuario();
        usuarioPrueba.setId_usuario(1L);
        usuarioPrueba.setNombre("Benjamon");

        cargaPrueba = new CargaTrabajo();
        cargaPrueba.setHoras_asignadas(20);
        cargaPrueba.setPeriodo("2024-Q1");
        cargaPrueba.setUsuario(usuarioPrueba);
    }

    @Test
    void testListarTodo() throws Exception {
        Mockito.when(cargaTrabajoService.listarTodas()).thenReturn(Arrays.asList(cargaPrueba));

        mockMvc.perform(get("/api/usuarios/carga_trabajo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].periodo").value("2024-Q1"));
    }

    @Test
    void testCrear() throws Exception {
        Mockito.when(cargaTrabajoService.guardar(any(CargaTrabajo.class))).thenReturn(cargaPrueba);

        mockMvc.perform(post("/api/usuarios/carga_trabajo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cargaPrueba)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.horas_asignadas").value(20));
    }

    @Test
    void testValidarCapacidad_Exito() throws Exception {
        Mockito.when(cargaTrabajoService.buscarPorUsuario(1L)).thenReturn(Arrays.asList(cargaPrueba));

        mockMvc.perform(get("/api/usuarios/carga_trabajo/validar/1")
                .param("nuevasHoras", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testValidarCapacidad_Exceso() throws Exception {
        Mockito.when(cargaTrabajoService.buscarPorUsuario(1L)).thenReturn(Arrays.asList(cargaPrueba));

        mockMvc.perform(get("/api/usuarios/carga_trabajo/validar/1")
                .param("nuevasHoras", "30"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void testEliminar() throws Exception {
        Mockito.doNothing().when(cargaTrabajoService).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/carga_trabajo/1"))
                .andExpect(status().isNoContent());
    }
}