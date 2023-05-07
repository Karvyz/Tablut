package Controlleur;

import Global.Configuration;
import Modele.Jeu;
import Modele.Joueurs;
import Modele.TypeJoueur;
import Modele.TypePion;

import static java.lang.System.exit;

public class TournoisControlleur {
    Jeu jeu;

    public void match(){
        int nbParties = 100;

        int nbVictoireJ1 = 0;
        int nbVictoireJ2 = 0;
        int nbEgalites = 0;
        for (int i = 0; i < nbParties; i++) {
            jeu = new Jeu();

            IA J1 = new IA_difficile_AttaqueRoi("", TypePion.ATTAQUANT, jeu);
            IA J2 = new IA_difficile_le_roi_c_ciao("", TypePion.DEFENSEUR, jeu);
            nouvellePartie(J1, J2);
            switch (play()) {
                case 1:
                    nbVictoireJ1++;
                    break;
                case 2:
                    nbVictoireJ2++;
                    break;
                case 3:
                    nbEgalites++;
                    break;
                default:
                    System.out.println("what");
                    exit(1);
            }
        }
        System.out.println("Attaquant J1 : " + nbVictoireJ1);
        System.out.println("Defenseur J2 : " + nbVictoireJ2);
        System.out.println("Egalités : " + nbEgalites);
    }

    private void nouvellePartie(IA J1, IA J2){
        jeu.joueurs[0] = J1;
        jeu.joueurs[1] = J2;
        jeu.nouvellePartie();
    }

    private int play(){
        int valeur_fin = 0;
        while ((valeur_fin = ((IA)jeu.getJoueurCourant()).joue()) == 0) {
            if (jeu.n.PlusdePion(jeu.get_num_JoueurCourant())) {
                jeu.setEnCours(false);
                System.out.println("Le joueur blanc a gagné car l'attaquant n'a plus de pion");
            }
        }
        return valeur_fin;
    }
}
