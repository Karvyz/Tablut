package Vues;

import Patterns.Observateur;
import Vues.JComposants.InfoJoueur;
import Vues.JComposants.CPlateau;
import Vues.JComposants.TexteJeu;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class VueNiveau extends JPanel implements Observateur {
    CollecteurEvenements controleur;
    CPlateau plateau;
    MatteBorder top, bottom;
    InfoJoueur j1, j2;
    TexteJeu texteJeu;
    VueJeu parent;

    VueNiveau(CollecteurEvenements c, VueJeu p, InfoJoueur j1, InfoJoueur j2, TexteJeu texteJeu) {
        controleur = c;
        parent = p;
        plateau = new CPlateau(c);
        this.j1 = j1;
        this.j2 = j2;
        this.texteJeu = texteJeu;

        c.jeu().ajouteObservateur(plateau);

        setBackground(new Color(255, 255, 255));
//        setBorder(BorderFactory.createEmptyBorder(140, 100, 140, 100));
//        setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
        setOpaque(false);
        setLayout(new GridLayout(1, 3, 10, 0));

        JPanel plateauPanel = new JPanel(new GridBagLayout());
        plateauPanel.setOpaque(false);
        plateau.setMinimumSize(new Dimension((400), 400));
        plateauPanel.add(plateau);

        // --
        add(plateauPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int size = Math.min(plateauPanel.getWidth(), plateauPanel.getHeight());
                plateau.setPreferredSize(new Dimension(size, size));
                plateauPanel.revalidate();
            }
        });

        // -- Add Listener
        //plateau.addMouseListener(new AdaptateurSouris2(c, plateau)); //TODO a voir si c'est possible

        top = BorderFactory.createMatteBorder(10, 0, 0, 0, Color.WHITE);
        bottom = BorderFactory.createMatteBorder(0, 0, 10, 0, Color.BLACK);
    }

    @Override
    public void miseAJour() {
        if (controleur.jeu().getCoupJoue()){
            parent.ModifBoutonUndo();
            parent.ModifBoutonRedo();
            controleur.jeu().setCoupJoue(false);
        }

        if (controleur.jeu().partieTerminee() && controleur.jeu().getConsulter() == false) {
            parent.showEnd();
            return;
        }


        j1.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur1())[0], controleur.jeu().info_pion(controleur.jeu().getJoueur1())[1]);
        j2.setPions(controleur.jeu().info_pion(controleur.jeu().getJoueur2())[0], controleur.jeu().info_pion(controleur.jeu().getJoueur2())[1]);
        texteJeu.set(controleur.jeu().info_pion(controleur.jeu().getJoueur2())[1], controleur.jeu().info_pion(controleur.jeu().getJoueur1())[1]);

        //texteJeu.setOpaque(true);

        Border border = BorderFactory.createLineBorder(Color.GREEN, 8);
        //new Color(9, 106, 9)

        Border border2 = BorderFactory.createLineBorder(Color.WHITE, 8);
        // Modif couleur arrière plan du joueur courant
        if (controleur.jeu().getJoueurCourant() == controleur.jeu().getJoueur1()) {
            // Créez une instance de bordure avec un style spécifique
            j2.p.setBorder(null);
            j2.p.setBorder(border2);
            j2.n.setForeground(Color.WHITE);
            j1.p.setBorder(border);
            j1.n.setForeground(Color.GREEN);
        } else {
            j1.p.setBorder(null);
            j1.p.setBorder(border2);
            j1.n.setForeground(Color.WHITE);
            j2.p.setBorder(border);
            j2.n.setForeground(Color.GREEN);
        }
    }
}
