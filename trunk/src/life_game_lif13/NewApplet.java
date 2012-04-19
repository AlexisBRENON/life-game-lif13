/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

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
		this.setSize(300, 80);

		//On centre le texte du JLabel et on écrit en bleu...
		label.setHorizontalAlignment(JLabel.CENTER);
		//C'est plus zoli. 
		label.setForeground(Color.blue);
				
		//Allez, une classe anonyme... Just for the fun ;)
		this.bouton.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent arg0) {
				label.setText("Vous avez cliqué " + (++count) 
                                        + " fois sur le bouton");
			}			
		});
		
		//On ajoute nos composants
		this.getContentPane().add(bouton, BorderLayout.SOUTH);
		this.getContentPane().add(label, BorderLayout.NORTH);
		//Et le tour est joué !
                System.out.println("Paramètre passé via la balise <param> : " 
                        + this.getParameter("message"));
	}

}
