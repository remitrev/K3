package Modeles;

import java.util.ArrayList;

import Controleur.*;

public class IAActeur extends Acteur {
    IAjeu jeu;
    IApioche pioche;
    int numerojoueur;

    public IAActeur(String nom, int diff, int numerojoueur) {
        super(nom);
        this.numerojoueur = numerojoueur;
        switch (diff) { // Construit nos IAs suivant la diffciulté choisis
            case 0:
                pioche = new IApiocheAlea();
                jeu = new IAjeuAlea();
                break;
            case 1:
                pioche = new IApiocheExpert();
                jeu = new IAjeuExpert(5);
                break;
            case 2:
                pioche = new IApiocheExpert();
                jeu = new IAjeuExpert(8); // definir la pronfondeur de recherche pour les prochains coups
                break;
            default:
                break;
        }
    }

    public int tempsReflexion() {
        int r = Aleatoire.genInt(100, 400);
        return r;
    }

    public ArrayList<PiecePyramide> phase1(Partie encours) {
        return pioche.CreerPioche(encours, numerojoueur);
    }

    @Override
    public Coup jouer(ArrayList<Coup> arr, Partie encours) {
        return jeu.IACoup(encours, numerojoueur);
    }

    @Override
    public PiecePyramide choixVol(ArrayList<PiecePyramide> arr) {
        return jeu.PieceAVoler(arr);
    }

}