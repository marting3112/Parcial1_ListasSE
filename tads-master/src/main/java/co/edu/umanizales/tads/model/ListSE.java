package co.edu.umanizales.tads.model;

import lombok.Data;

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
    public void add(Kid kid){
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                temp = temp.getNext();
            }
            /// Parado en el último
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
    public void addToStart(Kid kid){
        if(head !=null)
        {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        }
        else {
            head = new Node(kid);
        }
    }

    //Eliminar por identificación
    public void deleteByidentification (String identification){
        Node currentNode = head;
        Node prevNode = null;

        while (currentNode != null && currentNode.getData().getIdentification() != identification) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        if(currentNode != null){
            if (prevNode == null){
                head = currentNode.getNext();
            }else {
                prevNode.setNext(currentNode.getNext());
            }
        }
    }

    //Eliminar por edad
    public void deleteByAge (byte age){
        Node currentNode = head;
        Node prevNode = null;
         while (currentNode != null && currentNode.getData().getAge() != age){
             prevNode = currentNode;
             currentNode = currentNode.getNext();
         }

         if(currentNode != null){
             if (prevNode == null){
                 head = currentNode.getNext();
             }else {
                 prevNode.setNext(currentNode.getNext());
             }
         }
    }

    public void move(int actualPlace, int finalPlace) {
        Node prev = null;
        Node actual = head;

        for (int i = 1; i < actualPlace; i++) {
            prev = actual;
            actual = actual.getNext();
        }

        Node prevEnd = null;
        Node end = head;

        for (int i = 1; i < finalPlace; i++) {
            prevEnd = end;
            end = end.getNext();
        }

        if (prev == null) {
            head = actual.getNext();
        } else {
            prev.setNext(actual.getNext());
        }

        if (prev==null){
            actual.setNext(end.getNext());
            end.setNext(actual);
        } else {
            prev.setNext(actual.getNext());
            actual.setNext(end.getNext());
            end.setNext(actual);
        }

    }



    public void addByPosition(Kid kid, int position){
        Node nuevoNodo = new Node(kid);
        if (position == 0){
            nuevoNodo.setNext(head);
            head = nuevoNodo;
        } else {
            Node actual = head;
            for (int i = 1; i < position - 1; i++){
                actual = actual.getNext();
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

}
