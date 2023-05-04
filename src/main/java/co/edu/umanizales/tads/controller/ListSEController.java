package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Ranges;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import co.edu.umanizales.tads.service.RangeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
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

    public ResponseEntity<ResponseDTO> addToStart(@RequestBody Kid kid){
        try {
            listSEService.getKids().addToStart(kid);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "El niño fue añadido al principio", null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(400,
                    "Error al añadir al principio: " + e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/addposition")
    public ResponseEntity<ResponseDTO> addByPosition(@PathVariable int position, @RequestBody Kid kid) throws ListSEException {
        try {
            listSEService.getKids().addByPosition(kid, position);
            return new ResponseEntity<>(new ResponseDTO(200,
                    "El niño fue añadido en la posición", null), HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
            throw new ListSEException("La posición especificada no existe en la lista");
        } catch (NullPointerException e) {
            throw new ListSEException("La lista está vacía");
        }
    }

    @GetMapping(path = "/deletebyage/{age}")
    public ResponseEntity<ResponseDTO> deleteByAge(@PathVariable byte age) throws ListSEException {
        if (age < 0 || age > 14) {
            throw new ListSEException("La edad debe estar entre 0 y 14 años");
        }
        listSEService.getKids().deleteByAge(age);
        return new ResponseEntity<>(new ResponseDTO(200,
                "Niños por la edad dada eliminados", null), HttpStatus.OK);
    }


    @PostMapping(path = "/add")
    public ResponseEntity<ResponseDTO> addKid(@RequestBody @Valid KidDTO kidDTO) {
        try {
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
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, e.getMessage(),
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation(){
        try {
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
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/kidsbydepto")
    public ResponseEntity<ResponseDTO> getKidsByDeptoCode(){
        try {
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

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/infokids/{age}")
    public ResponseEntity<ResponseDTO> infokids(@PathVariable byte age){
        try {
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

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/invertir")
    public ResponseEntity<ResponseDTO> invert(){
        try {
            listSEService.getKids().invert();
            return new ResponseEntity<>(new ResponseDTO(
                    200,"Se ha invertido la lista",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, e.getMessage(),
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/promedio")
    public ResponseEntity<ResponseDTO> averageAge(){
        try {
            float averageAge = listSEService.getKids().averageAge();
            return new ResponseEntity<>(new ResponseDTO(
                    200,"la edad promedio de los niños es: " + averageAge,
                    null), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/boystostart")
    public ResponseEntity<ResponseDTO> addBoyStart() {
        try {
            listSEService.getKids().addBoyStart();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La lista se ordenó con éxito",
                    null), HttpStatus.OK);
        } catch (ListSEException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, e.getMessage(),
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor",
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/rafflekids")
    public ResponseEntity<ResponseDTO> raffleBoyGirl() {
        try {
            listSEService.getKids().raffleBoyGirl();
            return new ResponseEntity<>(new ResponseDTO(
                    200, "La lista se alternó con éxito", null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/rangeage")
    public ResponseEntity<ResponseDTO> informRangeByAge() {
        try {
            List<RangeDTO> kidsRangeList = new ArrayList<>();
            for (Ranges i : rangeService.getRanges()) {
                int quantity = listSEService.getKids().informRangeByAge(i.getFrom(), i.getTo());
                kidsRangeList.add(new RangeDTO(i, quantity));
            }
            return new ResponseEntity<>(new ResponseDTO(
                    200, "El rango de los niños es: " + kidsRangeList, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/sendfinish/{first}")
    public ResponseEntity<ResponseDTO> kidToFinishByLetter(@PathVariable char first) {
        try {
            listSEService.getKids().kidToFinishByLetter(Character.toUpperCase(first));
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Los niños se han enviado al final", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "El carácter debe ser una letra", null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/advanceposition/{code}/{numposition}")
    public ResponseEntity<ResponseDTO> gainPosition(@PathVariable String code, @PathVariable int numposition){
        try {
            listSEService.getKids().gainPosition(code,numposition,listSEService.getKids());
            return new ResponseEntity<>(new ResponseDTO(
                    200,"El niño se ha movido con éxito",
                    null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/loseposition/{code}/{numposition}")
    public ResponseEntity<ResponseDTO> backPosition(@PathVariable String code, @PathVariable int numposition) {
        try {
            listSEService.getKids().backPosition(code, numposition, listSEService.getKids());
            return new ResponseEntity<>(new ResponseDTO(
                    200, "El niño se ha movido con éxito", null), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseDTO(
                    400, "El número de posiciones debe ser mayor o igual a 1", null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(
                    500, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}