package Vues;

import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class PlateauGraphique extends JComponent implements Observateur {
	Jeu jeu;
	int largeurCase, hauteurCase;
    private Point pionEnDeplacement;
    private Color pionEnDeplacementCouleur;
    
    

	public PlateauGraphique(Jeu j) {
		jeu = j;
		jeu.ajouteObservateur(this);
		largeurCase = 48;
        hauteurCase = 48;
        setPreferredSize(new Dimension(450, 450));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        super.paintComponent(drawable);
    
        int offset = 5;
    
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (pionEnDeplacement != null && i * hauteurCase + offset == pionEnDeplacement.y / hauteurCase && j * largeurCase + offset == pionEnDeplacement.x / largeurCase) {
                    continue;
                }
                if (jeu.n.estRoi(i, j)) {
                    drawable.setColor(Color.RED);
                } else if (jeu.n.estAttaquant(i, j)) {
                    drawable.setColor(Color.BLACK);
                } else if (jeu.n.estDefenseur(i, j)) {
                    drawable.setColor(Color.WHITE);
                } else {
                    drawable.setColor(Color.BLUE);
                }
                drawable.fillRect(j * largeurCase + offset, i * hauteurCase + offset, largeurCase - 2 * offset, hauteurCase - 2 * offset);
            }
        }
    
        if (pionEnDeplacement != null) {
            drawable.setColor(pionEnDeplacementCouleur);
            drawable.fillOval(pionEnDeplacement.x - largeurCase / 2, pionEnDeplacement.y - hauteurCase / 2, largeurCase, hauteurCase);
        }
    }
    

    public Point getPionEnDeplacement() {
        return pionEnDeplacement;
    }

    public void setPionEnDeplacement(Point pionEnDeplacement, Color couleur) {
        this.pionEnDeplacement = pionEnDeplacement;
        this.pionEnDeplacementCouleur = couleur;
        miseAJour(); // Ajoutez cette ligne pour actualiser le plateau
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