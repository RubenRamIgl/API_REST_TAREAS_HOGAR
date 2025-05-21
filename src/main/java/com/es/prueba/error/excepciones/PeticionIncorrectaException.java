package com.es.prueba.error.excepciones;

public class PeticionIncorrectaException extends RuntimeException{
    public PeticionIncorrectaException(String mensaje){
        super(mensaje);
    }
}
