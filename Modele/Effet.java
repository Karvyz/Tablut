package Modele;

import java.io.Serializable;

import static java.util.Objects.requireNonNull;

public class Effet implements Serializable {
    private final Piece piece;
    private final Coordonne depart, arrivee;

    Effet(Piece piece, Coordonne depart, Coordonne arrivee) {
        requireNonNull(piece, "La pièce ne doit pas être null");

        if (depart == null && arrivee == null) {
            throw new IllegalArgumentException("Impossible de créer un état avec les cases avant et après null");
        }
        this.piece = piece;
        this.depart = depart;
        this.arrivee = arrivee;
    }

    Piece piece() {
        return piece;
    }

    Coordonne depart() {
        return depart;
    }

    Coordonne arrivee() {
        return arrivee;
    }

    @Override
    public String toString() {
        return "[" + piece + " : " + depart + " -> " + arrivee + "]";
    }
}
