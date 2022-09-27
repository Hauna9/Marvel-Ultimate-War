package views;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.Controller;

import javax.swing.JPanel;
import javax.swing.*;
//import controllers.Controller;
//import views.MyInputVerifier;
//import views.button.StyledButton;
//import views.panel.ImagePanel;


public class StartView extends JPanel {
	private JLabel label1;
    private JLabel label2;
    private JTextField name1;
    private JTextField name2;
    private JButton start;
    //JPanel panel = new JPanel();
    public StartView(Controller controller) {
    	//setSize(1000,1000);
    	 this.setLayout(new GridLayout(0,1));
    	 setComponent();
         addComponents();
         //sizeComponents();

         //this.setSize(new Dimension(25, 50));
        

         //setTitle("MARVEL:Ultimate War");
         ActionListenerExample exampleListener = new ActionListenerExample("GameView", controller);

         start.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent ae) {

                 if((!name1.getText().equals(""))&&(!name2.getText().equals(""))) {
                     controller.p1.setName(name1.getText());
                     controller.p2.setName(name2.getText());

                     controller.drawPanel("GameView");
                 }
                 else{
                     JOptionPane.showMessageDialog(null,"please enter names","INVALID NAMES",JOptionPane.ERROR_MESSAGE);

                 }
        	 }
         });
        // start.addActionListener(exampleListener);

         //setDefaultCloseOperation(EXIT_ON_CLOSE);
         setVisible(true);
         //setResizable(false);
         //setLocationRelativeTo(null);

    }
    public static void main(String []args) {
    	StartView view = new StartView(null);
    }
    private void addComponents() {
   
       this.add(label1);
       this.add(name1);
       this.add(label2);
       this.add(name2);
       this.add(start);
      // getContentPane().add(panel, BorderLayout.CENTER);
      
      
        //getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void sizeComponents() {
        label1.setBounds(50, 50, 400, 20);
        name1.setBounds(50, 100, 400, 20);
        label2.setBounds(50, 225, 400, 20);
        name2.setBounds(50, 275, 400, 20);
        //name2.setSelectedIndex(-1);
       
        start.setBounds(200, 350, 100, 20);
       
    }
    public JButton getStart() {
        return start;
    }

    public void setStart(StyledButton start) {
        this.start = start;
    }

    private void setComponent() {
        setLabel1(new StyledLabel("Enter first player name:", 20));
        setName1(new JTextField());
        setLabel2(new StyledLabel("Enter second Player name:", 20));
        setName2(new JTextField());

        setStart(new StyledButton("Start", 18));
   
    }
//    public setImage() {
//    	panel.setImageIcon()
//    	
//    }
    
	public JLabel getLabel1() {
		return label1;
	}
	public void setLabel1(JLabel label1) {
		this.label1 = label1;
	}
	public JLabel getLabel2() {
		return label2;
	}
	public void setLabel2(JLabel label2) {
		this.label2 = label2;
	}
	public JTextField getName1() {
		return name1;
	}
	public void setName1(JTextField name1) {
		this.name1 = name1;
	}
	public JTextField getName2() {
		return name2;
	}
	public void setName2(JTextField name2) {
		this.name2 = name2;
	}
	public void setStart(JButton start) {
		this.start = start;
	}
    
    
}
