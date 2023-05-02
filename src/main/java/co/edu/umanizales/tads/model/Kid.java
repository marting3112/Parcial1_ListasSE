package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor

public class Kid {
    @NotBlank
    private String identification;
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;
    @NotBlank
    @DecimalMin(value = "0",message = "La edad debe ser mayor a 0 años")
    @DecimalMax(value = "15", message = "La edad debe ser menor a 15 años")
    private byte age;
    @NotBlank
    @Pattern(regexp = "[fm]", message = "El género debe ser 'f' o 'm' ")
    private char gender;
    @NotBlank
    private Location location;
}
