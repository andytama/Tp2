/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info202.tp2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Andy
 */
public class Tp2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        debut();
    }

    public static void debut() { //lancer le jeu
        String[][] plateau = lirePlateau();
        Case[] tabDeJeu = conversion(plateau);
        Partie partie = null;
        Joueur[] tabJoueurs = new Joueur[3];
        if (verificationPlateau(plateau)) {
            System.out.println("Verification du plateau de jeu... ok");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("-------MONOPOLY----------");
        System.out.println("1) Charger la partie de sauvegarde\n"
                + "2) Démarrer une nouvelle partie\n"
                + "3) Quitter\n"
                + "Entrez votre choix: ");
        boolean continuer = true;
        int choix = 0;
        while (continuer) {
            try {
                choix = Integer.parseInt(sc.nextLine());
                if (choix < 1 || choix > 3) {
                    System.out.println("Donnee invalide. Re-entrez votre choix. (1, 2, 3)");
                    continuer = true;
                } else {
                    continuer = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Donnee invalide. Re-entrez votre choix. (1, 2, 3)");
                continuer = true;
            }
        }
        switch (choix) {
            case 1:
                partie = lireSauvegarde();
                tabDeJeu = partie.getTabDeJeu();
                tabJoueurs = partie.getTabJoueurs();
                partie.setTabJoueurs(tabJoueurs);
                break;
            case 2:
                if (!verificationPlateau(plateau)) {
                    System.out.println("PlateauDeJeu.csv est invalide.");
                    System.exit(0);
                }
                System.out.println("Saisie de joueurs: Entrez le nombre de joueurs:\n"
                        + "1. 2 joueurs\n"
                        + "2. 3 joueurs");
                int nbJoueurs = 1;
                boolean continuer1 = true;
                while (continuer1) {
                    try {
                        nbJoueurs = Integer.parseInt(sc.nextLine());
                        if (nbJoueurs < 1 || nbJoueurs > 2) {
                            System.out.println("Donnee invalide. Re-entrez votre choix. (1, 2)");
                            continuer1 = true;
                        } else {
                            continuer1 = false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Donnee invalide. Re-entrez votre choix. (1, 2)");
                        continuer1 = true;
                    }
                }

                Joueur player1 = new Joueur("null", 0, 0, tabDeJeu[0]);
                Joueur player2 = new Joueur("null", 1, 0, tabDeJeu[0]);
                Joueur player3 = new Joueur("null", 2, 0, tabDeJeu[0]);
                if (nbJoueurs == 1) {
                    System.out.println("Entrez le nom du 1er joueur: ");
                    String nom = sc.nextLine();
                    player1.setNom(nom);
                    System.out.println("Entrez le nom du 2e joueur: ");
                    String nom1 = sc.nextLine();
                    player2.setNom(nom1);
                } else {
                    System.out.println("Entrez le nom du 1er joueur: ");
                    String nom = sc.nextLine();
                    player1.setNom(nom);
                    System.out.println("Entrez le nom du 2e joueur: ");
                    String nom1 = sc.nextLine();
                    player2.setNom(nom1);
                    System.out.println("Entrez le nom du 3e joueur:");
                    String nom2 = sc.nextLine();
                    player3.setNom(nom2);
                }

                Joueur[] tabJoueur = new Joueur[nbJoueurs + 1];
                if (tabJoueur.length == 2) {
                    tabJoueur[0] = player1;
                    tabJoueur[1] = player2;
                } else {
                    tabJoueur[0] = player1;
                    tabJoueur[1] = player2;
                    tabJoueur[2] = player3;
                }
                partie = new Partie(tabDeJeu, tabJoueur);
                partie.setTabJoueurs(tabJoueur);
                break;
            case 3:
                System.out.println("Merci d'avoir jouer.");
                System.exit(0);
                break;
        }

        partie.demarrage();
    }

    public static void faireSauvegarde(Partie partie) { //sauvegarde la partie dans un fichier nommer sauvegarde.bin
        try (FileOutputStream FOS = new FileOutputStream("sauvegarde.bin")) {
            ObjectOutputStream OOS = new ObjectOutputStream(FOS);
            OOS.writeObject(partie);
        } catch (IOException e) {
            System.out.println("Erreur IOException.");
        }
    }

    public static Partie lireSauvegarde() { //lire le fichier savegarde.bin
        Partie partie = null;
        try (FileInputStream FIS = new FileInputStream("sauvegarde.bin")) {
            ObjectInputStream OIS = new ObjectInputStream(FIS);

            partie = (Partie) OIS.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Aucune sauvegarde retrouver.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Erreur IOException");
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("Aucune classe retrouver.");
            System.exit(0);
        }
        return partie;
    }

    public static String[][] lirePlateau() { //lit le fichier PlateauDeJeu.csv
        int nbRangers = 0;
        int temp1 = 0;
        String[][] GrilleC = null;
        if (GrilleC == null) {
            try {
                File grille = new File("PlateauDeJeu.csv");
                Scanner sc = new Scanner(grille);
                Scanner sc1 = new Scanner(grille);
                while (sc.hasNextLine()) {
                    nbRangers++;
                    String temp = sc.nextLine();
                }
                String[] Grille = new String[nbRangers];
                while (sc1.hasNextLine()) {
                    Grille[temp1] = sc1.nextLine();
                    temp1++;
                }
                GrilleC = new String[Grille.length][];
                for (int x = 0; x < Grille.length; x++) {
                    GrilleC[x] = Grille[x].split(",");
                }
                System.out.println("Chargement du plateau de jeu... ok");
                return GrilleC;
            } catch (FileNotFoundException e) {
                System.out.println("Fichier PlateauDeJeu.csv pas retrouver.");
                System.exit(0);
            }
        }
        return GrilleC;
    }

    public static Case[] conversion(String[][] tab) { //convertit le fichier plateau.csv dans un arraylist de Case
        ArrayList<Case> arrayCase = new ArrayList<Case>();

        for (int i = 0; i < tab.length; i++) {
            if (tab[i][0].contentEquals("D")) {
                arrayCase.add(new depart());
            } else if (tab[i][0].contentEquals("T")) {
                arrayCase.add(new terrain(Integer.parseInt(tab[i][3]), i - 1, Integer.parseInt(tab[i][4]), tab[i][1]));
            } else if (tab[i][0].contentEquals("SP")) {
                arrayCase.add(new servicePublic(i - 1, tab[i][1], Integer.parseInt(tab[i][3]), Integer.parseInt(tab[i][4])));
            } else if (tab[i][0].contentEquals("Tx")) {
                arrayCase.add(new taxe(tab[i][1], Integer.parseInt(tab[i][3]), i - 1, Integer.parseInt(tab[i][4])));
            } else if (tab[i][0].contentEquals("P")) {
                arrayCase.add(new stationnementGratuit(tab[i][1], i - 1));
            }
        }
        Case[] tabDeJeu = new Case[arrayCase.size()];
        for (int i = 0; i < tabDeJeu.length; i++) {
            tabDeJeu[i] = arrayCase.get(i);
        }
        return tabDeJeu;
    }

    public static boolean verificationPlateau(String[][] tab) {//verifie si le plateau est valide et retourne boolean
        boolean depart = false, propriete = false, taxe = false, stationnement = false, contient4types = false, premierCase = false;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i][0].contentEquals("D")) {
                depart = true;
            }
        }
        for (int i = 0; i < tab.length; i++) {
            if (tab[i][0].contentEquals("T") || tab[i][0].contentEquals("SP")) {
                propriete = true;
            }
        }
        for (int i = 0; i < tab.length; i++) {
            if (tab[i][0].contentEquals("Tx")) {
                taxe = true;
            }
        }
        for (int i = 0; i < tab.length; i++) {
            if (tab[i][0].contentEquals("P")) {
                stationnement = true;
            }
        }
        if (depart && propriete && taxe && stationnement) {
            contient4types = true;
        } else {
            contient4types = false;
        }
        if (tab[0][0].contentEquals("D") || tab[1][0].contentEquals("D")) {
            premierCase = true;
        }
        boolean temporaire = false;
        if (premierCase && contient4types) {
            temporaire = true;
        }
        return temporaire;
    }

    public static boolean joueurFaillite(Joueur[] tabJoueur) {
        for (int i = 0; i < tabJoueur.length; i++) {
            if (tabJoueur[i].getArgent() < 0) {
                return true;
            }
        }
        return false;
    }

    public static int menu(Joueur player) {//methode pour afficher le menu de choix et retourne le choix du joueur
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        System.out.println("C'est le tour de: " + player.nom());
        System.out.println("1) Lancer le dé\n"
                + "2) Sauvegarder et quitter\n"
                + "3) Mettre fin à la partie et quitter\n"
                + "Faites votre choix:");
        boolean continuer1 = true;
        while (continuer1) {
            try {
                choix = Integer.parseInt(sc.nextLine());
                if (choix != 1 && choix != 2 && choix != 3) {
                    System.out.println("Donnee invalide. Re-entrez votre choix. (1,2,3)");
                    continuer1 = true;
                } else {
                    continuer1 = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Donnee invalide. Re-entrez votre choix. (1,2,3)");
                continuer1 = true;
            }
        }
        return choix;
    }

    public static HashMap<Joueur, Integer> sortByValue(HashMap<Joueur, Integer> hm) { //trier le HashMap par values en decroissance (trier la liste de gagnants)
        List<Map.Entry<Joueur, Integer>> list
                = new LinkedList<Map.Entry<Joueur, Integer>>(hm.entrySet());
        LinkedHashMap<Joueur, Integer> reverseSortedMap = new LinkedHashMap<>();
        hm.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return reverseSortedMap;
    }
}
