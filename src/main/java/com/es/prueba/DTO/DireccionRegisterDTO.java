package com.es.prueba.DTO;

public class DireccionRegisterDTO {
    private String calle;
    private int numero;
    private String cp;
    private String provincia;
    private String municipio;
    private String userName;

    public DireccionRegisterDTO(String calle, int numero, String cp, String provincia, String municipio, String userName) {
        this.calle = calle;
        this.numero = numero;
        this.cp = cp;
        this.provincia = provincia;
        this.municipio = municipio;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
