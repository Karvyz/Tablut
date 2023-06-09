package Vues;

import Modele.Coordonne;
import Modele.Pion;
import Vues.JComposants.CPlateau;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class AdaptateurSouris extends MouseAdapter implements MouseMotionListener {
    CollecteurEvenements ctrl;
    CPlateau plateau;
    Point dragStart = null;
    int bordureGauche, bordureHaut, bordureDroite, bordureBas;
    boolean clicInutile = false;
    boolean clicSelection = false;
    boolean premier_clic = false;

    public AdaptateurSouris(CollecteurEvenements c, CPlateau plateau) {
        ctrl = c;
        this.plateau = plateau;
    }

    /**Définis les actions lors du clic sur le plateau*/
    @Override
    public void mousePressed(MouseEvent e) { // Méthode executée lors d'un clic

        dragStart = e.getPoint(); // On clique et stock le point de départ du dragStart
        int l = calcul_l(e);
        int c = calcul_c(e);
        if (check_ok(l, c)) {
            plateau.setPionSelec(null);
            plateau.setPionEnDeplacement(null);
            plateau.setDestinationsPossibles(null);
            return;
        }

        ctrl.jeu().setAideIA(null);
        plateau.setDrawFleche(false); // N'importe quel clic efface la flèche d'indication de l'IA
        premier_clic = true;

        Pion caseClique = ctrl.jeu().n.getPion(l, c);

        // Teste le déplacement
        if (plateau.getPionSelec() != null) {
            plateau.setPionEnDeplacement(null);// On ne peut pas drag
            if (ctrl.clicSouris(plateau.getPionSelec(), l, c)) { // Si on clique après avoir selectionné un pion on check si le coup est juste
                plateau.setPionSelec(null); // On déselectionne après avoir joué un coup
                plateau.setPionEnDeplacement(null);
                dragStart = null;
                affiche_destination(null);
                ctrl.jeu().setCoordooneJouerIA(null, null); // On met les coordonnées de la flèche à false
                plateau.setDrawFleche(true); // On prépare la flèche pour le prochain coup de l'IA
                premier_clic = false;
                return;
            } // S'il ne permet pas un déplacement on regarde si ce pion peut servir de nouvelle selection
            else if (ctrl.jeu().n.check_clic_selection_pion(caseClique, ctrl.jeu().get_num_JoueurCourant()) && caseClique.getDeplacement(ctrl.jeu().n.plateau).isEmpty()) { // Ici il ne sert pas de nouvelle selection
                clicSelection = false;
                clicInutile = false;
                plateau.setPionEnDeplacement(null);// On ne peut pas drag
                plateau.setPionSelec(null);// On ne peut pas drag

                return;
            } else if (!ctrl.jeu().n.check_clic_selection_pion(caseClique, ctrl.jeu().get_num_JoueurCourant())) {
                plateau.setPionEnDeplacement(null);// Initialise point de départ du mouvement pour le drag
                plateau.setPionSelec(null);
                clicSelection = false;
                clicInutile = false; // Changez ici si on veut garder les destinations affichés lors d'un clic sur un pion pas à nous
                plateau.setDessineCroix(new Point(l, c));
                if (caseClique == null) {
                    plateau.setDrawFleche(false);
                    return;
                }
                plateau.setDrawFleche(true);
                return;
            } else if (ctrl.jeu().n.check_clic_selection_pion(caseClique, ctrl.jeu().get_num_JoueurCourant())) {// Ici il sert de nouvelle selection
                plateau.setPionEnDeplacement(new Point(l, c));// Initialise point de départ du mouvement pour le drag
                plateau.setPionSelec(caseClique);
                setImage(caseClique);
                affiche_destination(caseClique);
                clicSelection = true;
                return;
            }
            clicInutile = true;
        } else {
            plateau.setDrawFleche(false); // Si on clique sur case vide, on désactive la flèche
        }

        // Le pion nous appartient mais il n'y a pas de déplacement possible
        if (!check_pion(caseClique)) {
            return;
        }
        // Le pion nous appartient, on affiche ses dispos
        else if (ctrl.jeu().n.check_clic_selection_pion(caseClique, ctrl.jeu().get_num_JoueurCourant())) {
            plateau.setPionSelec(caseClique); // Au laché, on affiche les dispos
            plateau.setPionEnDeplacement(new Point(l, c));// Initialise point de départ du mouvement pour le drag
            setImage(caseClique);
            plateau.setDrawFleche(false);
            clicSelection = true;
            return;
        }
        // Le pion nous appartient pas ou c'est une case vide
        if (!ctrl.jeu().n.check_clic_selection_pion(caseClique, ctrl.jeu().get_num_JoueurCourant())) {
            clicSelection = false;
            clicInutile = false; // Changer ici si on veut garder les destinations affichés lors d'un clic sur pion pas a nous
            plateau.setPionEnDeplacement(null);
            plateau.setDrawFleche(true);
        }

    }


    /**Définis les actions effectuées lorsque l'utilisateur relache le clic*/
    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragStart != null && plateau.getPionSelec() != null) {
            int l = calcul_l(e);
            int c = calcul_c(e);
            if (check_ok(l, c)) {
                plateau.setPionSelec(null);
                plateau.setPionEnDeplacement(null);
                plateau.setDestinationsPossibles(null);
                return;
            }

            Pion caseLache = ctrl.jeu().n.getPion(l, c);

            int startX = plateau.getPionSelec().getX();
            int startY = plateau.getPionSelec().getY();

            // Ici on gère le cas ou l'on clique sur un pion de la même couleur que le notre
            if (ctrl.jeu().n.check_clic_selection_pion(caseLache, ctrl.jeu().get_num_JoueurCourant())) {
                plateau.setPionSelec(caseLache);
                affiche_destination(plateau.getPionSelec());
                clicInutile = false;
                return;
            } else {
                plateau.setPionSelec(null);
                affiche_destination(null);
            }

            // Ici on gère le drag&drop
            if (startX != l || startY != c) {
                if (ctrl.dragANDdrop(new Coordonne(startX, startY), new Coordonne(l, c))) { //On teste le déplacement
                    affiche_destination(null);
                    plateau.setDrawFleche(true);
                    premier_clic = false;

                }
                plateau.setPionSelec(null);
                plateau.setPionEnDeplacement(null);
            }
            dragStart = null;
        }
        // Ici on gère le cas ou on clique sur un de nos pions, puis sur un pion adverse ou case vide, en enlève l'affichage des déplacements dispos
        if (plateau.getPionEnDeplacement() == null) {
            if (clicInutile || clicSelection) {
                if (plateau.getPionSelec() != null) {
                    affiche_destination(plateau.getPionSelec());
                    clicInutile = false;
                }
            } else {
                plateau.setPionSelec(null);
                affiche_destination(null);
            }
        }
    }

    /**Définition des actions pendant le drag&drop*/
    @Override
    public void mouseDragged(MouseEvent e) {
        Point pionEnDeplacement = plateau.getPionEnDeplacement();
        if (pionEnDeplacement != null) {
            int l = calcul_l(e);
            int c = calcul_c(e);
            if (check_ok(l, c)) {
                return;
            }
            pionEnDeplacement.setLocation(l, c); // Modifie les coordonne du Point pionEnDeplacement
            plateau.setPionEnDeplacement(pionEnDeplacement);
        }
    }

    /**Définition des actions lors du mouvement de la souris sur le plateau*/
    @Override
    public void mouseMoved(MouseEvent e) {

        int l = calcul_l(e);
        int c = calcul_c(e);
        if (check_ok(l, c)) {
            return;
        }
        // Obtener les informations de la case survolée
        Pion caseSurvole = ctrl.jeu().n.getPion(l, c);

        if (plateau.getPionSelec() == null) {
            // Permet d'afficher lorsqu'on survole
            if (ctrl.jeu().n.check_clic_selection_pion(caseSurvole, ctrl.jeu().get_num_JoueurCourant())) {
                affiche_destination(caseSurvole); //affiche les destinations du pion survole
                if (premier_clic) {
                    plateau.setDrawFleche(false);
                }
            } else if (caseSurvole != null && !ctrl.jeu().n.check_clic_selection_pion(caseSurvole, ctrl.jeu().get_num_JoueurCourant())) {
                plateau.setDrawFleche(true);
                affiche_destination(null); //affiche aucune destination
            } else {
                if (premier_clic) {
                    plateau.setDrawFleche(false);
                }
                affiche_destination(null); //affiche aucune destination
            }
        }
    }

    /**Renvoi l'image actuellement sélectionné*/
    private void setImage(Pion p) {
        if (ctrl.jeu().n.estAttaquant(p.getX(), p.getY())) {
            plateau.setImage(0);
        } else if (ctrl.jeu().n.estDefenseur(p.getX(), p.getY())) {
            plateau.setImage(1);
        } else {
            plateau.setImage(2);
        }
    }

    /**Permet de définir les destinations du pion sélectionné*/
    private void affiche_destination(Pion pionSelec) {//Permet l'affichage des destinations données pour un Pion donné
        if (pionSelec == null) {
            plateau.setDestinationsPossibles(null);
            return;
        }
        ArrayList<Coordonne> liste = pionSelec.getDeplacement(ctrl.jeu().n.plateau);
        if (liste.isEmpty()) {
            plateau.setDestinationsPossibles(null);
        } else {
            plateau.setToolTipText(null);
            plateau.setDestinationsPossibles(liste);
        }
    }

    private void calculerDimensions() {
        bordureHaut = Math.round(Theme.instance().bordureHaut() * plateau.getHeight() / (float) Theme.instance().hauteurPlateau());
        bordureGauche = Math.round(Theme.instance().bordureGauche() * plateau.getWidth() / (float) Theme.instance().largeurPlateau());
    }

    private boolean check_ok(int l, int c) {
        return l == -1 || c == -1;
    }

    private boolean check_pion(Pion p) {
        if (ctrl.jeu().n.check_clic_selection_pion(p, ctrl.jeu().get_num_JoueurCourant()) && p.getDeplacement(ctrl.jeu().n.plateau).isEmpty()) {
            clicSelection = false;
            clicInutile = false;
            plateau.setPionEnDeplacement(null);//On ne peut pas drag
            return false;
        }
        return true;
    }

    private int calcul_l(MouseEvent e) {
        calculerDimensions();
        int hauteur = plateau.getHeight() - bordureHaut - bordureBas;
        if (e.getX() < bordureGauche || e.getY() < bordureHaut ||
                e.getX() > plateau.getWidth() - bordureDroite ||
                e.getY() > plateau.getHeight() - bordureBas) {
            return -1;
        }

        int y = e.getY() - bordureHaut;

        return y * 9 / hauteur;
    }

    private int calcul_c(MouseEvent e) {
        calculerDimensions();
        int largeur = plateau.getWidth() - bordureGauche - bordureDroite;

        if (e.getX() < bordureGauche || e.getY() < bordureHaut ||
                e.getX() > plateau.getWidth() - bordureDroite ||
                e.getY() > plateau.getHeight() - bordureBas) {
            return -1;
        }

        int x = e.getX() - bordureGauche;

        return x * 9 / largeur;
    }

}
