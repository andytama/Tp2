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

    String[][] plateau = Tp2.lirePlateau();
    Case[] tabDeJeu = Tp2.conversion(plateau);
    Joueur[] tabJoueurs;

    public Partie(Case[] tabDeJeu, Joueur[] tabJoueurs) {
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

    public void printPlateau(String[][] plateau) {
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public void printCase(Case[] tabJeu) {
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

    public void printJoueur() {
        System.out.println("***************** Les joueurs **********************");
        for (int i = 0; i < tabJoueurs.length; i++) {
            System.out.println(tabJoueurs[i].nom() + ": est sur la case " + tabJoueurs[i].getCaseJoueur().nom() + ". Argent: " + tabJoueurs[i].getArgent());
        }
    }

    private int montantTotal(Joueur player) {
        int montant = 0;
        for (Case i : player.getListProprietes()) {
            montant += i.cout();
        }
        montant += player.getArgent();
        player.setArgent(montant);
        player.setValeurTotal(montant);
        return montant;
    }

    public void finir(Joueur[] tabJoueurs) {
        HashMap<Joueur,Integer> a = leaderboard(tabJoueurs);
        System.out.println("Leaderboard: ");
        int k=0;
        for (Joueur i : a.keySet()) {
            System.out.println("#" + (k + 1) + " : " + i.nom() + " Valeur totale: " + a.get(i) + "$");
            k++;
        }
        System.out.println("Fermeture.");
        System.exit(0);
    }

    public HashMap<Joueur,Integer> leaderboard(Joueur[] tabJoueurs) {
        HashMap<Joueur,Integer> a = new HashMap<Joueur,Integer>();
        for (int i = 0; i < tabJoueurs.length; i++) {
            a.put(tabJoueurs[i], montantTotal(tabJoueurs[i]));
        }
    HashMap<Joueur,Integer> b = Tp2.sortByValue(a);
        return b;
    }

    public int[] survoler(int depart, int fin, Case[] tabJeu, int de) {
        ArrayList<Integer> caseSurvoler = new ArrayList<Integer>();

        if (fin > depart) {
            for (int i = depart + 1; i < fin; i++) {
                if (i >= 10) {
                    i = 0;
                }
                caseSurvoler.add(i);
            }
        } else {

            int temp1 = depart + 1;
            for (int i = depart + 1; i < depart + de - 1; i++) {

                caseSurvoler.add(temp1);
                if (temp1 == tabJeu.length - 1) {
                    temp1 = 0;
                    caseSurvoler.add(0);
                }
                temp1++;
            }
        }
        int[] tabCS = new int[caseSurvoler.size()];
        for (int i = 0; i < caseSurvoler.size(); i++) {
            tabCS[i] = caseSurvoler.get(i);
        }
        return tabCS;
    }

    public void demarrage() {
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
                int temporaire = pos[i] - de;
                if (temporaire < 0) {
                    temporaire = temporaire + 10;
                }
                int[] temp = survoler(temporaire, pos[i], tabDeJeu, de);
                for (int j = 0; j < temp.length - 1; j++) {
                    int index = temp[j];
                    try {
                        tabDeJeu[index].survoler(tabJoueurs[i]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                tabDeJeu[pos[i]].menuChoix(tabJoueurs[i], tabDeJeu[pos[i]].owner(), de, tabDeJeu[pos[i]]);
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
}
