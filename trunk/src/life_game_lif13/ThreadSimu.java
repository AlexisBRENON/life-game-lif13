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
public class ThreadSimu extends Thread{
    private int tempsPause;
    private boolean etatExec;
    private Runnable monRunnable;

    public ThreadSimu(Modele aThis, int tempsPause, boolean etatExec) {
        super();
        this.tempsPause = tempsPause;
        this.etatExec = etatExec;
    }

    public ThreadSimu(Modele aThis) {
        super(aThis);
        
    }


    
    @Override
    public void run() {
        while(true){
            if(etatExec){
                monRunnable.run();
            }
            try {
                sleep(tempsPause);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadSimu.class.getName()).log(Level.SEVERE, 
                        null, ex);
            }
        }
    }
    
}
