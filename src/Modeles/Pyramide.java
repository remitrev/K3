package Modeles;

import java.util.ArrayList;

public class Pyramide {

	protected int largeur;
	protected int hauteur;
	protected Piece[][] pyramide;
	public int LARGEUR_MAX = 50;
	public int HAUTEUR_MAX = 50;
	protected ArrayList<PiecePyramide> historiquePieces;
	
	public Pyramide(int largeur, int hauteur) {
		if (largeur <= 0 || hauteur <= 0 || hauteur >= LARGEUR_MAX || largeur >= HAUTEUR_MAX) {
			System.err.println("erreur taille impossible : " + largeur + "," + hauteur);
		} else {
			this.largeur = largeur;
			this.hauteur = hauteur;
			int l = largeur;

			Piece[][] etage = new Piece[hauteur][];
			for (int i = 0; i < hauteur; i++) {
				Piece[] ligne = new Piece[l];
				etage[i] = ligne;
				l = l - 1;
			}
			this.pyramide = etage;
			this.historiquePieces=new ArrayList<PiecePyramide>();
		}
	}
	
	public boolean estPleine() {
		return pyramide[hauteur-1][0]!=null;
	}

	public int getlargeur() {
		return this.largeur;
	}

	public int getHauteur() {
		return this.hauteur;
	}

	public Piece getPiece(Position p) {
		if (p.etage >= hauteur || p.etage < 0 || p.rang >= pyramide[p.etage].length || p.rang < 0) {
			System.err.println("erreur position impossible : " + p.rang + "," + p.etage);
			return null;
		}
		return this.pyramide[p.etage][p.rang];
	}
	
	public void annulerDernierePiece() {
		int taille=this.historiquePieces.size();
		this.historiquePieces.remove(taille-1);
	}
	
	public boolean aPiecesPorteuses(int etage, int rang) {//renvoie true si la piece a la position p a deux pieces porteuses
		return pyramide[etage-1][rang] != null && pyramide[etage-1][rang+1] !=null;
	}

	public boolean estPiecePorteuse(Position p) {//renvoie true si la piece a la position p est porteuse
		boolean caseSupGauche, caseSupDroite;
		if (p.rang==0) {
            caseSupGauche = true; // si c'est la premiere case de la ligne en haut a gauche qui n'existe pas
        } else {
            caseSupGauche = (pyramide[p.etage + 1][p.rang - 1] == null); // on regarde si en haut a gauche est libre
        }
        if (p.rang == pyramide[p.etage].length - 1) {
            caseSupDroite = true; // si c'est la derniere case de la ligne en haut a droite qui n'existe pas
        } else {
            caseSupDroite = (pyramide[p.etage + 1][p.rang] == null); // on regarde si en haut a droite est libre
        }
        return(caseSupGauche && caseSupDroite);
	}

	public void retirer(Position p) {
		if (p.etage >= hauteur || p.etage < 0 || p.rang >= pyramide[p.etage].length || p.rang < 0 || pyramide[p.etage][p.rang] == null) {
			System.err.println("erreur impossible de retirer la piece.");
		} else {
			// avec piece porteuse
			boolean caseSupGauche, caseSupDroite;
			if (p.rang == 0) {
				caseSupGauche = true; // si c'est la premiere case de la ligne en haut a gauche qui n'existe
										// pas
			} else {
				caseSupGauche = (pyramide[p.etage + 1][p.rang - 1] == null); // on regarde si en haut a gauche est
			} // libre
			if (p.rang == pyramide[p.etage].length - 1) {
				caseSupDroite = true; // si c'est la derniere case de la ligne en haut a droite qui n'existe
										// pas
			} else {
				caseSupDroite = (pyramide[p.etage + 1][p.rang] == null); // on regarde si en haut a droite est libre
			}
			// on regarde ne haut a gauche + en haut a droite
			if (caseSupGauche && caseSupDroite) { // on regarde si il ya des pieces porteuses
				this.pyramide[p.etage][p.rang] = null;
				
			} else {
				System.err.println("La piece est une piece porteuse et ne peut pas etre retiree.");
			}
		}
	}

	public String toString() {
		String tableau = new String();
		String ligne="";
		String debut="";
		for (int i = hauteur - 1; i >= 0; i--) {
			switch(i) {
			case 8:
				debut="        ";
				break;
			case 7:
				debut="       ";
				break;
			case 6:
				debut="      ";
				break;
			case 5:
				debut="     ";
				break;
			case 4:
				debut="    ";
				break;
			case 3:
				debut="   ";
				break;
			case 2:
				debut="  ";
				break;
			case 1:
				debut=" ";
				break;
			case 0:
				debut="";
			}
			for (int j = 0; j < pyramide[i].length; j++) {
				if (pyramide[i][j] != null) {
					switch (pyramide[i][j].getColor()) {
						case BLEU:
							ligne += 'B';
							break;
						case NOIR:
							ligne += 'N';
							break;
						case ROUGE:
							ligne += 'R';
							break;
						case VERT:
							ligne += 'V';
							break;
						case JAUNE:
							ligne += 'J';
							break;
						case BLANC:
							ligne += 'W';
							break;
						case NATUREL:
							ligne += '#';
							break;
					}
				} else
					ligne += '.';
				ligne += ' ';
			}
			ligne += System.getProperty("line.separator");
			ligne=debut+ligne;
			tableau+=ligne;
			ligne="";
		}
		return tableau;
	}
	
	public void empiler(PiecePyramide pp) {
		Piece piece = pp.getPiece();
		Position p = pp.getPos();
		if (p.etage >= hauteur || p.etage < 0 || p.rang >= pyramide[p.etage].length || p.rang < 0) {
			System.err.println("Erreur : la position (" + p.rang + "," + p.etage + ") est impossible.");
			return;
		} if(this.pyramide[p.etage][p.rang] != null) {
			System.err.println("la place est deja prise");
		}
		else if (p.etage == 0) {
			// pas de piece porteuse
			this.pyramide[p.etage][p.rang] = piece;
			this.historiquePieces.add(pp);
		} else {
			// avec piece porteuse
			int porteurDroitRang = p.rang;
			int porteurDroitEtage = p.etage - 1;
			Piece porteurDroit = pyramide[porteurDroitEtage][porteurDroitRang];
			int porteurGaucheRang = porteurDroitRang + 1;
			int porteurGaucheEtage = p.etage - 1;
			Piece porteurGauche = pyramide[porteurGaucheEtage][porteurGaucheRang];

			if (porteurDroit != null && porteurGauche != null) {
				if (conditionEmpiler(piece, porteurGauche, porteurDroit)) {
					this.pyramide[p.etage][p.rang] = piece;
					this.historiquePieces.add(pp);
				} else {
					System.err.println("La piece n'est pas de la bonne couleur qu'une des 2 pieces porteuses.");
					return;
				}
			} else {
				System.err.println("Impossible d'empiler une piece sur du vide. Il faut au moins 2 pieces porteuses.");
				return;
			}
		}
	}
	
	public boolean conditionEmpiler(Piece piece, Piece porteurDroit, Piece porteurGauche) {
		return true;
	}
}
