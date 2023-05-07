
import Controlleur.TournoisControlleur;
import Modele.Jeu;

import Controlleur.ControlleurMediateur;
import Modele.TypeJoueur;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;

import javax.swing.*;


public class Tablut{
    public static void main(String[] args) {
        TournoisControlleur tournoisControlleur = new TournoisControlleur();
        tournoisControlleur.match(100, TypeJoueur.IA_FACILE, TypeJoueur.IA_DIFFICILE);
    }
}
 