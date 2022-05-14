package Controleur;

import java.util.ArrayList;
import java.util.Iterator;

import Modeles.*;

public class MinMax {
    int numerojoueur;
    Coup parfait;
    Partie copiepartie;

    MinMax(int numerojoueur) { // on créer une IA associé à un joueur
        this.numerojoueur = numerojoueur;
    }

    public int eval(Partie p) { // Fonction d'évalutaion de la configuration difference entre notre
                                // nombre de coups jouables et celui de l'adversaire

        int nbcoupsia, nbcoupsadv;
        if (numerojoueur == 0) {
            nbcoupsia = p.coupsJouables(p.joueur1()).size();
            nbcoupsadv = p.coupsJouables(p.joueur2()).size();
        } else {
            nbcoupsia = p.coupsJouables(p.joueur2()).size();
            nbcoupsadv = p.coupsJouables(p.joueur1()).size();
        }
        if (nbcoupsia == 0) {
            return -10000;
        }
        if (nbcoupsadv == 0) {
            return 10000;
        }
        return (nbcoupsia - nbcoupsadv);
    }

    public int meilleurConfigJ(Partie p, int horizon, boolean flag) {
        // System.out.println("On est a l'horizon :" + horizon);
        int joueurcourant = numerojoueur;
        if ((horizon == 0) || (p.estPartieFinie())) {
            // System.out.println("On est aller a l horizon :" + horizon);
            // System.out.println("Je suis une feuille");
            if (horizon == 5) {
                System.out.println("On est sur une fin de partie");
            }
            return eval(p);
        }
        Acteur JoueurCourant;
        if (joueurcourant == 0) {
            JoueurCourant = p.joueur1();
        } else {
            JoueurCourant = p.joueur2();
        }
        ArrayList<Coup> lc = p.coupsJouables(JoueurCourant);
        if (lc.size() == 1) {
            Coup c = lc.get(0);
            p.jouer(c, joueurcourant);
            int uncoup = eval(p);
            p.annulerCoup(c, joueurcourant);
            if (flag) {
                parfait = c;
            }
            return uncoup;
        }

        int valeurconfig = -10001;
        Iterator<Coup> it = lc.iterator();
        while (it.hasNext() && valeurconfig != 10000) {
            Coup c = it.next();
            // System.out.println("On teste le coup :" + c.toString());
            p.jouer(c, joueurcourant);
            // System.out.println(p.joueur2().getCamp().toString());
            // System.out.println(p.getBaseMontagne().toString());
            int valeurfils = meilleurConfigAD(p, horizon - 1);
            if (flag && valeurfils > valeurconfig) {
                parfait = c;
            }
            valeurconfig = Math.max(valeurconfig, valeurfils);
            p.annulerCoup(c, joueurcourant);
        }
        return valeurconfig;

    }

    public int meilleurConfigAD(Partie p, int horizon) {
        // System.out.println("On est a l'horizon :" + horizon);
        int joueurcourant = numerojoueur;
        if (numerojoueur == 0) {
            joueurcourant = 1;
        } else {
            joueurcourant = 0;
        }
        if ((horizon == 0) || (p.estPartieFinie())) {
            // System.out.println("On est aller a l horizon :" + horizon);
            // System.out.println("Je suis une feuille");
            return eval(p);
        }
        Acteur JoueurCourant;
        if (joueurcourant == 0) {
            JoueurCourant = p.joueur1();
        } else {
            JoueurCourant = p.joueur2();
        }
        ArrayList<Coup> lc = p.coupsJouables(JoueurCourant);
        // affiche(lc);
        int valeurconfig = 10001;
        Iterator<Coup> it = lc.iterator();
        while (it.hasNext() && valeurconfig != -10000) {
            Coup c = it.next();
            // System.out.println("On teste le coup :" + c.toString());
            p.jouer(c, joueurcourant);
            // System.out.println(p.getBaseMontagne().toString());
            valeurconfig = Math.min(valeurconfig, meilleurConfigJ(p, horizon - 1, false));
            p.annulerCoup(c, joueurcourant);
        }
        return valeurconfig;
    }

    public Coup getparfait() {
        return parfait;
    }

    public void affiche(ArrayList<Coup> l) {
        Iterator<Coup> it = l.iterator();
        System.out.println("Coups possibles");
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
        System.out.println();
    }

}