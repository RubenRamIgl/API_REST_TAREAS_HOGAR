package com.es.prueba.controller;

import com.es.prueba.DTO.TareaDTO;
import com.es.prueba.DTO.TareaRegisterDTO;
import com.es.prueba.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @PostMapping("/tareaRegister")
    public ResponseEntity<String> register(@RequestBody TareaRegisterDTO tareaDTO){
        if (tareaDTO.getNombre() == null || tareaDTO.getNombre().isEmpty()) {
            return new ResponseEntity<>("El nombre es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (tareaDTO.getDescripcion() == null || tareaDTO.getDescripcion().isEmpty()) {
            return new ResponseEntity<>("La descripción es obligatoria.", HttpStatus.BAD_REQUEST);
        }
        if (tareaDTO.getFechaFin() == null) {
            return new ResponseEntity<>("La fecha de finalización es obligatoria.", HttpStatus.BAD_REQUEST);
        }

        System.out.println("El nombre es: " + tareaDTO.getNombre());
        System.out.println("La descripción es: " + tareaDTO.getDescripcion());
        System.out.println("La fecha límite es: " + tareaDTO.getFechaFin());

        return new ResponseEntity<>("La tarea " + tareaDTO.getNombre() + " se ha registrado correctamente.", HttpStatus.OK);
    }

    @GetMapping("/mostrarTareas")
    public ResponseEntity<ArrayList<TareaDTO>> mostrarTareas() {

        return new ResponseEntity<>(tareaService.mostrarTareas(), HttpStatus.OK);
    }

    @GetMapping("/buscarTareaNombre/{palabra}")
    public ResponseEntity<ArrayList<TareaDTO>> buscarTareasNombre(@PathVariable String palabra) {
        return new ResponseEntity<>(tareaService.buscarTareasNombre(palabra), HttpStatus.OK);
    }

    @GetMapping("/mostrarTareaId/{id}")
    public ResponseEntity<TareaDTO> mostrarTareaPorId(@PathVariable int id) {
        return new ResponseEntity<>(tareaService.mostrarPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/eliminarTarea/{id}")
    public ResponseEntity<TareaDTO> eliminarTarea(@PathVariable int id) {
        return new ResponseEntity<>(tareaService.eliminarTarea(id), HttpStatus.OK);
    }


    @PostMapping("/insertarTarea")
    public ResponseEntity<String> insertarTarea(@RequestBody TareaRegisterDTO tareaDTO) {
        if (tareaDTO.getNombre() == null || tareaDTO.getNombre().isEmpty()) {
            return new ResponseEntity<>("El nombre es obligatorio.", HttpStatus.BAD_REQUEST);
        }

        if (tareaDTO.getDescripcion() == null || tareaDTO.getDescripcion().isEmpty()) {
            return new ResponseEntity<>("La descripción es obligatoria.", HttpStatus.BAD_REQUEST);
        }

        if (tareaDTO.getFechaFin() == null) {
            return new ResponseEntity<>("La fecha de finalización es obligatoria.", HttpStatus.BAD_REQUEST);
        }

        tareaService.insertarTarea(tareaDTO);
        System.out.println("Tarea agregada: " + tareaDTO.getNombre());
        return new ResponseEntity<>("La tarea '" + tareaDTO.getNombre() + "' se ha agregado correctamente.", HttpStatus.OK);
    }

}

