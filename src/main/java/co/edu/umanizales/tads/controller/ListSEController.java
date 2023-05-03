package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RangeService rangeService;


    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids(), null), HttpStatus.OK);
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
        return new ResponseEntity<>(new ResponseDTO(200,
                "Niños por la edad dada eliminados", null), HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody @Valid KidDTO kidDTO) {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        Boolean checkkid = listSEService.getKids().Checkkid(new Kid(kidDTO.getIdentification(), kidDTO.getName(),
                kidDTO.getAge(), kidDTO.getGender(), location));
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(
                    404, "La ubicación no existe",
                    null), HttpStatus.OK);
        } else if (checkkid.equals(false)) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "El petacón ya existe",
                    null), HttpStatus.OK);
        } else {
            listSEService.getKids().add(
                    new Kid(kidDTO.getIdentification(),
                            kidDTO.getName(), kidDTO.getAge(),
                            kidDTO.getGender(), location));

            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado el petacón",
                    null), HttpStatus.OK);
        }

    }




    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbydepto")
    public ResponseEntity<ResponseDTO> getKidsByDeptoCode(){
        List<KidsByLocationDTO> kidsByLocationDTOList1= new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getKids().getCountKidsByDeptoCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList1.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList1,
                null), HttpStatus.OK);
    }

    @GetMapping(path="/infokids/{age}")
    public ResponseEntity<ResponseDTO> infokids(@PathVariable byte age){
        List<KidsByCityByGenderDTO> KidsByCityByGenderDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            if(loc.getCode().length() == 8){
                String nameCity = loc.getName();
                List<GendersDTO> GendersDTOList = new ArrayList<>();

                GendersDTOList.add(new GendersDTO('m', listSEService.getKids().
                        getCountKidsByCityByGenderByAge
                        (loc.getCode(),'m',age)));

                GendersDTOList.add(new GendersDTO('f', listSEService.getKids().
                        getCountKidsByCityByGenderByAge
                        (loc.getCode(),'f',age)));

                // esta operación se usa para sumar un atributo de un objeto dentro de una lista
               int total = GendersDTOList.get(0).getQuantity() + GendersDTOList.get(1).getQuantity();

            KidsByCityByGenderDTOList.add(new KidsByCityByGenderDTO(nameCity,GendersDTOList,total));

            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,KidsByCityByGenderDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path="/invertir")
    public ResponseEntity<ResponseDTO> invert(){

        listSEService.getKids().invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"Se ha invertido la lista",
                null), HttpStatus.OK);
    }

    @GetMapping(path="/promedio")
    public ResponseEntity<ResponseDTO> averageAge(){

        float averageAge = listSEService.getKids().averageAge();
        return new ResponseEntity<>(new ResponseDTO(
                200,"la edad promedio de los niños es: " + averageAge,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/boystostart")
    public ResponseEntity<ResponseDTO> addBoyStart(){

        listSEService.getKids().addBoyStart();
        return new ResponseEntity<>(new ResponseDTO(
                200,"la lista se ordenó con éxito",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/rafflekids")
    public ResponseEntity<ResponseDTO> raffleBoyGirl(){

        listSEService.getKids().raffleBoyGirl();
        return new ResponseEntity<>(new ResponseDTO(
                200,"la lista se alternó con éxito",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/rangeage")


    public ResponseEntity<ResponseDTO> informRangeByAge(){

        List<RangeDTO> kidsRangeList = new ArrayList<>();
        for (Ranges i : rangeService.getRanges()){
            int quantity = listSEService.getKids().informRangeByAge(i.getFrom(), i.getTo());
            kidsRangeList.add(new RangeDTO(i,quantity));
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"el rango de los niños es: "+kidsRangeList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/sendfinish/{first}")
    public ResponseEntity<ResponseDTO> kidToFinishByLetter(@PathVariable char first){
        listSEService.getKids().kidToFinishByLetter(Character.toUpperCase(first));
        return new ResponseEntity<>(new ResponseDTO(
                200,"los niños se han enviado al final",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/advanceposition/{code}/{numposition}")
    public ResponseEntity<ResponseDTO> gainPosition(@PathVariable String code, @PathVariable int numposition){

        listSEService.getKids().gainPosition(code,numposition,listSEService.getKids());
        return new ResponseEntity<>(new ResponseDTO(
                200,"El niño se ha movido con éxito",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/loseposition/{code}/{numposition}")
    public ResponseEntity<ResponseDTO> backPosition(@PathVariable String code, @PathVariable int numposition){

        listSEService.getKids().backPosition(code,numposition,listSEService.getKids());
        return new ResponseEntity<>(new ResponseDTO(
                200,"El niño se ha movido con éxito",
                null), HttpStatus.OK);
    }


}