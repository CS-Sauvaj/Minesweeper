package fr.hugosimony.demineur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Bouton extends JButton{
	private static final long serialVersionUID = 1L;
	Game game;
	int[][] tab_memory;
	public boolean clicked = false;
	public Bouton(int i, int j, Game game) {
		this.game = game;
		setIcon(game.case_fond1);
		Bouton ce_bouton = this;
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!game.ia_on) {
					if(!clicked) {
						for(int i = 0; i < game.tab_xl; i++) {
							for(int j = 0; j < game.tab_yl; j++) {
								if(ce_bouton == game.tableau_bouton[i][j]) {
									game.x_clic = i;
									game.y_clic = j;
								}
							}
						}
						simuleClick(game.x_clic, game.y_clic);
					}
				}
			}
		});
	}
	
	public void simuleClick(int i, int j) {

		if(game.ia_on) {
			game.x_clic = i;
			game.y_clic = j;
		}
		Bouton bouton = game.tableau_bouton[i][j];
		if(!game.first_click && !game.flag) {
			game.randomBombs();
		    game.placeNumbers();
		    bouton.setBackground(game.fond2);
		    bouton.setIcon(null);
			game.first_click = true;
			
		    //game.printTab(game.tab);	// Debug
			
		}
		
		if(game.flag && !game.tableau_bouton[i][j].clicked && !game.end) {
			if(game.tableau_bouton[i][j].getIcon() == game.case_flag)
				game.tableau_bouton[i][j].setIcon(game.case_fond1);
			else {
				game.tableau_bouton[i][j].setIcon(game.case_flag);
				game.tab_ia[i][j] = 9;
				countAroundFlag(i, j);
				if(game.verifWin()) {
					game.end = true;
					game.label_info.setText("Score : " + game.nb_bombes);
					game.flag = false;
					for (int x = 0; x < game.tab_xl; x++) {
						for (int y = 0; y < game.tab_yl; y++) {
							simuleClick(x, y);
						}
					}
					Fin fin = new Fin(true, game.ia_on, game);
					fin.setVisible(true);
				}
			}
		}else {
			if(game.tableau_bouton[i][j].getIcon() != game.case_flag) {
				int bc = game.tab[i][j];
				if(bc == 9) {
					if(!game.end) {
						game.end = true;
						game.printBombs();
						Fin fin = new Fin(false, game.ia_on, game);
						fin.setVisible(true);
					}
				}else {
					game.tableau_bouton[i][j].clicked = true;
					if(bc == 0) {
						resetTab();
						game.tableau_bouton[i][j].setBackground(game.fond2);
						game.tableau_bouton[i][j].setIcon(null);
						checkNear(i, j);
					}
					else
						setImage(i, j);
				}
			}
		}
	}

	private void resetTab() {
		tab_memory = new int[game.tab_xl][game.tab_yl];
		for (int i = 0; i < game.tab_xl; i++) {
			for (int j = 0; j < game.tab_yl; j++) 
					tab_memory[i][j] = 0;
		}
	}
	
	private void checkNear(int x, int y) {
		int i = x;
		int j = y;
		for(int a = -1; a <= 1; a++) {
			for(int b = -1; b <= 1; b++) {
				if(!(a==0 && b==0) && !(a+i < 0 || b+j < 0 || a+i >= game.tab_xl || b+j >= game.tab_yl)){
					if(tab_memory[i+a][j+b] == 0) {
						if(game.tab[i+a][j+b] != 9) {
							game.tableau_bouton[i+a][j+b].clicked = true;
							if(game.tab[i+a][j+b] == 0) {
								tab_memory[i+a][j+b] = 1;
								if(game.ia_on && countClicableNear(i+a,j+b) == 0)
									IA.tab_memory[i+a][j+b] = 1;
								setImage(i+a, j+b);
								checkNear(i+a, j+b);
							}
							else
								setImage(i+a, j+b);
						}
					}
				}
			}
		}
	}
	
	public int countClickedNear(int x, int y) {
		int count = 0;
		for(int a = -1; a <= 1; a++) {
			for(int b = -1; b <= 1; b++) {
				if(!(a==0 && b==0)) {
					if(a+x < 0 || b+y < 0 || a+x >= game.tab_xl || b+y >= game.tab_yl)
						count++;
					else if (game.tableau_bouton[a+x][b+y].clicked)
						count++;
				}
			}
		}
		return count;
	}
	
	public int countClicableNear(int x, int y) {
		int count = 0;
		for(int a = -1; a <= 1; a++) {
			for(int b = -1; b <= 1; b++) {
				if(!(a==0 && b==0) && !(a+x < 0 || b+y < 0 || a+x >= game.tab_xl || b+y >= game.tab_yl) 
						&& !game.tableau_bouton[a+x][b+y].clicked && game.tableau_bouton[a+x][b+y].getIcon() != game.case_flag)
					count++;
			}
		}
		return count;
	}
	
	public void flagAround(int x, int y) {
		for(int a = -1; a <= 1; a++) {
			for(int b = -1; b <= 1; b++) {
				if(!(a==0 && b==0) && !(a+x < 0 || b+y < 0 || a+x >= game.tab_xl || b+y >= game.tab_yl) 
						&& game.tab_ia[a+x][b+y] != 9)
					simuleClick(a+x, b+y);
			}
		}
	}
	
	private void countAroundFlag(int x, int y) {
		for(int a = -1; a <= 1; a++) {
			for(int b = -1; b <= 1; b++) {
				if(!(a==0 && b==0) && !(a+x < 0 || b+y < 0 || a+x >= game.tab_xl || b+y >= game.tab_yl)) {
					if(game.tab_ia[a+x][b+y] != 9 && game.tab_ia[a+x][b+y] != game.tab[a+x][b+y])
						game.tab_ia[a+x][b+y]++;
				}
			}
		}
	}
	
	public int countFlag(int x, int y) {
		int count = 0;
		for(int a = -1; a <= 1; a++) {
			for(int b = -1; b <= 1; b++) {
				if(!(a==0 && b==0) && !(a+x < 0 || b+y < 0 || a+x >= game.tab_xl || b+y >= game.tab_yl) && game.tableau_bouton[a+x][b+y].getIcon() == game.case_flag)
					count++;
			}
		}
		return count;
	}
	
	private void setImage(int x, int y) {
		int i = game.tab[x][y];
		if(i==0) {
			game.tableau_bouton[x][y].setBackground(game.fond2);
			game.tableau_bouton[x][y].setIcon(null);
		}
		else if(i==1)
			game.tableau_bouton[x][y].setIcon(game.case1);
		else if(i==2)
			game.tableau_bouton[x][y].setIcon(game.case2);
		else if(i==3)
			game.tableau_bouton[x][y].setIcon(game.case3);
		else if(i==4)
			game.tableau_bouton[x][y].setIcon(game.case4);
		else if(i==5)
			game.tableau_bouton[x][y].setIcon(game.case5);
		else if(i==6)
			game.tableau_bouton[x][y].setIcon(game.case6);
		else if(i==7)
			game.tableau_bouton[x][y].setIcon(game.case7);
		else if(i==8)
			game.tableau_bouton[x][y].setIcon(game.case8);
	}
}
