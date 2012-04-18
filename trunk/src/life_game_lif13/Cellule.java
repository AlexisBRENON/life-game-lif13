/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

/**
 *
 * @author t0rp
 */
public class Cellule {
    private Coordonnee coord;
    private boolean etatCourant, etatSuivant;

    public Cellule(Coordonnee coord, boolean etat) {
        this.coord = coord;
        this.etatCourant = etat;
        this.etatSuivant=etat;
    }

    public Coordonnee getCoord() {
        return coord;
    }

    public boolean isEtatCourant() {
        return etatCourant;
    }

    public boolean isEtatSuivant() {
        return etatSuivant;
    }

    public void setCoord(Coordonnee coord) {
        this.coord = coord;
    }

    public void setEtatCourant(boolean etatCourant) {
        this.etatCourant = etatCourant;
    }

    public void setEtatSuivant(boolean etatSuivant) {
        this.etatSuivant = etatSuivant;
    }

    @Override
    public String toString() {
        return "Cellule{" + coord.toString() + ", etatCourant=" + etatCourant
                + ", etatSuivant=" + etatSuivant + '}';
    }

	@Override
    public Cellule clone () {
		return new Cellule(coord.clone(), etatCourant);
	}

}
