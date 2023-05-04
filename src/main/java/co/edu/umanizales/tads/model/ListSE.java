package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ListSE {
    private Node head;
    /*
    Algoritmo de adicionar al final
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el ùltimo

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void add(Kid kid) throws ListSEException {
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                if(temp.getData().getIdentification().equals(kid.getIdentification())){
                    throw new ListSEException("Ya existe un  niño");
                }
                temp = temp.getNext();
            }
            if(temp.getData().getIdentification().equals(kid.getIdentification())){
                throw new ListSEException("Ya existe un  niño");

            }
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
    }

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabeza
     */
    public void addToStart(Kid kid) throws ListSEException {
        if (kid == null) {
            throw new ListSEException("Kid object can't be null");
        }
        if (head != null && head.getData().getIdentification().equals(kid.getIdentification())) {
            throw new ListSEException("Kid with the same identification already exists at the start of the list");
        }
        Node newNode = new Node(kid);
        newNode.setNext(head);
        head = newNode;
    }

    //Eliminar por identificación
    public void deleteByIdentification(String identification) throws ListSEException {
        Node currentNode = head;
        Node prevNode = null;

        while (currentNode != null && currentNode.getData().getIdentification().equals(identification)) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        if (currentNode == null) {
            throw new ListSEException("El niño con la identificación " + identification + " no se encuentra en la lista");
        } else {
            if (prevNode == null) {
                head = currentNode.getNext();
            } else {
                prevNode.setNext(currentNode.getNext());
            }
        }
    }

    //Eliminar por edad
    public void deleteByAge(Byte age) throws ListSEException {
        Node temp = head;
        ListSE listcopy = new ListSE();
        boolean found = false;
        while (temp != null) {
            if (temp.getData().getAge() != age) {
                listcopy.addToStart(temp.getData());
            } else {
                found = true;
            }
            temp = temp.getNext();
        }
        if (found) {
            this.head = listcopy.getHead();
        } else {
            throw new ListSEException("No se encontraron niños con la edad especificada.");
        }
    }

    public void addByPosition(Kid kid, int position) throws ListSEException {
        if (position < 0) {
            throw new ListSEException("La posición no puede ser negativa");
        }

        Node nuevoNodo = new Node(kid);

        if (position == 0) {
            nuevoNodo.setNext(head);
            head = nuevoNodo;
        } else {
            Node actual = head;
            int index = 1;
            while (actual != null && index < position) {
                actual = actual.getNext();
                index++;
            }

            if (actual == null) {
                throw new ListSEException("La posición es mayor que el tamaño de la lista");
            }

            nuevoNodo.setNext(actual.getNext());
            actual.setNext(nuevoNodo);
        }
    }

    public int size() {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    //Reporte de niños por ciudad
    public int getCountKidsByLocationCode(String code) throws ListSEException {
        int count = 0;
        try {
            if(this.head != null) {
                Node temp = this.head;
                while(temp != null) {
                    if(temp.getData().getLocation().getCode().equals(code)) {
                        count++;
                    }
                    temp = temp.getNext();
                }
            } else {
                throw new ListSEException("La lista está vacía.");
            }
        } catch (NullPointerException e) {
            throw new ListSEException("La ubicación del niño no está especificada.");
        }
        return count;
    }

    //Reporte de niños por departamento
    public int getCountKidsByDeptoCode(String code) throws IllegalArgumentException {
        if (code == null || code.length() != 5) {
            throw new IllegalArgumentException("Código de departamento inválido");
        }

        int count = 0;

        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().substring(0, 5).equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }

        return count;
    }

    public boolean Checkkid(Kid kid) throws IllegalArgumentException {
        if (kid == null) {
            throw new IllegalArgumentException("Kid cannot be null");
        }
        if (kid.getName() == null || kid.getLocation() == null) {
            throw new IllegalArgumentException("Kid name and location cannot be null");
        }
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getName().equals(kid.getName()) && temp.getData().getLocation().equals(kid.getLocation())) {
                    return false;
                }
                temp = temp.getNext();
            }
        }
        return true;
    }

    public int getCountKidsByCityByGenderByAge(String code, char gender, byte age) throws IllegalArgumentException {
        if(code == null || code.isEmpty()){
            throw new IllegalArgumentException("El código de la ciudad no puede ser nulo o vacío.");
        }
        int count = 0;
        if(this.head != null){
            Node temp = this.head;
            while (temp != null){
                if (temp.getData().getLocation().getCode().equals(code)
                        && temp.getData().getGender() == gender && temp.getData().getAge() > age){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //Invertir lista
    public void invert() throws NullPointerException, ListSEException {
        if (this.head != null) {
            ListSE listcopy = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listcopy.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listcopy.getHead();
        } else {
            throw new NullPointerException("La lista está vacía");
        }
    }

    //Obtener promedio de edad

    public float averageAge() throws ArithmeticException {
        if(head != null){
            Node temp = head;
            int count = 0;
            int ages = 0;
            while (temp.getNext() != null){
                count++;
                ages = ages + temp.getData().getAge();
                temp = temp.getNext();
            }
            if(count != 0) {
                return (float) ages/count;
            } else {
                throw new ArithmeticException("Division by zero: the list is empty");
            }
        } else {
            throw new NullPointerException("The list is empty");
        }
    }

    //Agregar niños al inicio y niñas al final
    public void addBoyStart() throws ListSEException {
        if (this.head == null) {
            throw new ListSEException("The list is empty.");
        }

        ListSE listcopy = new ListSE();
        Node temp = this.head;

        while (temp != null) {
            if (temp.getData().getGender() == 'm') {
                listcopy.add(temp.getData());
            }
            temp = temp.getNext();
        }

        temp = this.head;

        while (temp != null) {
            if (temp.getData().getGender() == 'f') {
                listcopy.add(temp.getData());
            }
            temp = temp.getNext();
        }

        if (listcopy.getHead() == null) {
            throw new ListSEException("There are no boys in the list.");
        }

        this.head = listcopy.getHead();
    }

    //Intercalar niños y niñas
    public void raffleBoyGirl() throws ListSEException {
        if (head == null) {
            throw new ListSEException("La lista está vacía");
        }

        ListSE listM = new ListSE();
        ListSE listF = new ListSE();
        Node temp = head;
        while (temp != null) {
            if (temp.getData().getGender() == 'm') {
                listM.add(temp.getData());
            }
            if (temp.getData().getGender() == 'f') {
                listF.add(temp.getData());
            }
            temp = temp.getNext();
        }

        if (listM == null || listF == null) {
            throw new ListSEException("No hay suficientes niños y niñas en la lista para hacer un sorteo");
        }

        ListSE aleatList = new ListSE();
        Node mNode = listM.getHead();
        Node fNode = listF.getHead();
        while (mNode != null) {
            if (mNode != null) {
                aleatList.add(mNode.getData());
                mNode = mNode.getNext();
            }
            if (fNode != null) {
                aleatList.add(fNode.getData());
                fNode = fNode.getNext();
            }
        }
        this.head = aleatList.getHead();
    }

    //Informe de niños por rango de edad
    public ListSE getKidsByLocation(String code) throws ListSEException {
        if (code == null) {
            throw new ListSEException("Code can't be null");
        }
        if (code.isEmpty()) {
            throw new ListSEException("Code can't be empty");
        }

        ListSE result = new ListSE();
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation() != null && temp.getData().getLocation().getCode().equals(code)) {
                    result.add(temp.getData());
                }
                temp = temp.getNext();
            }
        }
        return result;
    }

    //Enviar al final niños que su nombre empiece por la letra dada
    public void kidToFinishByLetter(char first) throws ListSEException {
        if (!Character.isLetter(first)) {
            throw new ListSEException("First character must be a letter");
        }

        ListSE sendFinish = new ListSE();
        Node temp = this.head;

        while (temp != null){
            if(temp.getData().getName().charAt(0) != Character.toUpperCase(first)){
                sendFinish.add(temp.getData());
            }
            temp = temp.getNext();
        }
        temp = this.head;

        while (temp != null){
            if (temp.getData().getName().charAt(0) == Character.toUpperCase(first)){
                sendFinish.add(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = sendFinish.getHead();
    }

    //El niño adelante posiciones
    public void gainPosition(String id, int position, ListSE listSE) throws ListSEException {
        if (id == null) {
            throw new ListSEException("Kid's identification can't be null");
        }
        if (position < 1) {
            throw new ListSEException("Position can't be less than 1");
        }
        if (listSE == null) {
            throw new ListSEException("List can't be null");
        }

        if (head != null) {
            Node temp = this.head;
            int count = 1;

            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                temp = temp.getNext();
                count++;
            }
            int newPosition = count - position;
            Kid listCopy = temp.getData();
            listSE.deleteByIdentification(temp.getData().getIdentification());
            listSE.addByPosition(listCopy, newPosition);
        }
    }

    //El niño pierda posiciones
    public void backPosition(String id, int position, ListSE listSE) throws ListSEException {
        if (head == null) {
            throw new ListSEException("The list is empty");
        }
        if (id == null) {
            throw new ListSEException("Id can't be null");
        }
        if (position < 1 || position > listSE.size() + 1) {
            throw new ListSEException("Position out of range");
        }

        Node temp = this.head;
        int count = 1;

        while (temp != null && !temp.getData().getIdentification().equals(id)) {
            temp = temp.getNext();
            count++;
        }

        if (temp == null) {
            throw new ListSEException("Kid with id " + id + " not found");
        }

        int newPosition = position + count;
        Kid listCopy = temp.getData();
        listSE.deleteByIdentification(temp.getData().getIdentification());
        listSE.addByPosition(listCopy, newPosition);
    }

    //Informe de niños por rango de edad
    public int informRangeByAge(int first, int last) throws IllegalArgumentException {
        if (first < 0 || last < 0 || first > last) {
            throw new IllegalArgumentException("Invalid age range");
        }
        Node temp = head;
        int count = 0;
        while (temp != null){
            if (temp.getData().getAge() >= first && temp.getData().getAge() <= last){
                count ++;
            }
            temp = temp.getNext();
        }
        return count;
    }

}
