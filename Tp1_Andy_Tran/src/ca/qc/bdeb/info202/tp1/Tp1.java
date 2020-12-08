/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info202.tp1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author 1938297
 */
public class Tp1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        validationEtIntrus obj = new validationEtIntrus();
        
        String[] Mots = Mots();
        char[][] Grille = Grille();

        System.out.println("Démarrage");
        boolean grilleIsValide = false, motsIsValide = false;
        if (obj.fichierGrilleExiste()) { //chargement et valiation de la grille
            System.out.println("Chargement de la grille…ok");
        } else {
            System.out.println("Chargement de la grille: Le fichier grille.txt n'existe pas.");
        }
        if (obj.fichierGrilleExiste()) { //validation de la grille
            if (obj.verificationCARRE(Grille) && obj.verificationLETTRES(Grille)) {
                grilleIsValide = true;
                System.out.println("Validation de la grille…ok");
            } else if (!obj.verificationCARRE(Grille)) {
                System.out.println("Validation de la grille: La grille n'est pas carre");
            } else if (!obj.verificationLETTRES(Grille)) {
                System.out.println("Validation de la grille: La grille comprend des caracteres qui ne sont pas des lettres");
            }
        } else {
            System.out.println("Validation de la grille: Le fichier grille.txt n'existe pas.");
        }

        if (obj.fichierMotsExiste()) { //chargement de la liste mots
            System.out.println("Chargement de la liste de mots…ok");
        } else {
            System.out.println("Chargement de la liste de mots: Le fichier mots.txt n'existe pas.");
        }
        if (obj.fichierMotsExiste()) { //validation de la liste de mots
            if (obj.verificationMOTSexiste(Mots) && obj.verificationMOTSisLetter(Mots)) {
                motsIsValide = true;
                System.out.println("Validation de la liste de mots…ok");
            } else if (!obj.verificationMOTSexiste(Mots)) {
                System.out.println("Validation de la liste de mot: Il n'y a aucun mot dans le fichier mots.txt");
            } else if (!obj.verificationMOTSisLetter(Mots)) {
                System.out.println("Validation de la liste de mots: La liste de mots comprend des caracteres qui ne sont pas des lettres.");
            }
        } else {
            System.out.println("Validation de la liste de mots: Le fichier mots.txt n'existe pas.");
        }

        if (grilleIsValide && motsIsValide) {
            String[] motsValide = obj.motsValides(Grille, Mots);
            String[] intrus = obj.intrus(Mots, motsValide);
            ecritureIntrus(intrus);
            System.out.println("Recherche des intrus…ok");
            if (intrus[0] != "") {
                System.out.println("Les intrus trouvés sont dans le fichier intrus.txt");
            } else {
                System.out.println("Aucun intru n'a ete retrouver.");
            }
        } else {
            System.out.println("Recherche des intrus: impossible a cause d'une erreure precedante");
            System.out.println("Le fichier intrus.txt n'as pas ete modifier.");
        }
        System.out.println("Traitement terminé.");
    }

    public static void ecritureIntrus(String[] intrus) { //print les intrus dans un fichier intrus.txt
        try {
            FileWriter myWriter = new FileWriter("intrus.txt");
            FileWriter myWriter1 = new FileWriter("intrus.txt");
            if (intrus[0] != "") {
                for (int i = 0; i < intrus.length; i++) {
                    myWriter.write(intrus[i] + "\n");
                }
                myWriter.close();
            } else {
                myWriter1.write("aucun intrus");
                myWriter1.close();
            }
        } catch (IOException e) {
        }
    }

    public static String[] Mots() { //lit le fichier mots.txt et le retourne en tableau de string
        int nbRangers = 0;
        int temp1 = 0, temp2 = 0;
        try {
            File mots = new File("mots.txt");
            Scanner sc = new Scanner(mots);
            while (sc.hasNextLine()) {
                nbRangers++;
                String temp = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
        }

        String[] Mots = new String[nbRangers];
        try {
            File mots = new File("mots.txt");
            Scanner sc = new Scanner(mots);
            while (sc.hasNextLine()) {
                Mots[temp1] = sc.nextLine().trim().toLowerCase();
                temp1++;
            }
            return Mots;
        } catch (FileNotFoundException e) {
            return Mots;
        } finally {
            return Mots;
        }
    }

    public static char[][] Grille() { //lit le fichier grille.txt et le retourne en tableau de char
        int nbRangers = 0;
        int temp1 = 0;
        char[][] GrilleC = null;
        if (GrilleC == null) {
            try {
                File grille = new File("grille.txt");
                Scanner sc = new Scanner(grille);
                Scanner sc1 = new Scanner(grille);
                while (sc.hasNextLine()) {
                    nbRangers++;
                    String temp = sc.nextLine();
                }
                String[] Grille = new String[nbRangers];
                while (sc1.hasNextLine()) {
                    Grille[temp1] = sc1.nextLine().trim().toLowerCase();
                    temp1++;
                }

                GrilleC = new char[Grille.length][];
                for (int x = 0; x < Grille.length; x++) {
                    GrilleC[x] = Grille[x].toCharArray();
                }
                return GrilleC;
            } catch (FileNotFoundException e) {
            }
        }
        return GrilleC;
    }
}
