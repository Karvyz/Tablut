package Vues;

import javax.swing.*;

import Listerner.ClickListener;

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
		plateau.addMouseListener(new AdaptateurSouris(plateau, control)); //pour les clics
		plateau.addMouseMotionListener(new AdaptateurSouris(plateau, control)); //pour les glissements
		frame.add(plateau);
		Box barre;
		barre = Box.createVerticalBox();
		barre.add(Box.createGlue());
		for (int i=0; i<2; i++) {
			barre.add(new JLabel("Joueur " + (i+1)));
			JToggleButton but = new JToggleButton("IA");
			but.addActionListener(new AdaptateurJoueur(control, but, i));
			barre.add(but);
		}
		barre.add(Box.createGlue());
		frame.add(barre, BorderLayout.LINE_END);

		JButton btnAnnule = new JButton("Annule");// Creer le bouton Annule
		btnAnnule.setName("btnAnnule"); //Important de définir le nom pour le retrouvé dans ClickListener
        ClickListener clickListener3 = new ClickListener(0, btnAnnule, control, j); //ajoute l'action en cas de clique
		btnAnnule.addActionListener(clickListener3);
        add(btnAnnule, BorderLayout.SOUTH); //Ajoute le bouton a la fenetre
        barre.add(btnAnnule); // Place le bouton

		JButton btnRefaire = new JButton("Refaire");// Creer le bouton Refaire
		btnRefaire.setName("btnRefaire"); //Important de définir le nom pour le retrouvé dans ClickListener
        ClickListener clickListener4 = new ClickListener(0, btnRefaire, control, j); //ajoute l'action en cas de clique
		btnRefaire.addActionListener(clickListener4);
        add(btnRefaire, BorderLayout.SOUTH); //Ajoute le bouton a la fenetre
        barre.add(btnRefaire); // Place le bouton
        
		Timer chrono = new Timer( 100, new AdaptateurTemps(control));
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

