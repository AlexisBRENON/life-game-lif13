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
public class Grille {
    private int x, y;
    private  HashMap <Coordonnee, Cellule> map;
    private  HashMap <Coordonnee, Cellule> mapNext;

    public Grille(int x, int y){
        this.x = x;
        this.y = y;
        this.map= new HashMap<Coordonnee, Cellule>();
        this.mapNext=new HashMap<Coordonnee, Cellule>();


    }

    public void initGrille(){
        int i, j;
        for(i= 0; i<x; i++){
            for(j= 0; j<y; j++){
                if(new Random().nextBoolean()){
                    this.map.put(new Coordonnee(i, j), new Cellule(new Coordonnee(i, j), true));
                }
            }
        }

        this.mapNext=(HashMap<Coordonnee, Cellule>) this.map.clone();
    }

    public void clearGrille(){
        this.map.clear();
        this.mapNext.clear();
    }


    public void etatSuivant(){
        int i, j;
        int voisin =0;

        for(i= 0; i<x; i++){
            for(j= 0; j<y; j++){

                //test Voisin
                if(map.get(new Coordonnee(((i - 1) % x), ((j - 1) % y )))!=null)
                    voisin++;

                if(map.get(new Coordonnee(((i - 1) % x), j))!=null)
                    voisin++;

                if(map.get(new Coordonnee(((i - 1) % x), ((j + 1) % y))) != null)
                    voisin++;

                if(map.get(new Coordonnee(i, ((j - 1) % y ))) != null)
                    voisin++;

                if(map.get(new Coordonnee ( i, ((j + 1) % y)))!=null)
                    voisin++;

                if(map.get(new Coordonnee(((i + 1) % x), ((j - 1) % y)))!= null)
                    voisin++;

                if(map.get(new Coordonnee(((i + 1) % x), j))!= null)
                    voisin++;

                if(map.get(new Coordonnee(((i + 1) % x), ((j + 1) % y))) != null)
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

        this.map=(HashMap<Coordonnee, Cellule>) this.mapNext.clone();
    }

	public boolean estVivante (int x, int y) {
		return (map.containsKey(new Coordonnee(x, y)));
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}


}
