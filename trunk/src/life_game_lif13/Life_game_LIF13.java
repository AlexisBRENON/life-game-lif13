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
        
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FenetrePrincipale().setVisible(true);
            }
        });
        
        Modele m=new Modele();
    }
}
