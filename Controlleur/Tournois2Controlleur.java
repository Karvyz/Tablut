package Controlleur;

import Controlleur.heuristiques.*;
import Modele.Jeu;
import Modele.TypePion;

import java.util.ArrayList;
import java.util.Random;

public class Tournois2Controlleur {

    static int coupsLimit = 200;
    public void tournois2() {
        Random random = new Random();
        ArrayList<IA> ias = new ArrayList<>();
        ias.add(new IA_facile("", TypePion.ATTAQUANT, new Jeu()));
        ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueAttaqueRoi(),0));
        ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueAttaqueRoi(), 0));
        ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueAttaqueRoi(), 0));
        ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueAttaqueRoi(), 0));
        ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueAttaqueRoi(), 0));
        ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueFusion( 1, 1, 1, 1), 0));
        for (int i = 0; i < 10; i++) {
            ias.add(new IA_DifficileTemps("", TypePion.ATTAQUANT, new Jeu(), new HeuristiqueFusion( random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()), 0));
        }
        ArrayList<Integer> nbVictAttaque = new ArrayList<>();
        ArrayList<Integer> nbVictDefense = new ArrayList<>();
        for (int i = 0; i < ias.size(); i++) {
            nbVictAttaque.add(0);
            nbVictDefense.add(0);
        }


        for (int i = 0; i < ias.size(); i++) {
            for (int j = 0; j < ias.size(); j++) {
                Jeu jeu = new Jeu();
                ias.get(i).jeu = jeu;
                ias.get(j).jeu = jeu;
                jeu.joueurs[0] = ias.get(i);
                jeu.joueurs[1] = ias.get(j);
                jeu.nouvellePartie();
                switch (play(jeu)) {
                    case 1:
                        nbVictAttaque.set(i, nbVictAttaque.get(i) + 1);
                        break;
                    case 2:
                        nbVictDefense.set(j, nbVictDefense.get(j) + 1);
                        break;
                    default:
                }
            }
        }
        for (int i = 0; i < ias.size(); i++) {
            System.out.println(ias.get(i));
            System.out.println("attaques : " + nbVictAttaque.get(i));
            System.out.println("defenses : " + nbVictDefense.get(i));
        }

    }

    private int play(Jeu jeu){
        int nbCoups = 0;
        int valeur_fin = 0;
        while ((valeur_fin = ((IA)jeu.getJoueurCourant()).joue()) == 0) {
            if (jeu.n.PlusdePion(jeu.get_num_JoueurCourant())) {
                jeu.setEnCours(false);
                System.out.println("Le joueur blanc a gagné car l'attaquant n'a plus de pion");
            }
            nbCoups++;
            if (nbCoups > coupsLimit)
                return 3;
        }
        return valeur_fin;
    }
}
