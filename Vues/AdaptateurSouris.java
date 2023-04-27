
package Vues;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
	//NiveauGraphique niv;
	PlateauGraphique plateau;
	CollecteurEvenements control;

	AdaptateurSouris(PlateauGraphique p, CollecteurEvenements c) {
		control = c;
		plateau = p;
	}

	// AdaptateurSouris(NiveauGraphique n, CollecteurEvenements c) {
	// 	niv = n;
	// 	control = c;
	// }

	@Override
	public void mousePressed(MouseEvent e) {
		int l = e.getY() / plateau.hauteurCase;
		int c = e.getX() / plateau.largeurCase;
		System.out.println("Clic en (" + l +"," + c +")");
		control.clicSouris(l, c);
	}
}
