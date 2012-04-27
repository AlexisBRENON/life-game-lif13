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
 * It's a representation of a grid with cells which can be in 2 different
 * states.
 *
 * @author t0rp
 */
public class Grille {

	/**
	 * The width and the height of the grid.
	 */
	private int x, y;
	/**
	 * The front buffer which contains the active cells.
	 */
	private HashMap<Coordonnee, Cellule> map;
	/**
	 * The back buffer which contains the future active cells.
	 *
	 * @see Grille#swap()
	 */
	private HashMap<Coordonnee, Cellule> mapNext;

	/**
	 * The main constructor.
	 *
	 * @param x The width of the grid
	 * @param y The height of the grid
	 */
	public Grille (int x, int y) {
		this.x = x;
		this.y = y;
		this.map = new HashMap<Coordonnee, Cellule>();
		this.mapNext = new HashMap<Coordonnee, Cellule>();
	}

	/**
	 * Init the grid randomly.
	 */
	public void initGrille () {
		int i, j;
		for (i = 0; i < x; i++) {
			for (j = 0; j < y; j++) {
				if (new Random().nextBoolean()) {
					this.map.put(new Coordonnee(i, j), new Cellule(new Coordonnee(i, j), true));
				}
			}
		}
		this.mapNext.clear();
	}

	/**
	 * Clear the grid.
	 */
	public void clearGrille () {
		this.map.clear();
		this.mapNext.clear();
	}

	/**
	 * This function makes calculation to know which cells will be active.
	 */
	public void etatSuivant () {
		this.etatSuivant(1, 0);
	}

	/**
	 * This function makes calculation to know which cells will be active. It's
	 * an overloaded version which support multi-threading.
	 *
	 * @param nbThread Number of threads which work on this grid.
	 * @param num ID of the thread.
	 */
	public void etatSuivant (int nbThread, int num) {
		int i, j;
		int c;
		int nbCases = x * y;
		int voisin = 0;
		/*
		 * The thread makes only calculation on a part of the grid. For example,
		 * if nbThread = 2, the Thread 0 makes calculation on the first half of
		 * the grid, the Thread 1 makes calculation on the second half of the
		 * grid.
		 */
		for (c = num * (nbCases / nbThread); c < (num + 1) * (nbCases / nbThread) + 1; c++) {
			i = c % x;
			j = c / x;
			// We test if the neighbours of the cell are active.
			if (map.containsKey(new Coordonnee(((i - 1 + x) % x), ((j - 1 + y) % y)))) {
				voisin++;
			}

			if (map.containsKey(new Coordonnee(((i) % x), ((j - 1 + y) % y)))) {
				voisin++;
			}

			if (map.containsKey(new Coordonnee(((i + 1) % x), ((j - 1 + y) % y)))) {
				voisin++;
			}

			if (map.containsKey(new Coordonnee(((i - 1 + x) % x), ((j) % y)))) {
				voisin++;
			}

			if (map.containsKey(new Coordonnee(((i + 1) % x), ((j) % y)))) {
				voisin++;
			}

			if (map.containsKey(new Coordonnee(((i - 1 + x) % x), ((j + 1) % y)))) {
				voisin++;
			}

			if (map.containsKey(new Coordonnee(((i) % x), ((j + 1) % y)))) {
				voisin++;
			}

			if (map.containsKey(new Coordonnee(((i + 1) % x), ((j + 1) % y)))) {
				voisin++;
			}

			// We determine the next state of the cells.
			Coordonnee coord = new Coordonnee(i, j);
			if (map.containsKey(coord)) {
				if (voisin == 2 || voisin == 3) {
					mapNext.put(coord, new Cellule(coord, true));
				}
			} else if (map.get(coord) == null && voisin == 3) {
				mapNext.put(coord, new Cellule(coord, true));
			}
			// Reinit counter.
			voisin = 0;
		}
	}

	/**
	 * Swap the two buffers and reinit the back buffer.
	 */
	public void swap () {
		HashMap<Coordonnee, Cellule> t = map;
		map = mapNext;
		mapNext = t;
		this.mapNext.clear();
	}

	/**
	 * Determine if a cell is active or not.
	 *
	 * @param x The x coordinate of the cell.
	 * @param y The y coordinate of the cell.
	 * @return true if the cell is active, false otherwise.
	 */
	public boolean estVivante (int x, int y) {
		return (map.containsKey(new Coordonnee(x, y)));
	}

	public boolean estVivante (Coordonnee c) {
		return (map.containsKey(c));
	}

	/**
	 * Add an active cell to the grid.
	 *
	 * @param coord The coordinate of the cell to add.
	 */
	public void addCellule (Coordonnee coord) {
		if (!map.containsKey(coord)) {
			map.put(coord, new Cellule(coord, true));
		}
	}

	/**
	 * Remove an active cell from the grid.
	 *
	 * @param coord The coordinate of the cell to remove.
	 */
	public void removeCellule (Coordonnee coord) {
		map.remove(coord);
	}

	/**
	 * Add a pattern to the grid
	 *
	 * @param coord The coordinate on the grid of the center of the pattern.
	 * @param motif The pattern to add.
	 */
	public void addMotif (Coordonnee coord, Motif motif) {
		int i, j;
		for (i = 0; i < motif.getX(); i++) {
			for (j = 0; j < motif.getY(); j++) {
				Coordonnee coordGrille = new Coordonnee(
						((i + coord.getX() - motif.getX() / 2) + this.x) % this.x,
						((j + coord.getY() - motif.getY() / 2) + this.y) % this.y);
				if (motif.estVivante(new Coordonnee(i, j))) {
					this.addCellule(coordGrille);
				}
			}
		}
	}

	/**
	 * Save the current grid.
	 *
	 * @param file The path of the file to save.
	 * @throws IOException
	 */
	public void save (String file) throws IOException {
		int i, j;
		Properties p = new Properties();
		OutputStream out = new FileOutputStream(file);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		p.clear();
		p.store(out, "save " + dateFormat.format(date));

		for (j = 0; j < y; j++) {
			for (i = 0; i < x; i++) {
				Coordonnee coord = new Coordonnee(i, j);
				if (map.containsKey(coord)) {
					p.setProperty(coord.toString(), "true");
				}
			}
		}
		p.setProperty("x", String.valueOf(x));
		p.setProperty("y", String.valueOf(y));

		p.store(out, null);
	}

	/**
	 * Load a grid from a file.
	 *
	 * @param file The path to the file to load.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void load (String file) throws FileNotFoundException, IOException {
		int i, j;

		HashMap<Coordonnee, Cellule> loadMap = new HashMap<Coordonnee, Cellule>();
		Properties p = new Properties();
		InputStream out = new FileInputStream(file);
		String property;
		Boolean ok;
		p.load(out);
		Coordonnee coord;
		this.x = Integer.parseInt(p.getProperty("x"));
		this.y = Integer.parseInt(p.getProperty("y"));

		for (j = 0; j < y; j++) {
			for (i = 0; i < x; i++) {
				coord = new Coordonnee(i, j);
				property = p.getProperty(coord.toString());
				ok = Boolean.valueOf(property).booleanValue();

				if (ok) {
					System.out.println(property);
					if (property.equals("true")) {
						loadMap.put(coord, new Cellule(coord, true));
						System.out.println("ok x: " + i + " y: " + j);
					}
				}
			}
		}
		this.map.clear();
		this.map.putAll(loadMap);
	}

	/**
	 * GETTERS && SETTERS
	 */
	public HashMap<Coordonnee, Cellule> getMap () {
		return map;
	}

	public HashMap<Coordonnee, Cellule> getMapNext () {
		return mapNext;
	}

	public int getX () {
		return x;
	}

	public int getY () {
		return y;
	}

	public void setX (int x) {
		this.x = x;
	}

	public void setY (int y) {
		this.y = y;
	}
}
