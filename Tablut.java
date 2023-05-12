
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
            ias.add(new IA_facile("", TypePion.ATTAQUANT, new Jeu()));
            ias.add(new IA_facile("", TypePion.DEFENSEUR, new Jeu()));
            TournoisControlleur tournoisControlleur = new TournoisControlleur(ias, 1000);
//            tournoisControlleur.match(ias.get(0), ias.get(1), 0, 0);
            tournoisControlleur.tournois();
        }
    }
}
 