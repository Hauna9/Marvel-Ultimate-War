package views;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class StyledButton extends JButton {
	
	 public StyledButton() {

	  }

	  public StyledButton(String s, int size) {
	    super(s);
	    //var color = new Color(153, 102, 0);
	    //ActionListenerExample action = new ActionListenerExample();
	    setFont(new Font(Font.MONOSPACED, Font.PLAIN, size));
	    setForeground(Color.BLACK);
	    setBackground(Color.GREEN);
	    setBorder(new EmptyBorder(10, 10, 10, 10));
	    setBorderPainted(false);
	    setFocusPainted(false);
	    //this.addActionListener(action);
	  }
}
