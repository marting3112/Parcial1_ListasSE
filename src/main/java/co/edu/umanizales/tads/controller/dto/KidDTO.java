package co.edu.umanizales.tads.controller.dto;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class KidDTO {
        @NotBlank(message = "Este campo no puede ir vacío")
        private String identification;
        @NotBlank(message = "Este campo no puede ir vacío")
        @Size(max = 30, message = "el nombre debe ser máximo de 30 caracteres")
        private String name;

        @Min(value = 1)
        @Max(value = 14)
        private byte age;
        private char gender;
        @NotBlank(message = "Este campo no puede ir vacío")
        private String codeLocation;

}
