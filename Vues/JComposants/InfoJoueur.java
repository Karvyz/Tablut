package Vues.JComposants;

import Vues.Imager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoJoueur extends JPanel {

    public final JLabel n;
    public final JPanel p;
    private ImageIcon pawn;
    final private boolean reverse;

    public InfoJoueur(boolean reverse) {
        this.reverse = reverse;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(new EmptyBorder(5, !reverse ? 20 : 10, 5, !reverse ? 10 : 20));

        setMinimumSize(new Dimension(450, -1));
        setPreferredSize(new Dimension(450, -1));
        setMaximumSize(new Dimension(450, -1));

        n = new JLabel();
        n.setForeground(Color.WHITE);
        n.setFont(new Font("Arial", Font.BOLD, 18));
        n.setMinimumSize(new Dimension(150, 44));
        n.setPreferredSize(new Dimension(150, 44));


        pawn = new ImageIcon(Imager.getScaledImage(reverse ? "PN.png" : "PB.png", 60, 60));
        p = new CPions();

        p.setMinimumSize(new Dimension(250, 400));
        p.setPreferredSize(new Dimension(250, 400));
        p.setMaximumSize(new Dimension(250, 400));


        add(n, BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(p, reverse ? BorderLayout.EAST : BorderLayout.WEST);
        add(Box.createVerticalGlue());
    }

    public void setName(String nom) {
        n.setText(nom);
    }

    public void setPions(int nb1, int nb2) {
        setBackgroundJoueur(Color.GRAY);
        // Affichage des pions captures
        pawn = new ImageIcon(Imager.getScaledImage(reverse ? "PN.png" : "PB.png", 60, 60));
        p.removeAll();
        p.setLayout(new GridLayout(2, 2, 20, 20));


        p.add(new JLabel(pawn));

        String nb = String.valueOf(nb1);
        JLabel l = new JLabel(nb);
        l.setFont(new Font("Arial", Font.BOLD, 26));

        p.add(l);

        // Pions mangés
        pawn = new ImageIcon(Imager.getScaledImage(!reverse ? "PN_barre.png" : "PB_barre.png", 40, 40));
        p.add(new JLabel(pawn));

        String nb3 = String.valueOf(nb2);
        JLabel l3 = new JLabel(nb3);
        l3.setFont(new Font("Arial", Font.BOLD, 24));
        p.add(l3);

        p.revalidate(); // Actualiser le contenu du JPanel p
    }

    public void setBackgroundJoueur(Color c) {
        p.setBackground(c);
    }

}