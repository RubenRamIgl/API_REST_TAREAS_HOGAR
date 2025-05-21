package com.es.prueba.controller;

import com.es.prueba.DTO.UsuarioLoginDTO;
import com.es.prueba.DTO.UsuarioRegisterDTO;
import com.es.prueba.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<String> doLogin(@RequestBody UsuarioLoginDTO usuarioDTO) {
        if (usuarioDTO.getUsername() == null || usuarioDTO.getUsername().isEmpty()) {
            return new ResponseEntity<>("El userName es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("El password es obligatorio.", HttpStatus.BAD_REQUEST);
        }

        usuarioService.doLogin(usuarioDTO);

        System.out.println("El usuario es: " + usuarioDTO.getUsername());
        System.out.println("El password es: " + usuarioDTO.getPassword());

        return new ResponseEntity<>("Hola " + usuarioDTO.getUsername(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsuarioRegisterDTO usuarioDTO) {
        if (usuarioDTO.getNombre() == null || usuarioDTO.getNombre().isEmpty()) {
            return new ResponseEntity<>("El nombre es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getApellido() == null || usuarioDTO.getApellido().isEmpty()) {
            return new ResponseEntity<>("El apellido es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            return new ResponseEntity<>("El email es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("El password es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getRepetirPassword() == null || usuarioDTO.getRepetirPassword().isEmpty()) {
            return new ResponseEntity<>("Repetir el password es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getUsername() == null || usuarioDTO.getUsername().isEmpty()) {
            return new ResponseEntity<>("El userName es obligatorio.", HttpStatus.BAD_REQUEST);
        }

        usuarioService.register(usuarioDTO);

        System.out.println("El nombre es: " + usuarioDTO.getNombre());
        System.out.println("El apellido es: " + usuarioDTO.getApellido());
        System.out.println("El email es: " + usuarioDTO.getEmail());
        System.out.println("El password es: " + usuarioDTO.getPassword());
        System.out.println("El password introducido es: " + usuarioDTO.getRepetirPassword());
        System.out.println("El usuario es: " + usuarioDTO.getUsername());

        return new ResponseEntity<>("El usuario " + usuarioDTO.getUsername() + " ha sido creado con éxito", HttpStatus.OK);
    }

    @PutMapping("/usuarioUpdate")
    public ResponseEntity<String> actualizarUsuario(@RequestBody UsuarioRegisterDTO usuarioDTO) {
        if (usuarioDTO.getNombre() == null || usuarioDTO.getNombre().isEmpty()) {
            return new ResponseEntity<>("El nombre es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getApellido() == null || usuarioDTO.getApellido().isEmpty()) {
            return new ResponseEntity<>("El apellido es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            return new ResponseEntity<>("El email es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("El password es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioDTO.getRepetirPassword() == null || usuarioDTO.getRepetirPassword().isEmpty()) {
            return new ResponseEntity<>("Repetir el password es obligatorio.", HttpStatus.BAD_REQUEST);
        }

        if (usuarioDTO.getRoles() == null || usuarioDTO.getRoles().isEmpty()) {
            usuarioDTO.setRoles("USER");
        }

        usuarioService.actualizarUsuario(usuarioDTO);

        System.out.println("Usuario actualizado: " + usuarioDTO.getUsername());
        return new ResponseEntity<>("El usuario " + usuarioDTO.getUsername() + " ha sido actualizado con éxito", HttpStatus.OK);
    }

    @DeleteMapping("/usuarioDelete/{username}")
    public ResponseEntity<String> borrarUsuario(@PathVariable String username) {
        usuarioService.borrarUsuario(username);
        return new ResponseEntity<>("El usuario " + username + " y su dirección han sido eliminados correctamente.", HttpStatus.OK);
    }


}
