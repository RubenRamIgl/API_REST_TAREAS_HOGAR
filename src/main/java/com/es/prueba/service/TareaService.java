package com.es.prueba.service;

import com.es.prueba.DTO.TareaDTO;
import com.es.prueba.DTO.TareaRegisterDTO;
import com.es.prueba.error.excepciones.DuplicadoException;
import com.es.prueba.error.excepciones.NoEncontradoException;
import com.es.prueba.error.excepciones.PeticionIncorrectaException;
import com.es.prueba.model.Tarea;
import com.es.prueba.model.Usuario;
import com.es.prueba.repository.TareaRepository;
import com.es.prueba.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private ArrayList<TareaDTO> tareas;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean registerTarea(TareaRegisterDTO tareaDTO){
        if (tareaDTO.getFechaFin().isBefore(LocalDateTime.now())) {
            throw new PeticionIncorrectaException("La fecha de finalización no puede ser anterior a la fecha actual.");
        }

        if (tareaDTO.getFechaFin().isAfter(LocalDateTime.now().plusMonths(1))) {
            throw new PeticionIncorrectaException("La fecha de finalización no puede ser posterior a un mes desde hoy.");
        }

        return true;
    }

    public ArrayList<TareaDTO> mostrarTareas() {
        List<Tarea> tareasDeBD = tareaRepository.findAll();

        if (tareasDeBD.isEmpty()) {
            throw new NoEncontradoException("No hay tareas disponibles.");
        }

        ArrayList<TareaDTO> tareasDTO = new ArrayList<>();
        for (Tarea tarea : tareasDeBD) {
            TareaDTO tareaDTO = new TareaDTO(
                    tarea.getNombre(),
                    tarea.getDescripcion(),
                    tarea.getFechaFin(),
                    tarea.getEstado()
            );
            tareasDTO.add(tareaDTO);
        }

        return tareasDTO;
    }


    public ArrayList<TareaDTO> buscarTareasNombre(String palabra) {
        List<Tarea> tareasDeBD = tareaRepository.findAll();

        if (tareasDeBD.isEmpty()) {
            throw new NoEncontradoException("No hay tareas disponibles.");
        }

        ArrayList<TareaDTO> tareasFiltradas = new ArrayList<>();

        for (Tarea tarea : tareasDeBD) {
            if (tarea.getNombre() != null && tarea.getNombre().toLowerCase().contains(palabra.toLowerCase())) {
                tareasFiltradas.add(new TareaDTO(
                        tarea.getNombre(),
                        tarea.getDescripcion(),
                        tarea.getFechaFin(),
                        tarea.getEstado()
                ));
            }
        }

        if (tareasFiltradas.isEmpty()) {
            throw new NoEncontradoException("No se encontraron tareas con la palabra " + palabra);
        }

        return tareasFiltradas;
    }


    public TareaDTO mostrarPorId(int id) {
        Tarea tareaDeBD = tareaRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("No existe una tarea con el id " + id));

        return new TareaDTO(
                tareaDeBD.getNombre(),
                tareaDeBD.getDescripcion(),
                tareaDeBD.getFechaFin(),
                tareaDeBD.getEstado()
        );
    }



    public TareaDTO eliminarTarea(int id) {
        Tarea tareaDeBD = tareaRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("No existe una tarea con el id " + id));

        tareaRepository.delete(tareaDeBD);

        return new TareaDTO(
                tareaDeBD.getNombre(),
                tareaDeBD.getDescripcion(),
                tareaDeBD.getFechaFin(),
                tareaDeBD.getEstado()
        );
    }



    public boolean insertarTarea(TareaRegisterDTO tareaDTO) {
        if (tareaDTO.getFechaFin().isBefore(LocalDateTime.now())) {
            throw new PeticionIncorrectaException("La fecha de finalización no puede ser anterior a la fecha actual.");
        }

        if (tareaDTO.getFechaFin().isAfter(LocalDateTime.now().plusMonths(1))) {
            throw new PeticionIncorrectaException("La fecha de finalización no puede ser posterior a un mes desde hoy.");
        }

        Optional<Tarea> tareaExistente = tareaRepository.findByNombre(tareaDTO.getNombre());
        if (tareaExistente.isPresent()) {
            throw new DuplicadoException("Ya existe una tarea con el nombre '" + tareaDTO.getNombre() + "'.");
        }

        Usuario usuario = usuarioRepository.findById(tareaDTO.getUserName())
                .orElseThrow(() -> new NoEncontradoException("No existe el usuario " + tareaDTO.getUserName()));

        Tarea nuevaTarea = new Tarea(
                tareaDTO.getNombre(),
                tareaDTO.getDescripcion(),
                tareaDTO.getFechaFin(),
                false,
                usuario
        );

        tareaRepository.save(nuevaTarea);
        return true;
    }

}
