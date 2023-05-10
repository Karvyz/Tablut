
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
            ArrayList<IA> ias = new ArrayList<>();
            ias.add(new IA_difficile_Long_live_the_king("", TypePion.ATTAQUANT, new Jeu()));
            ias.add(new IA_difficile_le_roi_c_ciao("", TypePion.ATTAQUANT, new Jeu()));
            TournoisControlleur tournoisControlleur = new TournoisControlleur(ias, 100);
            tournoisControlleur.tournois();
        }
    }
}
 