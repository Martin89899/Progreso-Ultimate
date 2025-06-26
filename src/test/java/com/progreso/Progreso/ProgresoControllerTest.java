package com.progreso;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progreso.controller.ProgresoController;
import com.progreso.dto.Progreso;
import com.progreso.services.ProgresoService;

@WebMvcTest(ProgresoController.class)
public class ProgresoControllerTest {
    
    @Autowired
    private MockMvc;

    @MockitoBean
    private ProgresoService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v0/cursos devuelve lista de curso")
public void testListarCurso() throws Exception {
        List<Progreso> listaProgreso = Arrays.asList(
            new Progreso(1, "Introduccion a la programacion","Juan Perez", "programacion"),
            new Progreso(2, "Calculo","Luis Fernandez", "Matematica")

        );

        when(service.obtenerProgreso()).thenReturn(listaProgreso);

}
