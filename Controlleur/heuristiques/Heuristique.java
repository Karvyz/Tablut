package Controlleur.heuristiques;

import Modele.Niveau;
import Modele.TypePion;

import java.io.Serializable;

public abstract class Heuristique implements Serializable {
    public abstract float evaluation(Niveau n, TypePion typePion);
    @Override
    public abstract String toString();
}
