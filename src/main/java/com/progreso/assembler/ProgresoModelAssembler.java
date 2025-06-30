package com.progreso.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.progreso.controller.ProgresoController;
import com.progreso.dto.Progreso;
import com.progreso.dto.ProgresoModel;



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
        model.add(linkTo(methodOn(ProgresoController.class).actualizarProgreso(progreso, progreso.getIdEstudiante())).withRel("Actualizar"));

        return model;





    }

}
