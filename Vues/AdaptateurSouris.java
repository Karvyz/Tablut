
package Vues;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import Modele.Coordonne;
import Modele.Pion;

import java.awt.Color;
import java.awt.Point;



public class AdaptateurSouris extends MouseAdapter implements MouseMotionListener {
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
        Pion caseSelec = plateau.jeu.n.getPion(l,c);
        if (!plateau.jeu.n.check_clic_selection_pion(caseSelec, plateau.jeu.joueurCourant())){
            plateau.setPionEnDeplacement(null);
        }
        else{
            plateau.setPionEnDeplacement(new Point(c * plateau.largeurCase + plateau.largeurCase / 2, l * plateau.hauteurCase + plateau.hauteurCase / 2));            plateau.setCouleurEnDeplacement(l, c); // Ajoutez cette ligne pour définir la couleur du pion en déplacement
        }
        System.out.println("Clic en (" + l + "," + c + ")");
        control.clicSouris(l, c);
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        Point pionEnDeplacement = plateau.getPionEnDeplacement(); // Ajoutez cette ligne
        if (pionEnDeplacement != null) {
            int mouseX = e.getX() ;
            int mouseY = e.getY();
            pionEnDeplacement.setLocation(mouseX, mouseY); //modifie les coordonne du Point pionEnDeplacement
            plateau.setPionEnDeplacement(pionEnDeplacement);
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
        plateau.setPionEnDeplacement(null);
    }

}
