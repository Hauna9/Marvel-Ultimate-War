package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;

import javax.swing.*;

public class CaptureTextListener implements ActionListener{
	Controller controller;
	String name1;
	String name2;
	
	public CaptureTextListener(Controller controller, String name1 , String name2) {
	this.controller = controller;
	this.name1 = name1;
	this.name2 = name2;

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if((name1!="")||(name2!=""))
		{
			controller.p1.setName(name1);
			controller.p2.setName(name2);
		}
		else{
			JOptionPane.showMessageDialog(null,"please enter names","INVALID NAMES",JOptionPane.ERROR_MESSAGE);
			System.out.println("sa7aba");
		}
		
	}

}
