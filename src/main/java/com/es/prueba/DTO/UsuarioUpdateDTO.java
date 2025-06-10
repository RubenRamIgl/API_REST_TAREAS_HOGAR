package com.es.prueba.DTO;

public class UsuarioUpdateDTO {

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String repetirPassword;
    private String username;


    public UsuarioUpdateDTO(String nombre, String apellido, String email, String password, String repetirPassword, String username) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.repetirPassword = repetirPassword;
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepetirPassword() {
        return repetirPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepetirPassword(String repetirPassword) {
        this.repetirPassword = repetirPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}