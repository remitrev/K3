package Modeles;

import java.util.ArrayList;

public interface Acteur {

	// A PRORAMMER / IA
	public Coup jouer(ArrayList<Coup> arr); // pyramide au milieu
	
	void melangePiecesPiochees();
	
	public void setCamp(PyramideJoueur camp);
	
	public void placerPiecesRandom(PyramideMontagne pyrM);
	
	public void addBlancJoue();

	public void retireBlancJoue();
	
	public void addMauvaisCoup();
	
	public int getBlancsJoues();
	
	public int getMauvaisCoupsJoues();
	
	public int getNbVols();
	
	public void addVol();
	
	public void retireVol();
	
	public int placerPiece(int indice);// deja implemente
	
	public PiecePyramide choixVol(ArrayList<PiecePyramide> arr);

	public String getNom();// deja implemente

	public PyramideJoueur getCamp();// deja implemente
	
	public ArrayList<PiecePyramide> getPiecesJouables();// deja implemente

	public ArrayList<Piece> getPiecesVolees();// deja implemente
	
	public void addPiecePiochee(Piece p);// deja implemente

	public String toStringPiecesVolees();// deja implemente

	public ArrayList<Piece> getPiecesPiochees();// deja implemente

	public void addPieceVolee(Piece p);// deja implemente
	
	public Piece retirerPieceVolee(Piece p);

	public int getTaillePiecesPiochees();// deja implemente

	public Piece piocherPiece(ArrayList<Piece> sac);

	public void afficherCoupsJouables(ArrayList<Coup> arr);// deja implemente
}
