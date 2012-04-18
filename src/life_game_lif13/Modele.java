/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.util.Observable;

/**
 *
 * @author t0rp
 */
public class Modele extends Observable implements Runnable {
    Grille grille;

    public Modele(){
		grille = new Grille(10,10);
	}

	public Grille getGrille () {
		return grille;
	}

	public Modele(int x, int y){
		grille = new Grille(x,y);
	}

	public void lancerThread () {
		new ThreadSimu(1,
					   this).start();
	}

    @Override
    public void run() {
		calcul();
		setChanged();
		notifyObservers();
    }

    public void calcul(){
        grille.etatSuivant();
    }

	public boolean estVivante(int x, int y) {
		return grille.estVivante(x,y);
	}
}
