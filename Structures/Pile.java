package Structures;

import java.util.Arrays;
import java.util.EmptyStackException;

import Modele.Niveau;

public class Pile {
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

    public Niveau voir_sommet() {
        if (estVide()) {
            throw new EmptyStackException();
        }
        return elements[sommet];
    }

    public boolean estVide() {
        return sommet == -1;
    }

    public int taille() {
        return sommet + 1;
    }

    private void redimensionne() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }
}