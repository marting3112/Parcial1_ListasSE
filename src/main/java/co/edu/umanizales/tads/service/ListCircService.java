package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.ListCirc;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data

public class ListCircService {
    private ListCirc petCirc;
    public ListCircService (){
        petCirc = new ListCirc();
    }
}
