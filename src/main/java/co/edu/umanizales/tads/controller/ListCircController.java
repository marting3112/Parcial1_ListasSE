package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListCircException;
import co.edu.umanizales.tads.exception.ListDEExcepcion;
import co.edu.umanizales.tads.exception.LocationNotFoundException;
import co.edu.umanizales.tads.exception.PetAlreadyAddedException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListCircService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= "/listacircular")

public class ListCircController {
    @Autowired
    private ListCircService listCircService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() throws ListDEExcepcion {
        return new ResponseEntity<>(new ResponseDTO(
                200, listCircService.getPetCirc().print(), null), HttpStatus.OK);
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

            listCircService.getPetCirc().addPet(newPet);
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se ha adicionado a la mascota con éxito", null), HttpStatus.OK);
        } catch (LocationNotFoundException e) {
            return new ResponseEntity<>(new ResponseDTO(404, e.getMessage(), null), HttpStatus.OK);
        } catch (PetAlreadyAddedException e) {
            return new ResponseEntity<>(new ResponseDTO(400, e.getMessage(), null), HttpStatus.OK);
        }
    }

    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStar(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listCircService.getPetCirc().addToStart(
                new Pet(petDTO.getOwnerIdentification(), petDTO.getNamePet(),
                        petDTO.getAgePet(), petDTO.getPetType(), petDTO.getBreed(),
                        location, petDTO.getGender()));

        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada al inicio", null),
                HttpStatus.OK);
    }

    @PostMapping(path = "/addtoend")
    public ResponseEntity<ResponseDTO> addToEnd(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listCircService.getPetCirc().addToEnd(
                new Pet(petDTO.getOwnerIdentification(), petDTO.getNamePet(),
                        petDTO.getAgePet(), petDTO.getPetType(), petDTO.getBreed(),
                        location, petDTO.getGender()));

        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada al final", null),
                HttpStatus.OK);
    }

    @PostMapping(path = "/addbyposition/{position}")
    public ResponseEntity<ResponseDTO> addByPosition(@RequestBody PetDTO petDTO, @PathVariable int position) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listCircService.getPetCirc().addByPosition(
                new Pet(petDTO.getOwnerIdentification(), petDTO.getNamePet(),
                        petDTO.getAgePet(), petDTO.getPetType(), petDTO.getBreed(),
                        location, petDTO.getGender()),position);

        return new ResponseEntity<>(new ResponseDTO(200, "Mascota adicionada en la posicion: "
                + position, null),
                HttpStatus.OK);
    }

}
