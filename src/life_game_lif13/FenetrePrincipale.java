/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import com.sun.tools.internal.ws.processor.model.Model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
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
        private Boolean launch;

	/* Composants de la fenêtre */
	private JMenuItem _itemEnregistrer;
	private JMenuItem _itemOuvrir;
	private JMenuItem _itemQuitter;
	private JMenuItem _itemAPropos;
	private JPanel _panelGrille;
	private JButton _boutonLancer;
	private JToggleButton _boutonPause;
	private JButton _boutonInit;
	private JPanel[][] _cellules;
	/* Fin des composants */

	public FenetrePrincipale (Modele m) {
		_m = m;
                launch=false;
		_nbCol = m.getGrille().getX();
		_nbLigne = m.getGrille().getY();
		_cellules = new JPanel[_nbLigne][_nbCol];
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
		for (int i = 0; i < _nbLigne; i++) {
			for (int j = 0; j < _nbCol; j++) {
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

		JPanel panelBoutons = new JPanel(new GridLayout(3, 0, 5, 5));
		_boutonLancer = new JButton("Lancer !");
		_boutonPause = new JToggleButton("Pause");
		_boutonInit = new JButton("Initialiser");
		panelBoutons.add(_boutonInit);
		panelBoutons.add(_boutonLancer);
		panelBoutons.add(_boutonPause);
		panelPrincipal.add(panelBoutons, BorderLayout.EAST);

		/* Connection des signaux */
		_m.addObserver(this);
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
		_boutonLancer.addActionListener(
				new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				onLaunchAction();
			}
		});

		_boutonPause.addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onPauseAction();
			}
		});

		_boutonInit.addActionListener(
				new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent e) {
				onInitAction();
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
            if(launch==true){
		for (int i = 0; i < _nbLigne; i++) {
			for (int j = 0; j < _nbCol; j++) {
				if (_m.estVivante(j,i)) {
					_cellules[i][j].setBackground(Color.red);
				} else {
					_cellules[i][j].setBackground(Color.white);
				}
			}
		}
            }
            else{
                for (int i = 0; i < _nbLigne; i++) {
			for (int j = 0; j < _nbCol; j++)
                            _cellules[i][j].setBackground(Color.white);
			}
                }
        }

	private void onQuitAction () {
		System.exit(0);
	}

	private void onLaunchAction() {
                launch=true;
                update(_m, null);
		_m.lancerThread();
		_boutonLancer.setEnabled(false);
	}

	private void onPauseAction() {
		_m.switchPause();
		_boutonPause.setSelected((_boutonPause.isSelected()));
	}

	private void onInitAction() {
                launch=true;
		_m.clear();
		_m.switchPause();
		_m.getGrille().initGrille();
		_m.switchPause();
		update(_m, null);
	}

	public void onMouseEnteredOnCell(MouseEvent e) {
		if (e.getComponent() instanceof JPanel) {
			((JPanel)e.getComponent()).setBackground(Color.gray);
		}
	}
        
        public void onMouseClickedOnCell(MouseEvent e,int x,int y){
           
                if(_m.grille.estVivante(x, y)){
                    _m.grille.removeCellule(new Coordonnee(y, x));
                }
                else{
                    _m.grille.addCellule(new Coordonnee(y,x));
            }
            
        }

	public void onMouseExitedCell (MouseEvent e) {
            update(_m, null);     
	}
}
