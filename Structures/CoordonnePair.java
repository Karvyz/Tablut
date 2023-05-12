package Structures;

import Modele.Coordonne;

public class CoordonnePair {
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
