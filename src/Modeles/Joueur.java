package Modeles;

import java.util.ArrayList;
import java.util.Scanner;

public class Joueur extends ActeurClasse implements Acteur {

	public Joueur(String nom) {
		super(nom);
	}
	
	public Coup jouer(ArrayList<Coup> arr) {
		super.afficherCoupsJouables(arr);
		Scanner myObj = new Scanner(System.in);// NE PAS CLOSE() myObj
		String coup = myObj.nextLine();
		return arr.get(Integer.parseInt(coup));
	}

	public int placerPiece(int indice) {
		if(indice>piecesPiochees.size()-1) {
         	indice= piecesPiochees.size()-1;
         	System.err.println("Erreur : indice non valide. Recommencez.");
         }
     	if(indice<0) {
     		indice= 0;
     		System.err.println("Erreur : indice non valide. Recommencez.");
     	}
        Position pos;
        ArrayList<Position> arrPos;
        Piece p;
        for(int i=0; i<6; i++) {
            for(int j=0; j<6-i; j++) {
                if(super.piecesPiochees.size()==0) {
                    System.err.println("Plus de piece a placer ! Votre camp de base est deja construit !");
                    return -1;
                }
                int num=-1;
                while(num==-1) {
                	System.out.println(super.campJ.toString());//affichage du camp du joueur
                	System.out.println("indice : "+indice);
	                p=piecesPiochees.get(indice);
	                arrPos=super.campJ.posDisponibles();
	                System.out.println(toStringArrPos(arrPos));
	                System.out.println(super.getNom()+", ou voulez-vous placer cette piece ? "+p.toString());
	                System.out.println("Tapez + pour passer a la piece suivante ou - pour passer a la piece precedente.");
	                
	                Scanner myObj = new Scanner(System.in);// NE PAS CLOSE() myObj
	                String numChaine = myObj.nextLine();
	                if(numChaine.equals("+")) {
	                	System.out.println("Piece suivante demandee.");
	                	indice++;
	                }else {
	                	if(numChaine.equals("-")) {
	                		System.out.println("Piece precedente demandee.");
	                		indice--;
	                	}else {
		                	indice=Integer.parseInt(numChaine);
		                	num=0;
	                	}
	                }
	                if(indice>piecesPiochees.size()-1) {
	                	indice= piecesPiochees.size()-1;
	                	num=-1;
	                	System.err.println("Erreur : indice non valide. Recommencez.");
	                }
                	if(indice<0) {
                		indice= 0;
                		num=-1;
                		System.err.println("Erreur : indice non valide. Recommencez.");
                	}
                }
                p=piecesPiochees.get(indice);
                arrPos=super.campJ.posDisponibles();
                pos=arrPos.get(indice);//pos devient la position choisie par le joueur
                PiecePyramide pp = new PiecePyramide(p, pos);
                super.campJ.empiler(pp);
                super.piecesPiochees.remove(indice);
            }
        }
        return indice;
    }
	
	public PiecePyramide choixVol(ArrayList<PiecePyramide> arr) {//deja des pieces volables
		System.out.println(super.getNom()+", veuillez choisir une piece a voler :");
		for(int i=0; i<arr.size(); i++) {
			System.out.println("["+i+"]"+arr.get(i).toString());
		}
		Scanner myObj = new Scanner(System.in);// NE PAS CLOSE() myObj
		String num = myObj.nextLine();
		return arr.get(Integer.parseInt(num));
	}
	
	public String toStringArrPos(ArrayList<Position> arr) {
		String chaine=new String();
		String carSep;
		for(int i=0; i<arr.size(); i++) {
			if(i<arr.size()-1) {
				carSep="  |  ";
			}else {
				carSep="";
			}
			chaine+="["+i+"] : "+arr.get(i).toString()+carSep;
		}
		return chaine;
	}
}