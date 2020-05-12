/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.info202.tp2;

/**
 *
 * @author Andy
 */
abstract public class Propriete extends Case {

    abstract int position();

    abstract String nom();

    abstract boolean appartient();

    abstract int cout();

    abstract void acheter(Joueur joueur);

    abstract int loyer();

    abstract Joueur owner();

    abstract public void effectuerAction(Joueur player, int de);//methode effectuerAction

    abstract public void survoler(Joueur player); //methode survolerCase----------------------------------------------------------------------------------------------------
}
