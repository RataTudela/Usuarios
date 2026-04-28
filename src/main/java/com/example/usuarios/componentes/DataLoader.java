package com.example.usuarios.componentes;

import com.example.usuarios.model.CargaTrabajo;
import com.example.usuarios.model.Disponibilidad;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.repository.CargaTrabajoRepository;
import com.example.usuarios.repository.DisponibilidadRepository;
import com.example.usuarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CargaTrabajoRepository cargaRepository;
    private final DisponibilidadRepository disponibilidadRepository;

    public DataLoader(UsuarioRepository usuarioRepository, 
                      CargaTrabajoRepository cargaRepository, 
                      DisponibilidadRepository disponibilidadRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cargaRepository = cargaRepository;
        this.disponibilidadRepository = disponibilidadRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        disponibilidadRepository.deleteAll();
        cargaRepository.deleteAll();
        usuarioRepository.deleteAll();

        Usuario juan = new Usuario();
        juan.setNombre("Juan Perez");
        juan.setEmail("juan.perez@innovatech.com");
        juan.setContraseña("Informatica.25");
        juan.setRol("CONSULTOR");
        juan.setEstado("ACTIVO");

        Usuario maria = new Usuario();
        maria.setNombre("Maria Garcia");
        maria.setEmail("maria.garcia@innovatech.com");
        maria.setContraseña("Informatica.25");
        maria.setRol("SENIOR");
        maria.setEstado("ACTIVO");

        usuarioRepository.saveAll(Arrays.asList(juan, maria));

        CargaTrabajo cargaJuan = new CargaTrabajo();
        cargaJuan.setUsuario(juan);
        cargaJuan.setHoras_asignadas(25);
        cargaJuan.setPeriodo("Abril 2026");

        CargaTrabajo cargaMaria = new CargaTrabajo();
        cargaMaria.setUsuario(maria);
        cargaMaria.setHoras_asignadas(35);
        cargaMaria.setPeriodo("Abril 2026");

        cargaRepository.saveAll(Arrays.asList(cargaJuan, cargaMaria));

        Disponibilidad vacacionesMaria = new Disponibilidad();
        vacacionesMaria.setUsuario(maria);
        vacacionesMaria.setFechaInicio(LocalDate.of(2026, 5, 1));
        vacacionesMaria.setFechaFin(LocalDate.of(2026, 5, 15));
        vacacionesMaria.setMotivo("Vacaciones anuales");

        disponibilidadRepository.save(vacacionesMaria);

        System.out.println(">> DataLoader: Datos iniciales cargados con éxito.");
    }
}