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

    public void addToStart(Pet pet) {
        if (head == null) {
            addPet(pet);
        }else{
            NodeDE newNode = new NodeDE(pet);
            NodeDE temp = head.getPrev();
            temp.setNext(newNode);
            newNode.setPrev(temp);
            newNode.setNext(head);
            head.setPrev(newNode);
            head=newNode;
            size++;
        }
    }

    public void addToEnd(Pet pet) {
        if (head == null) {
            addPet(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            NodeDE lastNode = head.getPrev();
            lastNode.setNext(newNode);
            newNode.setPrev(lastNode);
            newNode.setNext(head);
            head.setPrev(newNode);
            size++;
        }
    }

    public void addByPosition(Pet pet, int position) {
        if (position == 1) {
            addToStart(pet);
        } else {
            NodeDE temp = head;
            int count = 1;

            while (count < position -1) {
                temp = temp.getNext();
                count++;
            }
            NodeDE newNode = new NodeDE(pet);
            newNode.setNext(temp.getNext());
            newNode.setPrev(temp);
            temp.getNext().setPrev(newNode);
            temp.setNext(newNode);
            size++;
        }
    }

}
