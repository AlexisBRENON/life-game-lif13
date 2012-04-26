/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.util.HashMap;
import java.util.Observable;

/**
 *
 * @author t0rp
 */
public class Modele extends Observable implements Runnable {
    private Grille grille;
	private ThreadSimu thr;
	private Motif pattern;
	private int nbThread;
	private int nbIter;
	private int threadDone;

    public Modele(){
		this(10,10);
	}

	public Modele(int x, int y){
		this(x,y,1);
	}

	public Modele(int x, int y, int nbThread){
		this(x, y, 1f, nbThread);
	}

	public Modele(int x, int y, float timeStep, int nbThread) {
		this.grille = new Grille(x,y);
		this.nbIter = 0;
		this.thr = new ThreadSimu(timeStep, this, nbThread);
		/* Create the threads which works on the model */
		this.nbThread = nbThread;
		/* Define the starting pattern used */
		this.pattern = new Motif(1, 1);
		this.pattern.addPoint(0, 0);
	}

	public void reInit() {
		grille.initGrille();
		nbIter = 0;
	}
	public void clear () {
		grille.clearGrille();
		nbIter = 0;
	}

	public void lancerSimulation () {
		thr.start();
	}

    @Override
    public void run() {
        calcul();
    }

	public void endedCalcul () {
		grille.swap();
		nbIter++;
		threadDone = 0;
		setChanged();
		notifyObservers();
	}

	private synchronized int getThreadNum () {
		int result = threadDone;
		threadDone++;
		return result;
	}

    public void calcul() {
		grille.etatSuivant(nbThread, getThreadNum());
    }

	public boolean estVivante(int x, int y) {
		return grille.estVivante(x,y);
	}

	public boolean estVivante(Coordonnee c) {
		return grille.estVivante(c);
	}

	public void setPaused (boolean b) {
		thr.setPaused(b);
	}
	public boolean isPaused () {
		return (thr.isPaused());
	}
	public void switchPause () {
		thr.setPaused(!thr.isPaused());
	}

	public void addCellule (Coordonnee c) {
		grille.addCellule(c);
	}
	public void removeCellule (Coordonnee c) {
		grille.removeCellule(c);
	}
	public void addMotif (Coordonnee c) {
		grille.addMotif(c, pattern);
	}


	public int getNbIter () {
		return nbIter;
	}

	public int getNbThread () {
		return nbThread;
	}

	public Motif getPattern () {
		return pattern;
	}
	public void setPattern (Motif pattern) {
		this.pattern = pattern;
	}

	public Grille getGrille () {
		return grille;
	}



}
