/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        private Boolean launch;

	/* Composants de la fenêtre */
	private JMenuItem _itemEnregistrer;
	private JMenuItem _itemOuvrir;
	private JMenuItem _itemQuitter;
	private JMenuItem _itemAPropos;
	private JPanel _panelGrille;
	private JComboBox _shapesBox;
	private JPanel[][] _cellules;
	/* Fin des composants */

	public FenetrePrincipale (Modele m) {
		_m = m;
        launch=false;
		_nbCol = m.getGrille().getX();
		_nbLigne = m.getGrille().getY();
		_cellules = new JPanel[_nbCol][_nbLigne];
		build();
	}


	private void build() {
		/* Création de l'interface */
		this.setMinimumSize(new Dimension(750, 600));

		/* Création de la barre de menu */
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

		/* Définition du layout principal */
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		this.setContentPane(panelPrincipal);

		/* Création de la grille */
		_panelGrille = new JPanel(new GridLayout(_nbLigne, _nbCol));
		for (int j = 0; j < _nbLigne; j++) {
			for (int i = 0; i < _nbCol; i++) {
				final int x=i, y=j;
				_cellules[i][j] = new JPanel();
				_cellules[i][j].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
				_cellules[i][j].setBackground(Color.white);
				_cellules[i][j].addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked (MouseEvent e) {
						//System.out.println(x+" "+y);
						onMouseClickedOnCell(e, x, y);
					}

					@Override
					public void mousePressed (MouseEvent e) {
					}

					@Override
					public void mouseReleased (MouseEvent e) {
					}

					@Override
					public void mouseEntered (MouseEvent e) {
						onMouseEnteredOnCell(e);
					}

					@Override
					public void mouseExited (MouseEvent e) {
						onMouseExitedCell(e);
					}
				});
				_panelGrille.add(_cellules[i][j]);
			}
		}
		panelPrincipal.add(_panelGrille, BorderLayout.CENTER);

		/* Création des boutons principaux */
		JPanel panelBoutons = new JPanel(new GridLayout(3, 0, 5, 5));
		JButton boutonLancer = new JButton("Lancer !");
		JToggleButton boutonPause = new JToggleButton("Pause");
		JButton boutonInit = new JButton("Initialiser");
		panelBoutons.add(boutonInit);
		panelBoutons.add(boutonLancer);
		panelBoutons.add(boutonPause);
		panelPrincipal.add(panelBoutons, BorderLayout.EAST);

		/* Ajout des boutons optionnels */
		JPanel optionPanel = new JPanel(new FlowLayout());
		JButton clearButton = new JButton("Effacer");
		String[] shapeList = {"Point", "Carré", "Trait"};
		_shapesBox = new JComboBox(shapeList);
		optionPanel.add(clearButton);
		optionPanel.add(_shapesBox);
		panelPrincipal.add(optionPanel, BorderLayout.SOUTH);

		/* Connection des signaux */
		_m.addObserver(this);
		this.addWindowListener(
				new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					onQuitAction();
				}
			});

		_itemEnregistrer.addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onSaveAction();
			}
		});
		_itemOuvrir.addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onOpenAction();
			}
		});
		_itemQuitter.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						onQuitAction();
					}
				});

		boutonLancer.addActionListener(
				new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				onLaunchAction(e);
			}
		});
		boutonPause.addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onPauseAction(e);
			}
		});
		boutonInit.addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onInitAction();
			}
		});

		clearButton.addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onClearAction();
			}
		});
		_shapesBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onShapesBoxSelectionChanged(e);
			}
		});

	}

	@Override
	public void run () {
		this.setVisible(true);
	}

	@Override
	public void update (Observable o,
						Object arg) {
		for (int i = 0; i < _nbCol; i++) {
			for (int j = 0; j < _nbLigne; j++) {
				if (_m.estVivante(i,j)) {
					_cellules[i][j].setBackground(Color.red);
				} else {
					_cellules[i][j].setBackground(Color.white);
				}
			}
		}
	}

	private void onQuitAction () {
		System.exit(0);
	}

	private void onLaunchAction(ActionEvent e) {
		Object o;
		o = e.getSource();
		if (o instanceof JButton) {
			((JButton)o).setEnabled(false);
		}
		_m.lancerThread();
	}

	private void onPauseAction(ActionEvent e) {
		_m.switchPause();
		Object o;
		o = e.getSource();
		if (o instanceof JToggleButton) {
			((JToggleButton)o).setSelected(((JToggleButton)o).isSelected());
		}
	}

	private void onInitAction() {
		_m.clear();
		_m.setPaused(true);
		_m.getGrille().initGrille();
		this.update(_m, null);
		_m.switchPause();
	}

	public void onMouseEnteredOnCell(MouseEvent e) {
		Object o = e.getComponent();
		if (o instanceof JPanel) {
			if (e.getButton() != MouseEvent.NOBUTTON) {
				System.out.println("Bouton pressé !");
			}
                ((JPanel)o).setBackground(((JPanel)o).getBackground().darker());
		}
	}

        public void onMouseClickedOnCell(MouseEvent e,int x,int y){
                if(_m.grille.estVivante(x, y)){
                    _m.grille.removeCellule(new Coordonnee(x, y));
                } else {
					_m.grille.addCellule(new Coordonnee(x,y));
				}
        }

	public void onMouseExitedCell (MouseEvent e) {
            update(_m, null);
	}

	public void onSaveAction () {
		int result;
		JFileChooser saveWindow = new JFileChooser();

		result = saveWindow.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				_m.getGrille().save(saveWindow.getSelectedFile().getAbsolutePath());
			} catch (IOException ex) {
				Logger.getLogger(FenetrePrincipale.class.getName()).
						log(Level.SEVERE,
							null,
							ex);
			}
		}

	}

	public void onOpenAction () {
		int result;
		JFileChooser openWindow = new JFileChooser();

		result = openWindow.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				_m.getGrille().load(openWindow.getSelectedFile().getAbsolutePath());
			} catch (IOException ex) {
				Logger.getLogger(FenetrePrincipale.class.getName()).
						log(Level.SEVERE,
							null,
							ex);
			}
		}
	}

	public void onClearAction () {
		_m.setPaused(true);
		_m.getGrille().clearGrille();
		this.update(_m, null);
		_m.setPaused(true);
	}

	public void onShapesBoxSelectionChanged (ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JComboBox) {
			Object o = ((JComboBox) source).getSelectedItem();
			if (o instanceof String) {
				String s = (String) o;
				System.out.println(s);
			}
		}
	}
}
