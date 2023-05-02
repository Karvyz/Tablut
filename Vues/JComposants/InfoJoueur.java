package Vues.JComposants;

//import Vues.Imager;

import Vues.Imager;
import Vues.JComposants.CPions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoJoueur extends JPanel {

    private final JLabel n;
    private final JPanel p;
    private final ImageIcon pawn;
    private final int hgap = 5;

    public InfoJoueur(boolean reverse) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //boolean reverse = num == 1; // 0 = blanc, 1 = noir, 2 = roi

        setBorder(new EmptyBorder(5, !reverse ? 20 : 10, 5, !reverse ? 10 : 20));

        n = new JLabel();
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Arial", Font.BOLD, 16));

        // -- TODO : ajouter les images des pions
        pawn = new ImageIcon(Imager.getScaledImage(reverse ? "assets/PN.png" : "assets/PB.png", 25, 30));
        // --
        p = new CPions(reverse);

        add(!reverse ? n : p);
        add(Box.createRigidArea(new Dimension(14, 0)));
        add(!reverse ? p : n);
    }

    public void setName(String nom) {
        n.setText(nom);
    }

    public void setPions(int nb) {
        // Affichage des pions captures
        p.removeAll();
        p.setLayout(new GridLayout(1, nb, hgap, 0));
        for (int i = 0; i < nb; i ++) p.add(new JLabel(pawn));
    }

}