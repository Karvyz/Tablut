package Vues.JComposants;

//import Vues.Imager;

import Vues.Imager;
import Vues.JComposants.CPions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static Vues.Imager.getImageIcon;

public class InfoJoueur extends JPanel {

    public final JLabel n;
    public final JPanel p;
    private ImageIcon pawn;
    private final int hgap = 20;
    private boolean reverse;

    public InfoJoueur(boolean reverse) {
        this.reverse = reverse;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(new EmptyBorder(5, !reverse ? 20 : 10, 5, !reverse ? 10 : 20));

        n = new JLabel();
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Arial", Font.BOLD, 18));

        // --
        //System.out.println("InfoJoueur : " + reverse);
        pawn = new ImageIcon(Imager.getScaledImage(reverse ? "PN.png" : "PB.png", 60, 60));
        // --
        p = new CPions(reverse);
        //p.setMaximumSize(new Dimension(100, -1));
        //p.setPreferredSize(new Dimension(100, -1));

        add(n, BorderLayout.CENTER);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(Box.createVerticalGlue());
        add(p);
        add(Box.createVerticalGlue());
    }

    public void setName(String nom) {
        n.setText(nom);
    }

    public void setPions(int nb1) {
        setBackgroundJoueur(Color.GRAY);
        // Affichage des pions captures
        //ImageIcon fois = new ImageIcon(Imager.getScaledImage("fois.png", 50, 60));
        //System.out.println("setPions : " + nb);
        pawn = new ImageIcon(Imager.getScaledImage(reverse ? "PN.png" : "PB.png", 60, 60));
        p.removeAll();
        //p.setLayout(new GridLayout(1, 2, 0, 0));

        p.setLayout(new FlowLayout(FlowLayout.CENTER, hgap, 0));

        p.add(new JLabel(pawn));

        String nb = String.valueOf(nb1);
        JLabel l = new JLabel(nb);
        l.setFont(new Font("Arial", Font.BOLD, 26));

        p.add(l);
        p.revalidate(); // Actualiser le contenu du JPanel p
    }

    public void setBackgroundJoueur(Color c) {
        p.setBackground(c);
    }

}