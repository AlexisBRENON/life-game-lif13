/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author alexis
 */
public class FenetrePrincipale extends JFrame implements Runnable, Observer {

	private static final long serialVersionUID = 1L;
	private int _nbCol;
	private int _nbLigne;
	private Modele _m;

	/*
	 * Composants de la fenêtre
	 */
	private JMenuItem _itemEnregistrer;
	private JMenuItem _itemOuvrir;
	private JMenuItem _itemQuitter;
	private JMenuItem _itemAPropos;
	private JPanel _panelGrille;
	private JComboBox _shapesBox;
	private JPanel[][] _cellules;
	private JButton _boutonInit;
	private JButton _boutonLancer;
	private JToggleButton _boutonPause;
	private JButton _clearButton;
	private JLabel _compteur;
	/*
	 * Fin des composants
	 */

	public FenetrePrincipale (Modele m) {
		_m = m;
		_nbCol = m.getGrille().getX();
		_nbLigne = m.getGrille().getY();
		_cellules = new JPanel[_nbCol][_nbLigne];
		build();
	}

	private void build () {
		/*
		 * Création de l'interface
		 */
		this.setMinimumSize(new Dimension(750, 600));

		/*
		 * Création de la barre de menu
		 */
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

		/*
		 * Définition du layout principal
		 */
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		this.setContentPane(panelPrincipal);

		/*
		 * Création de la grille
		 */
		_panelGrille = new JPanel(new GridLayout(_nbLigne, _nbCol));
		for (int j = 0; j < _nbLigne; j++) {
			for (int i = 0; i < _nbCol; i++) {
				final int x = i, y = j;
				_cellules[i][j] = new JPanel();
				_cellules[i][j].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
				_cellules[i][j].setBackground(Color.white);
				_panelGrille.add(_cellules[i][j]);
			}
		}
		panelPrincipal.add(_panelGrille, BorderLayout.CENTER);

		/*
		 * Création des boutons principaux
		 */
		JPanel panelBoutons = new JPanel(new GridLayout(3, 0, 5, 5));
		_boutonLancer = new JButton("Lancer !");
		_boutonPause = new JToggleButton("Pause");
		_boutonInit = new JButton("Initialiser");
		panelBoutons.add(_boutonInit);
		panelBoutons.add(_boutonLancer);
		panelBoutons.add(_boutonPause);
		panelPrincipal.add(panelBoutons, BorderLayout.EAST);

		/*
		 * Ajout des infos optionnels
		 */
		JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 0));
		_compteur = new JLabel("Nombre d'itérations : 0");
		_clearButton = new JButton("Effacer");
		String[] shapeList = {"Point", "Carré", "Trait"};
		_shapesBox = new JComboBox(shapeList);
		optionPanel.add(_compteur);
		optionPanel.add(_clearButton);
		optionPanel.add(_shapesBox);
		panelPrincipal.add(optionPanel, BorderLayout.SOUTH);



	}

	@Override
	public void run () {
		this.setVisible(true);
	}

	@Override
	public void update (Observable o,
						Object arg) {
		if (_m.getNbThread() == 1) {
		for (int i = 0; i < _nbCol; i++) {
			for (int j = 0; j < _nbLigne; j++) {
				if (_m.estVivante(i, j)) {
					_cellules[i][j].setBackground(Color.red);
				} else {
					_cellules[i][j].setBackground(Color.white);
				}
			}
		}
		} else {
			Thread[] tab = new Thread[_m.getNbThread()];
			for (int i = 0; i < _m.getNbThread(); i++) {
				tab[i] = new Thread(new ThreadedUpdate(_m.getNbThread(), i, this));
				tab[i].start();
			}
			for (int i = 0; i < _m.getNbThread(); i++) {
				try {
					tab[i].join();
				} catch (InterruptedException ex) {
					System.out.println("Problème de calcul distribué...");
					System.exit(1);
				}
			}
		}
		_compteur.setText("Nombre d'itérations : "+Integer.toString(_m.getNbIter()));
	}

	public JMenuItem getItemAPropos () {
		return _itemAPropos;
	}

	public JMenuItem getItemEnregistrer () {
		return _itemEnregistrer;
	}

	public JMenuItem getItemOuvrir () {
		return _itemOuvrir;
	}

	public JMenuItem getItemQuitter () {
		return _itemQuitter;
	}

	public Modele getM () {
		return _m;
	}

	public int getNbCol () {
		return _nbCol;
	}

	public int getNbLigne () {
		return _nbLigne;
	}

	public JPanel getPanelGrille () {
		return _panelGrille;
	}

	public JComboBox getShapesBox () {
		return _shapesBox;
	}

	public JPanel[][] getCellules () {
		return _cellules;
	}

	public JButton getBoutonInit () {
		return _boutonInit;
	}

	public JButton getBoutonLancer () {
		return _boutonLancer;
	}

	public JToggleButton getBoutonPause () {
		return _boutonPause;
	}

	public JButton getClearButton () {
		return _clearButton;
	}

	public void setSelected (Object o, boolean b) {
		if (o instanceof JPanel) {
			if (b) {
				((JPanel) o).setBackground(((JPanel) o).getBackground().darker());
			} else {
				((JPanel) o).setBackground(((JPanel) o).getBackground().brighter());
			}
		}
	}


}
