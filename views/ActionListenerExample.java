package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;

public class ActionListenerExample implements ActionListener {

	String viewType;
	Controller controller;
	
	public ActionListenerExample(String viewType , Controller controller) {
		this.viewType = viewType;
		this.controller = controller;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Listened successfully");
		controller.drawPanel(viewType);
		
		
	}

}
