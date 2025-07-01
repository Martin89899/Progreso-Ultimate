package com.progreso;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progreso.assembler.ProgresoModelAssembler;
import com.progreso.controller.ProgresoController;
import com.progreso.dto.Progreso;
import com.progreso.dto.ProgresoModel;
import com.progreso.services.ProgresoService;

@WebMvcTest(ProgresoController.class)
public class ProgresoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProgresoService service;

    @MockitoBean
    private ProgresoModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    public static class DummyProgresoModel extends ProgresoModel {
        public DummyProgresoModel(Progreso pro) {
            this.setIdEstudiante(pro.getIdEstudiante());
            this.setRutEstudiante(pro.getRutEstudiante());
            this.setNombreEstudiante(pro.getNombreEstudiante());
            this.setAsistencia(pro.getAsistencia());
            this.setNotas(pro.getNotas());
            this.add(Link.of("https://localhost/api/v0/progreso/" + pro.getIdEstudiante()).withSelfRel());
        }
    }

    @Test
    @DisplayName("GET /api/v0/progreso retorna 404 si no hay datos")
    public void testListarProgresosVacios() throws Exception {
        when(service.obtenerProgreso()).thenReturn(List.of());

        mockMvc.perform(get("/api/v0/progreso/"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v0/progreso/{idEstudiante} retorna 404 si no existe")
    public void testBuscarPorIdNoExistente() throws Exception {
        when(service.getProgresos(99)).thenReturn(null);

        mockMvc.perform(get("/api/v0/progreso/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v0/progreso/{idEstudiante} elimina estudiante existente")
    public void testEliminarProgreso() throws Exception {
    when(service.eliminarProgreso(3)).thenReturn(true); // Devuelve boolean, no string

    mockMvc.perform(delete("/api/v0/progreso/3"))
        .andExpect(status().isNoContent()); // 204
    }

    

    @Test
    @DisplayName("POST /api/v0/progreso crea un nuevo registro")
    public void testAgregarProgreso() throws Exception {
        Progreso nuevo = new Progreso(3, "11111111-1", "Ana Soto", 95, 88);
        Progreso guardado = new Progreso(3, "11111111-1", "Ana Soto", 95, 88);

        when(service.guardaProgreso(any(Progreso.class))).thenReturn(guardado);
        when(assembler.toModel(any(Progreso.class))).thenReturn(new DummyProgresoModel(guardado));

        mockMvc.perform(post("/api/v0/progreso")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombreEstudiante").value("Ana Soto"))
            .andExpect(jsonPath("$.idEstudiante").value(3));
    }

    @Test
    @DisplayName("PUT /api/v0/progreso/{idEstudiante} actualiza estudiante")
    public void testActualizarProgreso() throws Exception {
        Progreso actualizado = new Progreso(2, "22222222-2", "Carlos Pérez", 85, 90);

        when(service.actualizarProgreso(any(Progreso.class), eq(2))).thenReturn(actualizado);
        when(assembler.toModel(any(Progreso.class))).thenReturn(new DummyProgresoModel(actualizado));

        mockMvc.perform(put("/api/v0/progreso/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombreEstudiante").value("Carlos Pérez"));
    }

    @Test
    @DisplayName("PUT /api/v0/progreso/{idEstudiante} retorna 404 si no existe")
    public void testActualizarProgresoNoExistente() throws Exception {
        when(service.actualizarProgreso(any(Progreso.class), eq(999))).thenReturn(null);

        mockMvc.perform(put("/api/v0/progreso/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Progreso())))
            .andExpect(status().isNotFound());
    }
}
