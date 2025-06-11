package com.es.prueba.controller;

import com.es.prueba.DTO.DireccionRegisterDTO;
import com.es.prueba.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @PostMapping("/direccionRegister")
    public ResponseEntity<String> register(@RequestBody DireccionRegisterDTO direccionDTO) {

        if (direccionDTO.getCalle() == null || direccionDTO.getCalle().isEmpty()) {
            return new ResponseEntity<>("La calle es obligatoria", HttpStatus.BAD_REQUEST);
        }

        if (direccionDTO.getMunicipio() == null || direccionDTO.getMunicipio().isEmpty()) {
            return new ResponseEntity<>("El municipio es obligatorio", HttpStatus.BAD_REQUEST);
        }

        direccionService.registerDireccion(direccionDTO);

        return new ResponseEntity<>("La dirección de la calle " + direccionDTO.getCalle() + " se ha registrado correctamente.", HttpStatus.OK);
    }

    @PutMapping("/direccionUpdate")
    public ResponseEntity<String> actualizarDireccion(@RequestBody DireccionRegisterDTO direccionDTO) {

        if (direccionDTO.getCalle() == null || direccionDTO.getCalle().isEmpty()) {
            return new ResponseEntity<>("La calle es obligatoria", HttpStatus.BAD_REQUEST);
        }

        if (direccionDTO.getMunicipio() == null || direccionDTO.getMunicipio().isEmpty()) {
            return new ResponseEntity<>("El municipio es obligatorio", HttpStatus.BAD_REQUEST);
        }

        direccionService.actualizarDireccion(direccionDTO);

        return new ResponseEntity<>("La dirección del usuario " + direccionDTO.getUserName() + " se ha actualizado correctamente.", HttpStatus.OK);
    }


    @DeleteMapping("/eliminarDireccion/{username}")
    public ResponseEntity<String> eliminarDireccion(@PathVariable String username) {
        direccionService.eliminarDireccion(username);
        return ResponseEntity.ok("Dirección eliminada correctamente");
    }

    @PostMapping("/registerMiDireccion")
    public ResponseEntity<String> registerMiDireccion(@RequestBody DireccionRegisterDTO direccionDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (direccionDTO.getCalle() == null || direccionDTO.getCalle().isEmpty()) {
            return new ResponseEntity<>("La calle es obligatoria", HttpStatus.BAD_REQUEST);
        }
        if (direccionDTO.getMunicipio() == null || direccionDTO.getMunicipio().isEmpty()) {
            return new ResponseEntity<>("El municipio es obligatorio", HttpStatus.BAD_REQUEST);
        }

        direccionService.registerMiDireccion(username, direccionDTO);
        return new ResponseEntity<>("Tu dirección se ha registrado correctamente", HttpStatus.OK);
    }

    @PutMapping("/updateMiDireccion")
    public ResponseEntity<String> actualizarMiDireccion(@RequestBody DireccionRegisterDTO direccionDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (direccionDTO.getCalle() == null || direccionDTO.getCalle().isEmpty()) {
            return new ResponseEntity<>("La calle es obligatoria", HttpStatus.BAD_REQUEST);
        }
        if (direccionDTO.getMunicipio() == null || direccionDTO.getMunicipio().isEmpty()) {
            return new ResponseEntity<>("El municipio es obligatorio", HttpStatus.BAD_REQUEST);
        }

        direccionService.actualizarMiDireccion(username, direccionDTO);
        return new ResponseEntity<>("Tu dirección se ha actualizado correctamente", HttpStatus.OK);
    }

    @DeleteMapping("/deleteMiDireccion")
    public ResponseEntity<String> eliminarMiDireccion() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        direccionService.eliminarMiDireccion(username, username);
        return ResponseEntity.ok("Tu dirección ha sido eliminada correctamente");
    }

}
