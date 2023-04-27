package Listerner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Jeu;
import Modele.Pion;
import Patterns.Observateur;
import Vues.CollecteurEvenements;
import Vues.FenetrePlateau;

public class OuvrirFenetreBActionListener implements ActionListener {
    Jeu jeu;
    CollecteurEvenements control;

    public OuvrirFenetreBActionListener(Jeu jeu, CollecteurEvenements control) {
        this.jeu = jeu;
        this.control = control;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Créez et affichez une nouvelle instance de FenetreB
        new FenetrePlateau(jeu, control);
    }
}
