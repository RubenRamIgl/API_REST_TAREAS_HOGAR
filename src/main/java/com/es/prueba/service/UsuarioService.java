package com.es.prueba.service;

import com.es.prueba.DTO.UsuarioLoginDTO;
import com.es.prueba.DTO.UsuarioRegisterDTO;
import com.es.prueba.DTO.UsuarioUpdateDTO;
import com.es.prueba.error.excepciones.AccesoDenegadoException;
import com.es.prueba.error.excepciones.DuplicadoException;
import com.es.prueba.error.excepciones.NoEncontradoException;
import com.es.prueba.error.excepciones.PeticionIncorrectaException;
import com.es.prueba.model.Direccion;
import com.es.prueba.model.Tarea;
import com.es.prueba.model.Usuario;
import com.es.prueba.repository.DireccionRepository;
import com.es.prueba.repository.TareaRepository;
import com.es.prueba.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convierte el rol del usuario a una autoridad de Spring Security
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRoles());

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(List.of(authority))
                .build();
    }

    /*public boolean doLogin(UsuarioLoginDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioDTO.getUsername())
                .orElseThrow(() -> new PeticionIncorrectaException("El usuario no existe."));

        if (!passwordEncoder.matches(usuarioDTO.getPassword(), usuario.getPassword())) {
            throw new PeticionIncorrectaException("La contraseña es incorrecta.");
        }

        return true;
    }*/

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
        Optional<Direccion> direccionOpt = direccionRepository.findByUsuarioUsername(username);
        direccionOpt.ifPresent(direccion -> direccionRepository.delete(direccion));

        List<Tarea> tareas = tareaRepository.findByUsuarioUsername(username);
        if (tareas != null && !tareas.isEmpty()) {
            tareaRepository.deleteAll(tareas);
        }

        Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(() -> new NoEncontradoException("Usuario no encontrado: " + username));

        usuarioRepository.delete(usuario);
        return true;
    }

    public boolean borrarMiCuenta(String usernameActual, String username) {
        if (!usernameActual.equals(username)) {
            throw new AccessDeniedException("Solo puedes eliminar tu propia cuenta");
        }
        return borrarUsuario(username);
    }

    public UsuarioUpdateDTO actualizarMiPerfil(String usernameActual, UsuarioUpdateDTO usuarioDTO) {
        if (!usernameActual.equals(usuarioDTO.getUsername())) {
            throw new AccessDeniedException("Solo puedes actualizar tu propio perfil");
        }

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

        usuarioRepository.save(usuario);

        return usuarioDTO;
    }
}
