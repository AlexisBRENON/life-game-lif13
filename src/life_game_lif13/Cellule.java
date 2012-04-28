/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

/**
 *
 * This class represents a cell. It's currently not very used but declared to
 * let the possibility to add new features.
 *
 * @author t0rp
 */
public class Cellule {
	/**
	 * The coordinates of the cell.
	 */
    private Coordonnee coord;
	/**
	 * The current state of the cell.
	 */
    private boolean etatCourant;

    public Cellule(Coordonnee coord, boolean etat) {
        this.coord = coord;
        this.etatCourant = etat;
    }
	/**
	 * Create a new active cell at coord.
	 * @param coord The coordinates of the cell
	 */
	public Cellule(Coordonnee coord) {
		this(coord, true);
	}
	public Cellule (int x, int y) {
		this(new Coordonnee(x, y), true);
	}
	public Cellule() {
        this(new Coordonnee(), true);
    }

	@Override
    public String toString() {
        return coord.toString()+" -> "+((Boolean) etatCourant).toString();
    }

	@Override
    public Cellule clone () {
		return new Cellule(coord.clone(), etatCourant);
	}

	/**
	 * GETTERS && SETTERS
	 */

    public Coordonnee getCoord() {
        return coord;
    }

    public boolean isEtatCourant() {
        return etatCourant;
    }

    public void setCoord(Coordonnee coord) {
        this.coord = coord;
    }

    public void setEtatCourant(boolean etatCourant) {
        this.etatCourant = etatCourant;
    }
}
