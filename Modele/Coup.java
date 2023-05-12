package Modele;

import java.io.Serializable;

public class Coup implements Serializable {
    public Coordonne depart;
    public Coordonne arrivee;
    public Coup(Coordonne depart, Coordonne arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
    }
}
