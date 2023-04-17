package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<>(new ResponseDTO(200, "Niños eliminados", null), HttpStatus.OK);

    }


    @GetMapping(path = "/movekid/{initialplace}/{finalplace}")
    public ResponseEntity<ResponseDTO> move(@PathVariable ("initialplace")int initialplace,@PathVariable("finalplace")int finalplace) {
        if (initialplace < 1 || finalplace > listSEService.getKids().size()) {
            return new ResponseEntity<>(new ResponseDTO(400, "Los lugares no son válidos", null), HttpStatus.BAD_REQUEST);
        } else {
            listSEService.getKids().move(initialplace, finalplace);
            return new ResponseEntity<>(new ResponseDTO(200, "niño movido con éxito", null), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
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
        }else{
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



}