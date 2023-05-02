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
		this.setTitle("Ma fenetre a moi");

		PlateauGraphique plateau = new PlateauGraphique(j);
		plateau.addMouseListener(new AdaptateurSouris(plateau, control)); //pour les clics
		plateau.addMouseMotionListener(new AdaptateurSouris(plateau, control)); //pour les glissements
		this.add(plateau);
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
		this.add(barre, BorderLayout.LINE_END);

		JButton btnAnnule = new JButton("Annule");// Creer le bouton Annule
		btnAnnule.setName("btnAnnule"); //Important de définir le nom pour le retrouvé dans ClickListener
        ClickListener clickListener3 = new ClickListener(0, btnAnnule, control, j); //ajoute l'action en cas de clique
		btnAnnule.addActionListener(clickListener3);
        barre.add(btnAnnule); // Place le bouton

		JButton btnRefaire = new JButton("Refaire");// Creer le bouton Refaire
		btnRefaire.setName("btnRefaire"); //Important de définir le nom pour le retrouvé dans ClickListener
        ClickListener clickListener4 = new ClickListener(0, btnRefaire, control, j); //ajoute l'action en cas de clique
		btnRefaire.addActionListener(clickListener4);
        barre.add(btnRefaire); // Place le bouton


		JButton btnSauvegarde = new JButton("Sauve");// Creer le bouton Sauvegarde
		btnSauvegarde.setName("btnSauvegarde"); //Important de définir le nom pour le retrouvé dans ClickListener
        ClickListener clickListener5 = new ClickListener(0, btnSauvegarde, control, j); //ajoute l'action en cas de clique
		btnSauvegarde.addActionListener(clickListener5);
        barre.add(btnSauvegarde); // Place le bouton

		JButton btnCharger = new JButton("load");// Creer le bouton Charger
		btnCharger.setName("btnCharger"); //Important de définir le nom pour le retrouvé dans ClickListener
        ClickListener clickListener6 = new ClickListener(0, btnCharger, control, j); //ajoute l'action en cas de clique
		btnCharger.addActionListener(clickListener6);
        barre.add(btnCharger); // Place le bouton
        
		Timer chrono = new Timer( 100, new AdaptateurTemps(control));
		chrono.start();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
		//frame.setAlwaysOnTop(true); // Ajoutez cette ligne pour bloquer la fenêtre au premier plan
    }

    @Override
	public void miseAJour() {
		repaint();
	}

	public void fermer() {
        dispose();
    }
}

