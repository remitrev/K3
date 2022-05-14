package Controleur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Modeles.*;

public class Jeu {
	public Partie partieEnCours;
	public String chemin;
	public String cheminStats, cheminImages, cheminSauvegardes, photoProfil;
	private int num_tour, valeur_paire;
	public int volumeEffetsSonores, volumeMusique, modeDaltonien;
	private final int NB_LIGNES_OPTIONS = 4;// NB DE LIGNES DU FICHIER Options.txt = 6
	private final int TAILLE_CAMP_JOUEUR=21;

	public Jeu(String nomJ1, String nomJ2) {
		this.chemin=System.getProperty("user.home")+ "/Desktop/Jeu_K3/";
		this.cheminStats=chemin+"Statistiques/";
		this.cheminImages=chemin+"Images/";
		this.cheminSauvegardes=chemin+"Sauvegardes/";
		//creer les dossier du jeu s'il n'existent pas
		new File(this.chemin).mkdirs();
		new File(this.cheminStats).mkdirs();
		new File(this.cheminImages).mkdirs();
		new File(this.cheminSauvegardes).mkdirs();
		lireOptions();
		Joueur j1 = new Joueur(nomJ1);
		Joueur j2 = new Joueur(nomJ2);
		this.partieEnCours = new Partie(j1, j2);
		lancerJeu();
	}
	
	public void lancerJeu() {
		this.num_tour=1;
		this.valeur_paire=0;
		//PHASE 1
		jouerPhase1();
		//PHASE 2
		jouerPhase2();
	}
	
	public void jouerPhase1() {
		//initialisation des blancs et des naturels aux joueurs
		this.partieEnCours.distribuerBlancEtNaturels();
		//initialisation des pieces des deux joueurs
		while (this.partieEnCours.joueur1().getTaillePiecesPiochees() < this.TAILLE_CAMP_JOUEUR || this.partieEnCours.joueur2().getTaillePiecesPiochees() < this.TAILLE_CAMP_JOUEUR) {
			piocher();
		}
		int i=0, j=0;//indice des pieces a choisir
		while (this.partieEnCours.joueur1().getTaillePiecesPiochees()>0 && this.partieEnCours.joueur2().getTaillePiecesPiochees()>0) {
			afficherBaseMontagne();
			/*
			//chaque joueur doit choisir la piece a empiler sur sa pioche
			i=this.partieEnCours.joueur1().placerPiece(i);
			j=this.partieEnCours.joueur2().placerPiece(j);
			*/
			//creation des pioches automatiquement sans demander aux joueurs
			this.partieEnCours.joueur1().placerPiecesRandom(partieEnCours.getBaseMontagne());
			this.partieEnCours.joueur2().placerPiecesRandom(partieEnCours.getBaseMontagne());
		}
	}
	
	public void jouerPhase2() {
		System.out.println("Les deux camps des joueurs ont ete creer !");
		System.out.println("================ Deuxieme phase du jeu ================");
		while(!this.partieEnCours.estPartieFinie()) {//explicite
			faireJouerActeurs();//fait jouer les acteurs chacun leur tour
		}
		partieVictoire();//affichage uniquement
	}
	
	public void afficherBaseMontagne() {
		System.out.println(this.partieEnCours.getBaseMontagne().toString());
	}
	
	public void afficherTour() {	
		System.out.println("================ Tour "+this.num_tour+" ================");
		this.num_tour++;
	}
	
	public void faireJouerActeurs() {
		Coup coupDemande;
		ArrayList<Coup> cJ=new ArrayList<Coup>();
		Acteur jCourant, jPrecedent;
		
		if(this.valeur_paire%2==0) {//affichage du numero du tour actuel
			afficherTour();
			this.valeur_paire++;
		}
		//attribution du joueur courant et precedent
		if (this.partieEnCours.getJoueurCourant() == 0) {
			jCourant=this.partieEnCours.joueur1();
			jPrecedent=this.partieEnCours.joueur2();
		}else {
			jCourant=this.partieEnCours.joueur2();
			jPrecedent=this.partieEnCours.joueur1();
		}
		//affichage dans la console de la partie
		afficherBaseMontagne();
		System.out.println("Votre camp :");
		System.out.println(jCourant.getCamp().toString());
		System.out.println("Vos pieces volees : "+jCourant.toStringPiecesVolees());
		System.out.println("Camp adverse :");
		System.out.println(jPrecedent.getCamp().toString());
		System.out.println("Ses pieces volees : "+jPrecedent.toStringPiecesVolees());
		System.out.println(jCourant.getNom()+", veuillez jouer un coup :");
		
		//fait jouer un joueur
		cJ=this.partieEnCours.coupsJouables(jCourant);
		coupDemande=jCourant.jouer(cJ);//le joueur courant a choisi un coup a jouer
		if(coupDemande.getPosJ()!=null) {//si le joueur courant ne joue pas une piece volee
			jCourant.getCamp().retirer(coupDemande.getPosJ());//retire la piece jouee du camp du joueur courant
		}else {//si le joueur courant decide de jouer une de ses pieces volees
			jCourant.retirerPieceVolee(coupDemande.getPiece());
			jCourant.retireVol();
		}
		jCourant.addCoupHist(coupDemande);
		if(coupDemande.getPosBase()!=null) {//si le joueur ne choisit pas de jouer une piece BLANCHE
			this.partieEnCours.getBaseMontagne().empiler(new PiecePyramide(coupDemande.getPiece(),coupDemande.getPosBase()));
			if(this.partieEnCours.getBaseMontagne().estPorteursMemeCouleur(coupDemande.getPosBase())){//si vol possible
				jCourant.addMauvaisCoup();
				Coup vol = this.partieEnCours.volerPiece(jPrecedent, jCourant);
				jPrecedent.addCoupHist(vol);
			}
		}else {// joue une piece BLANCHE
			jCourant.addBlancJoue();
			System.out.println("Vous avez decide de passer votre tour !");
		}
		this.partieEnCours.changementJoueurCourant();
	}
	
	public void partieVictoire() {
		this.partieEnCours.getStats().ecrireStats(this.cheminStats+"stats-001.txt",this.partieEnCours);
		afficherBaseMontagne();
		System.out.println(this.partieEnCours.joueur1().getCamp().toString());
		System.out.println(this.partieEnCours.joueur2().getCamp().toString());
		System.out.println("Fin de la partie.");
		if (partieEnCours.getJoueurCourant() == 0) {
			System.out.print(this.partieEnCours.joueur2().getNom());
		}else {
			System.out.print(this.partieEnCours.joueur1().getNom());
		}
		System.out.println(" a gagn� !");
	}

	public void piocher() {
		Piece p;
		if (partieEnCours.getJoueurCourant() == 0) {
			p = this.partieEnCours.joueur1().piocherPiece(partieEnCours.getBasePieces());
			this.partieEnCours.joueur1().addPiecePiochee(p);
		} else {
			p = this.partieEnCours.joueur2().piocherPiece(partieEnCours.getBasePieces());
			this.partieEnCours.joueur2().addPiecePiochee(p);
		}
		partieEnCours.changementJoueurCourant();
	}

	public void lireOptions() {// au tout premier lancement du jeu, le fichier Options.txt existe deja
		boolean[] renvoi = new boolean[4];
		String nom_fichier = "Options.txt";
		if(!testFichierExistant(this.chemin+nom_fichier)) {
			ecrireOptions();
			renvoi[0]=false;
			return;
		}
		// 0 : test fichier options
		// 1 : testPhotoProfil
		// 2 : testDaltonien
		// 3 : testVolume
		boolean testPhotoProfil, testDaltonien, testVolume;
		ArrayList<String> tab = new ArrayList<String>();
			try {
				FileReader reader = new FileReader(this.chemin + nom_fichier);
				BufferedReader br = new BufferedReader(reader);
				String ligne_lue;
				while ((ligne_lue = br.readLine()) != null) {
					tab.add(ligne_lue);
				}
				if (tab.size() != this.NB_LIGNES_OPTIONS) {
					System.err.println("Erreur : le fichier " + nom_fichier + " a ete modifie. Il contient " + tab.size()+" lignes.");
					ecrireOptions();
					renvoi[0]=false;
					return;
				} else {
					// Nom du fichier de la photo de profil du joueur
					// Mode daltonien oui/non
					// Volume des effets sonores
					// Volume de la musique
					this.photoProfil = tab.get(0);
					testPhotoProfil = testFichierExistant(chemin+photoProfil+".jpg")||testFichierExistant(chemin+photoProfil+".png");
					this.modeDaltonien=Integer.parseInt(tab.get(1));
					testDaltonien = verifDaltonien(this.modeDaltonien);
					this.volumeEffetsSonores = Integer.parseInt(tab.get(2));
					this.volumeMusique = Integer.parseInt(tab.get(3));
					testVolume = verifVolume(this.volumeEffetsSonores, this.volumeMusique);
					renvoi[1] = testPhotoProfil;
					renvoi[2] = testDaltonien;
					renvoi[3] = testVolume;
				}
			}
			catch (Exception e) {
				System.err.println("Erreur : le fichier Options.txt est corrompu.");
				e.printStackTrace();
			}
	}
	
	public boolean verifDaltonien(int v) {// renvoie true si v est compris entre 0 et 1
		if(v!=0 && v!=1) {
			this.modeDaltonien=0;
			return false;
		}else {
			this.modeDaltonien=v;
			return true;
		}
	}

	public boolean verifVolume(int v1, int v2) {// renvoie true si le volume est compris entre 0 et 10
		boolean b;
		if (v1 > 10 || v1 < 0) {
			this.volumeEffetsSonores = 6;
			b=false;
		}else {
			this.volumeEffetsSonores = v1;
			b=true;
		}
		if (v2 > 10 || v2 < 0) {
			this.volumeMusique = 4;
			b=b&&false;
		}else {
			this.volumeMusique = v2;
			b=b&&true;
		}
		return b;
	}

	public boolean testFichierExistant(String nomFichier) {// renvoie true si le fichier existe
		try {
			File myFile = new File(nomFichier);
			if(myFile.isFile()) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erreur : le fichier " + nomFichier + " est inexistant.");
			e.printStackTrace();
		}
		return false;
	}

	public void ecrireOptions() {// reinitialise le fichier Options.txt en ecrivant des valeurs par defaut
		try {
			File f = new File(this.chemin + "/Options.txt");
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			FileWriter writer = new FileWriter(f, false);// ecrire en mode remplacement
			BufferedWriter bw = new BufferedWriter(writer);
			// Nom du fichier de la photo de profil du joueur
			// Mode daltonien oui/non
			// Volume des effets sonores
			// Volume de la musique
			this.photoProfil="Ma_photo_de_profil";
			this.modeDaltonien=0;
			this.volumeEffetsSonores=6;
			this.volumeMusique=4;
			bw.write(this.photoProfil);
			bw.newLine();
			bw.write(String.valueOf(this.modeDaltonien));
			bw.newLine();
			bw.write(String.valueOf(this.volumeEffetsSonores));
			bw.newLine();
			bw.write(String.valueOf(this.volumeMusique));
			bw.close();
			writer.close();
			System.out.println("Un nouveau fichier Options.txt a ete creer.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sauvegarderPartie(String nomFichier) {
		File f = new File(this.cheminSauvegardes+nomFichier);
		try {
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			FileWriter writer = new FileWriter(f, false);// erire en mode remplacement
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write("hauteur base montagne :" + this.partieEnCours.getBaseMontagne().getHauteur());
			bw.newLine();
			bw.write("hauteur camp joueur :" + this.partieEnCours.joueur1().getCamp().getHauteur());
			bw.newLine();
			bw.write("base montagne :");
			bw.newLine();
			bw.write(this.partieEnCours.getBaseMontagne().toString());
			bw.newLine();
			bw.write("joueur courant :"+this.partieEnCours.getJoueurCourant());
			bw.newLine();
			// ======================= JOUEUR 1 =======================
			bw.write(" ======================= JOUEUR 1 =======================");
			bw.newLine();
			bw.write("nom joueur 1:" + this.partieEnCours.joueur1().getNom());
			bw.newLine();
			bw.write("camp de base:");
			bw.newLine();
			bw.write(this.partieEnCours.joueur1().getCamp().toString());
			bw.write("pieces volees:" + this.partieEnCours.joueur1().toStringPiecesVolees());
			bw.newLine();
			int nbCoups=this.partieEnCours.joueur1().getHistCoups().size();
			bw.write("CoupsJ1:"+nbCoups);
			for (int k = 0; k < nbCoups; k++) {
				bw.write(this.partieEnCours.joueur1().getHistCoups().get(k).toString());
				bw.newLine();
			}

			// ======================= JOUEUR 2 =======================
			bw.write(" ======================= JOUEUR 2 =======================");
			bw.newLine();
			bw.write("nom joueur 2:" + this.partieEnCours.joueur2().getNom());
			bw.newLine();
			bw.write("camp de base:");
			bw.newLine();
			bw.write(this.partieEnCours.joueur2().getCamp().toString());
			bw.write("pieces volees:" + this.partieEnCours.joueur2().toStringPiecesVolees());
			bw.newLine();
			nbCoups=this.partieEnCours.joueur2().getHistCoups().size();
			bw.write("CoupsJ2:"+nbCoups);
			for (int k = 0; k < nbCoups; k++) {
				bw.write(this.partieEnCours.joueur2().getHistCoups().get(k).toString());
				bw.newLine();
			}
			bw.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void chargerPartie(String cheminSauvegardes) {
		// Fichiers.lisSauvegarde();
	}
			
}