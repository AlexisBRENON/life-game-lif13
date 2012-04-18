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
    
    public Modele(){
        int tempsPause =5;
        Thread t=new ThreadSimu(this, tempsPause, true);
        t.start();
        
    }

    @Override
    public void run() {
        while(true){
            calcul();
        }
    }
   
    public void calcul(){
        
    }
    
}
