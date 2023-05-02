package Vues.JComposants;

import Modele.Jeu;
import Modele.Niveau;
import Modele.Pion;
import Patterns.Observateur;
import Vues.CollecteurEvenements;
import Vues.Theme;

import javax.swing.*;
import java.awt.*;

public class CPlateau extends JPanel implements Observateur {
    CollecteurEvenements controleur;
    int bordureHaut, bordureGauche, hauteurCase, largeurCase;

    public CPlateau(CollecteurEvenements c) {
        controleur = c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculerDimensions();
        drawPlateau(g);
        drawContenu(g);
    }

    private void drawPlateau(Graphics g) {
        Image current = Theme.instance().plateau();
        g.drawImage(current, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawContenu(Graphics g) {
        Jeu j = controleur.jeu();
        Niveau n = j.getNiveau();
        int x = bordureGauche;
        int y = bordureHaut;

        for (int l = 0; l < 9; l++) {
            for (int c = 0; c < 9; c++) {
                Pion courant = n.getPion(l, c);
                // Dessin des pions, forteresses, roi, konakis
                // - Si c'est un des coins alors on dessine les forteresses
                if (n.estForteresse(l, c)) {
                    g.drawImage(Theme.instance().forteresse(), x, y, largeurCase, hauteurCase, this);
                }
                // - Si c'est la case centrale alors on dessine le konakis
                if (n.estKonakis(l, c)) {
                    g.drawImage(Theme.instance().konakis(), x, y, largeurCase, hauteurCase, this);
                }
                if (n.estAttaquant(courant)) {
                    g.drawImage(Theme.instance().blanc_inactif(), x, y, largeurCase, hauteurCase, this);
                } else if (n.estRoi(l, c)) {
                    g.drawImage(Theme.instance().roi(), x, y, largeurCase, hauteurCase, this);
                } else if (n.estDefenseur(courant)) {
                    g.drawImage(Theme.instance().noir_inactif(), x, y, largeurCase, hauteurCase, this);
                }
                x += largeurCase;
            }
            y += hauteurCase;
            x = bordureGauche;
        }
    }

    public void drawBrillance(Graphics g) {
        Jeu j = controleur.jeu();
        Niveau n = j.getNiveau();

        /*
        // TODO : implémenter un tour avec une sélection de pion à faire, puis une case à sélectionner, puis un mouvement (implicitement)
        if (j.pionSelectionne() && (j.pion() != null)) {
            int x = bordureGauche + j.pion().colonne() * largeurCase;
            int y = bordureHaut + j.pion().ligne() * hauteurCase;

            int l = j.pion().ligne();
            int c = j.pion().colonne();

            // TODO : Affichage des cases possibles

            // - Affichage du pion selectionné (en fonction de la couleur)
            // TODO : Aura autour du pion
            if (j.getJoueurCourant().aPionsBlancs()) {
                g.drawImage(Theme.instance().blanc_selectionne(), x, y, largeurCase, hauteurCase, this);
            } else if (j.getJoueurCourant().aPionsNoirs()) {
                g.drawImage(Theme.instance().noir_selectionne(), x, y, largeurCase, hauteurCase, this);
            }
        }

         */
    }

    private void calculerDimensions() {
        bordureHaut = Math.round(Theme.instance().bordureHaut() * getHeight() / (float) Theme.instance().hauteurPlateau());
        bordureGauche = Math.round(Theme.instance().bordureGauche() * getWidth() / (float) Theme.instance().largeurPlateau());
        hauteurCase = Math.round(Theme.instance().hauteurCase() * getHeight() / (float) Theme.instance().hauteurPlateau());
        largeurCase = Math.round(Theme.instance().largeurCase() * getWidth() / (float) Theme.instance().largeurPlateau());
    }

    @Override
    public void miseAJour() {
        repaint();
    }
}
