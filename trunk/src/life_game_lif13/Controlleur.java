/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;

/**
 *
 * @author alexis
 */
public class Controlleur {

	private FenetrePrincipale win;
	private Modele m;

	public Controlleur () {
		m = new Modele(10,
					   10);
		win = new FenetrePrincipale(m);
		this.connectSignals();
	}

	public Controlleur (int width, int height) {
		m = new Modele(width,
					   height);
		win = new FenetrePrincipale(m);
		this.connectSignals();
	}

	public Controlleur (int nbThread) {
		m = new Modele(10, 10, nbThread);
		win = new FenetrePrincipale(m);
		this.connectSignals();
	}

	public Controlleur (int width, int height, int nbThread) {
		m = new Modele(width, height, nbThread);
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
						onMouseEnteredOnCell(e);
					}

					@Override
					public void mouseExited (MouseEvent e) {
						onMouseExitedCell(e);
					}
				});
			}
		}

		/* Connection des signaux */
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
		Object o;
		o = e.getSource();
		if (o instanceof JButton) {
			((JButton) o).setEnabled(false);
		}
		m.lancerThread();
	}

	private void onPauseAction (ActionEvent e) {
		m.switchPause();
		Object o;
		o = e.getSource();
		if (o instanceof JToggleButton) {
			((JToggleButton) o).setSelected(((JToggleButton) o).isSelected());
		}
	}

	private void onInitAction () {
		m.clear();
		m.setPaused(true);
		m.getGrille().initGrille();
		win.update(m, null);
		m.switchPause();
	}

	public void onMouseEnteredOnCell (MouseEvent e) {
		win.setSelected(e.getComponent(), true);
	}

	public void onMouseClickedOnCell (MouseEvent e, int x, int y) {
		if (m.getGrille().estVivante(x, y)) {
			m.getGrille().removeCellule(new Coordonnee(x, y));
		} else {
			m.getGrille().addCellule(new Coordonnee(x, y));
		}
		win.update(m, null);
	}

	public void onMouseExitedCell (MouseEvent e) {
		win.setSelected(e.getComponent(), false);
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
				Logger.getLogger(FenetrePrincipale.class.getName()).
						log(Level.SEVERE,
							null,
							ex);
			}
		}
	}

	public void onClearAction () {
		m.setPaused(true);
		m.getGrille().clearGrille();
		win.update(m, null);
		m.setPaused(true);
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

	public FenetrePrincipale getWin () {
		return win;
	}


}
