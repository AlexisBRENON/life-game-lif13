/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import com.sun.tools.internal.ws.processor.model.Model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author t0rp
 */
public class NewApplet extends JApplet {

private JLabel label = new JLabel();
	private JButton bouton = new JButton("Cliquez");
	private int count = 0;
	/**
	 * Méthode d'initialisation de l'applet
	 * C'est cette méthode qui fait office de constructeur
	 */
    @Override
	public void init(){
		this.setSize(300, 300);
                Modele m =new Modele(10,10);
                
                FenetrePrincipale f = new FenetrePrincipale(m);

		new Thread(f).start();
	}

}