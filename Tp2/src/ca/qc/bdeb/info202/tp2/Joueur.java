/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info202.tp2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Andy
 */
public class Joueur implements Serializable {

    String nom;
    int numeroDeJoueur;
    int argent = 400;
    int pos = 2;
    private Case caseJoueur;
    int valeurTotal;
    private ArrayList<Case> listProprietes = new ArrayList<Case>();

    public Joueur(String nom, int numeroDeJoueur, int pos, Case caseJoueur) {
        this.nom = nom;
        this.numeroDeJoueur = numeroDeJoueur;
        this.pos = pos;
        this.caseJoueur = caseJoueur;
    }

    public void add(Case element) {
        listProprietes.add(element);
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public Case getCaseJoueur() {
        return caseJoueur;
    }

    public void setCaseJoueur(Case caseJoueur) {
        this.caseJoueur = caseJoueur;
    }

    public String nom() {
        return nom;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public ArrayList<Case> getListProprietes() {
        return listProprietes;
    }

    public int getValeurTotal() {
        return valeurTotal;
    }

    public void setValeurTotal(int valeurTotal) {
        this.valeurTotal = valeurTotal;
    }
}
