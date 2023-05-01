package Vues;

import Modele.Jeu;

import javax.swing.*;

public class InterfaceGraphique implements Runnable {
	Jeu j;
	CollecteurEvenements control;
	private JFrame fenetrePrincipale;

	public InterfaceGraphique(Jeu jeu, CollecteurEvenements c) {
		j = jeu;
		control = c;
	}

    public void fermerFenetrePrincipale() {
		if (fenetrePrincipale != null) {
			fenetrePrincipale.dispose();
		}
		else{
			System.out.println("fenetrePrincipale est null");
		}
	}

	public static void demarrer(Jeu j, CollecteurEvenements control, InterfaceGraphique interfaceGraphique) {
		SwingUtilities.invokeLater(interfaceGraphique);
	}

	@Override
	public void run() {
		fenetrePrincipale = new FenetrePlateau(j, control);
	}
}