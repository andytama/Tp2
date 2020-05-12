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

    private String nom;
    private int numeroDeJoueur;
    private int argent = 400;
    private int pos;
    private Case caseJoueur;
    private int valeurTotal;
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
    public void setNom(String nom){
        this.nom=nom;
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
