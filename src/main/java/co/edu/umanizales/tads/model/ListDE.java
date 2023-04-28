package co.edu.umanizales.tads.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ListDE {
    private NodeDE head;
    private NodeDE tail;

    //Adicionar mascota
    public void addPet(Pet pet) {
        int size = 0;

        if (head == null) {
            head = new NodeDE(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            NodeDE current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
            newNode.setPrev(current);
        }
        size++;
    }


    public void addToStart(Pet pet){
        if(head !=null)
        {
            NodeDE newNode = new NodeDE(pet);
            newNode.setNext(head);
            newNode.setPrev(head);
            head = newNode;
        }
        else {
            head = new NodeDE(pet);
        }
    }

    //Eliminar por edad
    public void deleteByAge (Byte age){
        NodeDE temp = head;
        while (temp != null){
            if (temp.getData().getAgePet() == age){
            NodeDE prev = temp.getPrev();
            NodeDE next = temp.getNext();
            if (prev == null){
                head = next;
            }else{
                prev.setNext(next);
            }
            if (next != null){
                next.setPrev(prev);
            }
            }
            temp = temp.getNext();
        }
    }

    //Eliminar por identificación
    public void deleteById (String id){
        NodeDE temp = head;
        while (temp != null){
            if (temp.getData().getOwnerIdentification().equals(id)){
                NodeDE prev = temp.getPrev();
                NodeDE next = temp.getNext();
                if (prev == null){
                    head = next;
                }else{
                    prev.setNext(next);
                }
                if (next != null){
                    next.setPrev(prev);
                }
            }
            temp = temp.getNext();
        }
    }

    //Invertir la lista
    public void invert(){
        if (head != null){
            ListDE listcopy = new ListDE();
            NodeDE temp = tail;
            while (temp != null){
                listcopy.addPet(temp.getData());
                temp = temp.getPrev();
            }
            head = listcopy.getHead();
            tail = listcopy.getTail();
        }
    }

    //Agregar niños al inicio y niñas al final
    public void addBoyStart(){
        if(head == null){
            return;
        }
        ListDE boysList = new ListDE();
        ListDE girlsList = new ListDE();

        NodeDE temp = head;
        while (temp != null){
            if (temp.getData().getGender()=='m'){
                boysList.addPet(temp.getData());
            }else{
                girlsList.addPet(temp.getData());
            }
            temp = temp.getNext();
        }
        head = null;
        NodeDE boyNode = boysList.getTail();
        while (boyNode != null){
            addToStart(boyNode.getData());
            boyNode = boyNode.getPrev();
        }
        NodeDE girlNode = girlsList.getTail();
        while (girlNode != null){
            addToStart(girlNode.getData());
            girlNode = girlNode.getPrev();

        }
    }


}
