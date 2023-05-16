
import Controlleur.*;
import Controlleur.heuristiques.*;
import Modele.Jeu;
import Modele.TypePion;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;

import java.util.ArrayList;
import java.util.Random;


public class Tablut{
    public static void main(String[] args) {
        if (args.length == 0) {
            Jeu j = new Jeu();
            CollecteurEvenements control = new ControlleurMediateur();
            control.fixeJeu(j);
            InterfaceGraphique.demarrer(control);
        }
        else {
            ArrayList<IA> ias = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
            }
            ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueFusion(0.4668334F, 0.33374965F,0.9967921F, 0.5499482F), 100));
            ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueMassacrePion(), 100));
            ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueLeRoiCCiao(), 100));
            ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueLongLiveTheKing(), 100));

            TournoisControlleur tournoisControlleur = new TournoisControlleur(ias, 100);
            tournoisControlleur.tournois();
        }
    }
}
 