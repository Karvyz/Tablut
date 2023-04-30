package Vues;

import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class PlateauGraphique extends JComponent implements Observateur {
	Jeu jeu;
	int largeurCase, hauteurCase;
    private Point pionEnDeplacement;
    private Color couleurEnDeplacement;
    private Point pionSelec;
    
    

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
                if (pionEnDeplacement != null && i == pionEnDeplacement.y / hauteurCase && j == pionEnDeplacement.x / largeurCase) {
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

        if (getPionSelec() != null) {
            drawable.setColor(Color.BLUE);
            int caseX = getPionSelec().x - largeurCase / 2;
            int caseY = getPionSelec().y - hauteurCase / 2;
            drawable.fillRect(caseX + offset, caseY + offset, largeurCase - 2 * offset, hauteurCase - 2 * offset);
        }

        if (pionEnDeplacement != null) {
            drawable.setColor(couleurEnDeplacement);
            drawable.fillRect(pionEnDeplacement.x - largeurCase / 2 + offset, pionEnDeplacement.y - hauteurCase / 2 + offset, largeurCase - 2 * offset, hauteurCase - 2 * offset);
            
        }
    }
    
    public void setPionSelec(Point point) {
        this.pionSelec = point;
    }

    public Point getPionSelec(){
        return pionSelec;
    }


    public Point getPionEnDeplacement() {
        return pionEnDeplacement;
    }
    
    public void setPionEnDeplacement(Point pionEnDeplacement){
        
        this.pionEnDeplacement = pionEnDeplacement;
        miseAJour(); // Ajoutez cette ligne pour actualiser le plateau
    }

    public Color getCouleurEnDeplacement() {
        return couleurEnDeplacement;
    }

    public void setCouleurEnDeplacement(int l, int c) {
        if (jeu.n.estRoi(l, c)) {
            couleurEnDeplacement = Color.RED;
        } else if (jeu.n.estAttaquant(l, c)) {
            couleurEnDeplacement = Color.BLACK;
        } else if (jeu.n.estDefenseur(l, c)) {
            couleurEnDeplacement = Color.WHITE;
        }
        miseAJour(); // Ajoutez cette ligne pour actualiser le plateau
    }
    
    @Override
    public void miseAJour() {
        repaint();
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


}