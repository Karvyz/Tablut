package Controlleur.heuristiques;

import Modele.Niveau;
import Modele.TypePion;

public abstract class Heuristique {
    public abstract float evaluation(Niveau n, TypePion typePion);
    @Override
    public abstract String toString();
}
