package Controlleur;

import Global.Configuration;
import Modele.Jeu;
import Modele.TypeJoueur;
import Modele.TypePion;

import static java.lang.System.exit;

public class TournoisControlleur {
    Jeu jeu;

    public void match(int nbParties, TypeJoueur typeJ1, TypeJoueur typeJ2){
        int nbVictoireJ1 = 0;
        int nbVictoireJ2 = 0;
        int nbEgalites = 0;
        for (int i = 0; i < nbParties; i++) {
            jeu = new Jeu();
            nouvellePartie(typeJ1, typeJ2);
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
    private void nouvellePartie(TypeJoueur typeJ1, TypeJoueur typeJ2){
        jeu.nouveauJoueur("IA1", typeJ1, TypePion.ATTAQUANT); //Initialisation des joueurs
        jeu.nouveauJoueur("IA2", typeJ2, TypePion.DEFENSEUR);
        jeu.nouvellePartie();
        System.out.println(jeu.n);
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
