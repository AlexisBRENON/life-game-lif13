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
		Controlleur c = new Controlleur(100, 100, 2);
		c.startApp();
    }
}
