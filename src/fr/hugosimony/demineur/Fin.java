package fr.hugosimony.demineur;

import java.awt.Color;
import java.awt.Component;
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

public class Fin extends JFrame {
	
	private static final long serialVersionUID = 1L;
  
	private static Color fond_rouge = new Color(255, 150, 150);
	private static Color fond_vert = new Color(150, 255, 150);
	private static Color rouge = new Color(255, 0, 0);
	private static Color vert = new Color(0, 255, 0);
  
  	Font font = new Font("Arial", 1, 20);
  
  	Fin fin = this;
  
  	public Fin(boolean win, boolean ia, Game game) {
    setTitle("Démineur [Fin]");
    setSize(400, 150);
    setResizable(false);
    setLocationRelativeTo((Component)null);
    setAlwaysOnTop(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
    	public void windowClosed(WindowEvent e) {
    		System.exit(0);
        }
    });
    JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayout(3, 1));
    JLabel message_score = new JLabel();
    if (win) {
    	if(ia)
    		message_score.setText("L'IA a gagné ! Yeay !");
    	else
    		message_score.setText("Vous avez gagné !");
    }else {
    	if(ia)
    		message_score.setText("L'IA a perdu ! Putain de random :o");
    	else
    		message_score.setText("Vous avez perdu !");
    }
    message_score.setFont(font);
    message_score.setHorizontalAlignment(0);
    panel1.add(message_score);
    JLabel question = new JLabel();
    question.setText("Voulez vous rejouer ?");
    question.setFont(font);
    question.setHorizontalAlignment(0);
    panel1.add(question);
    JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayout(1, 2));
    JButton oui = new JButton("Oui");
    JButton non = new JButton("Non");
    oui.setFont(font);
    non.setFont(font);
    oui.setBackground(vert);
    non.setBackground(rouge);
    oui.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            fin.setVisible(false);
            game.setVisible(false);
            Menu menu = new Menu();
            menu.setVisible(true);
          }
        });
    non.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            fin.dispose();
            game.dispose();
          }
        });
    panel2.add(oui);
    panel2.add(non);
    panel1.add(panel2);
    if (win) {
    	panel2.setBackground(fond_vert);
    	panel1.setBackground(fond_vert);
    }
    else {
    	panel2.setBackground(fond_rouge);
    	panel1.setBackground(fond_rouge);
    }
    add(panel1);
  }
}
