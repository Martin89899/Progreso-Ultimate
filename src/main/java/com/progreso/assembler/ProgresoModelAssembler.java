package com.progreso.assembler;

import org.springframework.stereotype.Component;
import com.progreso.Progreso.controller.ProgresoController;
import com.progreso.Progreso.dto.Progreso;
import com.progreso.Progreso.dto.ProgresoModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.RepresentationModelAssembler;

@Component
public class ProgresoModelAssembler implements RepresentationModelAssembler<Progreso, ProgresoModel> {

    
    @Override
    public ProgresoModel toModel(Progreso progreso){
        ProgresoModel model = new ProgresoModel();
        model.setIdEstudiante(progreso.getIdEstudiante());
        model.setRutEstudiante(progreso.getRutEstudiante());
        model.setNombreEstudiante(progreso.getNombreEstudiante());
        model.setAsistencia(progreso.getAsistencia());
        model.setNotas(progreso.getNotas());



        model.add(linkTo(methodOn(ProgresoController.class).buscaProgreso(progreso.getIdEstudiante())).withSelfRel());
        model.add(linkTo(methodOn(ProgresoController.class).obtenerProgreso()).withRel("Progresos"));
        model.add(linkTo(methodOn(ProgresoController.class).eliminarProgreso(progreso.getIdEstudiante())).withRel("Eliminar"));
        model.add(linkTo(methodOn(ProgresoController.class).actualizarProgreso(progreso.getIdEstudiante(), progreso)).withRel("Actualizar"));

        return model;





    }

}
