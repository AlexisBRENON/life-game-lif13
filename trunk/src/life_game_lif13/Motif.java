/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * A Motif is a named grid which represents a pattern to insert on another
 * bigger grid.
 *
 * @author t0rp
 */
public final class Motif {
	/**
	 * Width and Height of the pattern.
	 */
	private int x, y;
	/**
	 * Representation of the active cells of the pattern.
	 */
	private HashMap<Coordonnee, Cellule> map;
	/**
	 * Name of the pattern, to display it in a list.
	 */
	private String name;

	/**
	 * This basic constructor build a pattern.
	 * @param x Width of the pattern
	 * @param y Height of the pattern
	 */
	public Motif (int x, int y) {
		this.x = x;
		this.y = y;
		this.name = null;
		this.map = new HashMap<Coordonnee, Cellule>();
	}
	/**
	 * This constructor build a pattern and affect it a name.
	 * @param x Width of the pattern
	 * @param y Height of the pattern
	 * @param name Name of the pattern
	 */
	public Motif (int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.map = new HashMap<Coordonnee, Cellule>();
	}


	/**
	 * This constructor build a pattern from a file.
	 * @param file The file which contains the pattern description.
	 * @see File
	 */
	public Motif (File file) {
		this.map = new HashMap<Coordonnee, Cellule>();
		try {
			this.loadMotif(file.getAbsolutePath());
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Motif.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Motif.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.name = file.getName().substring(0, file.getName().lastIndexOf("."));
	}

	/**
	 * Init the motif randomly.
	 */
	public void InitMotif () {
		int i, j;
		for (i = 0; i < x; i++) {
			for (j = 0; j < y; j++) {
				if (new Random().nextBoolean()) {
					this.map.put(new Coordonnee(i, j),
									new Cellule(new Coordonnee(i, j), true));
				}
			}
		}
	}

	/**
	 * Return the state of the cell of coordinate c.
	 * @param c The coordinates of the cell.
	 * @return true if the cell is active, false otherwise.
	 */
	public boolean estVivante (Coordonnee c) {
		return (map.containsKey(c));
	}

	/**
	 * Add a (active) point to the pattern.
	 * @param x x-coordinate of the point.
	 * @param y y-coordinate of the point.
	 */
	public void addPoint (int x, int y) {
		addPoint(new Coordonnee(x, y));
	}
	public void addPoint (Coordonnee c) {
		map.put(c, new Cellule(c));
	}


	/**
	 * GETTERS && SETTERS
	 */

	public int getX () {
		return x;
	}

	public void setX (int x) {
		this.x = x;
	}

	public int getY () {
		return y;
	}

	public void setY (int y) {
		this.y = y;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public HashMap<Coordonnee, Cellule> getMap () {
		return map;
	}

	public void setMap (HashMap<Coordonnee, Cellule> map) {
		this.map = map;
	}

	private void loadMotif (String file) throws FileNotFoundException, IOException {
		int i;
		String filePath = file;
		String str[];
		try {
			BufferedReader buff = new BufferedReader(new FileReader(filePath));
			try {
				String line;
				// Lecture du fichier ligne par ligne.


				while ((line = buff.readLine()) != null) {

					str = line.split(" ");
					this.x = str.length;
					for (i = 0; i < str.length; i++) {
						if ("1".equals(str[i])) {
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
