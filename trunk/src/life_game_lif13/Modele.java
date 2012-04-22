/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author t0rp
 */
public class Modele extends Observable implements Runnable {
    Grille grille;
	ThreadSimu t;

    public Modele(){
		this(10,10);
	}

	public Modele(int x, int y){
		grille = new Grille(x,y);
		grille.initGrille();
		t = new ThreadSimu(1f, this);
	}

	public Grille getGrille () {
		return grille;
	}

	public void lancerThread () {
		t.start();
	}

    @Override
    public void run() {
        try {
            calcul();
        } catch (IOException ex) {
            Logger.getLogger(Modele.class.getName()).log(Level.SEVERE, null, ex);
        }
		setChanged();
		notifyObservers();
    }

    public void calcul() throws IOException{
        grille.etatSuivant();
    }

	public boolean estVivante(int x, int y) {
		return grille.estVivante(x,y);
	}

	public void switchPause () {
		t.setEtatExec(!t.isEtatExec());
	}

	public void clear () {
		grille.clearGrille();
	}
        
       
}
