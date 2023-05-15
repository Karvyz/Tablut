package Controlleur;

import Controlleur.heuristiques.Heuristique;
import Modele.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;

import static java.lang.System.exit;

public class IA_DifficileTemps extends IA{

    TypePion monType;
    TypePion typeAdversaire;
    Heuristique heuristique;
    PriorityQueue<Etat> etats;
    long timeLimitMs;
    int maxDepth;
    public IA_DifficileTemps(String nom, TypePion roleJ, Jeu j, Heuristique heuristique, long timeLimitMs) {
        super(nom, TypeJoueur.IA_DIFFICILE, roleJ, j);
        this.heuristique = heuristique;
        this.timeLimitMs = timeLimitMs;
    }

    public Coup meilleurCoup() {
        long endtime = System.currentTimeMillis() + timeLimitMs;
        int nb_evals = 0;
        etats = new PriorityQueue<>();
        if (((jeu.get_num_JoueurCourant()) % 2 ) == 0) {
            monType = TypePion.ATTAQUANT;
            typeAdversaire = TypePion.DEFENSEUR;
        }
        else {
            monType = TypePion.DEFENSEUR;
            typeAdversaire = TypePion.ATTAQUANT;
        }

        etats.add(new Etat(jeu.n.clone(), 0, null, 0));
        do {
            Etat e = etats.poll();
            Coup overide = coups(e);
            nb_evals++;
            if (overide != null) {
//                System.out.println("fini en avance");
//                System.out.println("nb evals : " + nb_evals);
                return overide;
            }

        } while (System.currentTimeMillis() < endtime);
        Etat meilleurCoup = etats.poll();
//        System.out.println("fini");
//        System.out.println("nb evals : " + nb_evals);
//        System.out.println("max_depth : " + meilleurCoup.depth);
        return meilleurCoup.coupAJouer;
    }

    class Etat implements Comparable<Etat>, Serializable {
        Niveau niveau;
        float evaluation;
        Coup coupAJouer;
        int depth;

        Etat(Niveau niveau, float evaluation, Coup coupAJouer, int depth) {
            this.niveau = niveau;
            this.evaluation = evaluation;
            this.coupAJouer = coupAJouer;
            this.depth = depth;
        }
        @Override
        public int compareTo(Etat o) {
            float result = o.evaluation - evaluation;
            if (result > 0)
                return 1;
            if (result == 0)
                return 0;
            return -1;
        }
    }

    Coup coups(Etat e) {
        if (e.niveau == null) {
            exit(1);
        }
        if (maxDepth < e.depth + 2)
            maxDepth = e.depth + 2;
        ArrayList<Pion> pions = e.niveau.getPions(monType);
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(e.niveau.plateau);
            for (int i = 0; i < deplacements.size(); i++) {
                Coup coupAJouer = e.coupAJouer;
                if (coupAJouer == null)
                    coupAJouer = new Coup(pion.getCoordonne(), deplacements.get(i));
                Niveau clone = e.niveau.clone();
                int val_depl = clone.deplace_pion(new Coup(pion.getCoordonne(), deplacements.get(i)));
                if (val_depl > 0) {
                    if (val_depl < 3)
                        return coupAJouer;
                    continue;
                }
                reponse(clone, coupAJouer, e.depth + 2);
            }
        }
        return null;
    }

    void reponse(Niveau niveau, Coup coupAJouer, int depth) {
        ArrayList<Pion> pions = niveau.getPions(typeAdversaire);
        Etat reponseMinimale = new Etat(null, Integer.MAX_VALUE, null, depth);
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(niveau.plateau);
            for (int i = 0; i < deplacements.size(); i++) {
                Niveau clone = niveau.clone();
                float eval = 0;
                int valeur = clone.deplace_pion(new Coup(pion.getCoordonne(), deplacements.get(i)));
                if (valeur == 1 || valeur == 2) {
                    etats.add(new Etat(clone, Integer.MIN_VALUE + depth, coupAJouer, depth));
                    return;
                }
                if (valeur != 3)
                    eval = heuristique.evaluation(clone, monType);
                if (eval < reponseMinimale.evaluation) {
                    reponseMinimale = new Etat(clone, eval, coupAJouer, depth);
                }
            }
        }
        if (reponseMinimale.niveau == null)
            return;
        reponseMinimale.evaluation -= depth * 0.1;
        etats.add(reponseMinimale);
    }
}
