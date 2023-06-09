package co.edu.umanizales.tads.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data

public class ListCirc {
    private NodeDE head;
    private int size;
    private List<Pet> pets = new ArrayList<>();


    /*
    Verificar si la mascota es nula:

        Si la mascota es nula, salir del método.
        Crear un nuevo nodo con la mascota:

    Crear un nuevo objeto de tipo NodeDE y asignarle la mascota como su valor.
    Verificar si la lista está vacía:

        Si la cabeza de la lista es nula, significa que la lista está vacía.
        En ese caso, asignar el nuevo nodo como cabeza.
        Establecer el siguiente y el anterior del nuevo nodo como el propio nodo (ya que es el único elemento en la lista).
        Si la lista no está vacía:

    Obtener el último nodo de la lista.
    Establecer el siguiente del nuevo nodo como la cabeza de la lista.
    Establecer el anterior del nuevo nodo como el último nodo.
    Establecer el siguiente del último nodo como el nuevo nodo.
    Establecer el anterior de la cabeza de la lista como el nuevo nodo.
    Incrementar el tamaño de la lista.
     */

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

    public List <Pet> print(){
        pets.clear();

        NodeDE temp = head;
        do {

            pets.add(temp.getData());
            temp = temp.getNext();


        } while (temp != head);
        return pets;
    }

    /*
    Verificar si la lista está vacía:

Si la cabeza de la lista es nula, significa que la lista está vacía.
En ese caso, llamar al método "addPet(pet)" para agregar la mascota como único elemento en la lista.
Si la lista no está vacía:

Crear un nuevo nodo con la mascota:

Crear un nuevo objeto de tipo NodeDE y asignarle la mascota como su valor.
Obtener el último nodo de la lista y asignarlo a una variable temporal "temp".

Establecer el siguiente de "temp" como el nuevo nodo.

Establecer el anterior del nuevo nodo como "temp".

Establecer el siguiente del nuevo nodo como la cabeza actual de la lista.

Establecer el anterior de la cabeza de la lista como el nuevo nodo.

Actualizar la cabeza de la lista con el nuevo nodo.

Incrementar el tamaño de la lista.
     */

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

    /*
    Verificar si la lista está vacía:

Si la cabeza de la lista es nula, significa que la lista está vacía.
En ese caso, llamar al método "addPet(pet)" para agregar la mascota como único elemento en la lista.
Si la lista no está vacía:

Crear un nuevo nodo con la mascota:

Crear un nuevo objeto de tipo NodeDE y asignarle la mascota como su valor.
Obtener el último nodo de la lista y asignarlo a una variable "lastNode".

Establecer el siguiente de "lastNode" como el nuevo nodo.

Establecer el anterior del nuevo nodo como "lastNode".

Establecer el siguiente del nuevo nodo como la cabeza actual de la lista.

Establecer el anterior de la cabeza de la lista como el nuevo nodo.

Incrementar el tamaño de la lista.
     */

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

    /*
    Verificar si la posición es igual a 1:

Si la posición es 1, llamar al método "agregarAlInicio(mascota)" para agregar la mascota al principio de la lista.
Si la posición no es igual a 1:

Crear una variable temporal "temp" y asignarle la cabeza de la lista.
Inicializar una variable de contador en 1.
Mientras el contador sea menor que la posición - 1:

Mover "temp" al siguiente nodo.
Incrementar el contador en 1.
Crear un nuevo nodo con la mascota:

Crear un nuevo objeto de tipo NodeDE y asignarle la mascota como su valor.
Establecer el siguiente del nuevo nodo como el siguiente de "temp".

Establecer el anterior del nuevo nodo como "temp".

Establecer el anterior del siguiente nodo al nuevo nodo.

Establecer el siguiente de "temp" como el nuevo nodo.

Incrementar el tamaño de la lista.
     */

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

    /*
    Convertir la letra inicial a minúscula y asignarla a la variable "start".

Crear una variable temporal "temp" y asignarle la cabeza de la lista.

Verificar si la lista está vacía:

Si "temp" es nulo, significa que no hay perros para bañar.
En ese caso, retornar 0.
Verificar si la letra inicial es diferente de 'd' y 'i':

Si "start" no es 'd' ni 'i', significa que no ingresó una dirección válida.
En ese caso, retornar 0.
Generar un número aleatorio entre 1 y el tamaño de la lista (incluyendo ambos extremos).

Utilizar la clase Random para generar un número aleatorio.
Asignar el número aleatorio a la variable "num".
Verificar si el número aleatorio es igual a 1:

Si es igual a 1, significa que se seleccionó el primer perro de la lista.
Verificar si el perro está sucio:
Si está sucio, marcarlo como bañado (establecer "dirty" en falso).
Si ya está bañado, retornar 0.
Si el número aleatorio no es igual a 1:

Crear una variable de contador "count" y asignarle el valor 1.
Si la dirección es hacia la derecha ('d'):

Mientras "count" no sea igual a "num":
Mover "temp" al siguiente nodo.
Incrementar "count" en 1.
Si la dirección es hacia la izquierda ('i'):

Mientras "count" no sea igual a "num":
Mover "temp" al nodo anterior.
Incrementar "count" en 1.
Verificar si el perro seleccionado está sucio:

Si está sucio, marcarlo como bañado (establecer "dirty" en falso).
Si ya está bañado, retornar 0.
Retornar el número del perro que fue bañado.
     */

    public int takeShower(char letter) {
        char start = Character.toLowerCase(letter);
        NodeDE temp = head;

        if (temp == null) {
            // No hay perros para bañar
            return 0;
        }

        if (start != 'd' && start != 'i') {
            // Debe ingresar 'd' (derecha) o 'i' (izquierda)
            return 0;
        }

        Random rand = new Random();
        int num = rand.nextInt(size) + 1;
        if (num == 1) {
            if (temp.getData().isDirty()) {
                temp.getData().setDirty(false);
            } else {
                // La mascota ya está bañada
                return 0;
            }
        } else {
            int count = 1;
            if (start == 'd') {
                while (count != num) {
                    temp = temp.getNext();
                    count++;
                }
            } else {
                while (count != num) {
                    temp = temp.getPrev();
                    count++;
                }
            }

            if (temp.getData().isDirty()) {
                temp.getData().setDirty(false);
            } else {
                // La mascota ya está bañada
                return 0;
            }
        }

        return num;
    }
/*
Igualo temp a la cabeza
 Creo una variable de tipo Pet para guardar la mascota con más pulgas y la inicializo como null: maxPetFleas
 Reviso que la lista no esté vacía
 En caso de que no esté vacía, entonces igualo la variable maxPetFleas
 Recorro la lista con un bucle do-while (esto para asegurarme que llegue hasta el último pet) hasta que temp sea igual a cabeza
 Dentro del bucle, reviso si temp en ese momento es igual a la raza
 Si es igual a la raza, igualo la variable maxPetFleas con esa mascota y luego rompo el bucle
 Luego reviso que maxPetFleas no sea null, eso significa que se encontró una mascota con esa raza
 En caso de que no sea nulo, vuelvo a recorrer la lista hasta revisar si temp en ese momento tiene más pulgas que maxPetFleas
 y que tenga la misma raza. Si se cumple la condición, entonces temp será la nueva maxPetFleas. Repito este proceso hasta terminar el bucle
 Al final del bucle, tomo la variable maxPetFleas y la marco como "ensuciada" (dirty)
 Finalmente, devuelvo la variable maxPetFleas
*/


    public Pet getPetFleasByBreed(String breed )  {
        NodeDE temp = head;
        Pet maxPetFleas = null;
        if (head != null) {

            do {

                if ( temp.getData().getBreed().equalsIgnoreCase(breed)) {
                    maxPetFleas = temp.getData();
                    break;
                }

                temp = temp.getNext();

            } while (temp!= head);


            if (maxPetFleas != null){

            do {

                if (temp.getData().getFleas() > maxPetFleas.getFleas() && temp.getData().getBreed().equalsIgnoreCase(breed.toLowerCase())) {
                    maxPetFleas = temp.getData();
                }

                temp = temp.getNext();

            } while (temp != head);
                maxPetFleas.setDirty(true);
            }

        }
        return maxPetFleas;
    }



}
