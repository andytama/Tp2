/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info202.tp2;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Andy
 */
public class Partie implements Serializable {

    private String[][] plateau = Tp2.lirePlateau();
    private Case[] tabDeJeu = Tp2.conversion(plateau);
    private Joueur[] tabJoueurs;

    public Partie(Case[] tabDeJeu, Joueur[] tabJoueurs) {//constructeur de partie
        this.tabDeJeu = tabDeJeu;
        this.tabJoueurs = tabJoueurs;
    }

    public Case[] getTabDeJeu() {
        return tabDeJeu;
    }

    public Joueur[] getTabJoueurs() {
        return tabJoueurs;
    }

    public void setTabJoueurs(Joueur[] tabJoueurs) {
        this.tabJoueurs = tabJoueurs;
    }

    public void printPlateau(String[][] plateau) {//afficher le plateau de string
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public void printCase(Case[] tabJeu) {//afficher le tableau final(Case[])
        System.out.println("***************** Les cases   **********************");
        for (int i = 0; i < tabJeu.length; i++) {
            System.out.print(tabJeu[i].position() + ": " + tabJeu[i].nom() + " Cout: " + tabJeu[i].cout() + " Loyer: " + tabJeu[i].loyer() + " Proprietaire: ");
            if (tabJeu[i].owner() == null) {
                System.out.println("aucun");
            } else {
                System.out.println(tabJeu[i].owner().nom());
            }
        }
    }

    public void printJoueur() {//afficher les joueurs
        System.out.println("***************** Les joueurs **********************");
        for (int i = 0; i < tabJoueurs.length; i++) {
            System.out.println(tabJoueurs[i].nom() + ": est sur la case " + tabJoueurs[i].getCaseJoueur().nom() + ". Argent: " + tabJoueurs[i].getArgent());
        }
    }

    private int montantTotal(Joueur player) {//calcul le montant total d'un jouer incluant ses proprietes
        int montant = 0;
        for (Case i : player.getListProprietes()) {
            montant += i.cout();
        }
        montant += player.getArgent();
        player.setArgent(montant);
        player.setValeurTotal(montant);
        return montant;
    }

    public void finir(Joueur[] tabJoueurs) {//termine le jeu et affiche le leaderboard
        HashMap<Joueur, Integer> a = leaderboard(tabJoueurs);
        System.out.println("Leaderboard: ");
        int k = 0;
        for (Joueur i : a.keySet()) {
            System.out.println("#" + (k + 1) + " : " + i.nom() + " Valeur totale: " + a.get(i) + "$");
            k++;
        }
        System.out.println("Fermeture.");
        System.exit(0);
    }

    public HashMap<Joueur, Integer> leaderboard(Joueur[] tabJoueurs) {//lors du terminage du jeu, classer les joueurs selon leur montant d'argent total
        HashMap<Joueur, Integer> a = new HashMap<Joueur, Integer>();
        for (int i = 0; i < tabJoueurs.length; i++) {
            a.put(tabJoueurs[i], montantTotal(tabJoueurs[i]));
        }
        HashMap<Joueur, Integer> b = Tp2.sortByValue(a);
        return b;
    }

    public void demarrage() {//demarrer le jeu
        Scanner sc = new Scanner(System.in);
        int[] pos = new int[tabJoueurs.length];
        int i = 0;
        int action = 0;
        do {
            System.out.println("");
            System.out.println("");
            printJoueur();
            printCase(tabDeJeu);
            action = Tp2.menu(tabJoueurs[i]);
            if (action == 1) {
                int de = De.lancer();
                System.out.println("Vous avez obtenu: " + de);
                pos[i] = pos[i] + de;
                if (pos[i] > tabDeJeu.length - 1) {
                    int newPos = pos[i] - tabDeJeu.length;
                    tabJoueurs[i].setPos(newPos);
                    tabJoueurs[i].setCaseJoueur(tabDeJeu[newPos]);
                    pos[i] = newPos;
                } else {
                    tabJoueurs[i].setPos(pos[i]);
                    tabJoueurs[i].setCaseJoueur(tabDeJeu[pos[i]]);
                }
                int positionInitiale = pos[i] - de;
                if (positionInitiale < 0) {
                    positionInitiale = positionInitiale + tabDeJeu.length;
                }
                int[] temp = indexDesCasesSurvoler(positionInitiale, de, tabDeJeu);
                for (int j = 0; j < temp.length - 1; j++) {
                    int index = temp[j];
                    try {
                        tabDeJeu[index].survoler(tabJoueurs[i]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                tabDeJeu[pos[i]].effectuerAction(tabJoueurs[i], de);
            }
            i++;
            if (i >= tabJoueurs.length) {
                i = 0;
            }
            if (Tp2.joueurFaillite(tabJoueurs) && action == 1) {
                finir(tabJoueurs);
            }

        } while (!Tp2.joueurFaillite(tabJoueurs) && action == 1);
        if (action == 2) {
            Tp2.faireSauvegarde(this);
            System.out.println("Partie sauvegarder.\nFermeture.");
            System.exit(0);
        }
        if (action == 3) {
            finir(tabJoueurs);
        }
    }

    public String toString() {//afficher l'etat du jeu(le plateau et les joueurs)
        String listeJoueurs = "**********Les Joueurs***********";
        for (Joueur player : tabJoueurs) {
            listeJoueurs += "\n" + player.nom() + " est sur la case " + player.getCaseJoueur().nom() + " et possede " + player.getArgent() + "$\n";
        }
        String listeCase = "***********Les Cases***************";
        for (int i = 0; i < tabDeJeu.length; i++) {
            listeCase += i + ": " + tabDeJeu[i].nom() + "\n";
            Joueur proprietaire = ((Propriete) (tabDeJeu[i])).owner();
            if (proprietaire == null) {
                listeCase += "Proprietaire : aucun\n";
            } else {
                listeCase += "Proprietaire : " + proprietaire.nom() + "\n";
            }
        }
        return listeJoueurs + listeCase;
    }

    public int[] indexDesCasesSurvoler(int depart, int de, Case[] tabDeJeu) {//retourne les index que le jouer a survoler, sans atterir
        int[] tabDesIndex = new int[de];
        int j = 0;
        for (int i = depart + 1; i <= (de + depart); i++) {
            tabDesIndex[j] = (i % (tabDeJeu.length));
            j++;
        }
        return tabDesIndex;
    }
}
