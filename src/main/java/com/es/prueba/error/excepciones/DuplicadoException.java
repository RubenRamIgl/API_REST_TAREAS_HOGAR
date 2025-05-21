package com.es.prueba.error.excepciones;

public class DuplicadoException extends RuntimeException{
    public DuplicadoException(String mensaje){
        super(mensaje);
    }
}
