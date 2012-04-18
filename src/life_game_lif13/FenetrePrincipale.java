/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author alexis
 */
public class FenetrePrincipale extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;

	private int _nbCol;
	private int _nbLigne;

	public FenetrePrincipale (int x, int y) {
		_nbCol = x;
		_nbLigne = y;
		build();
	}

	private void build() {
		/* Création de l'interface */
		JPanel _panelPrincipal = new JPanel(new BorderLayout());
		this.setContentPane(_panelPrincipal);

		JPanel _panelGrille = new JPanel(new GridLayout(_nbLigne, _nbCol));
		for (int i = 0; i < _nbLigne*_nbCol; i++) {
			_panelGrille.add(new JPanel());
		}
		_panelPrincipal.add(_panelGrille, BorderLayout.CENTER);

		JPanel _panelBoutons = new JPanel(new FlowLayout());
		_panelBoutons.add(new JButton("Lancer !"));
		_panelBoutons.add(new JButton("Pause"));
		_panelBoutons.add(new JButton("Effacer"));
		_panelPrincipal.add(_panelBoutons, BorderLayout.EAST);

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
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
