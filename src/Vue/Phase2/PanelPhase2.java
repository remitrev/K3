/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vue.Phase2;

import Controleur.Jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JTextField;

import Modeles.Couleurs;
import Modeles.Joueur;
import Modeles.Partie;
import Modeles.Piece;
import Modeles.Position;
import Vue.TexturePack.LoadTexture;

public class PanelPhase2 extends javax.swing.JPanel {

  public LoadTexture texture;
  public Dimension tailleFenetre;
  public Jeu jeu;
  public ArrayList<PosPixel> pospion = new ArrayList<>();
  public int frameWidth, frameHeight;
  public int largeur_background, hauteur_background, posX_background, posY_background;
  public int posXPasserTour, posYPasserTour, largeurPasserTour, hauteurPasserTour, posXCoupPrecedent;
  public int largeur_ileJ, hauteur_ileJ, posX_ileJ1, posY_ileJ1, posX_ileJ2, posY_ileJ2;
  public int largeur_ileM, hauteur_ileM, posX_ileM, posY_ileM;
  public int largeur_piece, hauteur_piece, posY_depart;
  public int largeur_vol, hauteur_vol, posX_volJ1, poxY_volJ1, posX_volJ2, poxY_volJ2;
  public int posX_sauvegarder, posY_sauvegarder, largeur_sauvegarder, hauteur_sauvegarder;
  public int posX_settings, posY_settings, largeur_settings;
  public int largeur_cadre, hauteur_cadre, posX_cadreJ1, posY_cadreJ1, posX_cadreJ2, posY_cadreJ2;
  public int taille_police_nom_joueur, posX_nom_joueur1, posY_nom_joueur1, posX_nom_joueur2, posY_nom_joueur2;
  public int largeur_popup, hauteur_popup, posX_popup, posY_popup, largeur_popup_save, hauteur_popup_save,
      posX_popup_save, posY_popup_save;
  public int largeur_valider, hauteur_valider, largeur_fermer, posX_valider, posY_valider, posX_fermer, posY_fermer;
  public int posX_jtext, posY_jtext, largeur_jtext, hauteur_jtext;
  public int posX_back, posY_back, largeur_back, hauteur_back;
  public int posX_victoire, posY_victoire, largeur_victoire, hauteur_victoire, posX_cadre_victoire, posY_cadre_victoire;
  public int posX_jtext2, posY_jtext2, largeur_jtext2, hauteur_jtext2;
  public int largeur_degrade, hauteur_degade, posX_degrade, posY_degrade;
  public int posX_piece_voleeJ1, posY_piece_voleeJ1, posX_piece_voleeJ2, posY_piece_voleeJ2;
  boolean popup = false;
  boolean popup_save = false;
  public JTextField nomSave, joueurVictorieux;

  /**
   * Creates new form PanelPhase2
   */
  public PanelPhase2(Jeu j, LoadTexture texture) {
    this.texture = texture;
    this.jeu = j;
    setLayout(null);
    initComponents();
    this.addMouseListener(new Phase2Click(this));
    nomSave = new JTextField();
    this.add(nomSave);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")

  private void initComponents() {
    setAutoscrolls(true);
    setDoubleBuffered(false);
    setFocusable(false);
    setOpaque(false);
    setPreferredSize(new java.awt.Dimension(779, 699));
    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 679, Short.MAX_VALUE));
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 374, Short.MAX_VALUE));
  }// </editor-fold>//GEN-END:initComponents

  @Override
  public void paint(Graphics g) {
    pospion.clear();
    setChangementTaillefenetre();
    drawbackground(g);
    drawIle(g);
    drawbaPyramideJ1(g);
    drawbaPyramideJ2(g);
    drawPiecesVoleesJ2(g);
    drawbaPyramideMilieu(g);
    if (showcheck) {
      // System.out.println("Repaint transparence");
      drawtransparentpion(g);
      showcheck = false;
    }
    drawBoutons(g);
    afficherNomJoueur(g);
    afficheBoutonBack(g);
    affichePopupSave(g);
  }

  public void setChangementTaillefenetre() {
    this.tailleFenetre = this.getSize();
    this.frameWidth = this.getWidth();
    this.frameHeight = this.getHeight();
    double rapport = (double) 0.5625;// rapport de 2160/3840
    if ((double) ((double) frameHeight / (double) frameWidth) > rapport) {
      largeur_background = frameWidth;
      hauteur_background = (int) (largeur_background * rapport);
      posX_background = 0;
      posY_background = (frameHeight - hauteur_background) / 2;
    } else {// si largeur fenetre tres grande
      hauteur_background = frameHeight;
      largeur_background = (int) (hauteur_background / rapport);
      posX_background = (frameWidth - largeur_background) / 2;
      posY_background = 0;
    }
    // Passer son tour
    double rapportPasserTour = 0.3001776198934281;// 169/563
    largeurPasserTour = Math.min(largeur_background / 8, frameWidth / 8);
    hauteurPasserTour = (int) (largeurPasserTour * rapportPasserTour);
    int espacement = largeurPasserTour / 4;
    posXPasserTour = posX_background + largeur_background / 2 - largeurPasserTour - espacement / 2;
    posYPasserTour = posY_background + (int) (hauteur_background * 0.8);
    // Coup precedent
    posXCoupPrecedent = posXPasserTour + largeurPasserTour + espacement;
    // Sauvegarder
    double rapportSauvegarder = 0.3498964803312629; // 169/483
    hauteur_sauvegarder = (int) (hauteurPasserTour * 0.7);
    largeur_sauvegarder = (int) (hauteur_sauvegarder / rapportSauvegarder);
    posX_sauvegarder = posX_background + (int) (largeur_background * 0.88);
    posY_sauvegarder = posY_background + (int) (hauteur_background * 0.92);
    // Iles joueurs
    double rapportIlesJoueurs = 0.9269340974212034;// 647/698
    largeur_ileJ = Math.min((int) (largeur_background / 3.6), (int) (frameWidth / 3.6));
    hauteur_ileJ = (int) (largeur_ileJ * rapportIlesJoueurs);
    posX_ileJ1 = posX_background + (int) (largeur_background * 0.02);
    posY_ileJ1 = posY_background + (int) (hauteur_background * 0.52);
    posX_ileJ2 = posX_background + (int) (largeur_background * 0.98) - largeur_ileJ;
    ;
    posY_ileJ2 = posY_ileJ1;
    posY_depart = posY_ileJ1 - (int) (hauteur_ileJ * 0.04);
    // Ile montagne
    double rapportIleMontagne = 0.7764198418404026;// 1080/1391
    largeur_ileM = Math.min((int) (largeur_background / 2.5), (int) (frameWidth / 2.5));
    hauteur_ileM = (int) (largeur_ileM * rapportIleMontagne);
    posX_ileM = posX_background + largeur_background / 2 - largeur_ileM / 2;
    posY_ileM = posY_background + hauteur_background - (int) (hauteur_background / 1.7);
    // Camp joueur
    double rapportPiece = 0.8576709796672828;// 464/541
    largeur_piece = Math.min((int) (largeur_background / 35), (int) (frameWidth / 35));
    hauteur_piece = (int) (largeur_piece * rapportPiece);
    // Cadres joueurs
    double rapportCadre = 0.2200647249190939;// 204/927
    largeur_cadre = Math.min(largeur_background / 6, frameWidth / 6);
    hauteur_cadre = (int) (largeur_cadre * rapportCadre);
    posX_cadreJ1 = posX_background + (int) (largeur_background * 0.08);
    posY_cadreJ1 = posY_background + (int) (hauteur_background * 0.61);
    posX_cadreJ2 = posX_background + (int) (largeur_background * 0.755);
    posY_cadreJ2 = posY_cadreJ1;
    // noms joueurs
    taille_police_nom_joueur = (int) (hauteur_cadre / 2.5);
    posX_nom_joueur1 = posX_cadreJ1 + (int) (largeur_cadre * 0.07);
    posY_nom_joueur1 = posY_cadreJ1 + (int) (hauteur_cadre * 0.64);
    posX_nom_joueur2 = posX_cadreJ2 + (int) (largeur_cadre * 0.07);
    posY_nom_joueur2 = posY_nom_joueur1;
    // Vols
    double rapportVol = 0.8104738154613466;// 975/1203
    largeur_vol = largeur_piece * 6;
    hauteur_vol = (int) (largeur_vol * rapportVol);
    posX_volJ1 = posX_ileJ1 + largeur_ileJ / 2 - largeur_vol / 2 + (int) (largeur_piece * 0.4);
    poxY_volJ1 = posY_background + hauteur_background / 24;
    posX_volJ2 = posX_ileJ2 + largeur_ileJ / 2 - largeur_vol / 2 - (int) (largeur_piece * 0.2);
    poxY_volJ2 = poxY_volJ1;
    // settings
    largeur_settings = largeur_background / 18;
    posX_settings = posX_background + (int) (largeur_background * 0.92);
    posY_settings = posY_background + (int) (hauteur_background * 0.04);
    // popup sauvegarde
    double rapportPopupSave = 1.169139465875371;
    largeur_popup_save = (int) (largeur_background / 4);
    hauteur_popup_save = (int) (largeur_popup_save / rapportPopupSave);
    posX_popup_save = posX_background + largeur_background / 2 - largeur_popup_save / 2;
    posY_popup_save = posY_background + hauteur_background / 2 - hauteur_popup_save / 2;
    // valider et fermer
    double rapportValider = 0.5950704225352113;
    largeur_valider = largeur_popup_save / 3;
    hauteur_valider = (int) (largeur_valider * rapportValider);
    largeur_fermer = hauteur_valider;
    posX_valider = posX_popup_save + (int) (largeur_popup_save * 0.1);
    posY_valider = posY_popup_save + (int) (largeur_popup_save * 0.55);
    posX_fermer = posX_valider + largeur_valider * 2;
    posY_fermer = posY_valider;
    // jtext
    Font text1 = new Font("Dialog", Font.BOLD, (int) (taille_police_nom_joueur * 0.8));
    posX_jtext = posX_valider;
    posY_jtext = posY_popup_save + (int) (hauteur_popup_save * 0.2);
    largeur_jtext = (int) (largeur_popup_save * 0.79);
    hauteur_jtext = hauteur_valider;
    nomSave.setBounds(posX_jtext, posY_jtext, largeur_jtext, hauteur_jtext);
    nomSave.setFont(text1);
    nomSave.setVisible(popup_save);
    // Bouton retour
    double rapportBack = 0.8441247002398082;// 352/417
    largeur_back = Math.min(largeur_background / 15, frameWidth / 15);
    hauteur_back = (int) (largeur_back * rapportBack);
    posX_back = posX_background + (int) (largeur_background * 0.9);
    posY_back = posY_background + (int) (hauteur_background * 0.8);
    // Victoire
    double rapportVictoire = 0.2055030094582975;// 239/1163
    largeur_victoire = Math.min(largeur_background / 3, frameWidth / 3);
    hauteur_victoire = (int) (largeur_victoire * rapportVictoire);
    posX_victoire = posX_background + largeur_background / 2 - largeur_victoire / 2;
    posY_victoire = posY_background + (int) (hauteur_background * 0.05);
    // cadre fond joueur victorieux
    posX_cadre_victoire = posX_victoire + largeur_victoire / 2 - largeur_cadre / 2;
    posY_cadre_victoire = posY_victoire + (int) (hauteur_victoire * 1.5);
    // nom du gagnant
    posX_jtext2 = posX_cadre_victoire + (int) (largeur_cadre * 0.12);
    posY_jtext2 = posY_cadre_victoire + (int) (hauteur_cadre * 0.6);
    // fond degrade noir
    largeur_degrade = largeur_victoire * 2;
    hauteur_degade = hauteur_background;
    posX_degrade = posX_background + largeur_background / 2 - largeur_degrade / 2;
    posY_degrade = posY_background;
    // Pieces volees
    posX_piece_voleeJ1 = posX_volJ1 + (int) (largeur_vol * 0.11);
    posY_piece_voleeJ1 = poxY_volJ1 + (int) (hauteur_vol * 0.41);
    posX_piece_voleeJ2 = posX_volJ2 + (int) (largeur_vol * 0.11);
    posY_piece_voleeJ2 = poxY_volJ2 + (int) (hauteur_vol * 0.41);
  }

  public void afficheBoutonBack(Graphics g) {
    g.drawImage(texture.TutoMenu, posX_back, posY_back, largeur_back, hauteur_back, null);
  }

  public void affichePopupSave(Graphics g) {
    if (popup_save) {
      g.drawImage(texture.popup_save, posX_popup_save, posY_popup_save, largeur_popup_save, hauteur_popup_save, null);
      g.drawImage(texture.valider, posX_valider, posY_valider, largeur_valider, hauteur_valider, null);
      g.drawImage(texture.fermer, posX_fermer, posY_fermer, largeur_fermer, largeur_fermer, null);
    }
  }

  public void afficherNomJoueur(Graphics g) {
    String nomJ1, nomJ2;
    int taille_max = 14;
    nomJ1 = this.jeu.partieEnCours.joueur1().getNom();
    nomJ2 = this.jeu.partieEnCours.joueur2().getNom();
    if (nomJ1.length() > taille_max) {
      nomJ1 = nomJ1.substring(0, taille_max);
    }
    if (nomJ2.length() > taille_max) {
      nomJ2 = nomJ2.substring(0, taille_max);
    }
    g.setColor(Color.BLACK);
    g.setFont(new Font("Dialog", Font.BOLD, taille_police_nom_joueur));
    g.drawString(nomJ1, posX_nom_joueur1, posY_nom_joueur1);
    g.drawString(nomJ2, posX_nom_joueur2, posY_nom_joueur2);
  }

  public void drawbackground(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, frameWidth, frameHeight);
    g.drawImage(texture.backgroundSansLogo, posX_background, posY_background, largeur_background, hauteur_background,
        null);
  }

  public void drawBoutons(Graphics g) {
    g.drawImage(texture.passerTour, posXPasserTour, posYPasserTour, largeurPasserTour, hauteurPasserTour, null);
    g.drawImage(texture.boutonCoupPrecedent, posXCoupPrecedent, posYPasserTour, largeurPasserTour, hauteurPasserTour,
        null);
    g.drawImage(texture.settings, posX_settings, posY_settings, largeur_settings, largeur_settings, null);
    if (this.jeu.partieEnCours.getJoueurCourant() == 0) {
      g.drawImage(texture.cadre_joueur, posX_cadreJ1, posY_cadreJ1, largeur_cadre, hauteur_cadre, null);
      g.drawImage(texture.cadre_joueur_gris, posX_cadreJ2, posY_cadreJ2, largeur_cadre, hauteur_cadre, null);
    } else {
      g.drawImage(texture.cadre_joueur_gris, posX_cadreJ1, posY_cadreJ1, largeur_cadre, hauteur_cadre, null);
      g.drawImage(texture.cadre_joueur, posX_cadreJ2, posY_cadreJ2, largeur_cadre, hauteur_cadre, null);
    }
    if (this.jeu.partieEnCours.joueur1().getClass() == Joueur.class) {
      g.drawImage(texture.boutonSauvegarde, posX_sauvegarder, posY_sauvegarder, largeur_sauvegarder,
          hauteur_sauvegarder, null);
    }
  }

  public void drawIle(Graphics g) {
    g.drawImage(this.texture.ile_joueur1, posX_ileJ1, posY_ileJ1, largeur_ileJ, hauteur_ileJ, null);
    g.drawImage(this.texture.ile_joueur2, posX_ileJ2, posY_ileJ2, largeur_ileJ, hauteur_ileJ, null);
    g.drawImage(this.texture.ile_montagne, posX_ileM, posY_ileM, largeur_ileM, hauteur_ileM, null);
  }

  public void drawPiecesVoleesJ2(Graphics g) {
    g.drawImage(texture.imagevol, posX_volJ2, poxY_volJ2, largeur_vol, hauteur_vol, null);
    g.drawImage(texture.pieceBlanche, posX_piece_voleeJ2, posY_piece_voleeJ2, largeur_piece, hauteur_piece, null);
  }

  public void drawbaPyramideJ1(Graphics g) {
    g.drawImage(texture.imagevol, posX_volJ1, poxY_volJ1, largeur_vol, hauteur_vol, null);
    Position actualpos = new Position(0, 0);
    int posX_depart = posX_ileJ1 + (int) (largeur_ileJ * 0.54) - ((largeur_piece * 6) / 2);
    int posX = posX_depart;
    int posY = posY_depart;
    int decalage = 0;
    for (int i = 0; i < this.jeu.partieEnCours.joueur1().getCamp().getHauteur(); i++) { // etage
      for (int j = 0; j < (this.jeu.partieEnCours.joueur1().getCamp().getLargeur() - i); j++) { // rang
        actualpos.rang = j;
        actualpos.etage = i;
        Piece c = this.jeu.partieEnCours.joueur1().getCamp().getPiece(actualpos);
        Image image;
        if (c == null) {
          image = colortoimage(Couleurs.VIDE);
        } else {
          image = colortoimage(c.getColor());
        }
        g.drawImage(image, posX, posY, largeur_piece, hauteur_piece, null);
        posX += largeur_piece;
      }
      decalage += largeur_piece / 2;
      posY -= hauteur_piece * 0.9;
      posX = posX_depart + decalage;// +decalage;
    }
  }

  public void drawbaPyramideJ2(Graphics g) {
    Position actualpos = new Position(0, 0);
    int posX_depart = posX_ileJ2 + (int) (largeur_ileJ * 0.49) - ((largeur_piece * 6) / 2);
    int posX = posX_depart;
    int posY = posY_depart;
    int decalage = 0;
    for (int i = 0; i < this.jeu.partieEnCours.joueur2().getCamp().getHauteur(); i++) { // etage
      for (int j = 0; j < (this.jeu.partieEnCours.joueur2().getCamp().getLargeur() - i); j++) { // rang
        actualpos.rang = j;
        actualpos.etage = i;
        Piece c = this.jeu.partieEnCours.joueur2().getCamp().getPiece(actualpos);
        Image image;
        if (c == null) {
          image = colortoimage(Couleurs.VIDE);
        } else {
          image = colortoimage(c.getColor());
        }
        g.drawImage(image, posX, posY, largeur_piece, hauteur_piece, null);
        posX += largeur_piece;
      }
      decalage += largeur_piece / 2;
      posY -= hauteur_piece * 0.9;
      posX = posX_depart + decalage;// +decalage;
    }
  }

  public void drawbaPyramideMilieu(Graphics g) {
    Position actualpos = new Position(0, 0);
    int posX_depart = posX_ileM + largeur_ileM / 2 - ((largeur_piece * 9) / 2);
    int posX = posX_depart;
    int posY = posY_depart;
    int decalage = 0;
    for (int i = 0; i < this.jeu.partieEnCours.getBaseMontagne().getHauteur(); i++) { // etage
      for (int j = 0; j < (this.jeu.partieEnCours.getBaseMontagne().getLargeur() - i); j++) { // rang
        actualpos.rang = j;
        actualpos.etage = i;
        Piece c = this.jeu.partieEnCours.getBaseMontagne().getPiece(actualpos);
        Image image;
        if (c == null) {
          image = colortoimage(Couleurs.VIDE);
        } else {
          image = colortoimage(c.getColor());
        }
        g.drawImage(image, posX, posY, largeur_piece, hauteur_piece, null);
        posX += largeur_piece;
      }
      decalage += largeur_piece / 2;
      posY -= hauteur_piece * 0.9;
      posX = posX_depart + decalage;// +decalage;
    }
  }

  public void drawtransparentpion(Graphics g) {

    for (int z = 0; z < this.jeu.partieEnCours.getBaseMontagne().piecesPosables().size(); z++) {
      Position actualpos = new Position(0, 0);
      int posX_depart = posX_ileM + largeur_ileM / 2 - ((largeur_piece * 9) / 2);
      int posX = posX_depart;
      int posY = posY_depart;
      int decalage = 0;
      if (this.jeu.partieEnCours.getBaseMontagne().piecesPosables().get(z).getPiece().getColor() == tocheck) {
        for (int i = 0; i < this.jeu.partieEnCours.getBaseMontagne().getHauteur(); i++) { // etage
          for (int j = 0; j < (this.jeu.partieEnCours.getBaseMontagne().getLargeur() - i); j++) { // rang
            actualpos.rang = j;
            actualpos.etage = i;
            if (i == this.jeu.partieEnCours.getBaseMontagne().piecesPosables().get(z).getPos().etage
                && j == this.jeu.partieEnCours.getBaseMontagne().piecesPosables().get(z).getPos().rang) {

              // System.out.println("Debug");
              Piece c = this.jeu.partieEnCours.getBaseMontagne().getPiece(actualpos);
              Image image;

              image = colortoimageTRANS(tocheck);
              g.drawImage(image, posX, posY, largeur_piece, hauteur_piece, null);

            }

            posX += largeur_piece;

          }

          decalage += largeur_piece / 2;
          posY -= hauteur_piece * 0.9;
          posX = posX_depart + decalage;// +decalage;

        }
      }
      showcheck = false;

    }

  }

  public Image colortoimageTRANS(Couleurs c) {
    switch (c) {
      case BLEU:
        return this.texture.pieceBleueTransparent;
      case VERT:
        return this.texture.pieceVertTransparent;
      case NOIR:
        return this.texture.pieceNoireTransparent;
      case JAUNE:
        return this.texture.pieceJauneTransparent;
      case ROUGE:
        return this.texture.pieceRougeTransparent;
      case BLANC:
        return this.texture.pieceBlancheTransparent;
      case NATUREL:
        return this.texture.pieceNatureTransparent;
    }
    return null;
  }

  public Image colortoimage(Couleurs c) {
    switch (c) {
      case VIDE:
        return this.texture.pieceVide;
      case BLEU:
        return this.texture.pieceBleue;
      case VERT:
        return this.texture.pieceVert;
      case NOIR:
        return this.texture.pieceNoire;
      case JAUNE:
        return this.texture.pieceJaune;
      case ROUGE:
        return this.texture.pieceRouge;
      case BLANC:
        return this.texture.pieceBlanche;
      case NATUREL:
        return this.texture.pieceNature;
    }
    return null;
  }

  public Couleurs tocheck;
  public boolean showcheck;

}