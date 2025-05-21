package com.es.prueba.error.excepciones;

public class NoEncontradoException extends RuntimeException{
    public NoEncontradoException(String mensaje){
        super(mensaje);
    }
}
