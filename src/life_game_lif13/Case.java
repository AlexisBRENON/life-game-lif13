/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import javax.swing.JPanel;

/**
 *
 * @author t0rp
 */
public class Case extends JPanel{
	private static final long serialVersionUID = 1L;

	Coordonnee _coord;

	public Case () {
		_coord = new Coordonnee();
	}

	public Case (int x, int y) {
		_coord = new Coordonnee(x, y);
	}

	public Coordonnee getCoord () {
		return _coord;
	}

	public void setCoord (Coordonnee _coord) {
		this._coord = _coord;
	}

}
