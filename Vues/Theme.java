package Vues;

import Global.Configuration;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

import static Global.Configuration.chargerFichier;

public class Theme {
    private static Theme instance;
    private int hauteurPlateau, largeurPlateau;
    private int bordureHaut, bordureGauche, bordureBas, bordureDroite;
    private int hauteurCase, largeurCase;
    private Image plateau;
    private Image blanc_inactif;
    private Image blanc_selectionne;
    private Image noir_inactif;
    private Image noir_selectionne;

    private Image point_interrogation, err;
    private Image roi, forteresse, konakis, roi_selectionne;

    private Image fleche_bas, fleche_droite, fleche_gauche, fleche_haut;

    private Theme() {
        charger();
    }

    public static Theme instance() {
        if (instance == null) {
            instance = new Theme();
        }
        return instance;
    }

    private void chargerDimensions(String theme) {
        Properties p = new Properties();

        InputStream in = chargerFichier(theme + "dimensions.cfg");

        try {
            p.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException(new IOException("Impossible de charger le fichier dimensions.cfg : ", e));
        }
        hauteurPlateau = Integer.parseInt(p.getProperty("Hauteur_plateau"));
        largeurPlateau = Integer.parseInt(p.getProperty("Largeur_plateau"));
        bordureHaut = Integer.parseInt(p.getProperty("Bordure_haut"));
        bordureGauche = Integer.parseInt(p.getProperty("Bordure_gauche"));
        bordureBas = Integer.parseInt(p.getProperty("Bordure_bas"));
        bordureDroite = Integer.parseInt(p.getProperty("Bordure_droite"));
        hauteurCase = (hauteurPlateau - bordureHaut - bordureBas) / 9;
        largeurCase = (largeurPlateau - bordureGauche - bordureDroite) / 9;
    }

    void charger() {
        String theme = "assets/";
        chargerDimensions(theme);

        plateau = Imager.getImageBuffer(theme + "Plateau3.png");

        blanc_inactif = Imager.getImageBuffer(theme + "PB.png");
        blanc_selectionne = Imager.getImageBuffer(theme + "PB_selectionne.png");
        noir_inactif = Imager.getImageBuffer(theme + "PN.png");
        noir_selectionne = Imager.getImageBuffer(theme + "PN_selectionne.png");
        roi = Imager.getImageBuffer(theme + "Roi.png");
        roi_selectionne = Imager.getImageBuffer(theme + "Roi_selectionne.png");
        forteresse = Imager.getImageBuffer(theme + "Forteresse.png");
        konakis = Imager.getImageBuffer(theme + "Konakis2.png");
        point_interrogation = Imager.getImageBuffer(theme + "Point-d'interrogation.png");
        err = Imager.getImageBuffer(theme + "err.png");
        fleche_bas = Imager.getImageBuffer(theme + "fleche_bas.png");
        fleche_droite = Imager.getImageBuffer(theme + "fleche_droite.png");
        fleche_gauche = Imager.getImageBuffer(theme + "fleche_gauche.png");
        fleche_haut = Imager.getImageBuffer(theme + "fleche_haut.png");
    }

    public int hauteurPlateau() {
        return hauteurPlateau;
    }

    public int largeurPlateau() {
        return largeurPlateau;
    }

    public int bordureHaut() {
        return bordureHaut;
    }

    public int bordureGauche() {
        return bordureGauche;
    }

    public int bordureBas() {
        return bordureBas;
    }

    public int bordureDroite() {
        return bordureDroite;
    }

    public int hauteurCase() {
        return hauteurCase;
    }

    public int largeurCase() {
        return largeurCase;
    }

    public Image plateau() {
        return plateau;
    }

    public Image blanc_inactif() {
        return blanc_inactif;
    }

    public Image blanc_selectionne() {
        return blanc_selectionne;
    }

    public Image noir_inactif() {
        return noir_inactif;
    }

    public Image noir_selectionne() {
        return noir_selectionne;
    }

    public Image roi() {
        return roi;
    }

    public Image roi_selectionne() {
        return roi_selectionne;
    }

    public Image forteresse() {
        return forteresse;
    }

    public Image konakis() {
        return konakis;
    }

    public Image pointInterrogation() {
        return point_interrogation;
    }

    public Image fleche_bas() {
        return fleche_bas;
    }

    public Image fleche_droite() {
        return fleche_droite;
    }

    public Image fleche_gauche() {
        return fleche_gauche;
    }

    public Image fleche_haut() {
        return fleche_haut;
    }


    public Image err() {
        return err;
    }
}
