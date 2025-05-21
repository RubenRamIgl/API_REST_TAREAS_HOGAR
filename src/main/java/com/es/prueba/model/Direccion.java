package com.es.prueba.model;

import jakarta.persistence.*;

@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String calle;
    @Column(nullable = false)
    private int numero;
    @Column
    private String cp;
    @Column(nullable = false)
    private String provincia;
    @Column(nullable = false)
    private String municipio;
    @OneToOne
    @JoinColumn(name = "user_name", referencedColumnName = "username", nullable = false, unique = true)
    private Usuario usuario;

    public Direccion(String calle, int numero, String cp, String provincia, String municipio, Usuario usuario) {
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
        this.provincia = provincia;
        this.municipio = municipio;
        this.usuario = usuario;
    }

    public Direccion() {

    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getCp() {
        return cp;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
