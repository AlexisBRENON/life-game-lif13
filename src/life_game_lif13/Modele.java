/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.util.Observable;

/**
 *
 * @author t0rp - Alexis
 *
 * @class Implementation of the simulation model. This model extends Observable
 * because the View of the MVC observe it. Moreover, it's a Runnable object, its
 * run method make all calculations.
 */
public class Modele extends Observable implements Runnable {
    private Grille grille; /**< The grid containing cells */
	private ThreadSimu thr; /**< The thread of simulation. c.f : ThreadSimu */
	private Motif pattern; /**< The pattern which can be inserted by user */
	private int nbThread; /**< Number of threads of calculation */
	private int nbIter; /**< The number of iteration since the initialisation */
	private int threadDone; /**< A value to affects an ID to working threads */


	/**
	 * Constructors
	 */
    public Modele(){
		this(10,10);
	}

	public Modele(int x, int y){
		this(x,y,1);
	}

	public Modele(int x, int y, int nbThread){
		this(x, y, 1, nbThread);
	}

	/**
	 * The main contructor.
	 * @param x The width of the grid (default : 10)
	 * @param y The height of the grid (default : 10)
	 * @param timeStep The number of seconds between each iteration (default : 1)
	 * @param nbThread Number of threads of calculation (default : 1)
	 */
	public Modele(int x, int y, double timeStep, int nbThread) {
		this.grille = new Grille(x,y);
		this.nbIter = 0;
		/* Create the thread which works on the model */
		this.thr = new ThreadSimu(timeStep, this, nbThread);
		this.nbThread = nbThread;
		/* Define the using pattern*/
		this.pattern = new Motif(1, 1);
		this.pattern.addPoint(0, 0);
	}

	/*
	 * Load a new grid randomly
	 */
	public void reInit() {
		grille.initGrille();
		nbIter = 0;
	}
	/*
	 * Clear the grid
	 */
	public void clear () {
		grille.clearGrille();
		nbIter = 0;
	}

	/*
	 * Launch simulation launching the threadSimu
	 */
	public void lancerSimulation () {
		thr.start();
	}

	/*
	 * Overriden function, called by the threadSimu to make the calculations.
	 */
    @Override
    public void run() {
        calcul();
    }

	/*
	 * This function could be part of a new interface "MultiThreadedRunnable".
	 * It's called after all threads of calculation have done its job. It's
	 * called once, whatever nbThread.
	 */
	public void endedCalcul () {
		// When all calculations are done, we swap back and front buffer of the grid.
		grille.swap();
		nbIter++;
		threadDone = 0;
		// The model notify the View.
		setChanged();
		notifyObservers();
	}

	/*
	 * This function is used to affect an ID to the working threads.
	 * It can be called by only one thread at a time (synchronized). The
	 * returned value is the thread ID.
	 */
	private synchronized int getThreadNum () {
		int result = threadDone;
		threadDone++;
		return result;
	}

    public void calcul() {
		// Each thread call grille.etatSuivant with the number of working threads and its ID
		grille.etatSuivant(nbThread, getThreadNum());
    }

	/*
	 * Overloaded functions to know if a cell in the grid is active or not.
	 */
	public boolean estVivante(int x, int y) {
		return grille.estVivante(x,y);
	}
	public boolean estVivante(Coordonnee c) {
		return grille.estVivante(c);
	}

	/*
	 * Add or remove an active cell of the grid.
	 */
	public void addCellule (Coordonnee c) {
		grille.addCellule(c);
	}
	public void removeCellule (Coordonnee c) {
		grille.removeCellule(c);
	}
	/*
	 * Add the pattern pattern to the grid.
	 */
	public void addMotif (Coordonnee c) {
		grille.addMotif(c, pattern);
	}

	/*
	 * Getter and Setter to stop temporaly the simulation.
	 */
	public void setPaused (boolean b) {
		thr.setPaused(b);
	}
	public boolean isPaused () {
		return (thr.isPaused());
	}
	public void switchPause () {
		thr.setPaused(!thr.isPaused());
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

	public void setSpeed (Double d) {
		thr.setSleepTime(d);
	}
}
