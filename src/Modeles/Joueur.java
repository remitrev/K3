package Modeles;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur extends ActeurClasse implements Acteur {

	public Joueur(String nom) {
		super(nom);
	}
	
	public Coup jouer(ArrayList<Coup> arr) {
		System.out.println(super.getNom()+", veuillez jouer un coup :");
		super.afficherCoupsJouables(arr);
		Scanner myObj = new Scanner(System.in);// NE PAS CLOSE() myObj
		String coup = myObj.nextLine();
		return arr.get(Integer.parseInt(coup));
	}

	public void placerPieces() {
        Position pos;
        for(int i=0; i<6; i++) {
            for(int j=0; j<6-i; j++) {
                pos=new Position(i,j);
                if(piecesPiochees.size()==0) {
                    System.err.println("Plus de pi�ce � placer ! Votre camp de base est d�j� construit !");
                    return;
                }
                Piece p=piecesPiochees.get(0);
                PiecePyramide pp = new PiecePyramide(p, pos);
                campJ.empiler(pp);
                piecesPiochees.remove(p);
            }
        }
    }
	
	public PiecePyramide choixVol(ArrayList<PiecePyramide> arr) {//deja des pieces jouables
		System.out.println(super.getNom()+", veuillez choisir une piece a voler :");
		for(int i=0; i<arr.size(); i++) {
			System.out.println("["+i+"]"+arr.get(i).toString());
		}
		Scanner myObj = new Scanner(System.in);
		String coup = myObj.nextLine();
		return arr.get(Integer.parseInt(coup));
	}
}