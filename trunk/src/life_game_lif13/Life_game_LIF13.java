/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

/**
 * It's the starting point of the main program.
 *
 * @author t0rp
 */
public class Life_game_LIF13 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		// Create a controler and launch the application.
		Controlleur c = new Controlleur(20, 20, 0.25f, 2);
		c.startApp();
    }
}
