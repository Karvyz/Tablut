package Controlleur;

import Modele.*;

import java.util.ArrayList;

public class IA_difficile extends IA{

    int nevaluation = 0;
    static int MAX_DEPTH = 4;

    public IA_difficile(int num, Jeu jeu) {
        super(num, jeu);
    }

    @Override
    boolean tempsEcoule() {
        nevaluation = 0;
        long l = System.currentTimeMillis();
        //Code de l'IA renvoyer vrai une fois que le coup est joué
        TypePion current_type = ((jeu.getJoueurCourant()) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        ArrayList<Pion> pions = jeu.n.getPions(current_type);
        int valeur_retour = Integer.MIN_VALUE;
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(jeu.n.plateau);
            for (Coordonne deplacement : deplacements) {
                Niveau clone = new Niveau(jeu.n);
                clone.deplace_pion(pion.getCoordonne(), deplacement);
//                if (MAX_DEPTH - 1 == 0) {
//                    return evaluation(clone);
//                }
//                else {
                    int tmp = analyse_recursive(clone, 1);
                    if (tmp > valeur_retour){
                        valeur_retour = tmp;
//                    }
                }
            }
        }
        System.out.println(nevaluation + " evalutations en " + (System.currentTimeMillis() -l ) + "ms");
        return true;
    }

    private int analyse_recursive(Niveau n, int depth) {
        TypePion current_type = ((jeu.getJoueurCourant() + depth) % 2 ) == 0 ? TypePion.ATTAQUANT : TypePion.DEFENSEUR;
        ArrayList<Pion> pions = n.getPions(current_type);
        int valeur_retour = Integer.MAX_VALUE;
        if (depth % 2 == 0)
            valeur_retour = Integer.MIN_VALUE;
        for (Pion pion : pions) {
            ArrayList<Coordonne> deplacements = pion.getDeplacement(n.plateau);
            for (Coordonne deplacement : deplacements) {
                Niveau clone = new Niveau(n);
                clone.deplace_pion(pion.getCoordonne(), deplacement);
                if (depth == MAX_DEPTH - 1) {
                    return evaluation(clone);
                }
                else {
                    int tmp = analyse_recursive(clone, depth + 1);
                    if (depth % 2 == 0) {
                        if (tmp > valeur_retour){
                            valeur_retour = tmp;
                        }
                    }
                    else {
                        if (tmp < valeur_retour){
                            valeur_retour = tmp;
                        }
                    }
                }
            }
        }
        return  valeur_retour;
    }

    public boolean roiEntoure(Niveau n, Coordonne roi) {
        int nbcases = 0;
        ArrayList<Coordonne> casesAdjascentes = new ArrayList<>();
        casesAdjascentes.add(new Coordonne(roi.getX() + 1, roi.getY()));
        casesAdjascentes.add(new Coordonne(roi.getX() - 1, roi.getY()));
        casesAdjascentes.add(new Coordonne(roi.getX(), roi.getY() + 1));
        casesAdjascentes.add(new Coordonne(roi.getX(), roi.getY() - 1));
        for (Coordonne casesAdjascente : casesAdjascentes) {
            if (casesAdjascente.getX() < 0 || casesAdjascente.getX() > 8 || casesAdjascente.getY() < 0 || casesAdjascente.getY() > 8) {
                nbcases++;
                continue;
            }
            if (!n.estVide(casesAdjascente.getX(), casesAdjascente.getY()) && n.estAttaquant(casesAdjascente.getX(), casesAdjascente.getY())) {
                nbcases++;
                continue;
            }
            if (n.estKonakis(casesAdjascente.getX(), casesAdjascente.getY()) || n.estFortresse(casesAdjascente.getX(), casesAdjascente.getY())) {
                nbcases++;
            }
        }
        return nbcases == 4;
    }

    public int evaluation(Niveau n) {
        nevaluation++;
        int attaquants = 16;
        int defenseurs = 8;
        Coordonne roi = new Coordonne(0, 0);
        for (int i = 0; i < n.getTaille(); i++) {
            for (int j = 0; j < n.getTaille(); j++) {
                if (!n.estVide(i, j)) {
                    switch (n.typePion(i, j)) {
                        case ATTAQUANT:
                            attaquants--;
                            break;
                        case DEFENSEUR:
                            defenseurs--;
                            break;
                        case ROI:
                            roi = new Coordonne(i, j);
                    }
                }
            }
        }
        boolean roiEntoure = roiEntoure(n, roi);
        if (jeu.getJoueurCourant() == 0) {
            if (roiEntoure)
                return Integer.MAX_VALUE;
            return defenseurs - attaquants;
        }
        if (roiEntoure)
            return Integer.MIN_VALUE;
        return attaquants - defenseurs;
    }
}
