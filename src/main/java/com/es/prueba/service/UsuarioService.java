package com.es.prueba.service;

import com.es.prueba.DTO.UsuarioLoginDTO;
import com.es.prueba.DTO.UsuarioRegisterDTO;
import com.es.prueba.error.excepciones.DuplicadoException;
import com.es.prueba.error.excepciones.NoEncontradoException;
import com.es.prueba.error.excepciones.PeticionIncorrectaException;
import com.es.prueba.model.Usuario;
import com.es.prueba.repository.DireccionRepository;
import com.es.prueba.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean doLogin(UsuarioLoginDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioDTO.getUsername())
                .orElseThrow(() -> new PeticionIncorrectaException("El usuario no existe."));

        if (!passwordEncoder.matches(usuarioDTO.getPassword(), usuario.getPassword())) {
            throw new PeticionIncorrectaException("La contraseña es incorrecta.");
        }

        return true;
    }

    public boolean register(UsuarioRegisterDTO usuarioDTO) {
        if (usuarioDTO.getUsername().length() < 5 || usuarioDTO.getUsername().length() > 25) {
            throw new PeticionIncorrectaException("El nombre de usuario debe tener entre 5 y 25 caracteres.");
        }

        if (usuarioDTO.getPassword().length() < 10 || usuarioDTO.getPassword().length() > 20) {
            throw new PeticionIncorrectaException("La contraseña debe tener entre 10 y 20 caracteres.");
        }

        if (!usuarioDTO.getPassword().matches("[a-zA-Z0-9]+")) {
            throw new PeticionIncorrectaException("La contraseña solo puede contener letras y números.");
        }

        if (!usuarioDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@(gmail|hotmail|outlook|g\\.educaand)\\.(com|es)$")) {
            throw new PeticionIncorrectaException("El email debe tener un formato válido (gmail, hotmail, outlook o g.educaand y terminar en .com o .es)");
        }

        if (!usuarioDTO.getPassword().equals(usuarioDTO.getRepetirPassword())) {
            throw new PeticionIncorrectaException("Las contraseñas no coinciden.");
        }

        if (usuarioRepository.existsById(usuarioDTO.getUsername())) {
            throw new DuplicadoException("Ya existe un usuario con ese nombre.");
        }

        String rol = usuarioDTO.getRoles();
        if (rol == null || rol.isEmpty()) {
            rol = "USER";
        } else {
            rol = rol.toUpperCase();
            if (!rol.equals("USER") && !rol.equals("ADMIN")) {
                throw new PeticionIncorrectaException("Rol no válido. Solo se permite USER o ADMIN.");
            }
        }

        String hashedPassword = passwordEncoder.encode(usuarioDTO.getPassword());

        Usuario usuario = new Usuario(
                usuarioDTO.getNombre(),
                usuarioDTO.getApellido(),
                usuarioDTO.getEmail(),
                hashedPassword,
                hashedPassword,
                usuarioDTO.getUsername(),
                rol
        );

        usuarioRepository.save(usuario);
        return true;
    }

    public UsuarioRegisterDTO actualizarUsuario(UsuarioRegisterDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioDTO.getUsername())
                .orElseThrow(() -> new NoEncontradoException("No se encontró el usuario con ese username."));

        if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isEmpty()) {
            throw new PeticionIncorrectaException("La contraseña es obligatoria.");
        }
        if (usuarioDTO.getPassword().length() < 10 || usuarioDTO.getPassword().length() > 20) {
            throw new PeticionIncorrectaException("La contraseña debe tener entre 10 y 20 caracteres.");
        }
        if (!usuarioDTO.getPassword().matches("[a-zA-Z0-9]+")) {
            throw new PeticionIncorrectaException("La contraseña solo puede contener letras y números.");
        }
        if (!usuarioDTO.getPassword().equals(usuarioDTO.getRepetirPassword())) {
            throw new PeticionIncorrectaException("Las contraseñas no coinciden.");
        }

        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            throw new PeticionIncorrectaException("El email es obligatorio.");
        }
        if (!usuarioDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@(gmail|hotmail|outlook|g\\.educaand)\\.(com|es)$")) {
            throw new PeticionIncorrectaException("El email debe tener un formato válido (gmail, hotmail, outlook o g.educaand y terminar en .com o .es)");
        }

        String rol = usuarioDTO.getRoles();
        if (rol == null || rol.isEmpty()) {
            rol = "USER";
        } else {
            rol = rol.toUpperCase();
            if (!rol.equals("USER") && !rol.equals("ADMIN")) {
                throw new PeticionIncorrectaException("Rol no válido. Solo se permite USER o ADMIN.");
            }
        }

        if (usuarioDTO.getNombre() == null || usuarioDTO.getNombre().isEmpty()) {
            throw new PeticionIncorrectaException("El nombre es obligatorio.");
        }

        if (usuarioDTO.getApellido() == null || usuarioDTO.getApellido().isEmpty()) {
            throw new PeticionIncorrectaException("El apellido es obligatorio.");
        }

        String hashedPassword = passwordEncoder.encode(usuarioDTO.getPassword());

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(hashedPassword);
        usuario.setRepetirPassword(hashedPassword);
        usuario.setRoles(rol);

        usuarioRepository.save(usuario);

        return usuarioDTO;
    }

    public boolean borrarUsuario(String username) {
        direccionRepository.findByUsuarioUsername(username).ifPresent(direccionRepository::delete);

        Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(() -> new NoEncontradoException("No se encontró el usuario con username: " + username));

        usuarioRepository.delete(usuario);

        return true;
    }


}
