package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.service.ListSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids(), null), HttpStatus.OK);
    }

    @GetMapping(path="/add")
    public ResponseEntity<ResponseDTO> add(@RequestBody Kid kid){
        listSEService.getKids().add(kid);
        return new ResponseEntity<>(new ResponseDTO(200,
                "El niño ha sido añadido", null), HttpStatus.OK);
    }

    @GetMapping(path="/addstart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Kid kid){
        listSEService.getKids().addToStart(kid);
        return new ResponseEntity<>(new ResponseDTO(200,
                "El niño fue añadido al principio", null), HttpStatus.OK);
    }

    @GetMapping(path="/addposition")
    public ResponseEntity<ResponseDTO> addByPosition(@PathVariable int position, @RequestBody Kid kid){
        listSEService.getKids().addByPosition(kid, position);
        return new ResponseEntity<>(new ResponseDTO(200,
                "El niño fue añadido en la posición", null), HttpStatus.OK);}

    @GetMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age) {
        listSEService.getKids().deleteByAge(age);
        return new ResponseEntity<>(new ResponseDTO(200, "Niños eliminados", null), HttpStatus.OK);

    }

    /*

    Este es el getmapping para poder mover los niños por lugar. Lo primero que intenté hacer fue obtener
    el int del place y compararlo tanto que no sea menor a uno ni que sea mayor al tamaño de la lista haciendo el
    API. Desafortunadamente me sale un error cuando intento poner el tamaño de la lista.

    @GetMapping(path = "/movekid/{place}")
    public ResponseEntity<ResponseDTO> move(@PathVariable int place) {
        if (place < 1 && place > listSEService.getKids().size()) {
            return new ResponseEntity<>(new ResponseDTO(400, "Los lugares no son válidos", null), HttpStatus.BAD_REQUEST);
        } else {
            listSEService.getKids().move(place);
            return new ResponseEntity<>(new ResponseDTO(200, "niño movido con éxito", null), HttpStatus.OK);
        }
    }

     */

}