/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.Color;

/**
 *
 * @author alexis
 */
public class ThreadedUpdate implements Runnable {
	private FenetrePrincipale win;
	private int nbThread;
	private int num;

	ThreadedUpdate (int nbThread, int num, FenetrePrincipale win) {
		this.win = win;
		this.nbThread = nbThread;
		this.num = num;
	}

	@Override
	public void run () {
		int c, i, j;
		int nbCases = win.getNbCol()*win.getNbLigne();

		for (c = num * (nbCases / nbThread); c < (num + 1) * (nbCases / nbThread); c++) {
			i = c % win.getNbCol();
			j = c / win.getNbCol();
			if (win.getM().estVivante(i, j)) {
					win.getCellules()[i][j].setBackground(Color.red);
				} else {
					win.getCellules()[i][j].setBackground(Color.white);
				}
		}
	}

}
