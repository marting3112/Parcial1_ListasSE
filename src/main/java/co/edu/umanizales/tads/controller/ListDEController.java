package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
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
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        Boolean samePets = listDEService.getPets().CheckPet(new Pet(petDTO.getOwnerIdentification(),
                petDTO.getNamePet(),petDTO.getAgePet(), petDTO.getPetType(), petDTO.getBreed(),
                location, petDTO.getGender()));
        if (location == null){
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe", null), HttpStatus.OK);
        } else if (samePets.equals(false)) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "Esta mascota ya fue añadida", null), HttpStatus.OK);
        }else{
            listDEService.getPets().addPet(new Pet(petDTO.getOwnerIdentification(),
                    petDTO.getNamePet(),petDTO.getAgePet(), petDTO.getPetType(), petDTO.getBreed(),
                    location, petDTO.getGender()));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado a la mascota con éxito", null), HttpStatus.OK);

        }
    }

    @GetMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Pet pet) {
        listDEService.getPets().addToStart(pet);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue añadida al inicio", null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age) {
        listDEService.getPets().deleteByAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue eliminada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletebyid/{id}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable String id) {
        listDEService.getPets().deleteById(id);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue eliminada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/invertir")
    public ResponseEntity<ResponseDTO> invert() {
        listDEService.getPets().invert();
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue invertida", null), HttpStatus.OK);
    }

    @GetMapping(path = "/boysfirst")
    public ResponseEntity<ResponseDTO> addBoyStart() {
        listDEService.getPets().addBoyStart();
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue actualizada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/rafflepets")
    public ResponseEntity<ResponseDTO> raffleBoyGirl() {
        listDEService.getPets().raffleBoyGirl();
        return new ResponseEntity<>(new ResponseDTO(
                200, "La lista fue actualizada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO> averageAge() {
        float averageAge = listDEService.getPets().averageAge();
        return new ResponseEntity<>(new ResponseDTO(
                200, "La edad promedio de mascotas es: " + averageAge, null), HttpStatus.OK);
    }

    @GetMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody Pet pet, @PathVariable int position) {
        listDEService.getPets().addByPosition(pet,position);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue añadida en la posición solicitada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/gainposition/{code}/{move}")
    public ResponseEntity<ResponseDTO> gainPosition(@PathVariable String code, @PathVariable int move) {
        listDEService.getPets().gainPosition(code,move);
        return new ResponseEntity<>(new ResponseDTO(
                200, "La mascota fue adelantada en la lista", null), HttpStatus.OK);
    }

    @GetMapping(path = "/backposition/{code}/{move}")
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

    @GetMapping(path = "/infolocation")
    public ResponseEntity<ResponseDTO> getCountPetsByLocationCode(){
        List<KidsByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
            if(count>0){
                petsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,petsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/infodepto")
    public ResponseEntity<ResponseDTO> getCountPetsByDeptoCode(){
        List<KidsByLocationDTO> petsByLocationDTOList1= new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listDEService.getPets().getCountPetsByDeptoCode(loc.getCode());
            if(count>0){
                petsByLocationDTOList1.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,petsByLocationDTOList1,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/sendfinish/{first}")
    public ResponseEntity<ResponseDTO> petToFinishByLetter(@PathVariable char first){
        listDEService.getPets().petToFinishByLetter(Character.toUpperCase(first));
        return new ResponseEntity<>(new ResponseDTO(
                200,"los niños con la letra dada se han enviado al final",
                null), HttpStatus.OK);
    }

}
