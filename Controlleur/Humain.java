package Controlleur;

import Modele.*;

import static Modele.TypeJoueur.HUMAIN;

public class Humain extends Joueurs {

    public Humain(String nom, TypePion roleJ, Jeu j) {
        super(nom, TypeJoueur.HUMAIN, roleJ, j);
    }

    @Override
    public boolean jeu(Coordonne src, Coordonne dst) {
        Pion depart = jeu.n.getPion(src.getX(), src.getY()); //Recupère le pion
        if (jeu.n.check_clic_selection_pion(depart, jeu.get_num_JoueurCourant())) { //Vérifie que le Pion choisit est bien de notre Type, joueur 0 implique de jouer les Attaquants et joueur 1 implique de jouer Defenseurs et Roi
            if (jeu.n.check_clic_selection_dest(depart, dst.getX(), dst.getY())) { //On vérifie que la case d'arrive est accessible
                jeu.jouer(new Coup(src, dst));
                return true;
            } else
                System.out.println("Déplacement impossible");
        }
        return false;
    }
}
