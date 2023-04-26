package Vues;

import Modele.Jeu;
import Modele.Pion;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class NiveauGraphique extends JComponent implements Observateur {
	Jeu jeu;
	int largeurCase, hauteurCase;

	public NiveauGraphique(Jeu j) {
		jeu = j;
		jeu.ajouteObservateur(this);
		largeurCase = 50;
        hauteurCase = 50;
        setPreferredSize(new Dimension(450, 450));
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        super.paintComponent(drawable);

        //Pion [][] niveau = jeu.n.plateau;
        int offset = 5;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (jeu.n.estRoi(i, j)) {
                    drawable.setColor(Color.RED);
                } else if (jeu.n.estAttaquant(i,j) ) {
                    drawable.setColor(Color.BLACK);
                } else if (jeu.n.estDefenseur(i,j) ) {
                    drawable.setColor(Color.WHITE);
                }
				else {
                    drawable.setColor(Color.BLUE);
                }
                drawable.fillRect(j * largeurCase + offset, i * hauteurCase + offset, largeurCase - 2 * offset, hauteurCase - 2 * offset);
            }
        }
    }

	int largeur() {
		return getWidth();
	}

	int hauteur() {
		return getHeight();
	}

	public int largeurCase() {
		return largeurCase;
	}

	public int hauteurCase() {
		return hauteurCase;
	}

	@Override
	public void miseAJour() {
		repaint();
	}
}