package Controlleur;

import Global.Configuration;
import Modele.Jeu;
import Modele.Joueurs;
import Modele.TypeJoueur;
import Modele.TypePion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.exit;

public class TournoisControlleur {
    class MyRunable implements Runnable {
        int resultat;
        Jeu jeu;
        @Override
        public void run() {
            jeu = new Jeu();

            IA J1 = new IA_difficile_le_roi_c_ciao("", TypePion.ATTAQUANT, jeu);
            IA J2 = new IA_difficile_Long_live_the_king("", TypePion.DEFENSEUR, jeu);
            nouvellePartie(J1, J2);
            resultat = play();
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

    public void match(){
        long l = System.currentTimeMillis();
        int nbParties = 100;

        int nbVictoireJ1 = 0;
        int nbVictoireJ2 = 0;
        int nbEgalites = 0;
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<MyRunable> myRunables = new ArrayList<>();
        for (int i = 0; i < nbParties; i++) {
            MyRunable myRunable = new MyRunable();
            myRunables.add(myRunable);
            Thread thread = new Thread(myRunable);
            thread.start();
            threads.add(thread);
        }
        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
                switch (myRunables.get(i).resultat) {
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
                        nbVictoireJ2++;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println((System.currentTimeMillis() - l) + "ms");
        System.out.println("Attaquant J1 : " + nbVictoireJ1);
        System.out.println("Defenseur J2 : " + nbVictoireJ2);
        System.out.println("Egalités : " + nbEgalites);
    }


}
