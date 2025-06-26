package com.progreso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.progreso.dto.Progreso;

@Repository
public interface ProgresoRepository extends JpaRepository<Progreso, Integer> {


}
