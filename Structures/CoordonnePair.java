package Structures;

import Modele.Coordonne;

import java.io.Serializable;

public class CoordonnePair implements Serializable {
    private Coordonne depart;
    private Coordonne arrive;

    public CoordonnePair(Coordonne depart, Coordonne arrive) {
        this.depart = depart;
        this.arrive = arrive;
    }

    public Coordonne getdepart() {
        return depart;
    }

    public Coordonne getarrive() {
        return arrive;
    }
}
