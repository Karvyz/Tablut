package Modele;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;


public class Niveau implements Serializable, Cloneable {
    private int taille = 9;
    private ConfigurationJeu config;

    public Pion[][] plateau = new Pion[taille][taille];
    public Hashtable<String, Integer> data = new Hashtable<>();

    //On creer le plateau de jeu
    public Niveau(ConfigurationJeu config) {
        this.config = config;
        init_Niveau();
        data.put(this.toString(), 0);
    }

    public Niveau(String fichier) {
        Data_Niveau data_niveau;

        try {
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data_niveau = (Data_Niveau) objectIn.readObject();
            objectIn.close();
            fileIn.close();

            // Mettre à jour l'objet Niveau avec les données chargées
            this.taille = data_niveau.niveau.taille;
            this.plateau = data_niveau.niveau.plateau;

        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + fichier);
        } catch (EOFException | InvalidClassException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + fichier);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Classe Data_Niveau introuvable");
        }
    }


    //On initialise le plateau de jeu
    public void init_Niveau() {
        String[] tab = {"   AAA   ",
                "    A    ",
                "    D    ",
                "A   D   A",
                "AADDRDDAA",
                "A   D   A",
                "    D    ",
                "    A    ",
                "   AAA   "};

//        String[] tab = {"         ",
//                        "   R     ",
//                        "         ",
//                        "         ",
//                        "         ",
//                        "         ",
//                        "         ",
//                        "A        ",
//                        "         "};

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                switch (tab[i].charAt(j)) {
                    case 'R':
                        plateau[i][j] = new Roi(i, j);
                        break;
                    case 'A':
                        plateau[i][j] = new Pion(i, j, TypePion.ATTAQUANT);
                        break;
                    case 'D':
                        plateau[i][j] = new Pion(i, j, TypePion.DEFENSEUR);
                        break;
                    default:
                        plateau[i][j] = null;
                }
            }
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Pion p = plateau[i][j];
                if (p == null) {
                    sb.append("- ");
                } else if (p.getType() == TypePion.ATTAQUANT) {
                    sb.append("N ");
                } else if (p.getType() == TypePion.DEFENSEUR) {
                    sb.append("B ");
                } else if (p.getType() == TypePion.ROI) {
                    sb.append("R ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Niveau other = (Niveau) obj;

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Pion p1 = this.plateau[i][j];
                Pion p2 = other.plateau[i][j];

                if (p1 == null && p2 != null || p1 != null && p2 == null) {
                    return false;
                }
                if (p1 != null && !p1.equals(p2)) {
                    return false;
                }
            }
        }
        return true;
    }


    //On regarde la taille du plateau
    public int getTaille() {
        return taille;
    }

    public Pion getPion(int x, int y) {
        return plateau[x][y];
    }


    //On regarde si la case est vide
    public boolean estVide(int x, int y) {
        return plateau[x][y] == null;
    }

    //On regarde si la case est vide
    public boolean estVide(Pion p) {
        if (plateau[p.getX()][p.getY()] == null) {
            return true;
        }
        return false;
    }

    public int nb_pion_blc() {
        int nb = 0;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Pion p = plateau[i][j];
                if (p != null && (p.getType() == TypePion.DEFENSEUR )) {
                    nb++;
                }
            }
        }
        return nb;
    }

    public int nb_pion_nr() {
        int nb = 0;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Pion p = plateau[i][j];
                if (p != null && p.getType() == TypePion.ATTAQUANT) {
                    nb++;
                }
            }
        }
        return nb;
    }

    public TypePion typePion(int x, int y) {
        return plateau[x][y].getType();
    }

    //On regarde si la case est noire
    public boolean estAttaquant(int x, int y) {
        if (!estCorrect(x, y) || plateau[x][y] == null) {
            return false;
        }
        return plateau[x][y].getType() == TypePion.ATTAQUANT;
    }

    public boolean estAttaquant(Pion p) {
        if (p == null) {
            return false;
        }
        return p.getType() == TypePion.ATTAQUANT;
    }

    //On regarde si la case est blanche
    public boolean estDefenseur(int x, int y) {
        if (!estCorrect(x, y) || plateau[x][y] == null) {
            return false;
        }
        return plateau[x][y].getType() == TypePion.DEFENSEUR;
    }

    //On regarde si la case est blanche
    public boolean estDefenseur(Pion p) {
        if (p == null) {
            return false;
        }
        return p.getType() == TypePion.DEFENSEUR;
    }

    //On regarde si la case est le roi
    public boolean estRoi(int x, int y) {
        if (!estCorrect(x, y) || plateau[x][y] == null) {
            return false;
        }
        return plateau[x][y].getType() == TypePion.ROI;
    }

    public boolean estRoi(Pion p) {
        if (p == null) {
            return false;
        }
        return p.getType() == TypePion.ROI;
    }

    //On place une case vide sur le plateau aux coordonnées x et y (cas ou on capture par exemple)
    public void setVide(int x, int y) {
        plateau[x][y] = null;
    }

    public boolean estCorrect(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 9;
    }


    //On regarde si la case est une forteresse
    public boolean estForteresse(int x, int y) {
        if (x == 0 || x == 8) {
            return y == 0 || y == 8;
        }
        return false;
    }

    //On regarde si la case est un konakis
    public boolean estKonakis(int x, int y) {
        return x == 4 && y == 4;
    }

    public boolean a_boucle() {
        String key = this.toString();
        if (this.data.containsKey(key)) {
            int val = this.data.get(key);
            if (val == 2) // si on a déjà fait 2 fois le même coup
                return true;
            else
                this.data.put(this.toString(), val + 1); // on incrémente le nombre de fois qu'on a fait le même coup
        } else {
            this.data.put(this.toString(), 0); // on ajoute le coup à la map
        }
        return false;
    }


    //int = 0 coup joué , 1 noir on gagné, 2 blanc on gagné
    public int deplace_pion(Coup coup) {
        Pion p = plateau[coup.depart.x][coup.depart.y];
        setVide(coup.depart.x, coup.depart.y);
        plateau[coup.arrivee.x][coup.arrivee.y] = p;

        p.setCoordonne(coup.arrivee);
        if (!PionSeSuicide(p)) {
            AMangerPion(p);
        }

        if (estAttaquant(p)) {
            if (AMangerRoi(coup.arrivee))
                return 1;
        }
        if (estRoi(p)) {
            if (estForteresse(coup.arrivee.x, coup.arrivee.y) || (estContreBord(coup.arrivee.x, coup.arrivee.y) && config.isWinTousCote()))
                return 2;
        }
        if (a_boucle()) {
            return 3;
        }
        if (nb_pion_nr() == 0){
            return 4;
        }
        return 0;

    }

    public boolean defenseurSuicideF(int x, int y) {
        if ((estAttaquant(x + 1, y) && estForteresse(x - 1, y) && config.isPF()) || (estAttaquant(x - 1, y) && estForteresse(x + 1, y) && config.isPF()) || (estAttaquant(x, y - 1) && estForteresse(x, y + 1) && config.isPF()) || (estAttaquant(x, y + 1) && estForteresse(x, y - 1) && config.isPF())) {
            return true;
        }
        return false;
    }

    public boolean defenseurSuicide(int x, int y) {
        if ((estAttaquant(x + 1, y) && estAttaquant(x - 1, y)) || (estAttaquant(x, y + 1) && estAttaquant(x, y - 1))) {
            return true;
        }
        return false;
    }

    public boolean attaquantSuicide(int x, int y) {
        if ((estDefenseur(x + 1, y) && estDefenseur(x - 1, y)) || (estDefenseur(x, y + 1) && estDefenseur(x, y - 1))) {

            return true;
        }
        return false;
    }

    public boolean attaquantSuicideF(int x, int y) {

        if ((estDefenseur(x + 1, y) && estForteresse(x - 1, y) && config.isPF()) || (estDefenseur(x - 1, y) && estForteresse(x + 1, y) && config.isPF()) || (estDefenseur(x, y + 1) && estForteresse(x, y - 1) && config.isPF()) || (estDefenseur(x, y - 1) && estForteresse(x, y + 1) && config.isPF())) {
            return true;
        }
        return false;
    }

    //On regarde si le pion se suicide
    public boolean PionSeSuicide(Pion p) {
        if (config.isSA()) {
            if (estAttaquant(p)) {
                if (estContreDefenseur(p.getX(), p.getY())) {
                    if (attaquantSuicide(p.getX(), p.getY()) || attaquantSuicideF(p.getX(), p.getY()))
                        setVide(p.getX(), p.getY());
                    return true;
                }
            }
            if (estDefenseur(p)) {
                if (estContreAttaquant(p.getX(), p.getY())) {
                    if (defenseurSuicide(p.getX(), p.getY()) || defenseurSuicideF(p.getX(), p.getY()))
                        setVide(p.getX(), p.getY());
                    return true;
                }
            }
        }
        return false;
    }

    //On verifie que le pion est contre un attaquant
    public boolean estContreAttaquant(int x, int y) {
        if (estAttaquant(x + 1, y) || estAttaquant(x - 1, y) || estAttaquant(x, y + 1) || estAttaquant(x, y - 1)) {
            return true;
        }
        return false;
    }

    //On verifie que le pion est contre un defenseur
    public boolean estContreDefenseur(int x, int y) {
        if (estDefenseur(x + 1, y) || estDefenseur(x - 1, y) || estDefenseur(x, y + 1) || estDefenseur(x, y - 1)) {
            return true;
        }
        return false;
    }

    //On regarde si le pion a manger un defenseurs contre une forteresse
    public void attaquantForteresse(int x, int y) {
        if (estAttaquant(x + 1, y)) {
            if (estForteresse(x + 2, y) && config.isPF()) {
                setVide(x + 1, y);
            }
        }
        if (estAttaquant(x - 1, y)) {
            if (estForteresse(x - 2, y) && config.isPF()) {
                setVide(x - 1, y);
            }
        }
        if (estAttaquant(x, y + 1)) {
            if (estForteresse(x, y + 2) && config.isPF()) {
                setVide(x, y + 1);
            }
        }
        if (estAttaquant(x, y - 1)) {
            if (estForteresse(x, y - 2) && config.isPF()) {
                setVide(x, y - 1);
            }
        }
    }

    //On regarde si le pion a manger un ou plusieurs defenseurs avec un attaquant
    public void attaquantDefenseur(int x, int y) {
        if (estAttaquant(x + 1, y)) {
            if (estDefenseur(x + 2, y)) {
                setVide(x + 1, y);
            }
        }
        if (estAttaquant(x - 1, y)) {
            if (estDefenseur(x - 2, y)) {
                setVide(x - 1, y);
            }
        }
        if (estAttaquant(x, y + 1)) {
            if (estDefenseur(x, y + 2)) {
                setVide(x, y + 1);
            }
        }
        if (estAttaquant(x, y - 1)) {
            if (estDefenseur(x, y - 2)) {
                setVide(x, y - 1);
            }
        }
    }

    //On regarde si le pion a manger un attaquant contre une forteresse
    public void defenseurForteresse(int x, int y) {
        if (estDefenseur(x + 1, y)) {
            if (estForteresse(x + 2, y) && config.isPF()) {
                setVide(x + 1, y);
            }
        }
        if (estDefenseur(x - 1, y)) {
            if (estForteresse(x - 2, y) && config.isPF()) {
                setVide(x - 1, y);
            }
        }
        if (estDefenseur(x, y + 1)) {
            if (estForteresse(x, y + 2) && config.isPF()) {
                setVide(x, y + 1);
            }
        }
        if (estDefenseur(x, y - 1)) {
            if (estForteresse(x, y - 2) && config.isPF()) {
                setVide(x, y - 1);
            }
        }
    }

    //On regarde si un defenseur a manger un ou plusieurs attaquants
    public void defenseurAttaquant(int x, int y) {
        if (estDefenseur(x + 1, y)) {
            if (estAttaquant(x + 2, y)) {
                setVide(x + 1, y);
            }
        }
        if (estDefenseur(x - 1, y)) {
            if (estAttaquant(x - 2, y)) {
                setVide(x - 1, y);
            }
        }
        if (estDefenseur(x, y + 1)) {
            if (estAttaquant(x, y + 2)) {
                setVide(x, y + 1);
            }
        }
        if (estDefenseur(x, y - 1)) {
            if (estAttaquant(x, y - 2)) {
                setVide(x, y - 1);
            }
        }
    }

    public void AMangerPion(Pion p) {
        if (estAttaquant(p)) {
            if (estContreDefenseur(p.getX(), p.getY())) {
                defenseurAttaquant(p.getX(), p.getY());
                defenseurForteresse(p.getX(), p.getY());
            }
        } else if (estDefenseur(p)) {
            if (estContreAttaquant(p.getX(), p.getY())) {
                attaquantDefenseur(p.getX(), p.getY());
                attaquantForteresse(p.getX(), p.getY());
            }
        }
    }

    //On regarde si la case est contre le bord
    public boolean estContreBord(int x, int y) {
        if (x == 0 || x == 8) {
            return true;
        } else return y == 0 || y == 8;
    }

    //On regarde si le pion est contre une forteresse
    public boolean estContreForteresse(int x, int y) {
        return x == 0 && y == 1 || x == 0 && y == 7 || x == 1 && y == 0 || x == 1 && y == 8 || x == 7 && y == 0 || x == 7 && y == 8 || x == 8 && y == 1 || x == 8 && y == 7;
    }

    public boolean check_clic_selection_dest(Pion selec, int x, int y) {
        ArrayList<Coordonne> liste_depl = selec.getDeplacement(plateau);
        if (liste_depl.isEmpty()) { //Aucun coup possible pour ce pion
            return false;
        }
        Coordonne arrive = new Coordonne(x, y);
        return liste_depl.contains(arrive);
    }

    public boolean check_clic_selection_pion(Pion p, int JC) {
        if (p != null) {
            ArrayList<Pion> pions_dispo = getPionsDispo(JC);
            return pions_dispo.contains(p);
        }
        //}
        return false;
    }

    //Renvoi la liste d'un seul type de joueur.
    public ArrayList<Pion> getPions(TypePion type) {
        ArrayList<Pion> liste = new ArrayList<>();
        for (int x = 0; x < taille; x++) {
            for (int y = 0; y < taille; y++) {
                Pion courant = plateau[x][y];
                if (courant != null) {
                    if (type == courant.getType()) {
                        liste.add(courant);
                    }
                    if (type == TypePion.DEFENSEUR && courant.getType() == TypePion.ROI)
                        liste.add(courant);
                }
            }
        }
        return liste;
    }

    //Renvoi la liste des pions disponibles au joueur courant
    public ArrayList<Pion> getPionsDispo(int JC) {
        ArrayList<Pion> liste;
        TypePion t = typePion_JC(JC);

        liste = getPions(t);

        if (t == TypePion.DEFENSEUR) {
            TypePion t1 = TypePion.ROI;
            ArrayList<Pion> liste2 = getPions(t1);
            liste.addAll(liste2); // concaténation de list2 à la fin de list1
        }

        return liste;
    }

    public boolean PlusdePion(int JC) {
        return getPionsDispo(JC).isEmpty();
    }

    public TypePion typePion_JC(int JC) {
        switch (JC) {
            case 0:
                return TypePion.ATTAQUANT;
            case 1:
                return TypePion.DEFENSEUR;
            default:
                System.out.println("Joueur courant inconnu");
                return null;
        }
    }


    //On regarde si il est contre le trone
    public int estContreTrone(int x, int y) {
        if (x == 4 && y == 3) {
            return 3;
        } else if (x == 4 && y == 5) {
            return 4;
        } else if (x == 3 && y == 4) {
            return 1;
        } else if (x == 5 && y == 4) {
            return 2;
        }
        return 0;
    }

    //On regarde ou est le roi par rapport a notre Attaquant
    public int estContreRoi(int x, int y) {
        if (estRoi(x + 1, y)) {
            return 1;
        } else if (estRoi(x - 1, y)) {
            return 2;
        } else if (estRoi(x, y + 1)) {
            return 3;
        } else if (estRoi(x, y - 1)) {
            return 4;
        }
        return 0;
    }

    //On regarde si il y a un regicide contre les Forteresses
    public boolean regicideForteresse(int x, int y) {
        if (estContreBord(x, y)) {
            if (estContreForteresse(x, y)) {
                if (estAttaquant(x + 1, y) && estAttaquant(x, y + 1)) {
                    return true;
                } else if (estAttaquant(x + 1, y) && estAttaquant(x, y - 1)) {
                    return true;
                } else if (estAttaquant(x - 1, y) && estAttaquant(x, y + 1)) {
                    return true;
                } else if (estAttaquant(x - 1, y) && estAttaquant(x, y - 1)) {
                    return true;
                }
            }

        }
        return false;
    }

    //On regarde si il y a un regicide contre le trone
    public boolean regicideKonakis(int x, int y, int pos) {
        if (estContreTrone(x, y) == 1 && estAttaquant(x, y - 1) && estAttaquant(x - 1, y) && estAttaquant(x, y - 1)) {
            return true;
        } else if (estContreTrone(x, y) == 2 && estAttaquant(x, y - 1) && estAttaquant(x + 1, y) && estAttaquant(x, y + 1)) {
            return true;
        } else if (estContreTrone(x, y) == 3 && estAttaquant(x, y - 1) && estAttaquant(x - 1, y) && estAttaquant(x + 1, y)) {
            return true;
        } else if (estContreTrone(x, y) == 4 && estAttaquant(x, y + 1) && estAttaquant(x + 1, y) && estAttaquant(x - 1, y)) {
            return true;
        }
        return false;
    }

    //On regarde si il y a un regicide contre un mur
    public boolean regicideMur(int x, int y) {
        if (estContreBord(x, y)) {
            if (estAttaquant(x + 1, y) && estAttaquant(x - 1, y) && estAttaquant(x, y + 1)) {
                return true;
            } else if (estAttaquant(x, y + 1) && estAttaquant(x, y - 1) && estAttaquant(x + 1, y)) {
                return true;
            } else if (estAttaquant(x, y - 1) && estAttaquant(x - 1, y) && estAttaquant(x + 1, y)) {
                return true;
            } else if (estAttaquant(x, y + 1) && estAttaquant(x, y - 1) && estAttaquant(x - 1, y)) {
                return true;
            }
        }
        return false;
    }

    //On regarde si il y a un regicide contre un pion
    public boolean regicidePion(int x, int y) {
        if (estAttaquant(x + 1, y) && estAttaquant(x - 1, y) && estAttaquant(x, y + 1) && estAttaquant(x, y - 1)) {
            return true;
        }
        return false;
    }

    public boolean AMangerRoi(Coordonne dplc) {
        int pos = estContreRoi(dplc.x, dplc.y);
        if (pos == 1) {
            if ((regicideForteresse(dplc.x + 1, dplc.y) && config.isRF()) || (regicideKonakis(dplc.x + 1, dplc.y, pos) && config.isRT()) || (regicideMur(dplc.x + 1, dplc.y) && config.isRM()) || regicidePion(dplc.x + 1, dplc.y)) {
                return true;
            }
        } else if (pos == 2) {
            if ((regicideForteresse(dplc.x - 1, dplc.y) && config.isRF()) || (regicideKonakis(dplc.x - 1, dplc.y, pos) && config.isRT()) || (regicideMur(dplc.x - 1, dplc.y) && config.isRM()) || regicidePion(dplc.x - 1, dplc.y)) {
                return true;
            }
        } else if (pos == 3) {
            if ((regicideForteresse(dplc.x, dplc.y + 1) && config.isRF()) || (regicideKonakis(dplc.x, dplc.y + 1, pos) && config.isRT()) || (regicideMur(dplc.x, dplc.y + 1) && config.isRM()) || regicidePion(dplc.x, dplc.y + 1)) {
                return true;
            }
        } else if (pos == 4) {
            if ((regicideForteresse(dplc.x, dplc.y - 1) && config.isRF()) || (regicideKonakis(dplc.x, dplc.y - 1, pos) && config.isRT()) || (regicideMur(dplc.x, dplc.y - 1) && config.isRM()) || regicidePion(dplc.x, dplc.y - 1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Niveau clone() {
        try {
            Niveau clone = (Niveau) super.clone();
            clone.plateau = new Pion[taille][taille];
            for (int i = 0; i < taille; i++) {
                for (int j = 0; j < taille; j++) {
                    if (plateau[i][j] != null) {
                        clone.plateau[i][j] = plateau[i][j].clone();
                    }
                }
            }
            clone.data = (Hashtable<String, Integer>) this.data.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}






