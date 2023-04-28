package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.ListSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/listde")

public class ListDEController {

    @Autowired
    private ListDEService listDEService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listDEService.getPets(), null), HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody Pet pet) {
        listDEService.getPets().addPet(pet);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue añadida", null), HttpStatus.OK);
    }


}
