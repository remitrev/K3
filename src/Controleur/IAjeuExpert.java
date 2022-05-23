package Controleur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Modeles.*;

public class IAjeuExpert implements IAjeu {
    int horizon;
    PiecePyramide avoler;

    public IAjeuExpert(int horizon) {
        this.horizon = horizon;
    }

    public Coup IACoup(Partie p, int numeroJoueur) {
        MinMax mm = new MinMax(numeroJoueur);
        mm.meilleurConfigJ(p, horizon, true);
        avoler = mm.PieceAVoler();
        return mm.getparfait();
    }

    public PiecePyramide PieceAVoler(ArrayList<PiecePyramide> arr) {
        if (avoler == null || !arr.contains(avoler)) {
            Random r = new Random();
            int index = r.nextInt(arr.size());
            // System.out.println("L'IA vole la piece " + arr.get(index).toString());
            return arr.get(index);
        }
        // System.out.println("L'IA vole la piece " + avoler.toString());
        return avoler;
    }
    // voler piece: mode agressif: Voler les pieces importantes de l'adversaire
    // mode défensif: Voler les pieces permettant un maximum de coups au joueur
    // courant
}