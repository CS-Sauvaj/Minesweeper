package fr.hugosimony.demineur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game extends JFrame{
	private static final long serialVersionUID = 1L;

	public boolean ia_on;
	
	public Bouton[][] tableau_bouton; 
	public int score;
	JLabel label_info = new JLabel();
	
	public int tab_xl = 30;		// Arbitraire
	public int tab_yl = 30;		// Arbitraire
	
	public int nb_bombes;
	
	public int x_clic;
	public int y_clic;
	
	public boolean first_click;
	public boolean flag = false;
	public boolean end = false;
	
	public boolean ia_pause = false;
	
	public Font f = new Font("Arial", 1, 10);
	
	public Color vert = new Color(0,255,0);       // 1
	public Color bleu = new Color(0,0,255);       // 2
	public Color rouge = new Color(255,0,0);      // 3
	public Color violet = new Color(150,60,160);  // 4
	public Color marron = new Color(100,70,70);   // 5
	public Color cyan = new Color(0,150,255);     // 6
	public Color jaune = new Color(220,220,0);    // 7
	public Color rose = new Color(255,180,180);   // 8
	public Color noir = new Color(0,0,0);
	public Color cflag = new Color(255,150,0);
	public Color fond1 = new Color(190,190,190);
	public Color fond2 = new Color(140,140,140);
	
	public final URL case_fond1_url = Game.class.getResource("images/DémineurFond1.PNG");
	public final ImageIcon case_fond1 = new ImageIcon(case_fond1_url);
	public final URL case_flag_url = Game.class.getResource("images/DémineurFlag.PNG");
	public final ImageIcon case_flag = new ImageIcon(case_flag_url);
	public final URL case_bomb1_url = Game.class.getResource("images/DémineurBomb1.PNG");
	public final ImageIcon case_bomb1 = new ImageIcon(case_bomb1_url);
	public final URL case_bomb2_url = Game.class.getResource("images/DémineurBomb2.PNG");
	public final ImageIcon case_bomb2 = new ImageIcon(case_bomb2_url);
	public final URL case_bomb3_url = Game.class.getResource("images/DémineurBomb3.png");
	public final ImageIcon case_bomb3 = new ImageIcon(case_bomb3_url);
	public final URL case1_url = Game.class.getResource("images/Démineur1.PNG");
	public final ImageIcon case1 = new ImageIcon(case1_url);
	public final URL case2_url = Game.class.getResource("images/Démineur2.PNG");
	public final ImageIcon case2 = new ImageIcon(case2_url);
	public final URL case3_url = Game.class.getResource("images/Démineur3.PNG");
	public final ImageIcon case3 = new ImageIcon(case3_url);
	public final URL case4_url = Game.class.getResource("images/Démineur4.PNG");
	public final ImageIcon case4 = new ImageIcon(case4_url);
	public final URL case5_url = Game.class.getResource("images/Démineur5.PNG");
	public final ImageIcon case5 = new ImageIcon(case5_url);
	public final URL case6_url = Game.class.getResource("images/Démineur6.PNG");
	public final ImageIcon case6 = new ImageIcon(case6_url);
	public final URL case7_url = Game.class.getResource("images/Démineur7.png");
	public final ImageIcon case7 = new ImageIcon(case7_url);
	public final URL case8_url = Game.class.getResource("images/Démineur8.png");
	public final ImageIcon case8 = new ImageIcon(case8_url);
	
	public int[][] tab = new int[tab_xl][tab_yl];
	public int[][] tab_ia = new int[tab_xl][tab_yl];
	
	public Game(int difficulty, int rapidity, boolean ia_on) {
		 
		this.ia_on = ia_on;
		
	    Font font = new Font("Arial", 1, 25);
	    JPanel panel_game = new JPanel();

	    setTitle("Démineur [Difficulté " + difficulty + "]     -----    Made by Hugo Simony-Jungo");
	    setSize(tab_xl*30-100, tab_xl*30);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    addWindowListener(new WindowAdapter()
	        {
	          public void windowClosed(WindowEvent e) {
	            System.exit(0);
	          }
	        });
	    score = 0;
	    nb_bombes = difficulty * 25 + ((tab_xl + tab_yl)/2);
	    
	    JPanel panel_all = new JPanel();
	    panel_all.setLayout(new BorderLayout());
	    
	    label_info.setText("Score : ?");
	    label_info.setFont(font);
	    label_info.setHorizontalAlignment(0);
	    label_info.setVerticalAlignment(0);
	    
	    panel_all.add("North", label_info);
	    
	    panel_game.setLayout(new GridLayout(tab_xl, tab_yl));
	    
	    tableau_bouton = new Bouton[tab_xl][tab_yl];
	    
	    for (int i = 0; i < tab_xl; i++) {
	    	for (int j = 0; j < tab_yl; j++) {
		        Bouton bouton = new Bouton(i, j, this);
		        panel_game.add(bouton);
		        tableau_bouton[i][j] = bouton;
	    	} 
	    }
	    first_click = false;
	    
	    panel_all.add("Center", panel_game);
	    
	    
	    if(ia_on) {
	    	
	    	JPanel panel_ia = new JPanel();
		    panel_ia.setLayout(new GridLayout(1,2));
		    
		    JButton pause = new JButton("Mettre en pause");
		    pause.setFont(font);
		    pause.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!end) {
						if(pause.getText().equals("Mettre en pause") ) {
							pause.setText("Reprendre");
							ia_pause = true;
						}
						else if(pause.getText().equals("Reprendre")) {
							pause.setText("Mettre en pause");
							ia_pause = false;
						}
					}
				}
			});
		    
		    JButton quit = new JButton("Revenir au menu");
		    quit.setFont(font);
		    quit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!end) {
						end = true;
						setVisible(false);
			            Menu menu = new Menu();
			            menu.setVisible(true);
					}
				}
			});
		    
		    panel_ia.add(quit);
		    panel_ia.add(pause);
		    panel_all.add("South", panel_ia);
	    	add(panel_all);
	    	
	    	
	    	Timer timer;
			timer = new Timer();
			timer.schedule(new IA(this), 500, 600 - rapidity);
			
	    }else {
	    	JButton bflag = new JButton("Flag on");
		    bflag.setFont(font);
		    bflag.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(flag) {
						flag = false;
						bflag.setText("Flag on");
					}else {
						flag = true;
						bflag.setText("Flag off");
					}
				}
		    });
		    
		    panel_all.add("South", bflag);
		    add(panel_all);
	    }
	    
	}
	
	//*****************************************************************

	public void randomBombs() {
		int count = 0;
	    while(count < nb_bombes) {
	    	int x_random = (int) (Math.random() * tab_xl);
	    	int y_random = (int) (Math.random() * tab_yl);
		    if(tab[x_random][y_random] != 9) {
		    	boolean ok = true;
		    	for(int i = -1; i < 2; i ++) {
					for(int j = -1; j < 2; j++) {
						if(x_clic+i >= 0 && x_clic+i < tab_xl && y_clic+j >= 0 && y_clic+j < tab_yl) {
							if(x_clic+i == x_random && y_clic+j == y_random)
								ok = false;
						}
					}
		    	}
		    	if(ok) {
			    	tab[x_random][y_random] = 9;
		    		count++;
		    	}
		    }
	    }
	}
	
	public void placeNumbers() {
		for (int i = 0; i < tab_xl; i++) {
			for (int j = 0; j < tab_yl; j++) {
				if(tab[i][j] != 9)
					tab[i][j] = countNear(i, j);
			}
		}
	}
	
	private int countNear(int x, int y) {
		int count = 0;
		for(int i = -1; i < 2; i ++) {
			for(int j = -1; j < 2; j++) {
				if(x+i >= 0 && x+i < tab_xl && y+j >= 0 && y+j < tab_yl) {
					if(tab[x+i][y+j] == 9)
						count++;
				}
			}
		}
		return count;
	}
	
	public void printBombs() {
		for (int i = 0; i < tab_xl; i++) {
			for (int j = 0; j < tab_yl; j++) {
				if(tab[i][j] == 9) {
					tableau_bouton[i][j].clicked = true;
					if(i==x_clic && j == y_clic)
						tableau_bouton[i][j].setIcon(case_bomb2);
					else if(tableau_bouton[i][j].getIcon() == case_flag) {
						tableau_bouton[i][j].setIcon(case_bomb3);
						score++;
					}
					else
						tableau_bouton[i][j].setIcon(case_bomb1);
				}
			}
		}
		label_info.setText("Score : " + score);
	}
	
	public boolean verifWin() {
		int count = 0;
		for (int i = 0; i < tab_xl; i++) {
			for (int j = 0; j < tab_yl; j++) {
				if(tableau_bouton[i][j].getIcon() == case_flag && tab[i][j] != 9)
					return false;
				else {
					if(tableau_bouton[i][j].getIcon() == case_flag && tab[i][j] == 9) {
						count++;
					}
				}
			}
		}
		return count == nb_bombes;
	}
	
	//******************************************************************************
	//*** Debug here ***************************************************************
	//******************************************************************************
	
	public void printTab(int[][] tab) {
		
		System.out.print("------------------------------------------------------------");
		for(int i = 0; i < tab_xl; i++) {
			System.out.println("");
			for(int j = 0; j < tab_yl; j++) {
				System.out.print(tab[i][j] + " ");
			}
		}
		System.out.println("");
	}
}
