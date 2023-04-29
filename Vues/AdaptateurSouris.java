
package Vues;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import Modele.Coordonne;

import java.awt.Color;
import java.awt.Point;



public class AdaptateurSouris extends MouseAdapter implements MouseMotionListener {
    PlateauGraphique plateau;
    CollecteurEvenements control;
    Point dragStart = null;
    private Color pionEnDeplacementCouleur;
    private Point pionEnDeplacement = null;

    AdaptateurSouris(PlateauGraphique p, CollecteurEvenements c) {
        control = c;
        plateau = p;
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY() / plateau.hauteurCase;
        int c = e.getX() / plateau.largeurCase;

        // Vérifiez si un pion est présent à ces coordonnées
        if (plateau.jeu.n.estRoi(l, c) || plateau.jeu.n.estAttaquant(l, c) || plateau.jeu.n.estDefenseur(l, c)) {
            dragStart = e.getPoint();

            if (plateau.jeu.n.estRoi(l, c)) {
                pionEnDeplacementCouleur = Color.RED;
            } else if (plateau.jeu.n.estAttaquant(l, c)) {
                pionEnDeplacementCouleur = Color.BLACK;
            } else if (plateau.jeu.n.estDefenseur(l, c)) {
                pionEnDeplacementCouleur = Color.WHITE;
            }

            pionEnDeplacement = new Point(c * plateau.largeurCase + plateau.largeurCase / 2, l * plateau.hauteurCase + plateau.hauteurCase / 2);
            System.out.println("Clic en (" + l + "," + c + ")");
            control.clicSouris(l, c);
        } else {
            // Réinitialisez les variables si aucun pion n'est présent
            dragStart = null;
            pionEnDeplacement = null;
            pionEnDeplacementCouleur = null;
        }
    }




    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Drag ");

        // Ajoutez cette condition pour vérifier si la couleur est différente de null
        if (pionEnDeplacementCouleur != null) {
            if (pionEnDeplacement != null) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Mettez à jour l'état de pionEnDeplacement ici
                pionEnDeplacement.setLocation(mouseX, mouseY);
                // Mettez à jour l'état de pionEnDeplacement et sa couleur dans PlateauGraphique
                plateau.setPionEnDeplacement(pionEnDeplacement, pionEnDeplacementCouleur);

                int coordX = mouseX / plateau.largeurCase;
                int coordY = mouseY / plateau.hauteurCase;
                System.out.println("Drag en (" + coordY + "," + coordX + ")");
            }
        }
    }



    @Override
    public void mouseReleased(MouseEvent e) {
        if (dragStart != null) {
            int startX = dragStart.x / plateau.largeurCase;
            int startY = dragStart.y / plateau.hauteurCase;
            int endX = e.getX() / plateau.largeurCase;
            int endY = e.getY() / plateau.hauteurCase;

            if (startX != endX || startY != endY) {
                control.dragANDdrop(new Coordonne(startY, startX), new Coordonne(endY, endX));
            }
        }
        Point pionEnDeplacement = plateau.getPionEnDeplacement(); // Modification ici
        plateau.setPionEnDeplacement(null, null);
    }
}
