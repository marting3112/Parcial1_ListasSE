package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor

public class Kid {
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
    private Location location;
}
