package Vues;

import Modele.Coordonne;
import Modele.Pion;
import Vues.JComposants.CPlateau;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.SQLOutput;

class AdaptateurSouris2 extends MouseAdapter implements MouseMotionListener {
    CollecteurEvenements controleur;
    CPlateau pane;
    Point dragStart = null;

    int bordureGauche, bordureHaut, bordureDroite, bordureBas, hauteurCase, largeurCase ;


    AdaptateurSouris2(CollecteurEvenements c, CPlateau pane) {
        controleur = c;
        this.pane = pane;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint(); // juste pour le non null
        calculerDimensions();
        int hauteur = pane.getHeight() - bordureHaut - bordureBas;
        int largeur = pane.getWidth() - bordureGauche - bordureDroite;

        if (e.getX() < bordureGauche || e.getY() < bordureHaut ||
                e.getX() > pane.getWidth() - bordureDroite ||
                e.getY() > pane.getHeight() - bordureBas) {
            return;
        }

        int x = e.getX() - bordureGauche;
        int y = e.getY() - bordureHaut;

        int l = y * 9 / hauteur;
        int c = x * 9 / largeur;

        Pion caseSelec = controleur.jeu().n.getPion(l, c);
        if (!controleur.jeu().n.check_clic_selection_pion(caseSelec, controleur.jeu().get_num_JoueurCourant())) {
            //System.out.println("Pion ne vous appartient pas");
            pane.setPionEnDeplacement(null);
            pane.setPionSelec(null);
        }
        else {
            pane.setPionSelec(new Point(l, c )); //on donne colonne puis lignes ici
            System.out.println("Coordonne du pion selec " + pane.getPionSelec().x + "," + pane.getPionSelec().y);
            pane.setPionEnDeplacement(new Point(l , c ));//Initialise point de départ du moov
            //pane.setCouleurEnDeplacement(l, c); //on donne colonne puis lignes ici
        }

        controleur.clicSouris(l, c);
        System.out.println("l = " + l + ", c = " + c);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        calculerDimensions();
        int hauteur = pane.getHeight() - bordureHaut - bordureBas;
        int largeur = pane.getWidth() - bordureGauche - bordureDroite;

        Point pionEnDeplacement = pane.getPionEnDeplacement();
        if (pionEnDeplacement != null) {
            if (e.getX() < bordureGauche || e.getY() < bordureHaut ||
                    e.getX() > pane.getWidth() - bordureDroite ||
                    e.getY() > pane.getHeight() - bordureBas) {
                return;
            }
            int x = e.getX() - bordureGauche;
            int y = e.getY() - bordureHaut;

            int l = y * 9 / hauteur;
            int c = x * 9 / largeur;

            pionEnDeplacement.setLocation(l, c); //modifie les coordonne du Point pionEnDeplacement
            pane.setPionEnDeplacement(pionEnDeplacement);

        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        calculerDimensions();
        int hauteur = pane.getHeight() - bordureHaut - bordureBas;
        int largeur = pane.getWidth() - bordureGauche - bordureDroite;

        if (dragStart != null && pane.getPionSelec() != null) {
            if (e.getX() < bordureGauche || e.getY() < bordureHaut ||
                    e.getX() > pane.getWidth() - bordureDroite ||
                    e.getY() > pane.getHeight() - bordureBas) {
                return;
            }

            int x = e.getX() - bordureGauche;
            int y = e.getY() - bordureHaut;
            int l = y * 9 / hauteur;
            int c = x * 9 / largeur;

            int startX = pane.getPionSelec().x;
            int startY = pane.getPionSelec().y;

            if (startX != l || startY != c) {
                controleur.dragANDdrop(new Coordonne(startX, startY), new Coordonne(l, c));
            }

            dragStart = null;
        }
        pane.setPionEnDeplacement(null);
        //pane.setCaseDestPotentielle(null);
        pane.setPionSelec(null);
        //pane.setCouleurDest(null);
    }

    private void calculerDimensions() {
        bordureHaut = Math.round(Theme.instance().bordureHaut() * pane.getHeight() / (float) Theme.instance().hauteurPlateau());
        bordureGauche = Math.round(Theme.instance().bordureGauche() * pane.getWidth() / (float) Theme.instance().largeurPlateau());
    }

}
