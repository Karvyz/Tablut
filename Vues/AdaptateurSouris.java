package Vues;

import Modele.Coordonne;
import Modele.Niveau;
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
    int bordureGauche, bordureHaut, bordureDroite, bordureBas;
    boolean clicInutile = false;
    boolean clicSelection = false;


    public AdaptateurSouris(CollecteurEvenements c, CPlateau pane) {
        controleur = c;
        this.pane = pane;
    }

    @Override
    public void mousePressed(MouseEvent e) { //Méthode executé lors d'un clic
        dragStart = e.getPoint(); // On clique et stock le point de départ du dragStart
        int l = calcul_l(e);
        int c = calcul_c(e);
        if (!check_ok(l,c)){return;};

        Pion caseClique = controleur.jeu().n.getPion(l,c);

        //Test le déplacement
        if (pane.getPionSelec() != null){
            pane.setPionEnDeplacement(null);//On ne peut pas drag
            if (controleur.clicSouris(pane.getPionSelec(), l, c) == true){ //Si on clique après avoir selectionne un pion on check si le coup est juste
                pane.setPionSelec(null); //On déselectionne après avoir joué un coup
                pane.setPionEnDeplacement(null);
                dragStart = null;
                affiche_destination(null);
                return;
            }//Si il ne permet pas un déplacement on regarde si ce pion peut servir de nouvelle selection
            else if (controleur.jeu().n.check_clic_selection_pion(caseClique, controleur.jeu().get_num_JoueurCourant()) && caseClique.getDeplacement(controleur.jeu().n.plateau).isEmpty()){//ici il ne sert pas de nouvelle selection
                    clicSelection = false;
                    clicInutile = false;
                    pane.setPionEnDeplacement(null);//On ne peut pas drag
                    pane.setPionSelec(null);//On ne peut pas drag

                return;
            }
            else if (controleur.jeu().n.check_clic_selection_pion(caseClique, controleur.jeu().get_num_JoueurCourant())){//ici il sert de nouvelle selection
                    pane.setPionEnDeplacement(new Point(l , c ));//Initialise point de départ du moovement pour le drag
                    pane.setPionSelec(caseClique);
                    setImage(caseClique);
                    affiche_destination(caseClique);
                    clicSelection = true;
                    return;
            }
            clicInutile = true;
        }

        //Le pion nous appartient mais il n'y a pas de deplacement possibles
        if (!check_pion(caseClique))
            return;
        //Le pion nous appartient, on affiche ses dispos
        else if (controleur.jeu().n.check_clic_selection_pion(caseClique, controleur.jeu().get_num_JoueurCourant())){
            pane.setPionSelec(caseClique); //au laché, on affiche les dispos
            pane.setPionEnDeplacement(new Point(l , c ));//Initialise point de départ du moovement pour le drag
            setImage(caseClique);
            clicSelection = true;
            return;
        }
        //le pion nous appartient pas ou c'est une case vide
        if(!controleur.jeu().n.check_clic_selection_pion(caseClique, controleur.jeu().get_num_JoueurCourant())){
            clicSelection = false;
            clicInutile = false; //Changez ici si on veut garder les destinations affichés lors d'un clic sur pion pas a nous
            pane.setPionEnDeplacement(null);
            //pane.setPionSelec(null);
        }

    }

    private void setImage(Pion p) {
        if (controleur.jeu().n.estAttaquant(p)){
            pane.setImage(0);
        }
        else if(controleur.jeu().n.estDefenseur(p)){
            pane.setImage(1);
        }
        else{
            pane.setImage(2);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(dragStart !=null && pane.getPionSelec() != null){
            int l = calcul_l(e);
            int c = calcul_c(e);
            if (!check_ok(l,c)){return;};

            Pion caseLache = controleur.jeu().n.getPion(l,c);

            int startX = pane.getPionSelec().getX();
            int startY = pane.getPionSelec().getY();

            //Ici on gère le cas ou l'on clique sur un pion de la même couleur que le notre
            if(controleur.jeu().n.check_clic_selection_pion(caseLache, controleur.jeu().get_num_JoueurCourant())){
                pane.setPionSelec(caseLache);
                affiche_destination(pane.getPionSelec());
                clicInutile = false;
                return;
            }else{
                pane.setPionSelec(null);
                affiche_destination(null);
            }

            //Ici on gère le drag&drop
            if (startX != l || startY != c) {
                if (controleur.dragANDdrop(new Coordonne(startX, startY), new Coordonne(l, c)) == true){ //On teste le déplacement
                    affiche_destination(null);
                }
                pane.setPionSelec(null);
                pane.setPionEnDeplacement(null);
            }
            dragStart = null;
        }
        //Ici on gère le cas ou on clique sur un de nos pions, puis sur un pion adverse ou case vide, en enlève l'affichage des déplacements dispos
        if (pane.getPionEnDeplacement() == null){
            if(clicInutile == true || clicSelection == true){
                if (pane.getPionSelec() != null){
                    affiche_destination(pane.getPionSelec());
                    clicInutile = false;
                }
            }else{
                pane.setPionSelec(null);
                affiche_destination(null);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point pionEnDeplacement = pane.getPionEnDeplacement();
        if (pionEnDeplacement != null) {
            int l = calcul_l(e);
            int c = calcul_c(e);
            if (!check_ok(l,c)){return;};
            pionEnDeplacement.setLocation(l, c); //modifie les coordonne du Point pionEnDeplacement
            pane.setPionEnDeplacement(pionEnDeplacement);
        }
        else{
            pane.setPointSelec(null);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int l = calcul_l(e);
        int c = calcul_c(e);
        if (!check_ok(l,c)){return;};

        // Obtenez les informations de la case survolée
        Pion caseSurvole = controleur.jeu().n.getPion(l, c);

        if (pane.getPionSelec() == null){
            //Permet d'afficher lorsqu'on survole
            if(controleur.jeu().n.check_clic_selection_pion(caseSurvole, controleur.jeu().get_num_JoueurCourant()) && caseSurvole.getDeplacement(controleur.jeu().n.plateau).isEmpty()){
                affiche_destination(null);
                UIManager.put("ToolTip.background", Color.RED);
                UIManager.put("ToolTip.foreground", Color.WHITE);
                pane.setToolTipText("Aucun déplacement possible ");
            }
            else if (controleur.jeu().n.check_clic_selection_pion(caseSurvole, controleur.jeu().get_num_JoueurCourant())){
                affiche_destination(caseSurvole); //affiche les destinations du pion survole
                pane.setToolTipText(null);
            }
            else{
                affiche_destination(null); //affiche aucune destination
                UIManager.put("ToolTip.background", Color.RED);
                UIManager.put("ToolTip.foreground", Color.WHITE);
                pane.setToolTipText("Zone invalide");
            }
        }else{
            //on ne fais rien dans le survol si on a choose un pions
        }

    }

    private void affiche_destination(Pion pionSelec) {//Permet l'affichage des destinations données pour un Pion donné
        if (pionSelec == null){
            pane.setDestinationsPossibles(null);
            return;
        }
        ArrayList<Coordonne> liste = pionSelec.getDeplacement(controleur.jeu().n.plateau);
        if (liste.isEmpty()){
            pane.setDestinationsPossibles(null);
        }else{
            pane.setToolTipText(null);
            pane.setDestinationsPossibles(liste);
        }
    }

    private void calculerDimensions() {
        bordureHaut = Math.round(Theme.instance().bordureHaut() * pane.getHeight() / (float) Theme.instance().hauteurPlateau());
        bordureGauche = Math.round(Theme.instance().bordureGauche() * pane.getWidth() / (float) Theme.instance().largeurPlateau());
    }

    private boolean check_ok(int l, int c) {
        if (l == -1 || c == -1 ){
            return false;
        }
        return true;
    }

    private boolean check_pion(Pion p){
        if (controleur.jeu().n.check_clic_selection_pion(p, controleur.jeu().get_num_JoueurCourant()) && p.getDeplacement(controleur.jeu().n.plateau).isEmpty()) {
            clicSelection = false;
            clicInutile = false;
            pane.setPionEnDeplacement(null);//On ne peut pas drag
            return false;
        }
        return true;
    }

    private int calcul_l(MouseEvent e) {
        calculerDimensions();
        int hauteur = pane.getHeight() - bordureHaut - bordureBas;
        if (e.getX() < bordureGauche || e.getY() < bordureHaut ||
                e.getX() > pane.getWidth() - bordureDroite ||
                e.getY() > pane.getHeight() - bordureBas) {
            return -1;
        }

        int y = e.getY() - bordureHaut;

        return y * 9 / hauteur;
    }

    private int calcul_c(MouseEvent e){
        calculerDimensions();
        int largeur = pane.getWidth() - bordureGauche - bordureDroite;

        if (e.getX() < bordureGauche || e.getY() < bordureHaut ||
                e.getX() > pane.getWidth() - bordureDroite ||
                e.getY() > pane.getHeight() - bordureBas) {
            return -1;
        }

        int x = e.getX() - bordureGauche;

        return x * 9 / largeur;
    }

}
