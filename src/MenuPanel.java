// package Game;

/*
 The code below displays the menu, in which the class MyFrame performs 2 choices: A* AI and Single AI
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
	
	int width;
	int height;
	JFrame frame;
	
	public MenuPanel(int width, int height, JFrame frame) {
		this.width = width;
		this.height = height;
		this.frame = frame;
		
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.BLUE);
		setLayout(null);

		JButton sAI = new JButton("Single AI");
		sAI.setLayout(null);
		sAI.setBounds((width / 2) - 100, (height / 4) + 250 - 30, 200, 60);
		sAI.setBackground(Color.GREEN);
		sAI.setBorder(BorderFactory.createBevelBorder(0));
		sAI.addActionListener(new SingleAIListener(frame));
		add(sAI);
		
		JButton Astar = new JButton("A* AI");
		Astar.setLayout(null);
		Astar.setBounds((width / 2) - 100, (height / 4) + 325 - 30, 200, 60);
		Astar.setBackground(Color.GREEN);
		Astar.setBorder(BorderFactory.createBevelBorder(0));
		Astar.addActionListener(new AStarListener(frame));
		add(Astar);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD,30 ));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("-Project of Group 6-", (width - metrics.stringWidth("Project of Group 6"))/2, height / 8);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("SNAKE AI", (width - metrics1.stringWidth("SNAKE AI"))/2, height / 4);
		
	}
}

