package com.es.prueba.error;

import com.es.prueba.error.excepciones.DuplicadoException;
import com.es.prueba.error.excepciones.NoEncontradoException;
import com.es.prueba.error.excepciones.PeticionIncorrectaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler({PeticionIncorrectaException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String manejarErrorBadRequest(HttpServletRequest resquest, Exception e){
        return e.getMessage();
    }

    @ExceptionHandler({NoEncontradoException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String manejarErrorNotFound(HttpServletRequest resquest, Exception e){
        return e.getMessage();
    }

    @ExceptionHandler({DuplicadoException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String manejarErrorConfrict(HttpServletRequest resquest, Exception e){
        return e.getMessage();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String manejarErrorGenerico(HttpServletRequest resquest, Exception e){
        return e.getMessage();
    }

}
