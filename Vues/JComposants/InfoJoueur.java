package Vues.JComposants;

//import Vues.Imager;

import Vues.Imager;
import Vues.JComposants.CPions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoJoueur extends JPanel {

    private final JLabel n;
    public final JPanel p;
    private ImageIcon pawn;
    private final int hgap = 5;
    private boolean reverse;

    public InfoJoueur(boolean reverse) {
        this.reverse = reverse;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(new EmptyBorder(5, !reverse ? 20 : 10, 5, !reverse ? 10 : 20));

        n = new JLabel();
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Arial", Font.BOLD, 16));

        // --
        System.out.println("InfoJoueur : " + reverse);
        pawn = new ImageIcon(Imager.getScaledImage(reverse ? "PN.png" : "PB.png", 25, 30));
        // --
        p = new CPions(reverse);

        add(n);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(p);
    }

    public void setName(String nom) {
        n.setText(nom);
    }

    public void setPions(int nb1, int nb2) {
        // Affichage des pions captures
        ImageIcon fois = new ImageIcon(Imager.getScaledImage("fois.png", 25, 30));
        //System.out.println("setPions : " + nb);
        pawn = new ImageIcon(Imager.getScaledImage(reverse ? "PN.png" : "PB.png", 25, 30));
        p.removeAll();
        p.setLayout(new GridLayout(2, 3, hgap, 0));
        p.add(new JLabel(pawn));
        p.add(new JLabel(fois));
        p.add(new JLabel(String.valueOf(nb1)));
        pawn = null;
        pawn = new ImageIcon(Imager.getScaledImage(reverse ? "PB_barre.png" : "PN_barre.png", 25, 30));
        p.add(new JLabel(pawn));
        p.add(new JLabel(fois));
        p.add(new JLabel(String.valueOf(nb2)));
    }

    public void setBackgroundJoueur(Color c) {
        p.setBackground(c);
    }

}