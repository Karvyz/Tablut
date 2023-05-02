package Modele;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public class Effet implements Serializable {
    private final Pion pion;
    private final Coordonne depart, arrivee;

    Effet(Pion pion, Coordonne depart, Coordonne arrivee) {
        requireNonNull(pion, "La pièce ne doit pas être null");

        if (depart == null && arrivee == null) {
            throw new IllegalArgumentException("Impossible de créer un état avec les cases avant et après null");
        }
        this.pion = pion;
        this.depart = depart;
        this.arrivee = arrivee;
    }

    Pion pion() {
        return pion;
    }

    Coordonne depart() {
        return depart;
    }

    Coordonne arrivee() {
        return arrivee;
    }

    @Override
    public String toString() {
        return "[" + pion + " : " + depart + " -> " + arrivee + "]";
    }
}
