/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author t0rp
 */
public class ThreadSimu extends Thread {
    private Runnable modele;
	private float tempsPause;
    private boolean etatExec;

    public ThreadSimu(float tempsPause, Runnable modele) {
        super();
        this.tempsPause = tempsPause;
        this.etatExec = true;
		this.modele = modele;
	}

	@Override
	public void run () {
		while (true) {
			if (etatExec) {
				(new Thread(modele)).start();
			}
			try {
				Thread.sleep(((long) tempsPause*1000));
			} catch (InterruptedException ex) {
				Logger.getLogger(ThreadSimu.class.getName()).
						log(Level.SEVERE,
							null,
							ex);
			}
		}
	}
}
