/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

/**
 *
 * A ThreadSimu is a thread which execute its task with a time and which can be
 * paused.
 * @see Thread
 *
 * @author Alexis
 *
 */
public class ThreadSimu extends Thread {
	/**
	 * The runnable object. After sleepTime seconds, the thread call the run
	 * method of this object.
	 * @see Runnable
	 */
    private Runnable runnable;
	/**
	 * Number of threads working on the runnable.
	 */
	private int nbThread;
	/**
	 * The time in seconds between every run() call.
	 */
	private double sleepTime;
	/**
	 * Setting paused at true makes the thread to pause.
	 */
    private boolean paused;
	/**
	 * Setting stopped at true makes the thread to stop.
	 */
	private boolean stopped;

	/**
	 * Main constructor.
	 * @param sleepTime The time in seconds between each call to run().
	 * @param runnable The Runnable object which is executed by the thread.
	 * @param nbThread Number of threads working on the runnable.
	 */
    public ThreadSimu(double sleepTime, Runnable runnable, int nbThread) {
        super();
		this.runnable = runnable;
        this.sleepTime = sleepTime;
        this.paused = false;
		this.stopped = false;
		this.nbThread = nbThread;
	}

	/**
	 * The override method called when the start() method of the Thread object
	 * is invoked.
	 * This function create nbThread threads and launch them to work on the
	 * runnable object. When all threads are done. This function call the
	 * endedCalcul() method of the runnable object to finalize all calculations.
	 */
	@Override
	public void run () {
		while (!stopped) {
			if (!paused) {
				Thread[] threads = new Thread[nbThread];
				for (int i = 0; i < nbThread; i++) {
					threads[i] = new Thread(runnable);
					threads[i].start();
				}
				for (int i = 0; i < nbThread; i++) {
					try {
						threads[i].join();
					} catch (InterruptedException ex) {
						System.out.println("Erreur Multi-thread.");
					}
				}
				((Modele) runnable).endedCalcul();
			}
			try {
				Thread.sleep((int) (sleepTime*1000));
			} catch (InterruptedException ex) {
				System.out.print("Erreur sleep");
			}
		}

	}

	/**
	 *
	 * GETTERS && SETTERS
	 *
	 */

	public boolean isPaused () {
		return paused;
	}

	public void setPaused (boolean paused) {
		this.paused = paused;
	}

	public double getSleepTime () {
		return sleepTime;
	}

	public void setSleepTime (double sleepTime) {
		this.sleepTime = sleepTime;
	}

	public boolean isStopped () {
		return stopped;
	}

	public void setStopped (boolean stopped) {
		this.stopped = stopped;
	}
}
