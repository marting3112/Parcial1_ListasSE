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

}
