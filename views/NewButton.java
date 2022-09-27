package views;

import javax.swing.*;

public class NewButton extends JButton {

    int i;
    int j;

    public NewButton(int i, int j)
    {
        super();
        this.i=i;
        this.j=j;
    }

    public NewButton (String s,int  i , int j)
    {
        super(s);
        this.i=i;
        this.j=j;
    }

}
