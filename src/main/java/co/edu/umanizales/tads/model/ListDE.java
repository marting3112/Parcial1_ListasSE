package co.edu.umanizales.tads.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDE {
    private NodeDE head;
    private NodeDE tail;
    private int size;
    private List<Pet> pets = new ArrayList<>();

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

    public List <Pet> print(){
        pets.clear();
        if (head != null){
            NodeDE temp = head;
            while (temp != null){
                pets.add(temp.getData());
                temp = temp.getNext();
            }
        }
        return pets;
    }


    public void addToStart(Pet pet){

        NodeDE newNode = new NodeDE(pet);
        if(head !=null)
        {
            head.setPrev(newNode);
            newNode.setNext(head);
        }
        head = newNode;
        size ++;

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
        if (this.head != null){
            ListDE listcopy = new ListDE();
            NodeDE temp = this.head;
            while (temp != null){
                listcopy.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listcopy.getHead();

        }
    }

    //Agregar mascotas m al inicio y f al final
    public void addBoyStart(){
        if (head != null){
            ListDE listCopy = new ListDE();
            NodeDE temp = head;
            while (temp != null){
                if (temp.getData().getGender() == 'm'){
                    listCopy.addToStart(temp.getData());
                }else{
                    listCopy.addPet(temp.getData());
                }
                temp = temp.getNext();
            }
            head = listCopy.getHead();
        }
    }


    //Intercalar niño - niña
    public void raffleBoyGirl(){
        ListDE listMale = new ListDE();
        ListDE listFemale = new ListDE();
        NodeDE temp = this.head;
        while (temp != null){
            if(temp.getData().getGender()=='m'){
                listMale.addPet(temp.getData());
            }
            if(temp.getData().getGender()=='f'){
                listFemale.addPet(temp.getData());
            }
            temp = temp.getNext();
        }

        ListDE sortedList = new ListDE();
        NodeDE maleNode = listMale.getHead();
        NodeDE femaleNode = listFemale.getHead();
        while (maleNode != null || femaleNode != null){
            if (maleNode != null){
                sortedList.addPet(maleNode.getData());
                maleNode = maleNode.getNext();
            }
            if (femaleNode != null){
                sortedList.addPet(femaleNode.getData());
                femaleNode = femaleNode.getNext();
            }
        }
        this.head = sortedList.getHead();
    }


    //Obtener promedio de edad
    public float averageAge(){
        if(head != null){
            NodeDE temp = head;
            int count = 0;
            int ages = 0;
            while (temp.getNext() != null){
                count++;
                ages = ages + temp.getData().getAgePet();
                temp = temp.getNext();
            }
            return (float) ages/count;
        }else{
            return (int) 0;
        }
    }

    //Reporte de mascotas por ciudad
    public int getCountPetsByLocationCode(String code){
        int count =0;
        if( this.head!=null){
            NodeDE temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //Reporte de mascotas por departamento
    public int getCountPetsByDeptoCode(String code){
        int count =0;
        if( this.head!=null){
            NodeDE temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().substring(0,5).equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //Añadir la mascota por posición
    public void addByPosition(Pet pet, int position){
        NodeDE newNodo = new NodeDE(pet);
        if (position == 0){
            newNodo.setNext(head);
            if (head != null){
                head.setPrev(newNodo);
            }
            head = newNodo;
        }else {
            NodeDE current = head;
            for (int i = 1; i < position -1; i++){
                current = current.getNext();
            }
            newNodo.setNext(current.getNext());
            if (current.getNext() != null){
                current.getNext().setPrev(newNodo);
            }
            current.setNext(newNodo);
            newNodo.setPrev(current);
        }
    }

    //Que la mascota adelante posiciones
    public void gainPosition(String id, int position){
        if (head != null){
            NodeDE temp = head;
            int count =1;
            while (temp != null && !temp.getData().getOwnerIdentification().equals(id)){
                temp = temp.getNext();
                count ++;
            }
            if (temp != null){
                int newPosition = count - position;
                Pet listCopy = temp.getData();
                deleteById(temp.getData().getOwnerIdentification());
                if (newPosition > 0 ){
                    addByPosition(listCopy,newPosition);
                }else{
                    addToStart(listCopy);
                }
            }
        }
    }

    //Que el niño pierda posiciones
    public void backPosition(String id, int position){
        if (head != null){
            NodeDE temp = head;
            int count =1;
            while (temp != null && !temp.getData().getOwnerIdentification().equals(id)){
                temp = temp.getNext();
                count ++;
            }
            int sum = position + count;
            if (temp != null){
                Pet listCopy = temp.getData();
                deleteById(temp.getData().getOwnerIdentification());
                addByPosition(listCopy,sum);
            }
        }
    }

    //Informe de mascotas por rango de edad
    public int informRangeByAge(int first, int last){
        NodeDE temp = head;
        int count = 0;
        while (temp != null){
            if (temp.getData().getAgePet() >= first && temp.getData().getAgePet() <= last){
                count ++;
            }
            temp = temp.getNext();
        }
        return count;
    }

    //Enviar al final los niños que su nombre inicie por la letra dada
    public void petToFinishByLetter (char first){
        if (this.head != null){
            ListDE listCopy = new ListDE();
            NodeDE temp = this.head;
            char firstChar = Character.toUpperCase(first);
            while (temp != null){
                char firstLetter = temp.getData().getNamePet().charAt(0);
                if (Character.toUpperCase(firstLetter) != firstChar){
                    listCopy.addToStart(temp.getData());
                }else {
                    listCopy.addPet(temp.getData());
                }
                temp = temp.getNext();
            }
            this.head = listCopy.getHead();
        }
    }

    public boolean CheckPet (Pet pet){
        if(this.head != null){
            NodeDE temp = this.head;
            while (temp != null){
                if (temp.getData().getNamePet().equals(pet.getNamePet()) && temp.getData().getLocation().equals(pet.getLocation())){
                    return false;
                }
                temp = temp.getNext();
            }
        }
        return true;
    }

}
