package com.es.prueba.repository;

import com.es.prueba.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    Optional<Direccion> findByUsuarioUsername(String username);
}
