package fr.hugosimony.demineur;

import java.util.ArrayList;
import java.util.TimerTask;

public class IA extends TimerTask {
	
	Game game;
	Bouton bouton;
	int i = 1;
	int x;
	int y;
	int loop;
	public static int[][] tab_memory;
	boolean end_turn;
	public IA(Game game) {
		this.game = game;
		bouton = game.tableau_bouton[0][0];
		for (int i = 0; i < game.tab_xl; i++) {
			for (int j = 0; j < game.tab_yl; j++) 
					game.tab_ia[i][j] = 0;
		}
		tab_memory = new int[game.tab_xl][game.tab_yl];
		for (int i = 0; i < game.tab_xl; i++) {
			for (int j = 0; j < game.tab_yl; j++) 
					tab_memory[i][j] = 0;
		}
	}

	@Override
	public void run() {
		
		if(!game.ia_pause && !game.end) {
			
			//************************************************************************************************************************
			// Random
			
			if(loop > 2) {
				ArrayList<Integer> clicable = new ArrayList<Integer>();
				for (int i = 0; i < game.tab_xl; i++) {
					for (int j = 0; j < game.tab_yl; j++) {
						if(!game.tableau_bouton[i][j].clicked && game.tableau_bouton[i][j].getIcon() != game.case_flag) {
							clicable.add(i);
							clicable.add(j);
						}
					}
				}
				int random = (int) (Math.random() * (clicable.size()/2));
			    bouton.simuleClick(clicable.get(random*2), clicable.get(random*2+1));
				loop = 0;
			}else {
				
				
			//************************************************************************************************************************
			// First Click
			
				if(i == 1) {
					bouton.simuleClick(game.tab_xl/2, game.tab_yl/2);
					loop = 0;
					i++;
					
			//************************************************************************************************************************
			// Flag
					
				}else if(i == 2) {
					
					end_turn = false;
					
					x = 0;
					while(!end_turn && x < game.tab_xl) {
						y = 0;
						while(!end_turn && y < game.tab_yl) {
							if(game.tableau_bouton[x][y].clicked && game.tab[x][y] != 0 && game.tab[x][y] == 8 - bouton.countClickedNear(x, y) 
								&& game.tab_ia[x][y] != game.tab[x][y]) {
								
								game.flag = true;
								bouton.flagAround(x,y);
								game.flag = false;
								end_turn = true;
								loop = 0;
							}
							y++;
						}
						x++;
					}
					
					if(!end_turn) {
						i++;
						loop++;
					}
					
				}
				
			//************************************************************************************************************************
			// Safe click 
				
				else if(i == 3) {
					
					end_turn = false;
					
					x = 0;
					while(!end_turn && x < game.tab_xl) {
						y = 0;
						while(!end_turn && y < game.tab_yl) {
							if(game.tableau_bouton[x][y].clicked && game.tab[x][y] != 0 && game.tab[x][y] == bouton.countFlag(x, y) && tab_memory[x][y] == 0 	
								&& bouton.countClicableNear(x, y) != 0) {
								
								tab_memory[x][y] = 1;
								boolean min_one = false;
								for(int a = -1; a <= 1; a++) {
									for(int b = -1; b <= 1; b++) {
										if(!(a+x < 0 || b+y < 0 || a+x >= game.tab_xl || b+y >= game.tab_yl) && tab_memory[x+a][y+b] == 0) {

											bouton.simuleClick(x+a, y+b);	
											if(bouton.countClicableNear(x+a, y+b) == 0)
												tab_memory[x+a][y+b] = 1;
											else
												min_one = true;
										}
									}
								}
								if(min_one)
									end_turn = true;
								loop = 0;
							}
							y++;
						}
						x++;
					}
					
					if(!end_turn) {
						loop++;
						i--;
					}
				}
			}
		}
	}
	
}
