package fr.hugosimony.demineur;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Menu extends JFrame {
	private static final long serialVersionUID = 1L;
	Main main;
  
  public Menu() {
    this.main = new Main();
    this.menu = this;
    this.difficulty_set = 3;
    this.rapidity_set = 300;

    
    setTitle("Démineur [Menu]");
    setSize(500, 500);
    setResizable(false);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addWindowListener(new WindowAdapter() {
    	@Override
    	public void windowClosed(WindowEvent e) {
    		System.exit(0);
    	}
	});
    
    Font font_question = new Font("Arial", 1, 20);
    Font font_difficulty = new Font("Arial", 1, 20);
    Font font_play = new Font("Arial", 1, 50);
    Font font_ia = new Font("Arial", 1, 40);
    
    JPanel panel = new JPanel();
    add(panel);
    panel.setLayout(new GridLayout(5, 1));
    
    
    JLabel question = new JLabel("Choisissez la difficulté (et la rapidité de l'IA)");
    question.setFont(font_question);
    question.setHorizontalAlignment(0);
    panel.add(question);

    
    final JSlider difficulty = new JSlider(1, 5);
    difficulty.setFont(font_difficulty);
    difficulty.setMinorTickSpacing(1);
    difficulty.setMajorTickSpacing(1);
    difficulty.setPaintTicks(true);
    difficulty.setPaintLabels(true);
    difficulty.setValue(3);
    difficulty.setToolTipText("Choisissez la difficulté du jeu.");
    
    panel.add(difficulty);
    
    final JSlider rapidity = new JSlider(1, 599);
    rapidity.setFont(font_difficulty);
    rapidity.setMinorTickSpacing(1);
    rapidity.setMajorTickSpacing(1);
    rapidity.setValue(300);
    rapidity.setToolTipText("Choisissez la rapidité de l'IA.");
    
    panel.add(rapidity);

    
    JButton play = new JButton("Jouer");
    play.setFont(font_play);
    panel.add(play);
    play.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Menu.this.difficulty_set = difficulty.getValue();
            Menu.this.rapidity_set = rapidity.getValue();
            Game game = new Game(difficulty_set, rapidity_set, false);
            game.setVisible(true);
            Menu.this.menu.setVisible(false);
          }
        });
    
    JButton ia = new JButton("Laisser jouer l'IA");
    ia.setFont(font_ia);
    panel.add(ia);
    ia.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Menu.this.difficulty_set = difficulty.getValue();
            Menu.this.rapidity_set = rapidity.getValue();
            Game game = new Game(difficulty_set, rapidity_set, true);
            game.setVisible(true);
            Menu.this.menu.setVisible(false);
          }
        });
  }
  
  JFrame menu;
  int difficulty_set;
  int rapidity_set;
}

