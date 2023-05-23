package Controlleur;

import Modele.Jeu;
import java.util.ArrayList;

public class TournoisControlleur {
    ArrayList<IA> ias;
    int nbManches;
    static int limite_coups = 10000;
    ArrayList<ArrayList<Integer>> victoires;
    ArrayList<ArrayList<Integer>> defaites;
    public TournoisControlleur(ArrayList<IA> ias, int nbManches) {
        this.ias = ias;
        this.nbManches = nbManches;
        victoires = new ArrayList<>();
        defaites = new ArrayList<>();
        for (int i = 0; i < ias.size(); i++) {
            victoires.add(new ArrayList<>());
            defaites.add(new ArrayList<>());
        }
    }

    public void tournois() {
        for (int i = 0; i < ias.size(); i++) {
            for (int j = 0; j < ias.size(); j++) {
                match(ias.get(i), ias.get(j), i);
            }
        }
        ias.forEach(System.out::println);
        System.out.println("Victoires En attaque");
        for (ArrayList<Integer> victoire : victoires) {
            System.out.println(victoire);
        }
        System.out.println("Victoires en defense");
        for (ArrayList<Integer> defaite : defaites) {
            System.out.println(defaite);
        }
    }

    class MyRunable implements Runnable {
        int resultat;
        Jeu jeu;
        IA ia1;
        IA ia2;
        MyRunable(IA ia1, IA ia2) {
            this.ia1 = (IA) ia1.clone();
            this.ia2 = (IA) ia2.clone();
        }

        @Override
        public void run() {
            jeu = new Jeu();
            ia1.jeu = jeu;
            ia2.jeu = jeu;
            nouvellePartie(ia1, ia2);
            resultat = play();
        }

        private void nouvellePartie(IA J1, IA J2){
            jeu.joueurs[0] = J1;
            jeu.joueurs[1] = J2;
            jeu.nouvellePartie();
        }

        private int play(){
            int valeur_fin = 0;
            int nb_coups = 0;
            while ((valeur_fin = ((IA)jeu.getJoueurCourant()).joue()) == 0) {
                if (jeu.n.PlusdePion(jeu.get_num_JoueurCourant())) {
                    jeu.setEnCours(false);
                    System.out.println("Le joueur blanc a gagné car l'attaquant n'a plus de pion");
                }
                nb_coups++;
                if (nb_coups > limite_coups) {
                    return 3;
                }
            }
            return valeur_fin;
        }
    }

    public void match(IA ia1, IA ia2, int k){

        int nbVictoireJ1 = 0;
        int nbVictoireJ2 = 0;
        int nbEgalites = 0;
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<MyRunable> myRunables = new ArrayList<>();
        for (int i = 0; i < nbManches; i++) {
            MyRunable myRunable = new MyRunable(ia1, ia2);
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
        victoires.get(k).add(nbVictoireJ1);
        defaites.get(k).add(nbVictoireJ2);
        System.out.println("Attaquant J1 : " + nbVictoireJ1);
        System.out.println("Defenseur J2 : " + nbVictoireJ2);
        System.out.println("Egalités : " + nbEgalites);
    }


}
