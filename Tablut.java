
import Modele.Jeu;

import Controlleur.ControlleurMediateur;
import Vues.CollecteurEvenements;
import Vues.InterfaceGraphique;

import javax.swing.*;


public class Tablut{
    public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ignored) {
        }
        Jeu j = new Jeu();
        CollecteurEvenements control = new ControlleurMediateur(j);
        InterfaceGraphique.demarrer(control);
    }
}
 