package com.es.prueba.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column
    private LocalDateTime fechaFin;

    @Column
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "username", nullable = false)
    private Usuario usuario;

    public Tarea() {
    }

    public Tarea(String nombre, String descripcion, LocalDateTime fechaFin, Boolean estado, Usuario usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.usuario = usuario;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
