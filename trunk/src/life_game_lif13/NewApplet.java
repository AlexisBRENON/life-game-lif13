/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author t0rp
 */
public class NewApplet extends JApplet implements Runnable {
	private static final long serialVersionUID = 1L;
	private Controlleur c;
	/**
	 * Méthode d'initialisation de l'applet
	 */
    @Override
	public void start(){
        this.setSize(800, 600);
        c = new Controlleur();
	this.add(c.getWin().getContentPane());
        new Thread(this).start();
	}

	@Override
	public void run () {
            this.setVisible(true);
            c.getWin().getJMenuBar().setVisible(false);
		//c.getWin().setVisible(false);
	}

}
