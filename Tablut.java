
import Controlleur.*;
import Modele.Jeu;
import Modele.TypePion;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;

import java.util.ArrayList;


public class Tablut{
    public static void main(String[] args) {
        if (args.length == 0) {
            Jeu j = new Jeu();
            CollecteurEvenements control = new ControlleurMediateur(j);
            InterfaceGraphique.demarrer(control);
        }
        else {
            Tournois2Controlleur tournois2Controlleur = new Tournois2Controlleur();
            tournois2Controlleur.tournois2();
        }
    }
}
 