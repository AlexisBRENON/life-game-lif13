/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author alexis
 */
public class Controlleur {

	private FenetrePrincipale win;
	private Modele m;

	public Controlleur () {
		this(10, 10, 1f, 1);
	}

	public Controlleur (int width, int height) {
		this(width, height, 1f, 1);
	}

	public Controlleur (int nbThread) {
		this(10, 10, 1f, nbThread);
	}

	public Controlleur (int width, int height, int nbThread) {
		this(width, height, 1f, nbThread);
	}

	public Controlleur (int width, int height, float timeStep, int nbThread) {
		m = new Modele(width, height, timeStep, nbThread);
		win = new FenetrePrincipale(m);
		this.connectSignals();
	}

	public void startApp () {
		new Thread(win).start();
	}

	public void startApplet() {
		new Thread(win).start();
		win.setVisible(false);
	}

	private void connectSignals() {
            /*Ajout des addlistener sur les différents éléments de la fenetre*/

            /*Ajout des listener sur les cellules de la grilles*/
                for (int j = 0; j < m.getGrille().getY(); j++) {
			for (int i = 0; i < m.getGrille().getX(); i++) {
				final int x = i;
				final int y = j;
				win.getCellules()[i][j].addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked (MouseEvent e) {
						//System.out.println(x+" "+y);
						onMouseClickedOnCell(e,
											 x,
											 y);
					}

					@Override
					public void mousePressed (MouseEvent e) {
					}

					@Override
					public void mouseReleased (MouseEvent e) {
					}

					@Override
					public void mouseEntered (MouseEvent e) {
						onMouseEnteredOnCell(e, x, y);
					}

					@Override
					public void mouseExited (MouseEvent e) {
						onMouseExitedCell(e, x, y);
					}
				});
			}
		}

		/* Connection des signaux sur le fenetre principale*/
		m.addObserver(win);
		win.addWindowListener(
				new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					onQuitAction();
				}
			});

		win.getItemEnregistrer().addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onSaveAction();
			}
		});
		win.getItemOuvrir().addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onOpenAction();
			}
		});
		win.getItemQuitter().addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent e) {
						onQuitAction();
					}
				});

		win.getBoutonLancer().addActionListener(
				new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				onLaunchAction(e);
			}
		});
		win.getBoutonPause().addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onPauseAction(e);
			}
		});
		win.getBoutonInit().addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onInitAction();
			}
		});

		win.getClearButton().addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onClearAction();
			}
		});
		win.getShapesBox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onShapesBoxSelectionChanged(e);
			}
		});
	}

	private void onQuitAction () {
		System.exit(0);
	}

	private void onLaunchAction (ActionEvent e) {
            /* Lance le jeu de la vie*/
		Object o;
		o = e.getSource();
		if (o instanceof JButton) {
			((JButton) o).setEnabled(false);
		}
		m.lancerThread();
	}

	private void onPauseAction (ActionEvent e) {
            /* Met en pause le jeu */
		m.switchPause();
		Object o;
		o = e.getSource();
		if (o instanceof JToggleButton) {
			((JToggleButton) o).setSelected(((JToggleButton) o).isSelected());
		}
	}

	private void onInitAction () {
         /* Initialise la grille avec des valeur pour chaque cellule aléatoire */
		boolean currentState = m.isPaused();
		m.clear();
		m.setPaused(true);
		m.init();
		win.update(m, null);
		m.setPaused(currentState);
	}

	public void onMouseEnteredOnCell (MouseEvent e, int x, int y) {
        /*Assombri la cellule selectionné avec la souris*/
		Motif pattern = m.getPattern();
		for (int i = 0; i < pattern.getX(); i++) {
			for (int j = 0; j < pattern.getY(); j++) {
				if (pattern.estVivante(new Coordonnee(i, j))) {
					JPanel p = win.getCellules()
							[((i + x - pattern.getX() / 2) + win.getNbCol()) % win.getNbCol()]
							[((j + y - pattern.getY() / 2) + win.getNbLigne()) % win.getNbLigne()];
					win.setSelected(p, true);
				}
			}
		}
	}

	public void onMouseClickedOnCell (MouseEvent e, int x, int y) {
            /* Ajoute ou supprime une cellule sur la grille aux coordonnées x, y
             */
		Object o = win.getShapesBox().getSelectedItem();
		if (o instanceof String) {
			if (m.getGrille().estVivante(x, y) && ((String)o).equalsIgnoreCase("Point")) {
				m.getGrille().removeCellule(new Coordonnee(x, y));
			}
		}
		m.addMotif(new Coordonnee(x, y));
		win.update(m, null);
	}

	public void onMouseExitedCell (MouseEvent e, int x, int y) {
		Motif pattern = m.getPattern();
		for (int i = 0; i < pattern.getX(); i++) {
			for (int j = 0; j < pattern.getY(); j++) {
				if (pattern.estVivante(new Coordonnee(i, j))) {
					JPanel p = win.getCellules()
							[((i + x - pattern.getX() / 2) + win.getNbCol()) % win.getNbCol()]
							[((j + y - pattern.getY() / 2) + win.getNbLigne()) % win.getNbLigne()];
					win.setSelected(p, false);
				}
			}
		}
	}

	public void onSaveAction () {
		int result;
		JFileChooser saveWindow = new JFileChooser();

		result = saveWindow.showSaveDialog(win);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				m.getGrille().save(
						saveWindow.getSelectedFile().getAbsolutePath());
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

		result = openWindow.showOpenDialog(win);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				m.getGrille().load(
						openWindow.getSelectedFile().getAbsolutePath());
			} catch (IOException ex) {
				System.out.print("Erreur Catch\n");
			}
			win.update(m, null);
		}
	}

	public void onClearAction () {
		boolean currentState = m.isPaused();
		m.setPaused(true);
		m.getGrille().clearGrille();
		win.update(m, null);
		m.setPaused(currentState);
	}

	public void onShapesBoxSelectionChanged (ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JComboBox) {
			Object o = ((JComboBox) source).getSelectedItem();
			if (o instanceof String) {
				String s = (String) o;
				if (s.equalsIgnoreCase("Point")) {
					Motif pattern = new Motif(1, 1);
					pattern.getMap().put(new Coordonnee(0,0),
										 new Cellule(new Coordonnee(0,0), true));
					m.setPattern(pattern);
				} else if (s.equalsIgnoreCase("Trait Vertical")) {
					Motif pattern = new Motif(1, 3);
					pattern.getMap().put(new Coordonnee(0,0),
										 new Cellule(new Coordonnee(0,0), true));
					pattern.getMap().put(new Coordonnee(0,1),
										 new Cellule(new Coordonnee(0,1), true));
					pattern.getMap().put(new Coordonnee(0,2),
										 new Cellule(new Coordonnee(0,2), true));
					m.setPattern(pattern);
				} else if (s.equalsIgnoreCase("Trait Horizontal")) {
					Motif pattern = new Motif(3, 1);
					pattern.getMap().put(new Coordonnee(0,0),
										 new Cellule(new Coordonnee(0,0), true));
					pattern.getMap().put(new Coordonnee(1,0),
										 new Cellule(new Coordonnee(1,0), true));
					pattern.getMap().put(new Coordonnee(2,0),
										 new Cellule(new Coordonnee(2,0), true));
					m.setPattern(pattern);
				} else if (s.equalsIgnoreCase("Carré")) {
					Motif pattern = new Motif(2, 2);
					pattern.getMap().put(new Coordonnee(0,0),
										 new Cellule(new Coordonnee(0,0), true));
					pattern.getMap().put(new Coordonnee(0,1),
										 new Cellule(new Coordonnee(0,1), true));
					pattern.getMap().put(new Coordonnee(1,0),
										 new Cellule(new Coordonnee(1,0), true));
					pattern.getMap().put(new Coordonnee(1,1),
										 new Cellule(new Coordonnee(1,1), true));
					m.setPattern(pattern);
				}
			}
		}
	}

	public FenetrePrincipale getWin () {
		return win;
	}


}
