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
        Grille grille;
        Thread t;
    
    public Modele(){
        
        int tempsPause =1;
        t=new ThreadSimu(this, tempsPause, true);
        t.start();
        
    }

    @Override
    public void run() {
        int x, y;
        x=10;
        y=10;
        grille= new Grille(x,y);
        while(true){
            
            System.out.println("calcul: ");
            calcul();
        }
    }
   
    public void calcul(){
        grille.etatSuivant();
        
    }
    
}
