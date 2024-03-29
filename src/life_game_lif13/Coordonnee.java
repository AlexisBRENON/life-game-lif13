/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

/**
 *
 * Coordonate representation.
 *
 * @author alexis
 */
public class Coordonnee {
	private int _x;
	private int _y;

	public Coordonnee () {
		_x = _y = 0;
	}

	public Coordonnee (int x, int y) {
		_x = x;
		_y = y;
	}

	@Override
	public int hashCode () {
		return _x*_y+_y;
	}

	@Override
	public boolean equals (Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Coordonnee other = (Coordonnee) obj;
		if (this._x != other._x) {
			return false;
		}
		if (this._y != other._y) {
			return false;
		}
		return true;
	}
	
	@Override
    public String toString() {
        return "("+_x+" ; "+_y+")";
    }

	@Override
	public Coordonnee clone () {
		return new Coordonnee(_x, _y);
	}


	public int getX () {
		return _x;
	}

	public void setX (int _x) {
		this._x = _x;
	}

	public int getY () {
		return _y;
	}

	public void setY (int _y) {
		this._y = _y;
	}
}
