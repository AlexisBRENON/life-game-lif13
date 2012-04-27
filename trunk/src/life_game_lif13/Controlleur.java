/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Controlleur is the controler part of the MVC. It makes the control
 * (and not update) link between View and Model. On each useful event from the
 * View, it update the model.
 *
 * @see Modele
 * @see FenetrePrincipale
 *
 * @author alexis
 *
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

	/**
	 * The main constructor.
	 * @param width Width of the grid.
	 * @param height Height of the grid.
	 * @param timeStep Time in seconds between to update
	 * @param nbThread Number of threads of calculation.
	 */
	public Controlleur (int width, int height, float timeStep, int nbThread) {
		m = new Modele(width, height, timeStep, nbThread);
		win = new FenetrePrincipale(m);
		this.connectSignals();
	}

	/**
	 * Launch the application, opening the window.
	 */
	public void startApp () {
		new Thread(win).start();
	}
	/**
	 * Lanch the applet.
	 */
	public void startApplet () {
		new Thread(win).start();
		win.setVisible(false);
	}

	/**
	 * Connect all Listeners between the View and the controller.
	 */
	private void connectSignals () {
		/*
		 * Ajout des addlistener sur les différents éléments de la fenetre
		 */

		/*
		 * Ajout des listener sur les cellules de la grilles
		 */
		for (int j = 0; j < win.getNbLigne(); j++) {
			for (int i = 0; i < win.getNbCol(); i++) {
				final int x = i;
				final int y = j;
				win.getCellules()[i][j].addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked (MouseEvent e) {
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
						onMouseEnteredOnCell(e, x, y);
					}

					@Override
					public void mouseExited (MouseEvent e) {
						onMouseExitedCell(e, x, y);
					}
				});
			}
		}

		/*
		 * Connection des signaux sur le fenetre principale
		 */
		m.addObserver(win);
		win.addWindowListener(
				new WindowAdapter() {

					@Override
					public void windowClosing (WindowEvent e) {
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

		win.getItemOuvrirMotif().addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed (ActionEvent e) {
						onOpenMotifAction();
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

		win.getWidthSpinner().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged (ChangeEvent e) {
				onWidthChanged(e);
			}
		});

		win.getHeightSpinner().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged (ChangeEvent e) {
				onHeightChanged(e);
			}
		});
	}

	/**
	 * The exiting function.
	 * It's called when the user close the window or click on the Quit item.
	 */
	private void onQuitAction () {
		System.exit(0);
	}
	/**
	 * Launch the game, starting the simulation.
	 * @param e
	 */
	private void onLaunchAction (ActionEvent e) {
		Object o;
		o = e.getSource();
		if (o instanceof JButton) {
			JButton b = (JButton) o;
			/*
			 * Lance le jeux de la vie
			 */
			m.lancerSimulation();
			b.setEnabled(false);
		}
	}

	/**
	 * Pause the simulation.
	 * @param e
	 */
	private void onPauseAction (ActionEvent e) {
		/*
		 * Met en pause le jeu
		 */
		m.switchPause();
		Object o;
		o = e.getSource();
		if (o instanceof JToggleButton) {
			((JToggleButton) o).setSelected(((JToggleButton) o).isSelected());
		}
	}

	/**
	 * Init the game, randomly.
	 */
	private void onInitAction () {
		/*
		 * Initialise la grille aléatoirement
		 */
		boolean currentState = m.isPaused();
		m.clear();
		m.setPaused(true);
		m.reInit();
		win.update(m, null);
		m.setPaused(currentState);
	}
	/**
	 * Display the selected item on the grid when the mouse enter on it.
	 * @param e
	 * @param x The x-coord of the cell where the mouse is on.
	 * @param y The y-coord of the cell where the mouse is on.
	 */
	public void onMouseEnteredOnCell (MouseEvent e, int x, int y) {
		/*
		 * Assombri les cellules recouvertes par le pattern
		 */
		Motif pattern = m.getPattern();
		for (int i = 0; i < pattern.getX(); i++) {
			for (int j = 0; j < pattern.getY(); j++) {
				if (pattern.estVivante(new Coordonnee(i, j))) {
					JPanel p = win.getCellules()[((i + x - pattern.getX() / 2) + win.getNbCol()) % win.getNbCol()][((j + y - pattern.getY() / 2) + win.getNbLigne()) % win.getNbLigne()];
					win.setSelected(p, true);
				}
			}
		}
	}
	/**
	 * Add the pattern selected where the mouse is.
	 * @param e
	 * @param x The x-coord of the cell where the mouse is on.
	 * @param y The y-coord of the cell where the mouse is on.
	 */
	public void onMouseClickedOnCell (MouseEvent e, int x, int y) {
		/*
		 * Un pattern ou supprime UNE cellule
		 */
		Object o = win.getShapesBox().getSelectedItem();
		if (o instanceof String) {
			if (m.getGrille().estVivante(x, y) && ((String) o).equalsIgnoreCase("Point")) {
				m.getGrille().removeCellule(new Coordonnee(x, y));
			} else {
				m.addMotif(new Coordonnee(x, y));
			}
			win.update(m, null);
			if (((String) o).equalsIgnoreCase("Aléatoire")) {
				Motif motif = new Motif((int) (Math.random() * win.getNbCol() / 2),
										(int) (Math.random() * win.getNbLigne() / 2));
				motif.InitMotif();
				m.setPattern(motif);
			}
		}
	}
	/**
	 * Reinit the color of the cells which are not covered by the pattern.
	 * @param e
	 * @param x The x-coord of the cell where the mouse is on.
	 * @param y The y-coord of the cell where the mouse is on.
	 */
	public void onMouseExitedCell (MouseEvent e, int x, int y) {
		/*
		 * Réinitialise la couleur des cellules mises en évidences
		 */
		Motif pattern = m.getPattern();
		for (int i = 0; i < pattern.getX(); i++) {
			for (int j = 0; j < pattern.getY(); j++) {
				if (pattern.estVivante(new Coordonnee(i, j))) {
					JPanel p = win.getCellules()[((i + x - pattern.getX() / 2) + win.getNbCol()) % win.getNbCol()][((j + y - pattern.getY() / 2) + win.getNbLigne()) % win.getNbLigne()];
					win.setSelected(p, false);
				}
			}
		}
	}

	/**
	 * Open a SaveAs dialog box to save the current configuration of the grid.
	 */
	private void onSaveAction () {
		/*
		 * Ouvre une fenêtre de sauvegarde de fichier et lance la sauvegarde
		 */
		int result;
		JFileChooser saveWindow = new JFileChooser();

		result = saveWindow.showSaveDialog(win);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				m.getGrille().save(
						saveWindow.getSelectedFile().getAbsolutePath());
				JOptionPane.showMessageDialog(win,
											  "Sauvegarde effectuée.",
											  "Succès",
											  JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(win,
											  "Echec de la sauvegarde :\n" +
											  ex.getLocalizedMessage(),
											  "Erreur",
											  JOptionPane.ERROR_MESSAGE);
			}
		}

	}
	/**
	 * Open an open dialog box to load a grid configuration.
	 */
	private void onOpenAction () {
		/*
		 * Ouvre la fenêtre d'ouverture et charge le fichier
		 */
		int result;
		JFileChooser openWindow = new JFileChooser();

		result = openWindow.showOpenDialog(win);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				m.getGrille().load(
						openWindow.getSelectedFile().getAbsolutePath());
			} catch (IOException ex) {
				System.out.print("Erreur de chargement\n");
				JOptionPane.showMessageDialog(win,
											  "Echec du chargement :\n"+
											  ex.getLocalizedMessage(),
											  "Erreur",
											  JOptionPane.ERROR_MESSAGE);
			}
			win.setPanelGrille(m.getGrille().getY(), m.getGrille().getX(), this);
			win.update(m, null);
		}
	}

	/**
	 * Open an open dialog box to load a new pattern.
	 */
	public void onOpenMotifAction () {
		/*
		 * Ouvre la fenêtre d'ouverture et charge le fichier
		 */
		int result;
		JFileChooser openWindow = new JFileChooser();

		result = openWindow.showOpenDialog(win);
		if (result == JFileChooser.APPROVE_OPTION) {
			Motif motif = new Motif(openWindow.getSelectedFile());
			win.getPatternList().put(motif.getName(), motif);
			win.updateShapeBox();
		}
	}

	/**
	 * Clear all the grid to make place clean.
	 */
	private void onClearAction () {
		/*
		 * Efface la grille
		 */
		boolean currentState = m.isPaused();
		m.setPaused(true);
		m.clear();
		win.update(m, null);
		m.setPaused(currentState);
	}

	/**
	 * Change the selected pattern to add.
	 * @param e
	 */
	private void onShapesBoxSelectionChanged (ActionEvent e) {
		/*
		 * Change le pattern d'ajout
		 */
		Object source = e.getSource();
		if (source instanceof JComboBox) {
			Object o = ((JComboBox) source).getSelectedItem();
			if (o instanceof String) {
				String s = (String) o;
				m.setPattern(win.getPatternList().get(s));
			}
		}
	}

	/**
	 * Change the width of the grid.
	 * @param e
	 */
	private void onWidthChanged (ChangeEvent e) {
		boolean currentState = m.isPaused();
		m.setPaused(true);
		win.setPanelGrille(win.getNbLigne(),
						   (Integer) ((JSpinner) e.getSource()).getValue(),
						   this);
		m.getGrille().setX((Integer) ((JSpinner) e.getSource()).getValue());
		m.setPaused(currentState);
	}
	/**
	 * Change the height of the grid.
	 * @param e
	 */
	public void onHeightChanged (ChangeEvent e) {
		boolean currentState = m.isPaused();
		m.setPaused(true);
		win.setPanelGrille((Integer) ((JSpinner) e.getSource()).getValue(),
						   win.getNbCol(),
						   this);
		m.getGrille().setY((Integer) ((JSpinner) e.getSource()).getValue());
		m.setPaused(currentState);
	}

	/**
	 * Getter for the JApplet.
	 * @return The main window.
	 * @see FenetrePrincipale
	 */
	public FenetrePrincipale getWin () {
		return win;
	}
}
