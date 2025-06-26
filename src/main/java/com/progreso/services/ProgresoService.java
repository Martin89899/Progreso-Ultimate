package com.progreso.Progreso.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progreso.Progreso.dto.Progreso;
import com.progreso.Progreso.repository.ProgresoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProgresoService {
    
    @Autowired
    private ProgresoRepository progresoRepository;


    public List <Progreso>obtenerProgreso() {
        return progresoRepository.findAll();
    }

    public Progreso guardaProgreso(Progreso pro) {
        return progresoRepository.save(pro);
    }

    public Progreso getProgresos(int idEstudiante) {
        return progresoRepository.findById(idEstudiante).orElse(null);
    }

    public Progreso actualizarProgreso(Progreso pro, Integer idEstudiante) {
      Progreso aux = getProgresos(idEstudiante);
      if (aux != null) {
        aux.setRutEstudiante(pro.getRutEstudiante());
        aux.setNombreEstudiante(pro.getNombreEstudiante());
        aux.setAsistencia(pro.getAsistencia());
        aux.setNotas(pro.getNotas());
      }
      return aux;


    }

    public String eliminarProgreso(Integer idEstudiante) {
        Progreso aux = getProgresos(idEstudiante);
        if(aux != null){
            progresoRepository.delete(aux);
        }
        return;


    }

    public Progreso obtenerTopNota() {
    List<Progreso> lista = progresoRepository.findAll();
    if (lista.isEmpty()) {
        return null;  
    }
    return lista.stream()
                .max((p1, p2) -> Integer.compare(p1.getNotas(), p2.getNotas()))
                .orElse(null);
}   


}
