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

    //Para decirle al niño que adelante posiciones

    //Añadir por posición

    public void move(int position, Kid data){
        if(head == null) {
            return;
        }

        Node actual = head;
        Node prev = null;
        int counter = 0;

        while (actual != null && counter < position){
            prev = actual;
            actual = actual.getNext();
            counter++;
        }
        if (actual==null){
            return;
        }

        Node nuevo = new Node(data);
        if (prev==null){
            head = nuevo;
        } else {
            prev.setNext(nuevo);
        }
    }


    public void addByPosition(Kid kid, int position){
        Node nuevoNodo = new Node(kid);
        if (position == 0){
            nuevoNodo.setNext(head);
            head = nuevoNodo;
        } else {
            Node actual = head;
            for (int i = 0; i < position - 1; i++){
                actual = actual.getNext();
            }
            nuevoNodo.setNext(actual.getNext());
            actual.setNext(nuevoNodo);
        }
    }

}
