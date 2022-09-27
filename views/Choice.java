package views;


import controller.Controller;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Choice extends JPanel {
	private JPanel Champions;
	private JTextArea info;
	private JTextArea selection;
	private Controller controller;
	
public Choice(Controller controller) {

	this.controller=controller;
	this.setLayout(new BorderLayout());
	Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
	this.setSize(d);
	//this.setBounds(250,250,1000,1000);
	this.setVisible(true);
	//this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	info = new JTextArea("  Info of each Champion:");
	selection=new JTextArea("  Champions selected:"+"\n"+"Player 1:"+ controller.getP1().getName());
	info.setPreferredSize(new Dimension(this.getWidth()/3,this.getHeight()));
	info.setEditable(false);
	JScrollPane scroll=new JScrollPane(info);
	info.setAutoscrolls(true);
	this.add(scroll);
	Champions = new JPanel();
	Champions.setPreferredSize(new Dimension(this.getWidth()/3,this.getHeight()));
	Champions.setLayout(new GridLayout(0,3));

	
	this.add(scroll,BorderLayout.WEST);
	selection.setPreferredSize(new Dimension(this.getWidth()/3,this.getHeight()));
	selection.setEditable(false);
	
	this.add(Champions,BorderLayout.CENTER);

	
	this.add(selection,BorderLayout.EAST);
	this.revalidate();
	this.repaint();
	
}
public JPanel getChampions() {
	return Champions;
}

public void setChampions(JPanel champions) {
	Champions = champions;
}

public String getInfo() {
	return info.getText();
}

public void setInfo(String info) {
	this.info.setText(info);
}
public String getSelection() {
	return selection.getText();
}
public void setSelection(String info) {
	this.selection.setText(info);
}



}
