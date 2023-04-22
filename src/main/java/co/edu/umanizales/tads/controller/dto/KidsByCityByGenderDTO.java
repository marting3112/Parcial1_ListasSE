package co.edu.umanizales.tads.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KidsByCityByGenderDTO {

    private String city;
    private List <GendersDTO> genders;
    private int total;

}
