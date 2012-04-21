/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

/**
 *
 * @author t0rp
 */
public class Life_game_LIF13 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Modele m = new Modele(20,20);
		FenetrePrincipale f = new FenetrePrincipale(m);

		new Thread(f).start();
    }
}
