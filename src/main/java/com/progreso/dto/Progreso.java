package com.progreso.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name ="progreso")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Progreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEstudiante;

    @Column(nullable = false)
    private String rutEstudiante;

    @Column(nullable = false)
    private String NombreEstudiante;

    @Column(nullable = false)
    private int asistencia;

    @Column(nullable = false)
    private int notas;




}
