package com.es.prueba.controller;

import org.springframework.web.bind.annotation.*;

/*
La anotación ResControllermarca esta clase como controller

Las clases controllers son las clases que reciben las peticiones HTTP directamete.
También son las que respoden al cliente.

En definitiva, son las clases que se COMUNICAN con el cliente.

Las clases Controller son las que tienen todos los endpoints de la aplicación.
*/

@RestController //
public class HolaMundoController {

    /*
    HTTP:
    GET -> OBTENER DATOS / LEER DATOS -> @GetMapping()
    POST -> INSERTAR DATOS -> @PostMapping()
    PUT -> ACTUALIZAR DATOS -> @PutMapping()
    DELETE -> ELIMINAR DATOS -> @DeleteMapping()
     */

    /*
    Hemos marcado la función holaMundo con la anotación @GetMapping("/es")

    Cuando entre una petición HTTP con  la función  GET y la URI /es, entonces ejecutará lo que tenga esta función
     */
    @GetMapping("/es")
    public String holaMundo(){
        return "<h1>Hola mundo</h1>";
    }

    @GetMapping("/en")
    public String helloWorld(){
        return "<h1>Hello world</h1>";
    }

    @GetMapping("/fr")
    public String bonjourMonde(){
        return "<h1>Bonjour le monde</h1>";
    }

    @GetMapping("/it")
    public String ciaoMondo(){
        return "<h1>Ciao Mondo</h1>";
    }

    @GetMapping("/de")
    public String HalloWelt(){
        return "<h1>Hallo Welt</h1>";
    }

    @GetMapping("/pt")
    public String OlaMundo(){
        return "<h1>Olá mundo</h1>";
    }

    @PostMapping("/es")
    public String insertarHolaMundo(){
        return "<h1>Insertar Hola Mundo</h1>";
    }

    @PutMapping("/es")
    public String actualizarHolaMundo(){
        return "<h1>Actualizar Hola Mundo</h1>";
    }

    @DeleteMapping("/es")
    public String eliminarHolaMundo(){
        return "<h1>Insertar Hola Mundo</h1>";
    }
}
