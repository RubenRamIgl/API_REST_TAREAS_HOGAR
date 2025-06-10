package com.es.prueba.repository;

import com.es.prueba.model.Direccion;
import com.es.prueba.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer>{
    Optional<Tarea> findByNombre(String nombre);
    List<Tarea> findByUsuarioUsername(String username);
}
