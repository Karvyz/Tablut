package Vues;

import Modele.Coordonne;
import Modele.Pion;
import Vues.JComposants.CPlateau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.SQLOutput;

public class AdaptateurSouris2 extends MouseAdapter implements MouseMotionListener {
    CollecteurEvenements controleur;
    CPlateau pane;
    Point dragStart = null;

    private boolean Deux_cliques = false;

    int bordureGauche, bordureHaut, bordureDroite, bordureBas, hauteurCase, largeurCase ;


    public AdaptateurSouris2(CollecteurEvenements c, CPlateau pane) {
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
        if (caseSelec != null && !caseSelec.equals(pane.getPionSelec())){
            Deux_cliques = false;
        }

        if (!Deux_cliques)
            pane.updateBrillanceSelection(l,c);

        if (!controleur.jeu().n.check_clic_selection_pion(caseSelec, controleur.jeu().get_num_JoueurCourant())) {
            pane.setPionEnDeplacement(null);
            pane.setPionSelec(null);
            Deux_cliques = false;
            pane.updateBrillanceSelection(-1,-1); //TODO A discuter savoir si on met en surbrillance le clic sur un mauvais pion



        }
        else {
            pane.setPionSelec(new Point(l, c )); //on donne colonne puis lignes ici
            pane.setPionEnDeplacement(new Point(l , c ));//Initialise point de départ du moov
            Deux_cliques = true;
            //pane.setCouleurEnDeplacement(l, c); //on donne colonne puis lignes ici
        }

        controleur.clicSouris(l, c);
        //System.out.println("l = " + l + ", c = " + c);

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
            pane.updateBrillanceSelection(-1,-1);

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
                Deux_cliques = false;
            }

            dragStart = null;
        }
        pane.setPionEnDeplacement(null);
        //pane.setCaseDestPotentielle(null);
        pane.setPionSelec(null);
        //pane.setCouleurDest(null);
        pane.setDestinationsPossibles(null);
        pane.setToolTipText(null);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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

        // Obtenez les informations de la case survolée (par exemple, le type de pion)
        Pion caseSelec = controleur.jeu().n.getPion(l, c);


        // Configurez le texte de l'info-bulle pour le composant CPlateau
        if (caseSelec != null && controleur.jeu().n.check_clic_selection_pion(caseSelec, controleur.jeu().get_num_JoueurCourant())){
            Color bleuTresClair = new Color(180, 220, 255);
            UIManager.put("ToolTip.background", bleuTresClair);
            UIManager.put("ToolTip.foreground", Color.BLACK);
            //pane.setToolTipText("VOIR CE QU'on veut ecrire ");
            pane.setToolTipText(null);
            pane.setDestinationsPossibles(caseSelec.getDeplacement(controleur.jeu().n.plateau));
        }
        else if(caseSelec != null && !controleur.jeu().n.check_clic_selection_pion(caseSelec, controleur.jeu().get_num_JoueurCourant())){
            UIManager.put("ToolTip.background", Color.RED);
            UIManager.put("ToolTip.foreground", Color.WHITE);
            pane.setToolTipText("Ce pion ne vous appartient pas ");
            pane.setDestinationsPossibles(null);
        }
        else{
            pane.setDestinationsPossibles(null);
            pane.setToolTipText(null);
        }

    }

    private void calculerDimensions() {
        bordureHaut = Math.round(Theme.instance().bordureHaut() * pane.getHeight() / (float) Theme.instance().hauteurPlateau());
        bordureGauche = Math.round(Theme.instance().bordureGauche() * pane.getWidth() / (float) Theme.instance().largeurPlateau());
    }

}
