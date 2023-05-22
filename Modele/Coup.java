package Modele;

import java.io.Serializable;

public class Coup implements Serializable {
    private static final long serialVersionUID = 1L; //déclare une constante de sérialisation
    public Coordonne depart;
    public Coordonne arrivee;
    public Coup(Coordonne depart, Coordonne arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
    }

    @Override
    public String toString() {
        return depart + " -> " + arrivee;
    }
}
