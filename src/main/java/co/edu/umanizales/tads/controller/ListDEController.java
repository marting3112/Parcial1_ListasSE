package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.PetsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.RangeDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/listde")

public class ListDEController {

    @Autowired
    private ListDEService listDEService;

    @Autowired
    private RangeService rangeService;

    @Autowired
    private LocationService locationService;

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

    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Pet pet) {
        listDEService.getPets().addToStart(pet);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue añadida al inicio", null), HttpStatus.OK);
    }

    @PostMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age) {
        listDEService.getPets().deleteByAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue eliminada", null), HttpStatus.OK);
    }

    @PostMapping(path = "/deletebyid/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable String id) {
        listDEService.getPets().deleteById(id);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue eliminada", null), HttpStatus.OK);
    }

    @PostMapping(path = "/invertir")
    public ResponseEntity<ResponseDTO> invert() {
        listDEService.getPets().invert();
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue invertida", null), HttpStatus.OK);
    }

    @PostMapping(path = "/boysfirst")
    public ResponseEntity<ResponseDTO> addBoyStart() {
        listDEService.getPets().addBoyStart();
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue actualizada", null), HttpStatus.OK);
    }

    @PostMapping(path = "/rafflepets")
    public ResponseEntity<ResponseDTO> raffleBoyGirl() {
        listDEService.getPets().raffleBoyGirl();
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue actualizada", null), HttpStatus.OK);
    }

    @PostMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> averageAge() {
        float averageAge = listDEService.getPets().averageAge();
        return new ResponseEntity<>(new ResponseDTO(
                200, "La edad promedio de mascotas es: " + averageAge, null), HttpStatus.OK);
    }

    @PostMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody Pet pet, @PathVariable int position) {
        listDEService.getPets().addByPosition(pet,position);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue añadida en la posición solicitada", null), HttpStatus.OK);
    }

    @PostMapping(path = "/gainposition/{code}/{move}")
    public ResponseEntity<ResponseDTO> gainPosition(@PathVariable String code, @PathVariable int move) {
        listDEService.getPets().gainPosition(code,move);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue adelantada en la lista", null), HttpStatus.OK);
    }

    @PostMapping(path = "/backposition/{code}/{move}")
    public ResponseEntity<ResponseDTO> backPosition(@PathVariable String code, @PathVariable int move) {
        listDEService.getPets().backPosition(code,move);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota perdio posiciones en la lista", null), HttpStatus.OK);
    }

    @GetMapping(path = "/rangeage")
    public ResponseEntity<ResponseDTO> informRangeByAge(){

        List<RangeDTO> PetsRangeList = new ArrayList<>();
        for (Ranges i : rangeService.getRanges()){
            int quantity = listDEService.getPets().informRangeByAge(i.getFrom(), i.getTo());
            PetsRangeList.add(new RangeDTO(i,quantity));
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"el rango de los niños es: "+PetsRangeList,
                null), HttpStatus.OK);
    }

}
