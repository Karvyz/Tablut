package Structures;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EmptyStackException;

import Modele.Niveau;

public class Pile implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int CAPACITE_DEPART = 16;
    private Niveau[] elements;
    private int sommet;

    public Pile() {
        elements = new Niveau[CAPACITE_DEPART];
        sommet = -1;
    }

    public void empiler(Niveau niveau) {
        if (sommet == elements.length - 1) {
            redimensionne();
        }
        elements[++sommet] = niveau;
    }

    public Niveau depiler() {
        if (estVide()) {
            throw new EmptyStackException();
        }
        Niveau niveau = elements[sommet];
        elements[sommet--] = null;
        return niveau;
    }


    public boolean estVide() {
        return sommet == -1;
    }

    public void clear() {
        for (int i = 0; i <= sommet; i++) {
            elements[i] = null;
        }
        sommet = -1;
    }

    public int size() {
        return sommet + 1;
    }

    private void redimensionne() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    @Override
    public String toString() {
        return "Pile{" +
                 Arrays.toString(elements) +
                '}';
    }

}