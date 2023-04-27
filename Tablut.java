
import Modele.Jeu;

import Controlleur.ControlleurMediateur;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;



public class Tablut{
    public static void main(String[] args) {
        Jeu j = new Jeu();
        CollecteurEvenements control = new ControlleurMediateur(j);
        InterfaceGraphique.demarrer(j, control);

    }
}
 