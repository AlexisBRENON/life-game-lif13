/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package life_game_lif13;

import java.awt.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.swing.*;

/**
 *
 * @author alexis
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
	/*
	 * Fin des composants
	 */
    

	public FenetrePrincipale (Modele m) {
		_nbCol = m.getGrille().getX();
		_nbLigne = m.getGrille().getY();
		_cellules = new JPanel[_nbCol][_nbLigne];
		this.patternList = new HashMap<String, Motif>();
		initPatterns();
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
		JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 0));
		_compteur = new JLabel("Nombre d'itérations : 0");
		_clearButton = new JButton("Effacer");
		Set<String> shapeList = patternList.keySet();
		_shapesBox = new JComboBox(shapeList.toArray());
		_shapesBox.setSelectedItem("Point");
		optionPanel.add(_compteur);
		optionPanel.add(_clearButton);
		optionPanel.add(_shapesBox);
		panelPrincipal.add(optionPanel, BorderLayout.SOUTH);
	}

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

		pattern = new Motif(2,2);
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

		pattern = new Motif(9,9);
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

		pattern = new Motif(4,5);
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

		pattern = new Motif(3,4);
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
		pattern.addPoint(2,0);
		pattern.addPoint(2,1);
		pattern.addPoint(2,2);
		pattern.addPoint(1,2);
		pattern.addPoint(0,1);
		patternList.put("Planneur", pattern);

		pattern = new Motif((int)(Math.random()*_nbCol/2),
							(int)(Math.random()*_nbLigne/2));
		pattern.InitMotif();
		patternList.put("Aléatoire", pattern);
	}

	@Override
	public void run () {
		this.setVisible(true);
	}

	@Override
	public void update (Observable o,
						Object arg) {
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
			_compteur.setText("Nombre d'itérations : "+Integer.toString(m.getNbIter()));
		} else {
			System.out.println("Problème de mise à jour.");
		}
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

    public JMenuItem getItemOuvrirMotif() {
        return _itemOuvrirMotif;
    }

    public void setItemOuvrirMotif(JMenuItem _itemOuvrirMotif) {
        this._itemOuvrirMotif = _itemOuvrirMotif;
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

	public HashMap<String, Motif> getPatternList () {
		return patternList;
	}

    public void setNbCol(int _nbCol) {
        this._nbCol = _nbCol;
    }

    public void setNbLigne(int _nbLigne) {
        this._nbLigne = _nbLigne;
    }

        
        
        public void setPanelGrille(int lign, int col) {
            panelPrincipal.remove(_panelGrille);
            this._nbLigne=lign;
            this._nbCol=col;
               System.out.println(_nbLigne+" "+_nbCol);
           
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
         
        }
        
        
        


}
