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
public class servicePublic extends Propriete implements Serializable {

    private final int cout;
    private final int pos;
    private final String nom;
    private boolean appartient;
    private Joueur owner;
    private int loyer;

    public servicePublic(int pos, String nom, int cout, int loyer) {
        this.pos = pos;
        this.nom = nom;
        this.cout = cout;
        this.loyer = loyer;
    }

    public Joueur owner() {
        return owner;
    }

    public int loyer() {
        return loyer;
    }

    public void setLoyer(int nouveau) {
        this.loyer = nouveau;
    }

    public void acheter(Joueur player) {
        appartient = true;
        owner = player;
    }

    public int cout() {
        return cout;
    }

    public boolean appartient() {
        return appartient;
    }

    public boolean estLibre() {
        return true;
    }

    public String nom() {
        return nom;
    }

    public int position() {
        return pos;
    }

    public void setOwner(Joueur owner) {
        this.owner = owner;
    }

    public void payerLoyer(Joueur player, int De, Joueur owner) {
        int loyerS = De * 10;
        System.out.println("Ce service publique appartient a: " + owner.nom());
        System.out.println("Vous devez payer: " + loyerS + "$ (" + De + "*10)");
        if (player.getArgent() < loyerS) {
            System.out.println("Vous avez fait faillite.");
        }
        player.setArgent(player.getArgent() - loyerS);
        owner.setArgent(owner.getArgent() + loyerS);
    }

    public void effectuerAction(Joueur player, int De) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Vous avez atterit sur la case: " + nom());
        if (appartient) {
            payerLoyer(player, De, owner);
        } else {
            System.out.println("Cette case appartient à personne");
            System.out.println("Le prix est de: " + cout() + "$ Voulez-vous acheter la propriete? (o/n)");
            String acheter = sc.nextLine();
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
