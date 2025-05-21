package com.es.prueba.service;

import com.es.prueba.DTO.DireccionRegisterDTO;
import com.es.prueba.error.excepciones.DuplicadoException;
import com.es.prueba.error.excepciones.NoEncontradoException;
import com.es.prueba.error.excepciones.PeticionIncorrectaException;
import com.es.prueba.model.Direccion;
import com.es.prueba.model.Usuario;
import com.es.prueba.repository.DireccionRepository;
import com.es.prueba.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean registerDireccion(DireccionRegisterDTO direccionDTO) {

        if (direccionDTO.getNumero() <= 0) {
            throw new PeticionIncorrectaException("El número debe ser mayor a 0");
        }

        if (direccionDTO.getCp() == null || direccionDTO.getCp().length() != 5) {
            throw new PeticionIncorrectaException("El CP debe de tener 5 dígitos");
        }

        if (!direccionDTO.getProvincia().equalsIgnoreCase("Almería") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Cádiz") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Málaga") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Granada") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Córdoba") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Huelva") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Sevilla") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Jaén")) {
            throw new PeticionIncorrectaException("La provincia debe ser de Andalucía");
        }

        Optional<Direccion> direccionExistente = direccionRepository.findByUsuarioUsername(direccionDTO.getUserName());
        if (direccionExistente.isPresent()) {
            throw new DuplicadoException("Ya existe una dirección registrada para el usuario " + direccionDTO.getUserName() + "'.");
        }

        Usuario usuario = usuarioRepository.findById(direccionDTO.getUserName())
                .orElseThrow(() -> new NoEncontradoException("Ese usuario no existe"));

        Direccion direccion = new Direccion(
                direccionDTO.getCalle(),
                direccionDTO.getNumero(),
                direccionDTO.getCp(),
                direccionDTO.getProvincia(),
                direccionDTO.getMunicipio(),
                usuario
        );

        direccionRepository.save(direccion);

        return true;
    }

    public DireccionRegisterDTO actualizarDireccion(DireccionRegisterDTO direccionDTO) {
        Direccion direccion = direccionRepository.findByUsuarioUsername(direccionDTO.getUserName())
                .orElseThrow(() -> new NoEncontradoException("No existe una dirección registrada para el usuario " + direccionDTO.getUserName()));

        if (direccionDTO.getNumero() <= 0) {
            throw new PeticionIncorrectaException("El número debe ser mayor a 0");
        }

        if (direccionDTO.getCp() == null || direccionDTO.getCp().length() != 5) {
            throw new PeticionIncorrectaException("El CP debe de tener 5 dígitos");
        }

        if (!direccionDTO.getProvincia().equalsIgnoreCase("Almería") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Cádiz") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Málaga") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Granada") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Córdoba") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Huelva") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Sevilla") &&
                !direccionDTO.getProvincia().equalsIgnoreCase("Jaén")) {
            throw new PeticionIncorrectaException("La provincia debe ser de Andalucía");
        }

        direccion.setCalle(direccionDTO.getCalle());
        direccion.setNumero(direccionDTO.getNumero());
        direccion.setCp(direccionDTO.getCp());
        direccion.setProvincia(direccionDTO.getProvincia());
        direccion.setMunicipio(direccionDTO.getMunicipio());

        direccionRepository.save(direccion);

        return direccionDTO;
    }


    public boolean eliminarDireccion(String username) {
        Optional<Direccion> direccionOpt = direccionRepository.findByUsuarioUsername(username);

        if (direccionOpt.isEmpty()) {
            throw new NoEncontradoException("No existe una dirección para el usuario " + username);
        }

        direccionRepository.delete(direccionOpt.get());
        return true;
    }

}
