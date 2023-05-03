package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class KidDTO {
        @NotNull(message = "Este campo no puede ir vacío")
        private String identification;
        @NotNull(message = "Este campo no puede ir vacío")
        @Size(min = 1, max = 30)
        private String name;
        @NotNull(message = "Este campo no puede ir vacío")
        @Min(0)
        @Max(14)
        private byte age;
        @NotNull(message = "Este campo no puede ir vacío")
        @Pattern(regexp = "[fm]", message = "El género debe ser 'f' o 'm' ")
        private char gender;
        @NotNull(message = "Este campo no puede ir vacío")
        private String codeLocation;

}
