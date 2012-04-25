/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

/**
 *
 * @author alexis
 */
public class ThreadedCalcul implements Runnable {
	int nbThread;
	int num;
	Grille g;

	public ThreadedCalcul (int nbThread, int num, Grille grille) {
		this.nbThread = nbThread;
		this.num = num;
		g = grille;
	}

	@Override
	public void run () {
		g.threadedEtatSuivant(nbThread, num);
	}
}
