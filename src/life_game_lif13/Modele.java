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

	public void lancerThread () {
		new ThreadSimu(1,
					   this).start();
	}

    @Override
    public void run() {
        System.out.println("calcul: ");
		calcul();
		setChanged();
		notifyObservers();
    }

    public void calcul(){
        grille.etatSuivant();
    }

}
