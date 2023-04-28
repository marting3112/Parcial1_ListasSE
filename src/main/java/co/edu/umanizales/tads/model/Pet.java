package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String ownerIdentification;
    private String namePet;
    private byte agePet;
    private char gender;
    private String breed;
}
