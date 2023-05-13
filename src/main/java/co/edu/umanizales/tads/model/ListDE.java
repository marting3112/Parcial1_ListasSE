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
    public void addPet(Pet pet) throws IllegalArgumentException{
        if (pet == null) {
            throw new IllegalArgumentException("El objeto pet no puede ser nulo");
        }
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


    public void addToStart(Pet pet) throws IllegalArgumentException {
        if (pet == null) {
            throw new IllegalArgumentException("El objeto pet no puede ser nulo");
        }

        NodeDE newNode = new NodeDE(pet);
        if (head != null) {
            head.setPrev(newNode);
            newNode.setNext(head);
        }
        head = newNode;
        size++;
    }

    //Eliminar por edad
    public void deleteByAge(Byte age) throws IllegalArgumentException {
        if (age == null) {
            throw new IllegalArgumentException("La edad de la mascota no puede ser nula");
        }
        NodeDE temp = head;
        while (temp != null) {
            if (temp.getData().getAgePet() == age) {
                NodeDE prev = temp.getPrev();
                NodeDE next = temp.getNext();
                if (prev == null) {
                    head = next;
                } else {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrev(prev);
                }
            }
            temp = temp.getNext();
        }
    }

    //Eliminar por identificación
    public void deleteById(String id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("El identificador del dueño no puede ser nulo");
        }

        NodeDE temp = head;
        while (temp != null) {
            if (temp.getData().getOwnerIdentification().equals(id)) {
                NodeDE prev = temp.getPrev();
                NodeDE next = temp.getNext();
                if (prev == null) {
                    head = next;
                } else {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrev(prev);
                }
            }
            temp = temp.getNext();
        }
    }

    //Invertir la lista
    public void invert() {
        if (this.head == null) {
            return;
        }

        ListDE listcopy = new ListDE();
        NodeDE temp = this.head;
        while (temp != null) {
            listcopy.addToStart(temp.getData());
            temp = temp.getNext();
        }
        this.head = listcopy.getHead();
    }

    //Agregar mascotas m al inicio y f al final
    public void addBoyStart() throws IllegalStateException {
        if (head == null) {
            throw new IllegalStateException("La lista está vacía y no se pueden agregar elementos");
        }

        ListDE listCopy = new ListDE();
        NodeDE temp = head;
        while (temp != null) {
            if (temp.getData().getGender() == 'm') {
                listCopy.addToStart(temp.getData());
            } else {
                listCopy.addPet(temp.getData());
            }
            temp = temp.getNext();
        }
        head = listCopy.getHead();
    }


    //Intercalar niño - niña
    public void raffleBoyGirl() throws IllegalArgumentException {
        if (head == null) {
            throw new IllegalArgumentException("La lista está vacía y no se puede realizar el sorteo");
        }

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

        if (listMale.getSize() == 0 || listFemale.getSize() == 0) {
            throw new IllegalArgumentException("No hay suficientes mascotas de ambos géneros para realizar el sorteo");
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
    public float averageAge() throws IllegalStateException {
        if (head != null) {
            NodeDE temp = head;
            int count = 0;
            int ages = 0;
            while (temp.getNext() != null) {
                count++;
                ages += temp.getData().getAgePet();
                temp = temp.getNext();
            }
            if (count == 0) {
                throw new IllegalStateException("Cannot calculate average age of an empty list.");
            }
            return (float) ages / count;
        } else {
            throw new IllegalStateException("Cannot calculate average age of an empty list.");
        }
    }

    //Reporte de mascotas por ciudad
    public int getCountPetsByLocationCode(String code) throws IllegalArgumentException {
        if(code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //Reporte de mascotas por departamento
    public int getCountPetsByDeptoCode(String code){
        if (code == null || code.length() != 5) {
            throw new IllegalArgumentException("El código de departamento debe tener 5 caracteres.");
        }
        int count = 0;
        if (this.head != null){
            NodeDE temp = this.head;
            while (temp != null){
                String locCode = temp.getData().getLocation().getCode();
                if (locCode != null && locCode.length() >= 5 && locCode.substring(0, 5).equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //Añadir la mascota por posición
    public void addByPosition(Pet pet, int position) throws IndexOutOfBoundsException{
        if (position < 0 || position > size){
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }
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
    public void gainPosition(String id, int position) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (position < 0) {
            throw new IllegalArgumentException("La posición debe ser un número positivo");
        }
        if (head != null){
            NodeDE temp = head;
            int count = 1;
            while (temp != null && !temp.getData().getOwnerIdentification().equals(id)){
                temp = temp.getNext();
                count++;
            }
            if (temp != null){
                int newPosition = count - position;
                if (newPosition < 0) {
                    throw new IndexOutOfBoundsException("La posición especificada está fuera de los límites de la lista");
                }
                Pet listCopy = temp.getData();
                deleteById(temp.getData().getOwnerIdentification());
                if (newPosition > 0 ){
                    addByPosition(listCopy,newPosition);
                } else {
                    addToStart(listCopy);
                }
            }
        }
    }

    //Que el niño pierda posiciones
    public void backPosition(String id, int position) throws IllegalArgumentException {

        if (position < 0) {
            throw new IllegalArgumentException("Position must be a positive integer");
        }
        NodeDE temp = head;
        int count = 1;
        while (temp != null && !temp.getData().getOwnerIdentification().equals(id)) {
            temp = temp.getNext();
            count++;
        }

        int sum = position + count;
        Pet listCopy = temp.getData();
        deleteById(temp.getData().getOwnerIdentification());
        addByPosition(listCopy, sum);
    }

    //Informe de mascotas por rango de edad
    public int informRangeByAge(int first, int last) throws IllegalArgumentException {
        if (first < 0 || last < 0) {
            throw new IllegalArgumentException("Both first and last parameters must be non-negative integers.");
        }
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
    public void petToFinishByLetter(char first)  {
        if (this.head != null) {
            ListDE listCopy = new ListDE();
            NodeDE temp = this.head;
            char firstChar = Character.toUpperCase(first);

            while (temp != null) {
                char firstLetter = temp.getData().getNamePet().charAt(0);
                if (Character.toUpperCase(firstLetter) != firstChar) {
                    listCopy.addToStart(temp.getData());
                } else {
                    listCopy.addPet(temp.getData());
                }
                temp = temp.getNext();
            }
            this.head = listCopy.getHead();
        }
    }

    public boolean checkPet(Pet pet) throws IllegalArgumentException {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }

        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {

                temp = temp.getNext();
            }
        }

        return true;
    }

    /*
    Método kamikaze

    Miramos si la cabeza está vacia
        si
        No retornamos nada
        Si no
        Le pedimos al usuario que diligencie el id de el dueño de la mascota que quiere elmininar
        Llamamos a un ayudante
        Le pedimos que busque el costal que tiene a la mascota que desea eliminar
        Le decimos que la mascota anterior coja a la mascota siguiente del que queremos eliminar
        Validamos si el siguiente tiene algo
        Le decimos a la mascota siguiente de la que queremos eliminar que coja a la anterior de la que queremos eliminar
        La mascota que deseamos eliminar,le decimos que el siguiente sea nulo y el prev tambien sea igual a nulo

        Si está en la cabeza, el decimos que el siguiente sea igual a nulo
        Y a la mascota que está despues de la cabeza, le decimos que prev sea igual a nulo

        Si está en la cola, le decimos al anterior de la mascota que queremos eliminar que el siguiente sea nulo
        Y a la mascota que está antes, le decimos que el siguiente sea igual a nulo

        Si solo hay un niño, el siguiente debe ser igual a nulo y el prev también debe ser igual a nulo
     */

    public void kamikazeMethod (String id) {
        if (head != null) {
            if (head.getData().getOwnerIdentification().equals(id)){
                head = head.getNext();
                if (head != null) head.setPrev(null);
                size --;
                return;
            }
            int count = 0;
            NodeDE temp = head;
            while (temp != null) {
                if (temp.getData().getOwnerIdentification().equals(id)) {

                        temp.getPrev().setNext(temp.getNext());

                    if (temp.getNext() != null){
                        temp.getNext().setPrev(temp.getPrev());
                    }
                    size --;
                    count ++;
                }
                temp = temp.getNext();
            }
        }
    }

}
