/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;


/**
 *
 * @author alexis
 */
public class FenetrePrincipale extends JFrame implements Observer, Runnable {
	private static final long serialVersionUID = 1L;

	private int _nbCol;
	private int _nbLigne;
	private Modele _m;

	/* Composants de la fenêtre */
	private JMenuItem _itemEnregistrer;
	private JMenuItem _itemOuvrir;
	private JMenuItem _itemQuitter;
	private JMenuItem _itemAPropos;
	private JPanel _panelGrille;
	private JButton _boutonLancer;
	private JButton _boutonPause;
	private JButton _boutonEffacer;
	/* Fin des composants */

	public FenetrePrincipale (Modele m) {
		_m = m;
		_nbCol = m.getGrille().getX();
		_nbLigne = m.getGrille().getY();
		build();
	}

	private void build() {
		/* Création de l'interface */
		this.setMinimumSize(new Dimension(750, 600));

		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuEdition = new JMenu("Edition");
		JMenu menuAide = new JMenu("Aide");
		_itemOuvrir = new JMenuItem("Ouvrir...");
		_itemEnregistrer = new JMenuItem("Enregistrer Sous...");
		_itemQuitter = new JMenuItem("Quitter");
		_itemAPropos = new JMenuItem("A Propos...");
		menuFichier.add(_itemOuvrir);
		menuFichier.add(_itemEnregistrer);
		menuFichier.add(_itemQuitter);
		menuAide.add(_itemAPropos);
		JMenuBar barreMenu = new JMenuBar();
		barreMenu.add(menuFichier);
		barreMenu.add(menuEdition);
		barreMenu.add(menuAide);
		this.setJMenuBar(barreMenu);

		JPanel panelPrincipal = new JPanel(new BorderLayout());
		this.setContentPane(panelPrincipal);

		_panelGrille = new JPanel(new GridLayout(_nbLigne, _nbCol));
		for (int i = 0; i < _nbLigne*_nbCol; i++) {
			_panelGrille.add(new JPanel());
		}
		panelPrincipal.add(_panelGrille, BorderLayout.CENTER);

		JPanel panelBoutons = new JPanel(new GridLayout(3, 0, 5, 5));
		_boutonLancer = new JButton("Lancer !");
		_boutonPause = new JButton("Pause");
		_boutonEffacer = new JButton("Effacer");
		panelBoutons.add(_boutonLancer);
		panelBoutons.add(_boutonPause);
		panelBoutons.add(_boutonEffacer);
		panelPrincipal.add(panelBoutons, BorderLayout.EAST);

		/* Connection des signaux */
		this.addWindowListener(
			new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					onQuitAction();
				}
			});
		_itemQuitter.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						onQuitAction();
					}
				});
		_m.addObserver(this);
	}

	@Override
	public void run () {
		this.setVisible(true);
	}

	@Override
	public void update (Observable o,
						Object arg) {
		System.out.print("Update\n");
		for (int i = 0; i < _nbLigne; i++) {
			for (int j = 0; j < _nbCol; j++) {
				if (_m.estVivante(j,i)) {
					_panelGrille.getComponent((i+1)*j).
							setBackground(Color.red);
				} else {
					_panelGrille.getComponent((i+1)*j).
							setBackground(Color.white);
				}
			}
		}
	}

	private void onQuitAction () {
		System.exit(0);
	}
}
