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

    private Image noir_inactif;

    private Image point_interrogation, err;

    private Image roi;

    private Image croix;

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

        noir_inactif = Imager.getImageBuffer(theme + "PN.png");
        roi = Imager.getImageBuffer(theme + "Roi.png");
        //point_interrogation = Imager.getImageBuffer(theme + "Capture1.png");
        point_interrogation = Imager.getImageBuffer(theme + "Point.png");
        err = Imager.getImageBuffer(theme + "err.png");
        fleche_bas = Imager.getImageBuffer(theme + "fleche_bas.png");
        fleche_droite = Imager.getImageBuffer(theme + "fleche_droite.png");
        fleche_gauche = Imager.getImageBuffer(theme + "fleche_gauche.png");
        fleche_haut = Imager.getImageBuffer(theme + "fleche_haut.png");
        croix = Imager.getImageBuffer(theme + "croix.png");
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



    public Image noir_inactif() {
        return noir_inactif;
    }


    public Image roi() {
        return roi;
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

    public Image croix() {return croix;}


    public Image err() {
        return err;
    }
}
