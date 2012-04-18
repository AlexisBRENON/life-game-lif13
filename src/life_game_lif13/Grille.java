/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.util.Map;
import java.util.Random;

/**
 *
 * @author t0rp
 */
public class Grille {
    private int x, y;
    private  Map <Coordonnee, Cellule> map;
    
    public Grille(int x, int y){
        int i, j;
        
        this.x = x;
        this.y = y;
        
        for(i= 0; i<x; i++){
            for(j= 0; j<y; j++){
                if(new Random().nextBoolean()){
                    map.put(new Coordonnee(i, j), new Cellule(new Coordonnee(i, j), true));
                }
            }
        }
    }
    
    public void etatSuivant(){
        int i, j;
        int voisin =0;
        
        for(i= 0; i<x; i++){
            for(j= 0; j<y; j++){
                
                //test Voisin
                if(map.get(new Coordonnee(i - 1, j - 1))!=null)
                    voisin++;
                if(map.get(new Coordonnee(i - 1, j))!=null)
                        voisin++;
                if(map.get(new Coordonnee(i - 1, j + 1))!=null)
                        voisin++;
                if(map.get(new Coordonnee(i, j - 1))!=null)
                        voisin++;
                if(map.get(new Coordonnee(i, j + 1))!=null)
                        voisin++;
                if(map.get(new Coordonnee(i + 1, j - 1))!=null)
                        voisin++;
                if(map.get(new Coordonnee(i + 1, j))!=null)
                        voisin++;
                if(map.get(new Coordonnee(i + 1, j + 1))!=null)
                        voisin++;
                
                //action si voisin
                
            }
        }
    }
    
    
}
