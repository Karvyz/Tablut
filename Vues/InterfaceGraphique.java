package Vues;

import Modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements Runnable {
	Jeu j;
	CollecteurEvenements control;
	int size;

	InterfaceGraphique(Jeu jeu, CollecteurEvenements c) {
		j = jeu;
		control = c;
		size = 3;
	}

	public static void demarrer(Jeu j, CollecteurEvenements control) {
		SwingUtilities.invokeLater(new InterfaceGraphique(j, control));
	}

	@Override
	public void run() {
		JFrame frame = new JFrame("Ma fenetre a moi");
		NiveauGraphique niv = new NiveauGraphique(j);
		niv.addMouseListener(new AdaptateurSouris(control));
		frame.add(niv);
		// niv.addMouseListener(new AdaptateurSouris(niv, control));
		// frame.add(niv);
		// Box barre = Box.createVerticalBox();
		// barre.add(Box.createGlue());
		// barre.add(new JLabel("Taille"));
		//  JTextField userSize = new JTextField();
		// userSize.setMaximumSize(new Dimension(
		// 		userSize.getMaximumSize().width, userSize.getMinimumSize().height));
		// userSize.addActionListener(new AdaptateurTaille(control, userSize));
		// barre.add(userSize);
		// barre.add(Box.createGlue());
		// for (int i=0; i<2; i++) {
		// 	barre.add(new JLabel("Joueur " + (i+1)));
		// 	JToggleButton but = new JToggleButton("IA");
		// 	but.addActionListener(new AdaptateurJoueur(control, but, i));
		// 	barre.add(but);
		// }
		// barre.add(Box.createGlue());
		// frame.add(barre, BorderLayout.LINE_END);

		Timer chrono = new Timer( 200, new AdaptateurTemps(control));
		chrono.start();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}