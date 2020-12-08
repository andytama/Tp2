/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info202.tp1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author 1938297
 */
public class validationEtIntrus {

    public boolean verificationCARRE(char[][] grille) { //verifie si la grille est carrer
        for (int i = 0; i < grille.length; i++) {
            int j = grille[0].length;
            if (grille[i].length != j) {
                return false;
            }
        }
        return true;
    }

    public boolean verificationLETTRES(char[][] Grille) { //verifie si la grille ne contient que des lettre
        boolean temp;
        boolean util = true;
        for (int i = 0; i < Grille.length; i++) {
            for (int j = 0; j < Grille[i].length; j++) {
                temp = Character.isLetter(Grille[i][j]);
                if (temp == false) {
                    util = false;
                }
            }
        }
        return util;
    }

    public boolean verificationMOTSexiste(String[] Mots) { //verifie si mots.txt contient un mot
        boolean util = false;
        if (Mots.length == 0) {
            util = false;
        } else {
            util = true;
        }
        return util;
    }

    public boolean verificationMOTSisLetter(String Mots[]) { //verifie si tous les caracteres dans mots.txt sont des lettre
        char[][] motsChar = new char[Mots.length][];
        for (int i = 0; i < Mots.length; i++) {
            motsChar[i] = Mots[i].toCharArray();
        }
        boolean temp;
        for (int i = 0; i < motsChar.length; i++) {
            for (int j = 0; j < motsChar[i].length; j++) {
                temp = Character.isLetter(motsChar[i][j]);
                if (temp == false) {
                    return false;
                }
            }
        }
        return true;
    }

    public char[][] Grille() { //lit le fichier grille.txt et le retourne en tableau de char
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

    public boolean NE(String mot, int x, int y) { //verifie si le mots se retrouve en direction ↗
        char[][] Grille = Grille();
        if ((y + 1) - mot.length() < 0 || x + mot.length() > Grille[0].length) { //return false si le mot est plus long que le reste de la grille en direction ↗
            return false;
        }
        int a = x, b = y;
        for (char lettre : mot.toCharArray()) { //verifie si les lettres de la grille en direction ↗ correspondent au mot
            if (Grille[b][a] != lettre) {
                return false;
            }
            a++;
            b--;
        }
        return true;
    }

    public boolean NW(String mot, int x, int y) { //verifie si le mots se retrouve en direction ↖
        char[][] Grille = Grille();
        if ((y + 1) - mot.length() < 0 || (x + 1) - mot.length() < 0) { //return false si le mot est plus long que le reste de la grille en direction ↖
            return false;
        }
        int a = x, b = y;
        for (char lettre : mot.toCharArray()) { //verifie si les lettres de la grille en direction ↖ correspondent au mot
            if (Grille[b][a] != lettre) {
                return false;
            }
            a--;
            b--;
        }
        return true;
    }

    public boolean SE(String mot, int x, int y) { //verifie si le mots se retrouve en direction ↘
        char[][] Grille = Grille();
        if (y + mot.length() > Grille.length || x + mot.length() > Grille[0].length) { //return false si le mot est plus long que le reste de la grille en direction ↘
            return false;
        }
        int a = x, b = y;
        for (char lettre : mot.toCharArray()) { //verifie si les lettres de la grille en direction ↘ correspondent au mot
            if (Grille[b][a] != lettre) {
                return false;
            }
            a++;
            b++;
        }
        return true;
    }

    public boolean SW(String mot, int x, int y) { //verifie si le mots se retrouve en direction ↙
        char[][] Grille = Grille();
        if (y + mot.length() > Grille.length || (x + 1) - mot.length() < 0) { //return false si le mot est plus long que le reste de la grille en direction ↙
            return false;
        }
        int a = x, b = y;
        for (char lettre : mot.toCharArray()) { //verifie si les lettres de la grille en direction ↙ correspondent au mot
            if (Grille[b][a] != lettre) {
                return false;
            }
            a--;
            b++;
        }
        return true;
    }

    public String[] motsValides(char[][] Grille, String[] Mots) { //utilise les methodes de direction pour chercher et retourner les mots qui se retrouvent en diagonale dans la grille
        String solutions = "";
        for (int i = 0; i < Mots.length; i++) {
            if(Mots[i].isEmpty()){ //pour eviter une ligne vide dans le fichier mots.txt
                break;
            }else{
            char premiereLettre = Mots[i].charAt(0);
            for (int y = 0; y < Grille.length; y++) {
                for (int x = 0; x < Grille[0].length; x++) {
                    if (Grille[y][x] == premiereLettre) {
                        if (NE(Mots[i], x, y)) {
                            solutions = solutions + " " + Mots[i];
                        } else if (NW(Mots[i], x, y)) {
                            solutions = solutions + " " + Mots[i];
                        } else if (SE(Mots[i], x, y)) {
                            solutions = solutions + " " + Mots[i];
                        } else if (SW(Mots[i], x, y)) {
                            solutions = solutions + " " + Mots[i];
                        }
                    }
                }
            }
            }
        }
        solutions = solutions.trim();
        String[] motsValide = solutions.split(" ");
        return motsValide;
    }

    public String[] intrus(String[] mots, String[] motsValide) { //compare la liste de mots et celle de mots valides, et retourne les mots qui ne sont pas dans la grille
        String tabintrus = "";
        for (int i = 0; i < mots.length; i++) {
            if (contient(mots, motsValide, i)) {
                tabintrus = tabintrus + mots[i] + " ";
            }
        }
        tabintrus.trim();
        String[] tabIntrus = tabintrus.split(" ");
        return tabIntrus;
    }

    public boolean contient(String[] mots, String[] motsValide, int i) { //methode utiliser dans la methode intrus
        for (int k = 0; k < motsValide.length; k++) {
            if (mots[i].contains(motsValide[k])) {
                return false;
            }
        }
        return true;
    }

    public boolean fichierMotsExiste() { //verifie si le fichier mots.txt existe
        boolean util = true;
        try {
            File mots = new File("mots.txt");
            Scanner sc = new Scanner(mots);
        } catch (FileNotFoundException e) {
            util = false;
        }
        return util;
    }

    public boolean fichierGrilleExiste() { // verifier si le fichier grille.txt existe
        boolean util = true;
        try {
            File mots = new File("grille.txt");
            Scanner sc = new Scanner(mots);
        } catch (FileNotFoundException e) {
            util = false;
        }
        return util;
    }
}
