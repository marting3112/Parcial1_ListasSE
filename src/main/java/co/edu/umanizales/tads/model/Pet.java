package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String ownerIdentification;
    private String namePet;
    private byte agePet;
    private String petType;
    private String breed;
    private Location location;
    private char gender;
    private boolean dirty;
    private int fleas;

    public Pet(String ownerIdentification, String namePet, byte agePet, String petType,
               String breed, Location location, char gender,int fleas){
        this.ownerIdentification = ownerIdentification;
        this.namePet = namePet;
        this.agePet = agePet;
        this.petType = petType;
        this.breed = breed;
        this.location = location;
        this.gender = gender;
        dirty = true;
        this.fleas = fleas;
    }
}
