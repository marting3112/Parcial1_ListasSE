package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEExcepcion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data

public class ListCirc {
    private NodeDE head;
    private int size;

    public void addPet(Pet pet) {
        if (pet == null) {
            return;
        }

        NodeDE newNode = new NodeDE(pet);

        if (head == null) {
            head = newNode;
            head.setNext(newNode);
            head.setPrev(newNode);
        } else {
            NodeDE lastNode = head.getPrev();
            newNode.setNext(head);
            newNode.setPrev(lastNode);
            lastNode.setNext(newNode);
            head.setPrev(newNode);
        }

        size++;
    }

    public Pet[] print() throws ListDEExcepcion {
        Pet[] petList = new Pet[size];

        int num = 0;
        NodeDE temp = head;

        if (temp == null) {
            throw new ListDEExcepcion("ERROR: La lista está vacía");
        }

        do {
            petList[num] = temp.getData();
            temp = temp.getNext();
            num++;
        } while (temp != head );

        return petList;
    }




}
