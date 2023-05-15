package Vues.JComposants;

import Modele.*;
import Patterns.Observateur;
import Vues.AdaptateurSouris;
import Vues.AdaptateurSouris2;
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
    private int brillanceX = -1;
    private int brillanceY = -1;

    private ArrayList<Coordonne> destinationsPossibles = new ArrayList<>();
    private Point pointSelec;
    private Image image;

    private boolean drawFleche = true;
    private boolean remettreFleche = false;

    private Pion survole;


    public CPlateau(CollecteurEvenements c) {
        controleur = c;
        //AdaptateurSouris2 adaptateurSouris = new AdaptateurSouris2(c, this);
        //System.out.println("HERE"+controleur.jeu());
        AdaptateurSouris adaptateurSouris = new AdaptateurSouris(c, this);
        addMouseListener(adaptateurSouris);
        addMouseMotionListener(adaptateurSouris);
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
                if (getPionSelec() != null && l == getPionSelec().getX() && c == getPionSelec().getY()) { //Ici on efface le pion selec
                    x += largeurCase;
                    if (c % 2 == 0)
                        x++;
                    continue;
                }

                if (n.estAttaquant(l, c)) {
                    g.drawImage(Theme.instance().noir_inactif(), x +(largeurCase/2)-25, y + (hauteurCase/2)-25, 50, 50, this);
                } else if (n.estRoi(l, c)) {
                    g.drawImage(Theme.instance().roi(), x +(largeurCase/2)-30, y + (hauteurCase/2)-30, 60, 60, this);
                } else if (n.estDefenseur(l, c)) {
                    g.drawImage(Theme.instance().blanc_inactif(), x +(largeurCase/2)-25, y + (hauteurCase/2)-25, 50, 50, this);
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

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    if (i == l && c == j) {
                        //TODO mettre image des points
                        g2d.drawImage(getImage(), x + 4, y + 4, largeurCase - 4, hauteurCase - 4, this);
                    }
                    x += largeurCase + 1;
                }
                y += hauteurCase;
                x = bordureGauche;
            }
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
                        setDrawFleche1(false); //pour pas remettre tout a jour
                    }
                    if (i == l && c == j) {
                        //TODO mettre image des points
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
            //drawSurbrillance(g2d);

            //System.out.println("here");
            if ((controleur.jeu().getCoordooneDepartIA() != null)){//TODO delete
                //System.out.println(controleur.jeu().getCoordooneDepartIA() +","+ getDrawFleche());
            }

            if ((controleur.jeu().getCoordooneDepartIA() != null && getDrawFleche() == true)) {
                Coordonne depart = controleur.jeu().getCoordooneDepartIA();
                if (depart == null){return;}
                int l = depart.getX();
                int c = depart.getY();
                if (controleur.jeu().n.estKonakis(l,c)){
                    //on ne dessine pas la fleche sur le konaki. on comprend que le roi est parti de son trone
                }else{
                    switch (calcul_dir(depart, controleur.jeu().getCoordooneArriveIA())) {
                        case 0:
                            // Déplacement vers le bas
                            g2d.drawImage(Theme.instance().fleche_bas(), c * hauteurCase, l * largeurCase, largeurCase - 5, hauteurCase - 5, this);
                            break;
                        case 1:
                            //vers le haut
                            g2d.drawImage(Theme.instance().fleche_haut(), c * hauteurCase, l * largeurCase, largeurCase - 5, hauteurCase - 5, this);
                            break;
                        case 2:
                            // Déplacement vers la droite
                            g2d.drawImage(Theme.instance().fleche_droite(), c * hauteurCase, l * largeurCase, largeurCase - 5, hauteurCase - 5, this);
                            break;
                        case 3:
                            //vers la gauche
                            g2d.drawImage(Theme.instance().fleche_gauche(), c * hauteurCase, l * largeurCase, largeurCase - 5, hauteurCase - 5, this);
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

    public void drawSurbrillance(Graphics2D g) {
        Jeu J = controleur.jeu();
        Niveau n = J.getNiveau();
        if (pionSelec != null) {
            int x = pionSelec.getX();
            int y = pionSelec.getY();

            if (n.estRoi(x, y)) {
                // -- Dessin du roi avec arrière-plan
                g.drawImage(Theme.instance().roi_selectionne(), (largeurCase + 1) * y + 1, (hauteurCase + 1) * x + 1, largeurCase - 5, hauteurCase - 4, this);
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
        this.brillanceX = l;
        this.brillanceY = c;
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
        this.pointSelec = point;
    }

    public void setPionSelec(Pion pion) {
        this.pionSelec = pion;
    }

    public Pion getPionSelec() {
        return pionSelec;
    }

    public Point getPointSelec() {
        return pointSelec;
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
        survole = caseSurvole;
    }

    public Pion getSurvole(){
        return survole;
    }
}