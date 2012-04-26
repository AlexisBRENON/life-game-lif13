/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author t0rp
 */
public class Motif {
    private int x, y;
    private HashMap <Coordonnee, Cellule> map;
    private String name;

    public Motif(int x, int y) {
        this.x = x;
        this.y = y;
        this.name=null;
        this.map= new HashMap<Coordonnee, Cellule>();
    }

    public Motif(File file){
        this.map= new HashMap<Coordonnee, Cellule>();
        try {
            this.loadMotif(file.getAbsolutePath());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Motif.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Motif.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.name=file.getName().substring(0, file.getName().lastIndexOf("."));
    }


    public Motif(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name=name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

	public void addPoint (int x, int y) {
		addPoint(new Coordonnee(x, y));
	}

	public void addPoint (Coordonnee c) {
		map.put(c, new Cellule(c));
	}

        public void loadMotif(String file) throws FileNotFoundException, IOException{
        int i;
        String filePath =file;
        String str[];
                try{
                BufferedReader buff = new BufferedReader(new FileReader(filePath));
                try {
                String line;
                // Lecture du fichier ligne par ligne.


                while ((line = buff.readLine()) != null) {

                    str=line.split(" ");
                    this.x=str.length;
                    for(i=0; i<str.length;i++){
                        if("1".equals(str[i])){
                            this.map.put(new Coordonnee(i, this.y),
                                    new Cellule(new Coordonnee(i, this.y), true));
                        }
                    }

                this.y++;
                }
                } finally {
                buff.close();
                }
                } catch (IOException ioe) {
                System.out.println("Erreur --" + ioe.toString());
                }
	}
}
