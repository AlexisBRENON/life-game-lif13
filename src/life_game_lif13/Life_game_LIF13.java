/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.io.IOException;

/**
 *
 * @author t0rp
 */
public class Life_game_LIF13 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
                Modele m = new Modele(5,5);
		FenetrePrincipale f = new FenetrePrincipale(m);

		new Thread(f).start();
                
                  // m.grille.save();
                 //  m.grille.load();
    }
}
