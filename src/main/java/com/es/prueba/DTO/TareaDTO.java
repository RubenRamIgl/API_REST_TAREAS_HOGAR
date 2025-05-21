package com.es.prueba.DTO;

import java.time.LocalDateTime;

public class TareaDTO {
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaFin;
    private Boolean estado;
    private String userName;

    public TareaDTO(String nombre, String descripcion, LocalDateTime fechaFin, Boolean estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public Boolean getEstado() {
        return estado;
    }

    public String getUserName() {
        return userName;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
