
import Modele.Coordonne;
import Modele.Jeu;
import Modele.Niveau;
import Controlleur.ControlleurMediateur;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;

import java.security.InvalidParameterException;
import java.util.Iterator;

public class Tablut{
    public static void main(String[] args) {
        Jeu j = new Jeu();
        CollecteurEvenements control = new ControlleurMediateur(j);
        InterfaceGraphique.demarrer(j, control);

    }
}
 