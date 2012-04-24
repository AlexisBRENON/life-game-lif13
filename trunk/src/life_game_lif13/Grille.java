/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

/**
 *
 * @author t0rp
 */
public class Grille {
    private int x, y;
    private HashMap <Coordonnee, Cellule> map;
    private HashMap <Coordonnee, Cellule> mapNext;

    public Grille(int x, int y){
        this.x = x;
        this.y = y;
        this.map= new HashMap<Coordonnee, Cellule>();
        this.mapNext=new HashMap<Coordonnee, Cellule>();
    }

	@SuppressWarnings ("unchecked")
    public void initGrille(){
        int i, j;
		int counter = 0;
		do {
		  for(i= 0; i<x; i++){
	            for(j= 0; j<y; j++){
	                if(new Random().nextBoolean()){
	                    this.map.put(new Coordonnee(i, j), new Cellule(new Coordonnee(i, j), true));
						counter++;
	                }
	            }
	        }
		} while (counter < (10f/100f)*(x*y));
        this.mapNext.clear();
    }
        
       

    public void clearGrille(){
        this.map.clear();
        this.mapNext.clear();
    }


	@SuppressWarnings ("unchecked")
    public void etatSuivant(){
        int i, j;
        int voisin = 0;
		mapNext.clear();
        for(j = 0; j < y; j++){
            for(i = 0; i < x; i++){

                //test Voisin
                if(map.containsKey(new Coordonnee(((i - 1 + x) % x), ((j - 1 + y)%y))))
                    voisin++;

				if(map.containsKey(new Coordonnee(((i) % x), ((j - 1 + y)%y))))
                    voisin++;

				if(map.containsKey(new Coordonnee(((i + 1) % x), ((j - 1 + y)%y))))
                    voisin++;

                if(map.containsKey(new Coordonnee(((i - 1 + x) % x), ((j)%y))))
                    voisin++;

				if(map.containsKey(new Coordonnee(((i + 1) % x), ((j)%y))))
                    voisin++;

				if(map.containsKey(new Coordonnee(((i - 1 + x) % x), ((j + 1)%y))))
                    voisin++;

				if(map.containsKey(new Coordonnee(((i) % x), ((j + 1)%y))))
                    voisin++;

				if(map.containsKey(new Coordonnee(((i + 1) % x), ((j + 1)%y))))
                    voisin++;

				//System.out.println("("+ i+","+j+") -> "+voisin+"voisins");
                //action si voisin
                Coordonnee coord=new Coordonnee(i, j);
				//if(map.containsKey(coord)) {System.out.println(coord.toString()+"\n");}
                if(map.containsKey(coord)){
                    if(voisin==2 || voisin==3){
			mapNext.put(coord, new Cellule(coord, true));
                    }
                }
                else if(map.get(coord) == null && voisin == 3) {
                        mapNext.put(coord, new Cellule(coord, true));
				}
                //reinitialise le nombre de voisin.
                voisin=0;
            }
        }
        this.map.clear();
        map.putAll(mapNext);
        }

	public boolean estVivante (int x, int y) {
		return (map.containsKey(new Coordonnee(x, y)));
	}

	public boolean estVivante (Coordonnee c) {
		return (map.containsKey(c));
	}

        public void addCellule(Coordonnee coord){
            map.put(coord, new Cellule(coord, true));
            mapNext.clear();
            mapNext.putAll(map);
        }

        public void removeCellule(Coordonnee coord){
            Cellule remove = map.remove(coord);
            mapNext.clear();
            mapNext.putAll(map);
            }

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

        public void save(String file) throws IOException{
            int i, j;
            Properties p = new Properties();
            OutputStream out = new FileOutputStream(file);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            p.clear();
            p.store(out, "save "+ dateFormat.format(date));
            for(j = 0; j < y; j++){
                for(i = 0; i < x; i++){
                    Coordonnee coord= new Coordonnee(i,j);
                    if(map.containsKey(coord))
                        p.setProperty(coord.toString(),"true");
                }
            }

        p.store(out, null);
        //load(file);
        }

        public HashMap <Coordonnee, Cellule> load(String file) throws FileNotFoundException, IOException{
            int i, j;

            HashMap<Coordonnee, Cellule> loadMap=new HashMap<Coordonnee, Cellule>();
            Properties p = new Properties();
            InputStream out = new FileInputStream(file);
            String property;
            Boolean ok;
            p.load(out);
            Coordonnee coord;
            for(j = 0; j < y; j++){
                for(i = 0; i < x; i++){
                    coord=new Coordonnee(i, j);
                    property= p.getProperty(coord.toString());
                    ok= Boolean.valueOf(property).booleanValue();
                    //System.out.println("x: "+ i+ " y: "+j+" "+ok+property);
                    if(ok){
                        loadMap.put(coord, new Cellule(coord, true));
                        System.out.println("ok x: "+ i+ " y: "+j);
                    }
                    //p.storeToXML(out, coord.toString());
                }
            }
            return loadMap;
        }


}
