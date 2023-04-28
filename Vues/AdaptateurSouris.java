
package Vues;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Modele.Coordonne;

import java.awt.Point;



public class AdaptateurSouris extends MouseAdapter {
    PlateauGraphique plateau;
    CollecteurEvenements control;
    Point dragStart = null;

    AdaptateurSouris(PlateauGraphique p, CollecteurEvenements c) {
        control = c;
        plateau = p;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint();
        int l = e.getY() / plateau.hauteurCase;
        int c = e.getX() / plateau.largeurCase;
        System.out.println("Clic en (" + l + "," + c + ")");
        control.clicSouris(l, c);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragStart != null) {
            // Vous pouvez ajouter ici du code pour dessiner un pion en déplacement
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
				control.dragANDdrop( new Coordonne(startY, startX), new Coordonne(endY, endX));
            }
        }
        dragStart = null;
    }
}

