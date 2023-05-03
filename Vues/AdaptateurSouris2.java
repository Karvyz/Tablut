package Vues;

import Modele.Pion;
import Vues.JComposants.CPlateau;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AdaptateurSouris2 extends MouseAdapter {
    CollecteurEvenements controleur;
    CPlateau pane;

    AdaptateurSouris2(CollecteurEvenements c, CPlateau pane) {
        controleur = c;
        this.pane = pane;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        int bordureHaut = Math.round(Theme.instance().bordureHaut() * pane.getHeight() / (float) Theme.instance().hauteurPlateau());
        int bordureGauche = Math.round(Theme.instance().bordureGauche() * pane.getWidth() / (float) Theme.instance().largeurPlateau());
        int bordureBas = Math.round(Theme.instance().bordureBas() * pane.getHeight() / (float) Theme.instance().hauteurPlateau());
        int bordureDroite = Math.round(Theme.instance().bordureDroite() * pane.getWidth() / (float) Theme.instance().largeurPlateau());

        if (e.getX() < bordureGauche || e.getY() < bordureHaut ||
                e.getX() > pane.getWidth() - bordureDroite ||
                e.getY() > pane.getHeight() - bordureBas) {
            return;
        }

        int x = e.getX() - bordureGauche;
        int y = e.getY() - bordureHaut;
        int hauteur = pane.getHeight() - bordureHaut - bordureBas;
        int largeur = pane.getWidth() - bordureGauche - bordureDroite;

        int l = y * 9 / hauteur;
        int c = x * 9 / largeur;

        Pion caseSelec = controleur.jeu().n.getPion(l, c);
        /*if (!controleur.jeu().n.check_clic_selection_pion(caseSelec, controleur.jeu().get_num_JoueurCourant())) {
            pane.setPionEnDeplacement(null);
            pane.setPionSelec(null); // Ajoutez cette ligne
        }*/

        controleur.clicSouris(l, c);
        System.out.println("l = " + l + ", c = " + c);
    }



    @Override
    public void mouseDragged(MouseEvent e) {
        //S'inspirer de AdaptateurSouris
    }
}
