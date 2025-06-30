package com.progreso.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgresoModel extends RepresentationModel<ProgresoModel> {

    private Integer idEstudiante;
    private String rutEstudiante;
    private String nombreEstudiante;
    private int asistencia;
    private int notas;





}
