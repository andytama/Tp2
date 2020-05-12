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
public class stationnementGratuit extends Case implements Serializable {

    private final int pos;
    private final String nom;

    public stationnementGratuit(String nom, int pos) {
        this.nom = nom;
        this.pos = pos;
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

    public int cout() {
        return 0;
    }

    public void acheter(Joueur player) {
    }

    public int loyer() {
        return 0;
    }

    public Joueur owner() {
        return null;
    }

    public void effectuerAction(Joueur player, int de) {
        System.out.println("Vous avez atterit sur un stationnement gratuit.");
    }

    public void survoler(Joueur player) {
    }
}
