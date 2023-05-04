package co.edu.umanizales.tads.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PetDTO {
    @NotBlank(message = "Este campo no puede ir vacío")
    private String ownerIdentification;

    @Size(max = 30, message = "el nombre debe ser máximo de 30 caracteres")
    @NotBlank(message = "Este campo no puede ir vacío")
    private String namePet;
    @Min(value = 1)
    @Max(value = 14)
    private byte agePet;
    @NotBlank(message = "Este campo no puede ir vacío")
    private String petType;
    @NotBlank(message = "Este campo no puede ir vacío")
    private String breed;
    @NotBlank(message = "Este campo no puede ir vacío")
    private String codeLocation;
    private char gender;



}
