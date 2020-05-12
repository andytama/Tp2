/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author Andy
 */
public class terrain extends Propriete implements Serializable {

    private final int cout, pos, loyer;
    private final String nom;
    private boolean appartient;
    private Joueur owner;

    public terrain(int cout, int pos, int loyer, String nom) {
        this.cout = cout;
        this.pos = pos;
        this.nom = nom;
        appartient = false;
        this.loyer = loyer;
    }

    public int position() {
        return pos;
    }

    public String nom() {
        return nom;
    }

    public boolean estLibre() {
        return true;
    }

    public void acheter(Joueur player) {
        appartient = true;
        owner = player;
    }

    public boolean appartient() {
        return appartient;
    }

    public int cout() {
        return cout;
    }

    public int loyer() {
        return loyer;
    }

    public Joueur owner() {
        return owner;
    }

    public void setOwner(Joueur owner) {
        this.owner = owner;
    }

    public void payerLoyer(Joueur player, Joueur owner) {
        System.out.println("Cette case appartient a: " + owner.nom());
        int argent = player.getArgent();
        System.out.println("Vous devez payer: " + loyer() + "$");
        if (argent < loyer()) {
            System.out.println("Vous avez fait faillite.");
        }
        player.setArgent(argent - loyer);
        owner.setArgent(owner.getArgent() + loyer);
    }

    public void effectuerAction(Joueur player, int de) {
        Scanner sc = new Scanner(System.in);
        String acheter = null;
        System.out.println("Vous avez atterit sur la case: " + nom());
        if (player == owner) {
            System.out.println("Cette case vous appartient. Vous n'avez rien a payer.");
        } else if (appartient) {
            payerLoyer(player, owner);
        } else {
            System.out.println("Cette case appartient à personne");
            System.out.println("Le prix est de: " + cout() + "$ Voulez-vous acheter la propriete? (o/n)");
            do {
                acheter = sc.nextLine();
                if (!acheter.equalsIgnoreCase("o") && !acheter.equalsIgnoreCase("oui") && !acheter.equalsIgnoreCase("n") && !acheter.equalsIgnoreCase("non")) {
                    System.out.println("Entree invalide. Re-entrez votre choix. (o/n)");
                }
            } while (!acheter.equalsIgnoreCase("o") && !acheter.equalsIgnoreCase("oui") && !acheter.equalsIgnoreCase("n") && !acheter.equalsIgnoreCase("non"));

            boolean temp = false;
            do {
                if ((acheter.equalsIgnoreCase("o") || acheter.equalsIgnoreCase("oui")) && (player.getArgent() >= cout())) {
                    player.setArgent(player.getArgent() - cout());
                    setOwner(player);
                    appartient = true;
                    temp = true;
                    player.add(this);
                } else if ((acheter.equalsIgnoreCase("o") || acheter.equalsIgnoreCase("oui")) && player.getArgent() <= cout()) {
                    System.out.println("Vous-n'avez pas assez d'argent.");
                    temp = true;
                } else {
                    System.out.println("Vous n'avez pas acheter la propriete.");
                    temp = true;
                }
            } while (!temp);
        }
    }

    public void survoler(Joueur player) {
    }
}
