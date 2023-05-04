package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.LocationNotFoundException;
import co.edu.umanizales.tads.exception.PetAlreadyAddedException;
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
                200, listDEService.getPets().print(), null), HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        try {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null){
                throw new LocationNotFoundException("La ubicación no existe");
            }
            Pet newPet = new Pet(petDTO.getOwnerIdentification(), petDTO.getNamePet(),
                    petDTO.getAgePet(), petDTO.getPetType(), petDTO.getBreed(),
                    location, petDTO.getGender());

            listDEService.getPets().addPet(newPet);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado a la mascota con éxito", null), HttpStatus.OK);
        } catch (LocationNotFoundException e) {
            return new ResponseEntity<>(new ResponseDTO(404, e.getMessage(), null), HttpStatus.OK);
        } catch (PetAlreadyAddedException e) {
            return new ResponseEntity<>(new ResponseDTO(400, e.getMessage(), null), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Pet pet) {
        try {
            listDEService.getPets().addToStart(pet);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue añadida al inicio", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, e.getMessage(), null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se ha producido un error al añadir la mascota al inicio", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age) {
        try {
            listDEService.getPets().deleteByAge(age);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue eliminada", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al eliminar la mascota", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/deletebyid/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable String id) {
        try {
            listDEService.getPets().deleteById(id);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue eliminada", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al eliminar la mascota", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/invertir")
    public ResponseEntity<ResponseDTO> invert() {
        try {
            listDEService.getPets().invert();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La lista fue invertida", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Ocurrió un error al invertir la lista", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/boysfirst")
    public ResponseEntity<ResponseDTO> addBoyStart() {
        try {
            listDEService.getPets().addBoyStart();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al agregar el primer niño", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue actualizada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/rafflepets")
    public ResponseEntity<ResponseDTO> raffleBoyGirl() {
        try {
            listDEService.getPets().raffleBoyGirl();
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al sortear las mascotas", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue actualizada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> averageAge() {
        try {
            float averageAge = listDEService.getPets().averageAge();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La edad promedio de mascotas es: " + averageAge, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al calcular la edad promedio de las mascotas", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody Pet pet, @PathVariable int position) {
        try {
            listDEService.getPets().addByPosition(pet,position);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue añadida en la posición solicitada", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al agregar la mascota en la posición solicitada", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/gainposition/{code}/{move}")
    public ResponseEntity<ResponseDTO> gainPosition(@PathVariable String code, @PathVariable int move) {
        try {
            listDEService.getPets().gainPosition(code,move);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota fue adelantada en la lista", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al adelantar la mascota en la lista", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/backposition/{code}/{move}")
    public ResponseEntity<ResponseDTO> backPosition(@PathVariable String code, @PathVariable int move) {
        try {
            listDEService.getPets().backPosition(code,move);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La mascota perdio posiciones en la lista", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Se produjo un error al retroceder la mascota en la lista", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/rangeage")
    public ResponseEntity<ResponseDTO> informRangeByAge(){
        try {
            List<RangeDTO> PetsRangeList = new ArrayList<>();
            for (Ranges i : rangeService.getRanges()){
                int quantity = listDEService.getPets().informRangeByAge(i.getFrom(), i.getTo());
                PetsRangeList.add(new RangeDTO(i,quantity));
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200,"el rango de los niños es: "+PetsRangeList,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500,"Error al obtener el rango de edades",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/infolocation")
    public ResponseEntity<ResponseDTO> getCountPetsByLocationCode(){
        List<KidsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        try{
            for(Location loc: locationService.getLocations()){
                int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
                if(count>0){
                    petsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200,petsByLocationDTOList,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/infodepto")
    public ResponseEntity<ResponseDTO> getCountPetsByDeptoCode(){
        List<KidsByLocationDTO> petsByLocationDTOList1= new ArrayList<>();
        try{
            for(Location loc: locationService.getLocations()){
                int count = listDEService.getPets().getCountPetsByDeptoCode(loc.getCode());
                if(count>0){
                    petsByLocationDTOList1.add(new KidsByLocationDTO(loc,count));
                }
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200,petsByLocationDTOList1,
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/sendfinish/{first}")
    public ResponseEntity<ResponseDTO> petToFinishByLetter(@PathVariable char first){
        try{
            listDEService.getPets().petToFinishByLetter(Character.toUpperCase(first));
            return new ResponseEntity<>(new ResponseDTO(
                    200,"los niños con la letra dada se han enviado al final",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
