/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author t0rp
 */
public class Grille {
    private int x, y;
    private  HashMap <Coordonnee, Cellule> map;
    private  HashMap <Coordonnee, Cellule> mapNext;
    
    public Grille(int x, int y){
        int i, j;
        
        this.x = x;
        this.y = y;
        this.map= new HashMap<Coordonnee, Cellule>();
        this.mapNext=new HashMap<Coordonnee, Cellule>();
        
        
        for(i= 0; i<x; i++){
            for(j= 0; j<y; j++){
                if(new Random().nextBoolean()){
                    map.put(new Coordonnee(i, j), new Cellule(new Coordonnee(i, j), true));
                }
            }
        }
        mapNext=(HashMap<Coordonnee, Cellule>) map.clone();
    }
    
    public void etatSuivant(){
        int i, j;
        int voisin =0;
        
        for(i= 0; i<x; i++){
            for(j= 0; j<y; j++){
                
                //test Voisin
                if((i-1)>-1 && (j-1)>-1 
                        && map.get(new Coordonnee(i - 1, j - 1))!=null)
                    voisin++;
                
                if((i-1)>-1 && map.get(new Coordonnee(i - 1, j))!=null)
                    voisin++;
                
                if((i-1)>-1 && (j+1)<(y+1) 
                        && map.get(new Coordonnee(i - 1, j + 1))!=null)
                    voisin++;
                
                if((j-1)>-1 && map.get(new Coordonnee(i, j - 1))!=null)
                    voisin++;
                
                if((j+1)<y+1 && map.get(new Coordonnee(i, j + 1))!=null)
                    voisin++;
                
                if((i+1)<x+1 && (j-1)>-1 
                        && map.get(new Coordonnee(i + 1, j - 1))!=null)
                    voisin++;
                
                if((i+1)<x+1 && map.get(new Coordonnee(i + 1, j))!=null)
                    voisin++;
                
                if((i+1)<x+1 && (j+1)<y+1 
                        && map.get(new Coordonnee(i + 1, j + 1))!=null)
                    voisin++;
                
                //action si voisin
                Coordonnee coord=new Coordonnee(i, j);
                if(map.get(coord)!=null){
                    if(voisin==0 || voisin==1 || voisin > 3){
                        //mapNext.get(coord).setEtatSuivant(false);
                        System.out.println(i+" "+j);
                        mapNext.remove(coord);
                    }
                }
                else if(voisin==3)
                        mapNext.put(coord, new Cellule(coord, true));
                
                //reinitialise le nombre de voisin.
                voisin=0;
            }
        }
        
        map=(HashMap<Coordonnee, Cellule>) mapNext.clone();
    }
    
    
}
