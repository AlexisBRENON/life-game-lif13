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
    private Grille grille;
	private ThreadSimu t;
	private Motif pattern;
	private int nbThread;
	private int nbIter;

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
		grille = new Grille(x,y);
		this.nbThread = nbThread;
		nbIter = 0;
		t = new ThreadSimu(timeStep, this);
		pattern = new Motif(1, 1);
		pattern.getMap().put(new Coordonnee(0,0),
							 new Cellule(new Coordonnee(0,0), true));
	}


	public Grille getGrille () {
		return grille;
	}

	public void lancerThread () {
		t.start();
	}

	public void init() {
		nbIter = 0;
		grille.initGrille();
	}

    @Override
    public void run() {
        if (nbThread == 1) {
            calcul();
		} else {
			try {
				calculThreaded();
			} catch (InterruptedException ex) {
				System.out.println("Problème de calcul distribué");
				calcul();
			}
		}
		nbIter++;
		setChanged();
		notifyObservers();
    }

    public void calcul() {
        grille.etatSuivant();
    }

	private void calculThreaded() throws InterruptedException {
		Thread[] tab = new Thread[nbThread];
		grille.getMapNext().clear();
		for (int i = 0; i < nbThread; i++) {
			tab[i] = new Thread(new ThreadedCalcul(nbThread, i, grille));
			tab[i].start();
		}
		for (int i = 0; i < nbThread; i++) {
			tab[i].join();
		}
		grille.getMap().clear();
		grille.getMap().putAll(grille.getMapNext());
	}

	public boolean estVivante(int x, int y) {
		return grille.estVivante(x,y);
	}

	public boolean estVivante(Coordonnee c) {
		return grille.estVivante(c);
	}

	public void setPaused (boolean b) {
		t.setEtatExec(!b);
	}
	public boolean isPaused () {
		return !(t.getEtatExec());
	}
	public void switchPause () {
		t.setEtatExec(!t.getEtatExec());
	}

	public void clear () {
		grille.clearGrille();
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
}
