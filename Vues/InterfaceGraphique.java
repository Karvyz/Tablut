package Vues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InterfaceGraphique implements Runnable {
    //Jeu j;
    CollecteurEvenements control;
    Vues vues;
    JFrame frame;
    GraphicsEnvironment env;
    GraphicsDevice device;
    boolean maximized;

    InterfaceGraphique(CollecteurEvenements c) {
        control = c;
    }

    public static void demarrer(CollecteurEvenements control) {
        //SwingUtilities.invokeLater(new InterfaceGraphique(j, control));
        SwingUtilities.invokeLater(new InterfaceGraphique(control));
    }

    @Override
    public void run() {
        // Nouvelle fenêtre
        frame = new JFrame("Tablut");

        // On récupère des informations sur l'écran
        env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = env.getDefaultScreenDevice();
        DisplayMode dm = device.getDisplayMode();

        // TODO: Gérer la taille de la fenêtre dans default.cfg
        int width = dm.getWidth() / 4 * 3;
        int height = dm.getHeight() / 4 * 3;

        // On fixe le layout du conteneur contenant les différentes vues de la fenêtre
        frame.getContentPane().setLayout(new CardLayout());

        // Ajout de nos vues dans la fenêtre
        vues = new Vues(frame);

        ajouterVue(Vues.DEMARRAGE);
        ajouterVue(Vues.MENU_SAISIES);
        ajouterVue(Vues.MENU_PRINCIPAL);
        ajouterVue(Vues.MENU_PARTIES);
        ajouterVue(Vues.JEU);

        control.fixerMediateurVues(vues);

        // Ajout du timer
        Timer time = new Timer(16, new AdaptateurTemps(control));
        time.start();

        // Un clic sur le bouton de fermeture clos l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // On fixe la taille et centre la fenêtre
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public void toggleFullscreen() {
        if (maximized) {
            device.setFullScreenWindow(null);
            maximized = false;
        } else {
            device.setFullScreenWindow(frame);
            maximized = true;
        }
    }

    void ajouterVue(String nom) {
        JPanel vue;

        switch (nom) {
            case Vues.DEMARRAGE:
                vue = new VueDemarrage();
                break;
            case Vues.MENU_PRINCIPAL:
                vue = new VueMenuPrincipal(control);
                break;
            case Vues.MENU_SAISIES:
                vue = new VueMenuSaisies(control);
                //vue.resetTexte();
                break;
            case Vues.JEU:
                vue = new VueJeu(control);
                vues.fixerVueJeu((VueJeu) vue);
                //vue = new PlateauGraphique();
                break;
            case Vues.MENU_PARTIES:
                vue = new VueMenuParties2(control);
                vues.fixerVueMenuParties((VueMenuParties2) vue);
                break;
            default:
                throw new IllegalArgumentException("Nom de vue incorrect : " + nom);
        }
        frame.getContentPane().add(vue, nom);
    }
}