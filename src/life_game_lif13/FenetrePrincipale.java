/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;


/**
 *
 * @author alexis
 */
public class FenetrePrincipale extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;

	private int _nbCol;
	private int _nbLigne;

	public FenetrePrincipale () {
		_nbCol = 10;
		_nbLigne = 10;
		build();

	}

	private void build() {
		/* Création de l'interface */
		this.setMinimumSize(new Dimension(800, 600));

		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuEdition = new JMenu("Edition");
		JMenu menuAide = new JMenu("Aide");

		menuFichier.add(new JMenuItem("Ouvrir..."));
		menuFichier.add(new JMenuItem("Enregistrer Sous..."));
		menuFichier.add(new JMenuItem("Quitter"));

		menuAide.add(new JMenuItem("A Propos..."));

		JMenuBar barreMenu = new JMenuBar();
		barreMenu.add(menuFichier);
		barreMenu.add(menuEdition);
		barreMenu.add(menuAide);

		this.setJMenuBar(barreMenu);

		JPanel panelPrincipal = new JPanel(new BorderLayout());
		this.setContentPane(panelPrincipal);

		JPanel panelGrille = new JPanel(new GridLayout(_nbLigne, _nbCol));
		for (int i = 0; i < _nbLigne*_nbCol; i++) {
			panelGrille.add(new JPanel());
		}
		panelPrincipal.add(panelGrille, BorderLayout.CENTER);

		JPanel panelBoutons = new JPanel();
		BoxLayout box = new BoxLayout(panelBoutons,
									  BoxLayout.Y_AXIS);
		panelBoutons.setLayout(box);
		panelBoutons.add(new JButton("Lancer !"));
		panelBoutons.add(new JButton("Pause"));
		panelBoutons.add(new JButton("Effacer"));
		panelPrincipal.add(panelBoutons, BorderLayout.EAST);

		/* Connection des signaux */
		this.addWindowListener(
			new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
	}

	@Override
	public void update (Observable o,
						Object arg) {
		System.out.print("Update\n");
	}
}
