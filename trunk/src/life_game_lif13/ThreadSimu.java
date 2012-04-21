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
				Thread t = new Thread(modele);
				t.start();
				try {
					t.join();
				} catch (InterruptedException ex) {
					Logger.getLogger(ThreadSimu.class.getName()).
							log(Level.SEVERE,
								null,
								ex);
				}
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

	public boolean isEtatExec () {
		return etatExec;
	}

	public void setEtatExec (boolean etatExec) {
		this.etatExec = etatExec;
	}

	public float getTempsPause () {
		return tempsPause;
	}

	public void setTempsPause (float tempsPause) {
		this.tempsPause = tempsPause;
	}

}
