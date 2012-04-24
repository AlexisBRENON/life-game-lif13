/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author t0rp
 */
public class Motif {
    private int x, y;
    private HashMap <Coordonnee, Cellule> map;

    public Motif(int x, int y) {
        this.x = x;
        this.y = y;
        this.map= new HashMap<Coordonnee, Cellule>();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public HashMap<Coordonnee, Cellule> getMap() {
        return map;
    }

    public void setMap(HashMap<Coordonnee, Cellule> map) {
        this.map = map;
    }
    
    
    public void InitMotif(){
        int i, j;
	int counter = 0;
	do {
            for(i= 0; i<x; i++){
                for(j= 0; j<y; j++){
                    if(new Random().nextBoolean()){
                        this.map.put(new Coordonnee(i, j), 
                            new Cellule(new Coordonnee(i, j), true));
                        counter++;
                    }
                }
           }
	} while (counter < (10f/100f)*(x*y));
    }
    
    public void InitMotif(HashMap<Coordonnee, Cellule> oldMap){
        this.map.clear();
        this.map.putAll(oldMap);
    }
    
    public boolean estVivante (Coordonnee c) {
		return (map.containsKey(c));
	}
}
