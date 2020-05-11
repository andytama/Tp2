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
public class depart extends Case implements Serializable {

    private final int cout = 0, pos = 0, loyer = -50;
    private final String nom = "Depart";

    public boolean estLibre() {
        return false;
    }

    public int cout() {
        return 0;
    }

    public void acheter(Joueur player) {
    }

    public Joueur owner() {
        return null;
    }

    public String nom() {
        return nom;
    }

    public int position() {
        return pos;
    }

    public int loyer() {
        return loyer;
    }

    public boolean appartient() {
        return false;
    }

    public void payer(Joueur player) {
        System.out.println("Vous avez survoler la case de depart. (+50$)");
        player.setArgent(player.getArgent() - loyer());
        System.out.println("Votre argent: " + player.getArgent() + "$ (+50$)");
    }

    public void menuChoix(Joueur player, Joueur owner, int de, Case casee) {
        payer(player);
    }

    public void survoler(Joueur player) {
        System.out.println("Vous avez passer la case de depart. (+50$)");
        player.setArgent(player.getArgent() + 50);
    }
}
