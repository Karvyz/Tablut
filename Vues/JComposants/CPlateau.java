package Vues.JComposants;

import Modele.Coordonne;
import Modele.Jeu;
import Modele.Niveau;
import Modele.Pion;
import Patterns.Observateur;
import Vues.AdaptateurSouris;
import Vues.AdaptateurSouris2;
import Vues.CollecteurEvenements;
import Vues.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CPlateau extends JPanel implements Observateur {
    CollecteurEvenements controleur;

    private Point pionEnDeplacement;

    private Pion pionSelec;
    int bordureHaut, bordureGauche, hauteurCase, largeurCase;
    private int brillanceX = -1;
    private int brillanceY = -1;

    private ArrayList<Coordonne> destinationsPossibles = new ArrayList<>();
    private Point pointSelec;
    private Image image;


    public CPlateau(CollecteurEvenements c) {
        controleur = c;
        //AdaptateurSouris2 adaptateurSouris = new AdaptateurSouris2(c, this);
        //System.out.println("HERE"+controleur.jeu());
        AdaptateurSouris adaptateurSouris = new AdaptateurSouris(c, this);

        addMouseListener(adaptateurSouris);
        addMouseMotionListener(adaptateurSouris);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculerDimensions();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawPlateau(g2d);
        drawContenu(g2d);
        drawDestination(g2d);
        drawDeplacement(g2d);
        drawSurbrillance(g2d);

        if (brillanceX >= 0 && brillanceY >= 0) {
            drawBrillance(g2d, brillanceX, brillanceY);
        }
    }

    private void drawDeplacement(Graphics2D g2d) {
        if (getPionEnDeplacement()!=null){
            int x = bordureGauche;
            int y = bordureHaut;
            int l = getPionEnDeplacement().x;
            int c = getPionEnDeplacement().y;

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    if (i == l && c == j) {
                        //TODO mettre image des points
                        g2d.drawImage(getImage(), x + 5, y + 4, largeurCase - 4, hauteurCase - 4, this);
                    }
                    x += largeurCase + 1;
                }
                y += hauteurCase;
                x = bordureGauche;
            }
        }
    }

    private void drawDestination(Graphics2D g) {
        if (destinationsPossibles == null) {
            return;
        }
        for (Coordonne caseSelec : destinationsPossibles) {
            int x = bordureGauche;
            int y = bordureHaut;
            int l = caseSelec.getX();
            int c = caseSelec.getY();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    if (i == l && c == j) {
                        //TODO mettre image des points
                        g.drawImage(Theme.instance().pointInterrogation(), x + 5, y + 4, largeurCase - 4, hauteurCase - 4, this);
                    }
                    x += largeurCase + 1;
                }
                y += hauteurCase;
                x = bordureGauche;
            }
        }
    }

    public void setDestinationsPossibles(ArrayList<Coordonne> destinations) {
        this.destinationsPossibles = destinations;
        repaint();
    }

    public void drawSurbrillance(Graphics2D g) {
        Jeu J = controleur.jeu();
        Niveau n = J.getNiveau();
        if (pionSelec != null) {
            System.out.println("pionSelec != null" + pionSelec);
            int x = pionSelec.getX();
            int y = pionSelec.getY();

            if (n.estRoi(x, y)) {
                // -- Dessin du roi avec arrière-plan
                g.drawImage(Theme.instance().roi_selectionne(), (largeurCase + 1) * y + 1, (hauteurCase + 1) * x + 1, largeurCase - 5, hauteurCase - 4, this);
            }
        }
    }

    //Permet de dessiner le plateau sous les pions
    private void drawPlateau(Graphics2D g) {
        Image current = Theme.instance().plateau();
        g.drawImage(current, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawContenu(Graphics2D g) {
        Jeu j = controleur.jeu();
        Niveau n = j.getNiveau();
        int x = bordureGauche;
        int y = bordureHaut;

        for (int l = 0; l < 9; l++) {
            for (int c = 0; c < 9; c++) {
                // -- Dessin des pions, forteresses, roi, konakis
                if (n.estForteresse(l, c)) {
                    g.drawImage(Theme.instance().forteresse(), x + 4, y + 4, largeurCase - 11, hauteurCase - 10, this);
                }
                // - Si c'est la case centrale alors on dessine le konakis, s'il n'y a pas le roi
                if (n.estKonakis(l, c) && !n.estRoi(l, c)) {
                    g.drawImage(Theme.instance().konakis(), x + 5, y + 4, largeurCase - 8, hauteurCase - 8, this);
                }

                //System.out.println("l = " + l + ", c = " + c);
                //System.out.println("x = " + x + ", y = " + y);

                if (n.estAttaquant(l, c)) {
                    g.drawImage(Theme.instance().noir_inactif(), x + 4, y + 4, largeurCase - 8, hauteurCase - 8, this);
                } else if (n.estRoi(l, c)) {
                    g.drawImage(Theme.instance().roi(), x + 5, y + 5, largeurCase - 7, hauteurCase - 2, this);
                } else if (n.estDefenseur(l, c)) {
                    g.drawImage(Theme.instance().blanc_inactif(), x + 4, y + 4, largeurCase - 8, hauteurCase - 8, this);
                }
                x += largeurCase;
                if (c % 2 == 0)
                    x++;
            }
            y += hauteurCase;
            if (l % 2 == 0)
                y++;
            x = bordureGauche;
        }
    }

    public void updateBrillanceSelection(int l, int c) {
        this.brillanceX = l;
        this.brillanceY = c;
        repaint();
    }

    public void drawBrillance(Graphics2D g, int l, int c) {
        Jeu jeu = controleur.jeu();
        Niveau n = jeu.getNiveau();

        Point selec = getPointSelec();
        // TODO : Aura autour du pion
        int x = bordureGauche;
        int y = bordureHaut;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (i == l && c == j) {
                    //TODO erreur avec les images ICI
                    g.drawImage(Theme.instance().pointInterrogation(), x, y, largeurCase, hauteurCase, this);
                    /*if (n.estAttaquant(l,c)) {
                        g.drawImage(Theme.instance().noir_selectionne(), x, y, largeurCase, hauteurCase, this);

                    }else if (n.estRoi(l, c)) {
                        //g.drawImage(Theme.instance().)
                        System.out.println("ICII faut mettre une image de roi selec");
                    }

                    else {
                        g.drawImage(Theme.instance().blanc_selectionne(), x, y, largeurCase, hauteurCase, this);
                    }*/
                }
                x += largeurCase;
            }
            y += hauteurCase;
            x = bordureGauche;
        }


        /*// TODO : implémenter un tour avec une sélection de pion à faire, puis une case à sélectionner, puis un mouvement (implicitement)
        if (j.pionSelectionne() && (j.pion() != null)) {
            int x = bordureGauche + j.pion().colonne() * largeurCase;
            int y = bordureHaut + j.pion().ligne() * hauteurCase;

            int l = j.pion().ligne();
            int c = j.pion().colonne();

            // TODO : Affichage des cases possibles

            // - Affichage du pion selectionné (en fonction de la couleur)
        }*/


    }

    private void calculerDimensions() {
        bordureHaut = Math.round(Theme.instance().bordureHaut() * getHeight() / (float) Theme.instance().hauteurPlateau());
        bordureGauche = Math.round(Theme.instance().bordureGauche() * getWidth() / (float) Theme.instance().largeurPlateau());
        hauteurCase = Math.round(Theme.instance().hauteurCase() * getHeight() / (float) Theme.instance().hauteurPlateau());
        largeurCase = Math.round(Theme.instance().largeurCase() * getWidth() / (float) Theme.instance().largeurPlateau());
    }

    public Point getPionEnDeplacement() {
        return pionEnDeplacement;
    }

    public void setPionEnDeplacement(Point pionEnDeplacement) {

        this.pionEnDeplacement = pionEnDeplacement;
        miseAJour(); // Ajoutez cette ligne pour actualiser le plateau
    }

    public void setPointSelec(Point point) {
        this.pointSelec = point;
    }

    public void setPionSelec(Pion pion) {
        this.pionSelec = pion;
    }

    public Pion getPionSelec() {
        return pionSelec;
    }

    public Point getPointSelec() {
        return pointSelec;
    }

    @Override
    public void miseAJour() {
        repaint();
    }

    public void setImage(int image) {
        if (image == 0){
            this.image = Theme.instance().noir_inactif();
        }
        else if (image == 1){
            this.image = Theme.instance().blanc_inactif();
        }
        else{
            this.image = Theme.instance().roi();

        }
    }

    public Image getImage() {
        return image;
    }
}
