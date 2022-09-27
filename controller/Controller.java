package controller;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import engine.Game;
import engine.Player;
import model.world.Champion;
import views.*;
import views.Choice;

public class Controller {
//private Game model;
public GameView view;
private BoardView board;

	public Player getP1() {
		return p1;
	}

	public Player p1 = new Player();
public Player p2 = new Player();
public Controller() {
	
	view=new GameView(this);
	view.setExtendedState(Frame.MAXIMIZED_BOTH);
	//view.setPanel((JPanel)new StartView(this));

	//drawPanel("Start");
	//createButtons();
	view.revalidate();
	view.repaint();
	//view.pack();

	
	
}

public void createButtons() {

}
public void drawPanel(String panelName)
{
	switch(panelName) {
	case("Start") : view.setPanel(new StartView(this)); break;
	case("GameView") :
		try {
			
			Choice ChoicePanel=new Choice(this);
			Game.loadAbilities("Abilities.csv");
			Game.loadChampions("Champions.csv");
			
			
			for(int i=0;i<Game.getAvailableChampions().size();i++) {
				JButton Champion=new JButton(""+Game.getAvailableChampions().get(i).getName());
				//ActionListenerExample exampleListener = new ActionListenerExample();
				Champion shakhs = Game.getAvailableChampions().get(i);

				Champion.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String p1name= p1.getName();
						String p2name= p2.getName();

						if(p1.getTeam().size()< 3)
						{
							ArrayList<Champion> team = p1.getTeam();
							team.add(shakhs);
							p1.setTeam(team);
							ChoicePanel.setSelection(ChoicePanel.getSelection() + "\n" +shakhs.getName());
							Champion.setEnabled(false);
							if(p1.getTeam().size()==3){
								ChoicePanel.setSelection(ChoicePanel.getSelection() + "\n" +"\n"+"Player 2:"+p2name);
							}
						}
						else {
							if(p2.getTeam().size()< 3)
							{
								ArrayList<Champion> team = p2.getTeam();
								team.add(shakhs);
								p2.setTeam(team);
								ChoicePanel.setSelection(ChoicePanel.getSelection() + "\n" +shakhs.getName());

								Champion.setEnabled(false);
								if(p2.getTeam().size()==3){
									drawPanel("selectLeader1Panel");
								}

							}

						}

						
					}
					
				});
				Champion.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
						ChoicePanel.setInfo(shakhs.toString());
						
					}
					
					public void mouseExited(MouseEvent evt) {
						ChoicePanel.setInfo("");

						
					}
				});
				
			//	ChoicePanel.setInfo(ChoicePanel.getInfo()+"\n"+"\n"+model.getAvailableChampions().get(i).toString());
				ChoicePanel.getChampions().add(Champion);
			}
			Border padding = BorderFactory.createEmptyBorder(10,10,10,10);
			ChoicePanel.setBorder(padding);
			view.setContentPane(ChoicePanel);
			
			//view.setPanel(ChoicePanel);
			view.revalidate();
			view.repaint();
			view.pack();

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	break;
	
	case("selectLeader1Panel"):  JPanel selectLeader1Panel= new JPanel();
	JLabel text=new JLabel("Select first player leader : "+p1.getName());
	selectLeader1Panel.add(text);
	for(Champion e: p1.getTeam()) {
		Champion x= e;
		JButton Champion=new JButton(x.getName());
		selectLeader1Panel.add(Champion);
		Champion.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p1.setLeader(x);

				drawPanel("selectLeader2Panel");
				
			}
			
		});
	} view.setContentPane(selectLeader1Panel); view.revalidate();view.repaint(); //view.pack();
	break;
	
	case("selectLeader2Panel"):  JPanel selectLeader2Panel= new JPanel();
		JLabel text2=new JLabel("Select second player leader : "+p2.getName());
	selectLeader2Panel.add(text2);
	for(Champion e: p2.getTeam()) {
		Champion x= e;
		JButton Champion=new JButton(x.getName());
		selectLeader2Panel.add(Champion);
		Champion.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				p2.setLeader(x);

				drawPanel("BoardView"); //change
				
			}
			
		});
	} view.setContentPane(selectLeader2Panel); view.revalidate();view.repaint(); //view.pack();
	break;
		case("BoardView"):
		{

			board=new BoardView(this);
			view.setContentPane(board);
			//view.pack();
			view.revalidate();
			view.repaint();
			//view.pack();

		}
	
	
		
	}
	
}

public static void main(String[] args) {
	new Controller();
}

}
