/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;

/**
 *
 * @author Andy
 */
public class taxe extends Case implements Serializable {

    private final int cout, pos, loyer;
    private final String nom;

    public taxe(String nom, int cout, int pos, int loyer) {
        this.pos = pos;
        this.loyer = loyer;
        this.cout = cout;
        this.nom = nom;
    }

    public int position() {
        return pos;
    }

    public String nom() {
        return nom;
    }

    public boolean estLibre() {
        return false;
    }

    public boolean appartient() {
        return false;
    }

    public int loyer() {
        return loyer;
    }

    public int cout() {
        return 0;
    }

    public void acheter(Joueur player) {
    }

    public Joueur owner() {
        return null;
    }

    public void payer(Joueur player) {
        System.out.println("Vous devez payer: " + loyer + "$ en taxes.");
        if (player.getArgent() < loyer) {
            System.out.println("Vous avez fait faillite.");
        }
        player.setArgent(player.getArgent() - loyer);
    }

    public void survoler(Joueur player) {
        int argent = player.getArgent();
        int loyerS = (int) (loyer * 0.1);
        System.out.println("Vouz avez payer: " + loyerS + "$ en taxes. (Vous avez passer dessus " + nom() + ": 0.1*" + loyer() + ")");
        player.setArgent(argent - loyerS);
    }

    public void menuChoix(Joueur player, Joueur owner, int de, Case casee) {
        payer(player);
    }
}
