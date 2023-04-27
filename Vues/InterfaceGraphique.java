package Vues;

import Modele.Jeu;

import javax.swing.*;

public class InterfaceGraphique implements Runnable {
	Jeu j;
	CollecteurEvenements control;

	InterfaceGraphique(Jeu jeu, CollecteurEvenements c) {
		j = jeu;
		control = c;
	}

	public static void demarrer(Jeu j, CollecteurEvenements control) {
		SwingUtilities.invokeLater(new InterfaceGraphique(j, control));
	}

	@Override
	public void run() {
		new FenetrePlateau(j, control);
		//fenetrePlateau.setVisible(true);
	}
}