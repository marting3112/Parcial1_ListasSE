package co.edu.umanizales.tads.model;

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
    public void add(Kid kid){
        //en esta parte se pregunta si la cabeza está vacia
        if(head != null){
            //se crea un nodo que se pare en la cabeza (ayudante)
            Node temp = head;
            //Se crea un ciclo que dice que el ayudante va a recorrer la lista hasta que no haya nada
            while(temp.getNext() !=null)
            {
                //El mensajero se pasa por cada costal hasta que no encuentre nada
                temp = temp.getNext();
            }
            /// Se crea un nuevo costal con el niño
            Node newNode = new Node(kid);
            //Donde estaba parado el ayudante, queda como el nuevo nodo que será el niño
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
    public void deleteByAge (Byte age){
        Node temp = head;
        ListSE listcopy = new ListSE();
         while (temp != null){
             if (temp.getData().getAge() != age){
                 listcopy.addToStart(temp.getData());
                 temp.getNext();
             }
         }
         this.head = listcopy.getHead();
    }

    //El niño adelanta posiciones

    public void advanceKid (String id, int newPosition ){
        if(head==null || head.getNext() ==null){
            return;
        }

        Node current = head;
        Node previous = null;
        Node KidNode = null;

        //Buscamos al niño por su id
        while (current != null && current.getData().getIdentification() != id){
            previous = current;
            current = current.getNext();
        }

        if (current != null){
            KidNode = current;
            Kid kid = KidNode.getData();

            //Eliminamos al niño de su posición actual
            if(previous == null){
                head = current.getNext();
            }else{
                previous.setNext(current.getNext());
            }

            //Añadimos el nodo del niño a su nueva posición
            addByPosition(kid, newPosition);
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

    //Reporte de niños por ciudad
    public int getCountKidsByLocationCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public int getCountKidsByDeptoCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().substring(0,5).equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    public boolean Checkkid (Kid kid){
        if(this.head != null){
            Node temp = this.head;
            while (temp != null){
                if (temp.getData().getName().equals(kid.getName()) && temp.getData().getLocation().equals(kid.getLocation())){
                    return false;
                }
                temp = temp.getNext();
            }
        }
        return true;
    }

    public int getCountKidsByCityByGenderByAge (String code, char gender, byte age){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)
                        && temp.getData().getGender()==gender && temp.getData().getAge() > age){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //Invertir lista
    public void invert (){
        if(this.head != null){
            ListSE listcopy = new ListSE();
            Node temp = this.head;
            while (temp != null){
                listcopy.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listcopy.getHead();
        }
    }

    //Obtener promedio de edad

    public float averageAge(){
        if(head != null){
            Node temp = head;
            int count = 0;
            int ages = 0;
            while (temp.getNext() != null){
                count++;
                ages = ages + temp.getData().getAge();
                temp = temp.getNext();
            }
            return (float) ages/count;
        }else{
            return (int) 0;
        }
    }

    //Agregar niños al inicio y niñas al final
    public void addBoyStart(){
        ListSE listcopy = new ListSE();
        Node temp = this.head;
        while (temp != null){
            if (temp.getData().getGender() == 'm'){
                listcopy.add(temp.getData());
            }
            temp = temp.getNext();
        }
        temp = this.head;


        while (temp != null){
            if (temp.getData().getGender() == 'f'){
                listcopy.add(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = listcopy.getHead();

    }

    //Intercalar niños y niñas
    public void raffleBoyGirl(){
        ListSE listM = new ListSE();
        ListSE listF = new ListSE();
        Node temp = head;
        while (temp != null){
            if (temp.getData().getGender() == 'm'){
                listM.add(temp.getData());
            }
            if (temp.getData().getGender() == 'f'){
                listF.add(temp.getData());
            }
            temp = temp.getNext();
        }

        ListSE aleatList = new ListSE();
        Node mNode = listM.getHead();
        Node fNode = listF.getHead();
        while (mNode != null){
            if (mNode != null){
                aleatList.add(mNode.getData());
                mNode = mNode.getNext();
            }
            if (fNode != null){
                aleatList.add(fNode.getData());
                fNode = fNode.getNext();
            }
        }
        this.head = aleatList.getHead();
    }

    //Informe de niños por rango de edad
    public int informRangeByAge(int first, int last){
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

    //Enviar al final niños que su nombre empiece por la letra dada
    public void kidToFinishByLetter(char first){
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

}
