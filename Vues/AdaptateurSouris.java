
package Vues;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {
	//NiveauGraphique niv;
	CollecteurEvenements control;

	AdaptateurSouris(CollecteurEvenements c) {
		control = c;
	}

	// AdaptateurSouris(NiveauGraphique n, CollecteurEvenements c) {
	// 	niv = n;
	// 	control = c;
	// }

	@Override
	public void mousePressed(MouseEvent e) {
		int l = e.getY();
		int c = e.getX() ;
		control.clicSouris(l, c);
	}
}
