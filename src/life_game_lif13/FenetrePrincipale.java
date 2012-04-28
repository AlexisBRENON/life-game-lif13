/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author alexis
 * @class FenetrePrincipale is the View part of the MVC. It's the GUI, catching
 * all the events from the user and displaying the model's informations.
 * It's a window, so it extends JFrame, some thread manage the events and display,
 * so it implements Runnable, and it's looking after Model to update display, so
 * it implements Observer.
 */
public class FenetrePrincipale extends JFrame implements Runnable, Observer {

	private static final long serialVersionUID = 1L;
	private int _nbCol;
	private int _nbLigne;
	private HashMap<String, Motif> patternList;

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
	private JMenuItem _itemOuvrirMotif;
	private JPanel panelPrincipal;
	private JSpinner widthSpinner;
	private JSpinner heightSpinner;
	/*
	 * Fin des composants
	 */

	public FenetrePrincipale (Modele m) {
		_nbCol = m.getGrille().getX();
		_nbLigne = m.getGrille().getY();
		_cellules = new JPanel[_nbCol][_nbLigne];
		this.patternList = new HashMap<String, Motif>();
		// Init the patterns list.
		initPatterns();
		// Build the components of the window.
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
		_itemOuvrirMotif = new JMenuItem("Ouvrir Motif...");
		_itemEnregistrer = new JMenuItem("Enregistrer Sous...");
		_itemQuitter = new JMenuItem("Quitter");
		_itemAPropos = new JMenuItem("A Propos...");
		menuFichier.add(_itemOuvrir);
		menuFichier.add(_itemEnregistrer);
		menuFichier.add(new JSeparator());
		menuFichier.add(_itemQuitter);
		menuEdition.add(_itemOuvrirMotif);
		menuAide.add(_itemAPropos);
		JMenuBar barreMenu = new JMenuBar();
		barreMenu.add(menuFichier);
		barreMenu.add(menuEdition);
		barreMenu.add(menuAide);
		this.setJMenuBar(barreMenu);

		/*
		 * Définition du layout principal
		 */
		panelPrincipal = new JPanel(new BorderLayout());
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
		JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 5));
		// Création du compteur d'itérations
		_compteur = new JLabel("Nombre d'itérations : 0");
		// Création du bouton qui efface la grille.
		_clearButton = new JButton("Effacer");
		// Création de la combobox contenant les patterns disponibles (tries).
		Set<String> shapeList = patternList.keySet();
		String[] shapeArray = new String[shapeList.size()];
		shapeList.toArray(shapeArray);
		Arrays.sort(shapeArray, String.CASE_INSENSITIVE_ORDER);
		_shapesBox = new JComboBox(shapeArray);
		_shapesBox.setSelectedItem("Point");
		// Creation des spinners de redimmensionnement de la grille.
		JPanel widthPanel = new JPanel(new GridLayout(2, 1));
		widthSpinner = new JSpinner(new SpinnerNumberModel(_nbCol, 1, 250, 1));
		widthPanel.add(new JLabel("Largeur :"));
		widthPanel.add(widthSpinner);
		JPanel heightPanel = new JPanel(new GridLayout(2, 1));
		heightSpinner = new JSpinner(new SpinnerNumberModel(_nbLigne, 1, 250, 1));
		heightPanel.add(new JLabel("Hauteur :"));
		heightPanel.add(heightSpinner);

		// Ajout de tous ces composants.
		optionPanel.add(_compteur);
		optionPanel.add(_clearButton);
		optionPanel.add(_shapesBox);
		optionPanel.add(widthPanel);
		optionPanel.add(heightPanel);
		panelPrincipal.add(optionPanel, BorderLayout.SOUTH);
	}

	/*
	 * Define all commons pattern, assigning a name to each of them.
	 */
	private void initPatterns () {
		Motif pattern = new Motif(1, 1);
		pattern.addPoint(0, 0);
		patternList.put("Point", pattern);

		pattern = new Motif(3, 1);
		pattern.addPoint(0, 0);
		pattern.addPoint(1, 0);
		pattern.addPoint(2, 0);
		patternList.put("Trait Horizontal", pattern);

		pattern = new Motif(1, 3);
		pattern.addPoint(0, 0);
		pattern.addPoint(0, 1);
		pattern.addPoint(0, 2);
		patternList.put("Trait Vertical", pattern);

		pattern = new Motif(2, 2);
		pattern.addPoint(0, 0);
		pattern.addPoint(0, 1);
		pattern.addPoint(1, 0);
		pattern.addPoint(1, 1);
		patternList.put("Carré", pattern);

		pattern = new Motif(3, 3);
		pattern.addPoint(1, 0);
		pattern.addPoint(0, 1);
		pattern.addPoint(1, 2);
		pattern.addPoint(2, 1);
		patternList.put("Croix Stable", pattern);

		pattern = new Motif(9, 9);
		pattern.addPoint(4, 0);
		pattern.addPoint(4, 1);
		pattern.addPoint(4, 2);
		pattern.addPoint(4, 6);
		pattern.addPoint(4, 7);
		pattern.addPoint(4, 8);
		pattern.addPoint(0, 4);
		pattern.addPoint(1, 4);
		pattern.addPoint(2, 4);
		pattern.addPoint(6, 4);
		pattern.addPoint(7, 4);
		pattern.addPoint(8, 4);
		patternList.put("Croix Cyclique", pattern);

		pattern = new Motif(4, 5);
		pattern.addPoint(1, 0);
		pattern.addPoint(2, 0);
		pattern.addPoint(3, 0);
		pattern.addPoint(2, 1);
		pattern.addPoint(2, 2);
		pattern.addPoint(2, 3);
		pattern.addPoint(2, 4);
		pattern.addPoint(1, 4);
		pattern.addPoint(0, 3);
		patternList.put("J", pattern);

		pattern = new Motif(3, 4);
		pattern.addPoint(1, 0);
		pattern.addPoint(0, 1);
		pattern.addPoint(2, 1);
		pattern.addPoint(0, 2);
		pattern.addPoint(1, 2);
		pattern.addPoint(2, 2);
		pattern.addPoint(0, 3);
		pattern.addPoint(2, 3);
		patternList.put("A", pattern);

		pattern = new Motif(36, 9);
		pattern.addPoint(0, 4);
		pattern.addPoint(0, 5);
		pattern.addPoint(1, 4);
		pattern.addPoint(1, 5);
		pattern.addPoint(10, 4);
		pattern.addPoint(10, 5);
		pattern.addPoint(10, 6);
		pattern.addPoint(11, 3);
		pattern.addPoint(11, 7);
		pattern.addPoint(12, 2);
		pattern.addPoint(12, 8);
		pattern.addPoint(13, 2);
		pattern.addPoint(13, 8);
		pattern.addPoint(14, 5);
		pattern.addPoint(15, 3);
		pattern.addPoint(15, 7);
		pattern.addPoint(16, 4);
		pattern.addPoint(16, 5);
		pattern.addPoint(16, 6);
		pattern.addPoint(17, 5);
		pattern.addPoint(20, 2);
		pattern.addPoint(20, 3);
		pattern.addPoint(20, 4);
		pattern.addPoint(21, 2);
		pattern.addPoint(21, 3);
		pattern.addPoint(21, 4);
		pattern.addPoint(22, 1);
		pattern.addPoint(22, 5);
		pattern.addPoint(24, 0);
		pattern.addPoint(24, 1);
		pattern.addPoint(24, 5);
		pattern.addPoint(24, 6);
		pattern.addPoint(34, 2);
		pattern.addPoint(34, 3);
		pattern.addPoint(35, 2);
		pattern.addPoint(35, 3);
		patternList.put("Gun (36*9)", pattern);

		pattern = new Motif(3, 3);
		pattern.addPoint(2, 0);
		pattern.addPoint(2, 1);
		pattern.addPoint(2, 2);
		pattern.addPoint(1, 2);
		pattern.addPoint(0, 1);
		patternList.put("Planneur", pattern);

		pattern = new Motif((int) (Math.random() * _nbCol / 2),
							(int) (Math.random() * _nbLigne / 2));
		pattern.InitMotif();
		patternList.put("Aléatoire", pattern);
	}

	/*
	 * The overrided method, lauch by the EDT.
	 */
	@Override
	public void run () {
		this.setVisible(true);
	}

	/*
	 * The Observer overrided method. It's called each time the model call
	 * 'notify'.
	 */
	@Override
	public void update (Observable o, Object arg) {
		if (o instanceof Modele) {
			Modele m = (Modele) o;
			for (int i = 0; i < _nbCol; i++) {
				for (int j = 0; j < _nbLigne; j++) {
					if (m.estVivante(i, j)) {
						_cellules[i][j].setBackground(Color.red);
					} else {
						_cellules[i][j].setBackground(Color.white);
					}
				}
			}
			_compteur.setText("Nombre d'itérations : " + Integer.toString(m.getNbIter()));
		} else {
			System.out.println("Problème de mise à jour.");
		}
	}

	/*
	 * This function is called when a new pattern is inserted into the map.
	 * it redraw the ComboBox to take in count the new pattern.
	 */
	public void updateShapeBox () {
		Set<String> shapeList = patternList.keySet();
		String[] shapeArray = (String[]) shapeList.toArray();
		Arrays.sort(shapeArray, String.CASE_INSENSITIVE_ORDER);
		_shapesBox.removeAllItems();
		_shapesBox.addItem(shapeArray);
	}


	/*
	 * This function is used to set more visible a cell of the grid
	 */
	public void setSelected (Object o, boolean b) {
		if (o instanceof JPanel) {
			if (b) {
				((JPanel) o).setBackground(((JPanel) o).getBackground().darker());
			} else {
				((JPanel) o).setBackground(((JPanel) o).getBackground().brighter());
			}
		}
	}


	/*
	 * GETTERS && SETTERS
	 */

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

	public JMenuItem getItemOuvrirMotif () {
		return _itemOuvrirMotif;
	}

	public JSpinner getHeightSpinner () {
		return heightSpinner;
	}

	public JSpinner getWidthSpinner () {
		return widthSpinner;
	}

	public HashMap<String, Motif> getPatternList () {
		return patternList;
	}

	public void setNbCol (int _nbCol) {
		this._nbCol = _nbCol;
	}

	public void setNbLigne (int _nbLigne) {
		this._nbLigne = _nbLigne;
	}

	/*
	 * This function is used to change the grid size.
	 * It destroy the current panel, create a new one, and connect Listeners.
	 */
	public void setPanelGrille (int lign, int col, final Controlleur c) {
		panelPrincipal.remove(_panelGrille);
		this._nbLigne = lign;
		this._nbCol = col;

		_panelGrille = new JPanel(new GridLayout(_nbLigne, _nbCol));
		_cellules = new JPanel[_nbCol][_nbLigne];
		for (int j = 0; j < _nbLigne; j++) {
			for (int i = 0; i < _nbCol; i++) {
				final int x = i, y = j;
				_cellules[i][j] = new JPanel();
				_cellules[i][j].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
				_cellules[i][j].setBackground(Color.white);
				_cellules[i][j].addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked (MouseEvent e) {
						c.onMouseClickedOnCell(e, x, y);
					}

					@Override
					public void mousePressed (MouseEvent e) {
					}

					@Override
					public void mouseReleased (MouseEvent e) {
					}

					@Override
					public void mouseEntered (MouseEvent e) {
						c.onMouseEnteredOnCell(e, x, y);
					}

					@Override
					public void mouseExited (MouseEvent e) {
						c.onMouseExitedCell(e, x, y);
					}
				});
				_panelGrille.add(_cellules[i][j]);
			}
		}
		panelPrincipal.add(_panelGrille, BorderLayout.CENTER);

		// Used to redraw this part of the window.
		_panelGrille.revalidate();
		_panelGrille.repaint();
	}
}
