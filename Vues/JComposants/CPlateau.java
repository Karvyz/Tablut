package Vues.JComposants;

import Modele.*;
import Patterns.Observateur;
import Vues.AdaptateurSouris;
import Vues.CollecteurEvenements;
import Vues.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CPlateau extends JPanel implements Observateur {
    CollecteurEvenements controleur;

    private Point pionEnDeplacement;

    private Pion pionSelec;
    int bordureHaut, bordureGauche, hauteurCase, largeurCase;

    private ArrayList<Coordonne> destinationsPossibles = new ArrayList<>();
    private Image image;

    private boolean drawFleche = true;

    private Point dessineCroix;

    private AdaptateurSouris adaptateurSouris;
    int compteur;



    public CPlateau(CollecteurEvenements c) {
        controleur = c;
        adaptateurSouris = new AdaptateurSouris(c, this);
        addMouseListener(adaptateurSouris);
        addMouseMotionListener(adaptateurSouris);
        compteur = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        calculerDimensions();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        verif_debut_partie();
        test_annuler_refaire();
        drawPlateau(g2d);
        drawContenu(g2d);
        drawMouvIA(g2d);
        drawAideIA(g2d);
        drawCroixRouge(g2d);

        if (controleur.jeu().partieTerminee() && compteur == 0){
            removeMouseListener(adaptateurSouris);
            removeMouseMotionListener(adaptateurSouris);
            setPionEnDeplacement(null);
            compteur =1;
        }

    }

    private void verif_debut_partie() {
        if(controleur.jeu().debutPartie()) {
            setPionEnDeplacement(null);
            setDestinationsPossibles(null);
            if(controleur.jeu().getCoordooneDepartIA()==null){
                setDrawFleche1(false);
            }
            setPionSelec(null);
            setSurvole(null);
            setPointSelec(null);
            miseAJour();
        }
    }

    private void test_annuler_refaire() {
        if (controleur.jeu().test_annuler_refaire == true) {
            setPointSelec(null);
            setPionSelec(null);
            setDestinationsPossibles(null);
            setPionEnDeplacement(null);
            setDrawFleche(false);
            //controleur.jeu().setCoordooneJouerIA(null, null);
        }
        controleur.jeu().setAnnuler_refaire(false);
    }

    //Permet de dessiner le plateau sous les pions
    private void drawPlateau(Graphics2D g) {
        Image current = Theme.instance().plateau();
        g.drawImage(current, 0, 0, getWidth(), getHeight(), null);
    }

    private void drawContenu(Graphics2D g) {
        Jeu j = controleur.jeu();
        Niveau n = j.getNiveau();
        int x = bordureGauche;
        int y = bordureHaut;

        for (int l = 0; l < 9; l++) {
            for (int c = 0; c < 9; c++) {

                // -- Dessin des pions, forteresses, roi, konakis
                if(controleur.jeu().partieTerminee()){
                    setPionSelec(null);
                }

                if (getPionSelec() != null && l == getPionSelec().getX() && c == getPionSelec().getY() && controleur.jeu().getAideIA()!= null &&
                        l==controleur.jeu().getAideIA().depart.getX() && c==controleur.jeu().getAideIA().depart.getY()){
                    g.drawImage(getImage(), x + 4, y + 4, largeurCase - 4, hauteurCase - 4, this); //Permet d'afficher le pion si il est selectionne avant l'aide de l'IA
                }


                if (getPionSelec() != null && l == getPionSelec().getX() && c == getPionSelec().getY() && !controleur.jeu().partieTerminee()) { //Ici on efface le pion selec, sauf si partie_fini
                    x += largeurCase;
                    if (c % 2 == 0)
                        x++;
                    continue;
                }

                if (n.estAttaquant(l, c)) {
                    int tal = (largeurCase / 4) * 3;
                    int tah = (hauteurCase / 4) * 3;
                    g.drawImage(Theme.instance().noir_inactif(), x + (largeurCase / 2) - (tal / 2), y + (hauteurCase / 2) - (tah / 2), tal, tah, this);
                } else if (n.estRoi(l, c)) {
                    int trl = (largeurCase / 4) * 3;
                    int trh = (hauteurCase / 4) * 3;
                    g.drawImage(Theme.instance().roi(), x + (largeurCase / 2) - (trl / 2), y + (hauteurCase / 2) - (trh / 2), trl, trh, this);
                } else if (n.estDefenseur(l, c)) {
                    int tdl = (largeurCase / 4) * 3;
                    int tdh = (hauteurCase / 4) * 3;
                    g.drawImage(Theme.instance().blanc_inactif(), x + (largeurCase / 2) - (tdl / 2), y + (hauteurCase / 2) - (tdh / 2), tdl, tdh, this);
                }
                x += largeurCase;
                if (c % 2 == 0)
                    x++;
            }
            y += hauteurCase;
            if (l % 2 == 0)
                y++;
            x = bordureGauche;
        }
    }


    private void drawDeplacement(Graphics2D g2d) {
        if (getPionEnDeplacement() != null) {
            int x = bordureGauche;
            int y = bordureHaut;
            int l = getPionEnDeplacement().x;
            int c = getPionEnDeplacement().y;

            boolean autorise =true;
            if(destinationsPossibles != null){
                autorise = false;
                for (Coordonne caseSelec : destinationsPossibles) {
                    int l_case_dest = caseSelec.getX();
                    int c_case_dest = caseSelec.getY();
                    if(l == getPionSelec().getX() && c == getPionSelec().getY() ||  l == l_case_dest && c == c_case_dest){
                        autorise = true;
                    }
                }
            }

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    if (i == l && c == j) {
                        if(!autorise){
                            g2d.drawImage(Theme.instance().croix(), x +2, y + 6 ,largeurCase -5, hauteurCase -5, this);
                        }
                        else{
                            g2d.drawImage(getImage(), x + 4, y + 4, largeurCase - 4, hauteurCase - 4, this);
                        }
                    }
                    x += largeurCase + 1;
                }
                y += hauteurCase;
                x = bordureGauche;
            }
        }
        else if(controleur.jeu().partieTerminee()){
            drawContenu(g2d); //bien affiche le dernier coup en clic-clic
        }
    }

    private void drawDestination(Graphics2D g) {
        if (destinationsPossibles == null) {
            return;
        }
        for (Coordonne caseSelec : destinationsPossibles) {
            int x = bordureGauche;
            int y = bordureHaut;
            int l = caseSelec.getX();
            int c = caseSelec.getY();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (controleur.jeu().getCoordooneDepartIA() != null && controleur.jeu().getCoordooneDepartIA().equals(new Coordonne(l, c))) {
                        setDrawFleche1(false);
                    }
                    if (i == l && c == j) {

                        g.drawImage(Theme.instance().pointInterrogation(), x +(largeurCase/2), y + (hauteurCase/2), 10, 10, this);
                    }
                    x += largeurCase;
                }
                y += hauteurCase;
                x = bordureGauche;
            }
        }
    }

    private void drawMouvIA(Graphics2D g2d) {

        if (controleur.jeu().getJoueur1().estHumain() || controleur.jeu().getJoueur2().estHumain()) {
            drawDestination(g2d);
            drawDeplacement(g2d);

            if ((controleur.jeu().getCoordooneDepartIA() != null && getDrawFleche() == true)) {
                Coordonne depart = controleur.jeu().getCoordooneDepartIA();
                if (depart == null){return;}
                int l = depart.getX();
                int c = depart.getY();
                if (controleur.jeu().n.estKonakis(l,c)){
                    //on ne dessine pas la fleche sur le konaki. on comprend que le roi est parti de son trone
                }else {
                    int tfl = largeurCase / 2;
                    int tfh = hauteurCase / 2;
                    switch (calcul_dir(depart, controleur.jeu().getCoordooneArriveIA())) {
                        case 0:
                            // Déplacement vers le bas
                            g2d.drawImage(Theme.instance().fleche_bas(), c * hauteurCase + (hauteurCase / 4), l * largeurCase + (largeurCase / 2), tfl, tfh, this);
                            break;
                        case 1:
                            //vers le haut
                            g2d.drawImage(Theme.instance().fleche_haut(), c * hauteurCase + (hauteurCase / 4), (l * largeurCase), tfl, tfh, this);
                            break;
                        case 2:
                            // Déplacement vers la droite
                            g2d.drawImage(Theme.instance().fleche_droite(), (c * hauteurCase) + (hauteurCase / 2), l * largeurCase + (largeurCase / 4), tfl, tfh, this);
                            break;
                        case 3:
                            //vers la gauche
                            g2d.drawImage(Theme.instance().fleche_gauche(), (c * hauteurCase), l * largeurCase + (largeurCase / 4), tfl, tfh, this);
                            break;
                    }

                }
                int l_arr = controleur.jeu().getCoordooneArriveIA().getX();
                int c_arr = controleur.jeu().getCoordooneArriveIA().getY();
                g2d.setColor(Color.orange);
                g2d.fillRect((largeurCase) * c_arr + 5, (hauteurCase) * l_arr + 5, largeurCase - 6, hauteurCase - 6);
                drawContenu(g2d);
            }

        }
    }

    private void drawAideIA(Graphics2D g2d) {
        if(controleur.jeu().getAideIA() != null){
            Coup aide = controleur.jeu().getAideIA();
            int l_dep = aide.depart.getX();
            int c_dep = aide.depart.getY();
            int l_arr = aide.arrivee.getX();
            int c_arr = aide.arrivee.getY();

            g2d.setColor(Color.green);
            g2d.fillRect((largeurCase) * c_dep + 5, (hauteurCase) * l_dep + 5, largeurCase - 6, hauteurCase - 6);

            g2d.fillRect((largeurCase) * c_arr + 5, (hauteurCase) * l_arr + 5, largeurCase - 6, hauteurCase - 6);
            drawContenu(g2d);
        }
    }

    private void drawCroixRouge(Graphics2D g2d) {
        if(dessineCroix != null){
            int x = bordureGauche;
            int y = bordureHaut;
            int l = dessineCroix.x;
            int c = dessineCroix.y;

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    if (i == l && c == j) {
                        g2d.drawImage(Theme.instance().croix(), x +4, y + 4, largeurCase -5, hauteurCase -5, this);
                        dessineCroix = null;
                        return;
                    }
                    x += largeurCase + 1;
                }
                y += hauteurCase;
                x = bordureGauche;
            }
        }
    }

    public int calcul_dir(Coordonne depart, Coordonne coordonneArriveIA) {
        int deltaX = coordonneArriveIA.getX() - depart.getX();
        int deltaY = coordonneArriveIA.getY() - depart.getY();

        if (deltaX > 0 && Math.abs(deltaX) >= Math.abs(deltaY)) {
            // Déplacement vers le bas
            return 0;
        } else if (deltaX < 0 && Math.abs(deltaX) >= Math.abs(deltaY)) {
            // Déplacement vers le haut
            return 1;
        } else if (deltaY > 0 && Math.abs(deltaY) >= Math.abs(deltaX)) {
            // Déplacement vers la droite
            return 2;
        } else if (deltaY < 0 && Math.abs(deltaY) >= Math.abs(deltaX)) {
            // Déplacement vers la gauche
            return 3;
        } else {
            // Aucune direction valide trouvée
            return -1;
        }
    }


    public void updateBrillanceSelection(int l, int c) {
        repaint();
    }

    private void calculerDimensions() {
        bordureHaut = Math.round(Theme.instance().bordureHaut() * getHeight() / (float) Theme.instance().hauteurPlateau());
        bordureGauche = Math.round(Theme.instance().bordureGauche() * getWidth() / (float) Theme.instance().largeurPlateau());
        hauteurCase = Math.round(Theme.instance().hauteurCase() * getHeight() / (float) Theme.instance().hauteurPlateau());
        largeurCase = Math.round(Theme.instance().largeurCase() * getWidth() / (float) Theme.instance().largeurPlateau());
    }

    public void setDrawFleche(boolean b) {
        drawFleche = b;
        miseAJour();
    }

    private void setDrawFleche1(boolean b) {
        drawFleche = b;
    }

    public boolean getDrawFleche() {
        return drawFleche;
    }

    public void setDestinationsPossibles(ArrayList<Coordonne> destinations) {
        this.destinationsPossibles = destinations;
        miseAJour();
    }

    public Point getPionEnDeplacement() {
        return pionEnDeplacement;
    }

    public void setPionEnDeplacement(Point pionEnDeplacement) {
        this.pionEnDeplacement = pionEnDeplacement;
        miseAJour(); // Ajoutez cette ligne pour actualiser le plateau
    }

    public void setPointSelec(Point point) {
    }

    public void setPionSelec(Pion pion) {
        this.pionSelec = pion;
    }

    public Pion getPionSelec() {
        return pionSelec;
    }

    @Override
    public void miseAJour() {
        repaint();
    }

    public void setImage(int image) {
        if (image == 0) {
            this.image = Theme.instance().noir_inactif();
        } else if (image == 1) {
            this.image = Theme.instance().blanc_inactif();
        } else {
            this.image = Theme.instance().roi();

        }
    }

    public Image getImage() {
        return image;
    }

    public void setSurvole(Pion caseSurvole) {
        miseAJour();
    }

    public void setDessineCroix(Point point) {
        dessineCroix = point;
    }


}