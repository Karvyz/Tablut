package Vues;

import Modele.Coordonne;
import Modele.Pion;
import Vues.JComposants.CPlateau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class AdaptateurSouris extends MouseAdapter implements MouseMotionListener {
    CollecteurEvenements controleur;
    CPlateau pane;
    Point dragStart = null;

    private boolean Deux_cliques = false;

    private boolean draw_destination = false;

    int bordureGauche, bordureHaut, bordureDroite, bordureBas, hauteurCase, largeurCase ;

    boolean clicInutile = false;
    boolean clicSelection = false;


    public AdaptateurSouris(CollecteurEvenements c, CPlateau pane) {
        controleur = c;
        this.pane = pane;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint(); // On clique et stock le point de départ du dragStart
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

        //Test le déplacement
        if (pane.getPionSelec() != null){
            if (controleur.clicSouris(pane.getPionSelec(), l, c) == true){
                pane.setPionSelec(null);
                affiche_destination(null);
                return;
            }
            clicInutile = true;
        }

        Pion caseClique = controleur.jeu().n.getPion(l,c);
        //Le pion nous appartient, on affiche ses dispos
        if (controleur.jeu().n.check_clic_selection_pion(caseClique, controleur.jeu().get_num_JoueurCourant())){
            pane.setPionSelec(caseClique); //au laché, on affiche les dispos
            clicSelection = true;
            return;
        }

        if(caseClique != null && !controleur.jeu().n.check_clic_selection_pion(caseClique, controleur.jeu().get_num_JoueurCourant())){
            clicSelection = false;
            clicInutile = true;
            //ne rien faire, on fera au survol
            return;
        }

        if (caseClique == null){
            System.out.println("Clique sur caseVide");
            return;
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if(clicInutile == true || clicSelection == true){
            if (pane.getPionSelec() != null){
                affiche_destination(pane.getPionSelec());
                clicInutile = false;
            }
        }else{
            pane.setPionSelec(null);
        }



    }

    private void affiche_destination(Pion pionSelec) {
        if (pionSelec == null){
            pane.setDestinationsPossibles(null);
            return;
        }

        ArrayList<Coordonne> liste = pionSelec.getDeplacement(controleur.jeu().n.plateau);
        if (liste.isEmpty()){
            /*UIManager.put("ToolTip.background", Color.RED);
            UIManager.put("ToolTip.foreground", Color.WHITE);
            pane.setToolTipText("Aucun déplacement possible ");*/
            pane.setDestinationsPossibles(null);
            System.out.println("Aucun deplacement possible");
        }else{
            pane.setDestinationsPossibles(liste);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private void affiche_feedbak(Pion caseSelec) {

    }

    private void calculerDimensions() {
        bordureHaut = Math.round(Theme.instance().bordureHaut() * pane.getHeight() / (float) Theme.instance().hauteurPlateau());
        bordureGauche = Math.round(Theme.instance().bordureGauche() * pane.getWidth() / (float) Theme.instance().largeurPlateau());
    }

}
