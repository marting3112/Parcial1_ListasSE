package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

@Data
public class PetDTO {

    private String ownerIdentification;
    private String namePet;
    private byte agePet;
    private String petType;
    private String breed;
    private String codeLocation;
    private char gender;



}
