package com.progreso;

import com.progreso.dto.Progreso;
import com.progreso.repository.ProgresoRepository;
import com.progreso.services.ProgresoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProgresoServiceTest {

    @Mock
    private ProgresoRepository progresoRepository;

    @InjectMocks
    private ProgresoService progresoService;

    private Progreso ejemploProgreso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ejemploProgreso = new Progreso();
        ejemploProgreso.setIdEstudiante(1);
        ejemploProgreso.setRutEstudiante("11.111.111-1");
        ejemploProgreso.setNombreEstudiante("Juan Pérez");
        ejemploProgreso.setAsistencia(85);
        ejemploProgreso.setNotas(70);
    }

    @Test
    void testObtenerProgreso() {
        List<Progreso> lista = Arrays.asList(ejemploProgreso);
        when(progresoRepository.findAll()).thenReturn(lista);

        List<Progreso> resultado = progresoService.obtenerProgreso();

        assertEquals(1, resultado.size());
        verify(progresoRepository).findAll();
    }

    @Test
    void testGuardarProgreso() {
        when(progresoRepository.save(ejemploProgreso)).thenReturn(ejemploProgreso);

        Progreso guardado = progresoService.guardaProgreso(ejemploProgreso);

        assertNotNull(guardado);
        assertEquals("Juan Pérez", guardado.getNombreEstudiante());
        verify(progresoRepository).save(ejemploProgreso);
    }

    @Test
    void testGetProgresos() {
        when(progresoRepository.findById(1)).thenReturn(Optional.of(ejemploProgreso));

        Progreso resultado = progresoService.getProgresos(1);

        assertNotNull(resultado);
        assertEquals(90, resultado.getNotas());
        verify(progresoRepository).findById(1);
    }

    @Test
    void testActualizarProgreso() {
        when(progresoRepository.findById(1)).thenReturn(Optional.of(ejemploProgreso));

        Progreso nuevo = new Progreso();
        nuevo.setRutEstudiante("22.222.222-2");
        nuevo.setNombreEstudiante("Ana López");
        nuevo.setAsistencia(95);
        nuevo.setNotas(99);

        Progreso actualizado = progresoService.actualizarProgreso(nuevo, 1);

        assertNotNull(actualizado);
        assertEquals("Ana López", actualizado.getNombreEstudiante());
    }

    @Test
    void testEliminarProgreso_Existe() {
        when(progresoRepository.findById(1)).thenReturn(Optional.of(ejemploProgreso));

        boolean eliminado = progresoService.eliminarProgreso(1);

        assertTrue(eliminado);
        verify(progresoRepository).delete(ejemploProgreso);
    }

    @Test
    void testEliminarProgreso_NoExiste() {
        when(progresoRepository.findById(1)).thenReturn(Optional.empty());

        boolean eliminado = progresoService.eliminarProgreso(1);

        assertFalse(eliminado);
    }

    @Test
    void testObtenerTopNota() {
        Progreso otro = new Progreso();
        otro.setIdEstudiante(2);
        otro.setRutEstudiante("33.333.333-3");
        otro.setNombreEstudiante("Carlos Díaz");
        otro.setAsistencia(90);
        otro.setNotas(95);

        List<Progreso> lista = Arrays.asList(ejemploProgreso, otro);
        when(progresoRepository.findAll()).thenReturn(lista);

        Progreso mejor = progresoService.obtenerTopNota();

        assertNotNull(mejor);
        assertEquals("Carlos Díaz", mejor.getNombreEstudiante());
    }

    @Test
    void testObtenerTopNota_ListaVacia() {
        when(progresoRepository.findAll()).thenReturn(Collections.emptyList());

        Progreso mejor = progresoService.obtenerTopNota();

        assertNull(mejor);
    }
}
