
import Controlleur.*;
import Modele.Jeu;

import Modele.TypeJoueur;
import Modele.TypePion;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;

import javax.swing.*;
import java.util.ArrayList;


public class Tablut{
    public static void main(String[] args) {
        ArrayList<IA> ias = new ArrayList<>();
        ias.add(new IA_facile("", TypePion.ATTAQUANT, new Jeu()));
        ias.add(new IA_difficile_le_roi_c_ciao("", TypePion.ATTAQUANT, new Jeu()));
        TournoisControlleur tournoisControlleur = new TournoisControlleur(ias, 100);
        tournoisControlleur.tournois();
    }
}
 