/*
 * Morpion pédagogique

 * Copyright (C) 2016 Guillaume Huard

*/

package Vues;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurJoueur implements ActionListener {
	CollecteurEvenements control;
	JToggleButton toggle;
	int num;

	AdaptateurJoueur(CollecteurEvenements c, JToggleButton b, int n) {
		control = c;
		toggle = b;
		num = n;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (toggle.isSelected())
			control.changeJoueur(num, 3); //DIFFICILE
		else
			control.changeJoueur(num, 0);
	}
}
