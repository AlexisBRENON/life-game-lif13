/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexis
 *
 * Extends Thread. Execute run() of the runnable object every sleepTime second
 * and if paused is false.
 */
public class ThreadSimu extends Thread {
    private Runnable model;
	private int nbThread;
	private float sleepTime;
    private boolean paused;
	private boolean stopped;

    public ThreadSimu(float sleepTime, Runnable model, int nbThread) {
        super();
		this.model = model;
        this.sleepTime = sleepTime;
        this.paused = false;
		this.stopped = false;
		this.nbThread = nbThread;
	}

	@Override
	public void run () {
		while (!stopped) {
			if (!paused) {
				Thread[] threads = new Thread[nbThread];
				for (int i = 0; i < nbThread; i++) {
					threads[i] = new Thread(model);
					threads[i].start();
				}
				for (int i = 0; i < nbThread; i++) {
					try {
						threads[i].join();
					} catch (InterruptedException ex) {
						System.out.println("Erreur Multi-thread.");
					}
				}
				((Modele) model).endedCalcul();
			}
			try {
				Thread.sleep((int) sleepTime*1000);
			} catch (InterruptedException ex) {
				Logger.getLogger(ThreadSimu.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	public boolean isPaused () {
		return paused;
	}

	public void setPaused (boolean paused) {
		this.paused = paused;
	}

	public float getSleepTime () {
		return sleepTime;
	}

	public void setSleepTime (float sleepTime) {
		this.sleepTime = sleepTime;
	}

	public boolean isStopped () {
		return stopped;
	}

	public void setStopped (boolean stopped) {
		this.stopped = stopped;
	}
}
