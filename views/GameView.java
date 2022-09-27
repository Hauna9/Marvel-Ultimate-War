package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;

public class GameView extends JFrame {
	private JPanel panel;

	public Controller controller;
	
public GameView(Controller controller) {
	this.controller = controller;
	this.setBounds(250,250,1400,800);
	this.setVisible(true);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	panel = (JPanel)new StartView(controller);
	//panel.setPreferredSize(new Dimension(1000,100));
	//panel.setLayout(new GridLayout(0,3));
	panel.setBorder(new EmptyBorder(10,10,10,10));
	
	this.add(panel);
	
	this.revalidate();
	this.repaint();
	
}

public static void main (String[]args) {
	new GameView(new Controller());
}

public JPanel getPanel() {
	return panel;
}

public void setPanel(JPanel panel) {
	this.panel = panel;
	this.panel.revalidate();
	this.panel.repaint();
}

public Controller getController() {
	return controller;
}

public void setController(Controller controller) {
	this.controller = controller;
}


}
