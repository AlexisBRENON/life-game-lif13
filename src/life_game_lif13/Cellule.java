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
    
    
    
}
