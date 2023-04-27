package Vues;

import javax.swing.*;

import Listerner.OuvrirFenetreBActionListener;

import java.awt.BorderLayout;

import Modele.Jeu;

import Patterns.Observateur;


public class FenetrePlateau extends JFrame implements Observateur{
    Jeu j;
    CollecteurEvenements control;

    public FenetrePlateau(Jeu jeu, CollecteurEvenements control) {
        this.j = jeu;
        this.control = control;

		JFrame frame = new JFrame("Ma fenetre a moi");
		PlateauGraphique plateau = new PlateauGraphique(j);
		plateau.addMouseListener(new AdaptateurSouris(plateau, control));
		frame.add(plateau);
		Box barre;
		barre = Box.createVerticalBox();
		barre.add(Box.createGlue());
		for (int i=0; i<2; i++) {
			barre.add(new JLabel("Joueur " + (i+1)));
			JToggleButton but = new JToggleButton("IA");
			barre.add(but);
		}
		barre.add(Box.createGlue());
		frame.add(barre, BorderLayout.LINE_END);

        JButton btnOuvrirFenetreB = new JButton("Fen B");
        btnOuvrirFenetreB.addActionListener(new OuvrirFenetreBActionListener(jeu, control));
        add(btnOuvrirFenetreB, BorderLayout.SOUTH);
        barre.add(btnOuvrirFenetreB);
        
		Timer chrono = new Timer( 200, new AdaptateurTemps(control));
		chrono.start();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true); // Ajoutez cette ligne pour bloquer la fenêtre au premier plan
    }

    @Override
	public void miseAJour() {
		repaint();
	}
}

