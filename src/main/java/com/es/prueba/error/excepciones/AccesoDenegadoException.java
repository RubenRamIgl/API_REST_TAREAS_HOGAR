package com.es.prueba.error.excepciones;

public class AccesoDenegadoException extends RuntimeException {
  public AccesoDenegadoException(String mensaje) {
    super(mensaje);
  }
}