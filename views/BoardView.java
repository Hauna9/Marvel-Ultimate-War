package views;
import controller.Controller;
import engine.Game;
import engine.Player;
import engine.PriorityQueue;
import exceptions.*;
import jdk.nashorn.internal.scripts.JO;
import model.abilities.AreaOfEffect;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BoardView extends JPanel{
//attributes currentChampData, panel for actions
    private JPanel theBoard;
    private JTextArea playersDetails;

    private JTextArea orderStatus;
    private Game theGame;
    private JTextArea currentChampDetails;
    private JPanel possibleActions;
    private JPanel possibleAbilities;

    private JButton ability1;
    private JButton ability2;
    private JButton ability3;
    private JButton ability4;
    Controller controller;

    private JPanel directionButtons;
    boolean flag;
    int iability;

    public BoardView(Controller controller)
    { this.setBounds(250,250,1400,800);
        this.controller=controller;


        setLayout(null);
        theBoard =new JPanel(new GridLayout(5,5));

        theGame=new Game(controller.p1,controller.p2);

        for(int i=4;i>=0;i--)
        {for(int j=0;j<5;j++){
            NewButton cell;
                if(theGame.getBoard()[i][j]==null)
                {
                     cell =new NewButton("", i ,j);
                     cell.setBackground(Color.GRAY);
                }
                else{
                    if(theGame.getBoard()[i][j] instanceof Cover)
                    {
                        cell =new NewButton("Cover"+"\n"+"HP: "+((Cover)theGame.getBoard()[i][j]).getCurrentHP() , i, j);
                        cell.setBackground(Color.GREEN);
                    }
                    else
                    {
                        if(theGame.getFirstPlayer().getTeam().contains((Champion)theGame.getBoard()[i][j]) )
                        {
                            cell=new NewButton( ((Champion)theGame.getBoard()[i][j]).getName() , i ,j);
                            // cell.setBackground(Color.BLUE);
                            if ((((Champion)theGame.getBoard()[i][j]).getName()).equals(theGame.getCurrentChampion().getName()))
                                cell.setBackground(Color.YELLOW);
                            else
                                cell.setBackground(Color.BLUE);
                        }
                        else
                        {
                            cell=new NewButton(((Champion)theGame.getBoard()[i][j]).getName() , i ,j);
                            //cell.setBackground(Color.RED);
                            if ((((Champion)theGame.getBoard()[i][j]).getName()).equals(theGame.getCurrentChampion().getName()))
                                cell.setBackground(Color.YELLOW);
                            else
                                cell.setBackground(Color.RED);
                        }
                    }
                }
                 theBoard.add(cell);
            cell.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (flag)  {
                        flag=false;
                        int j1= 4- ((NewButton) e.getSource()).j ;
                        int i1=  ((NewButton) e.getSource()).i ;
                        try {
                            theGame.castAbility(theGame.getCurrentChampion().getAbilities().get(iability),i1,j1);
                            gameOver();
                            updatePanel();

                        } catch (NotEnoughResourcesException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        } catch (AbilityUseException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        } catch (InvalidTargetException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        } catch (CloneNotSupportedException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                    } }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            }
        }
        theBoard.setBounds(210,0,600,600);
        this.add(theBoard);





        //FIX THE LAYOUT TO BE IN THE WEST;
        //JFrame panelScroll=new JFrame();


        JPanel toScroll=new JPanel();
        toScroll.setBounds(0,0,200,700);
        toScroll.setLayout(new BorderLayout());
        playersDetails=new JTextArea();
        playersDetails.setEditable(false);
        toScroll.add(new JScrollPane(playersDetails),BorderLayout.CENTER);
        addTextToScroll(this.player1Details()+"\n"+this.player2Details(),playersDetails);
        add(toScroll);
        this.revalidate();
        this.repaint();



        //ADD ORDER STATUS.
        orderStatus = new JTextArea();
        orderStatus.setEditable(false);
        //String s=orderDetails();
    //    JTextArea orderDetails=new JTextArea(s);
        orderStatus.setText(orderDetails());
        orderStatus.setBounds(0,710,750,300);
        add(orderStatus);


       //ADD CURRENT CHAMP DETAILS
        /*currentChampDetails= new JTextArea(theGame.getCurrentChampion().champDetails()+"\n"+this.abilityOfCurrentChampDetails());
        currentChampDetails.setBounds(850,0,200,600);
        add(currentChampDetails);
        */
        JPanel toScrollChamp=new JPanel();
        toScrollChamp.setBounds(850,0,200,600);
        toScrollChamp.setLayout(new BorderLayout());
        currentChampDetails=new JTextArea();
        currentChampDetails.setEditable(false);
        toScrollChamp.add(new JScrollPane(currentChampDetails),BorderLayout.CENTER);
        addTextToScroll(theGame.getCurrentChampion().champDetails()+"\n"+this.abilityOfCurrentChampDetails(),currentChampDetails);
        add(toScrollChamp);
        this.revalidate();
        this.repaint();






        //ADD POSSIBLE ACTIONS :
        possibleActions =new JPanel((new GridLayout(5,1)));
        //possibleActions.setLayout(null);
        possibleActions.setBounds(1100,0,350,600);
        JButton moveUP=new JButton("Move up");

        moveUP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  {
                try {
                    theGame.move(Direction.UP);

                   // theBoard.removeAll();
                    //redrawTheBoard();
                    updatePanel();


                } catch (NotEnoughResourcesException ex) {
                    //throw new RuntimeException(ex);
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);



                } catch (UnallowedMovementException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton moveDOWN=new JButton("Move down");
        moveDOWN.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theGame.move(Direction.DOWN);
                    updatePanel();


                } catch (NotEnoughResourcesException ex) {
                    //throw new RuntimeException(ex);

                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);


                } catch (UnallowedMovementException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton moveRIGHT=new JButton("Move right");
        moveRIGHT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theGame.move(Direction.RIGHT);
                    updatePanel();


                } catch (NotEnoughResourcesException ex) {
                    //throw new RuntimeException(ex);

                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);


                } catch (UnallowedMovementException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        JButton moveLEFT=new JButton("Move left");
        moveLEFT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theGame.move(Direction.LEFT);
                    updatePanel();


                } catch (NotEnoughResourcesException ex) {
                    //throw new RuntimeException(ex);

                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);


                } catch (UnallowedMovementException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton attackUP=new JButton("attack UP");
        attackUP.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theGame.attack(Direction.UP);
                    gameOver();
                    updatePanel();



                } catch (NotEnoughResourcesException ex) {
                    //throw new RuntimeException(ex);

                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);


                }  catch (InvalidTargetException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                } catch (ChampionDisarmedException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        JButton attackDOWN=new JButton("attack DOWN ");
        attackDOWN.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theGame.attack(Direction.DOWN);
                    updatePanel();
                    gameOver();


                } catch (NotEnoughResourcesException ex) {
                    //throw new RuntimeException(ex);

                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);


                }  catch (InvalidTargetException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                } catch (ChampionDisarmedException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        JButton attackRIGHT=new JButton("attack RIGHT");
        attackRIGHT.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theGame.attack(Direction.RIGHT);
                    updatePanel();
                    gameOver();


                } catch (NotEnoughResourcesException ex) {
                    //throw new RuntimeException(ex);

                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);


                }  catch (InvalidTargetException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                } catch (ChampionDisarmedException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        JButton attackLEFT=new JButton("attack LEFT");
        attackLEFT.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theGame.attack(Direction.LEFT);
                    updatePanel();
                    gameOver();


                } catch (NotEnoughResourcesException ex) {
                    //throw new RuntimeException(ex);

                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);


                }  catch (InvalidTargetException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                } catch (ChampionDisarmedException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        JButton useLeaderAbility=new JButton ("USE LEADER ABILITY");
        useLeaderAbility.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    theGame.useLeaderAbility();
                    updatePanel();
                    gameOver();

                } catch (LeaderNotCurrentException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                } catch (LeaderAbilityAlreadyUsedException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton endTurn=new JButton("END TURN");
        endTurn.addActionListener(new ActionListener (){

            @Override
            public void actionPerformed(ActionEvent e) {
                theGame.endTurn();
                gameOver();
                //orderStatus.removeAll();
                orderStatus.setText(orderDetails());
                updatePanel();
                redrawPossibleActionsPanel();
            }
        });


        /*
        for(int i=0;i<theGame.getCurrentChampion().getAbilities().size();i++)
        {
            if(i==0)
            { ability1=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
            possibleActions.add(ability1);
                ability1.addActionListener(new ActionListener()
                {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        abilityButtons(0,ability1); //check this
                    }
                });
            }
            if(i==1)
            { ability2=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                possibleActions.add(ability2);
                ability2.addActionListener(new ActionListener()
                {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        abilityButtons(1,ability3); //check this
                    }
                });
            }
            if(i==2)
            {ability3=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                possibleActions.add(ability3);
        ability3.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                abilityButtons(2,ability3); //check this
            }
        }); }
        }*/





        possibleActions.add(moveUP);
        possibleActions.add(moveDOWN);
        possibleActions.add(moveRIGHT);
        possibleActions.add(moveLEFT);
        possibleActions.add(attackUP);
        possibleActions.add(attackDOWN);
        possibleActions.add(attackLEFT);
        possibleActions.add(attackRIGHT);


        possibleActions.add(endTurn);
        possibleActions.add(useLeaderAbility);
        possibleActions.setBackground(Color.cyan);


        possibleActions.revalidate();
        possibleActions.repaint();
        this.add(possibleActions);
        this.revalidate();
        this.repaint();
        this.setVisible(true);


        //now buttons are created BUT no action handling yet only adding buttons we will then do ability 1.add action listener.....etc WE WILL EXECUTE THE ABILITY AT INDEX 0 in case button aility 1 and so on
        possibleAbilities=new JPanel();
        possibleAbilities.setLayout(new GridLayout());
        possibleAbilities.setBackground(Color.PINK);
        possibleAbilities.setBounds(1100,650,650,100);

        for(int i=0;i<theGame.getCurrentChampion().getAbilities().size();i++){
            if(i==0)
            {

                ability1=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                abilityButtons(i,ability1);
                possibleAbilities.add(ability1);
            }
            if(i==1)
            {

                ability2=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                abilityButtons(i,ability2);
                possibleAbilities.add(ability2);
            }
            if(i==2)
            {

                ability3=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                abilityButtons(i,ability3);
                possibleAbilities.add(ability3);
            }
            if(theGame.isCurrChampDisarmed())
            {

                //or change it to punch i mean the name
                ability4=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                possibleAbilities.add(ability4);
            }


        }

        add(possibleAbilities);
        this.repaint();
        this.revalidate();





















    }

    public void updateButtons()
    {
        for(int i=0;i<theGame.getCurrentChampion().getAbilities().size();i++){
            if(i==0)
            {
                ability1=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                abilityButtons(i,ability1);
                possibleAbilities.add(ability1);
            }
            if(i==1)
            {
                ability2=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                abilityButtons(i,ability2);
                possibleAbilities.add(ability2);
            }
            if(i==2)
            {
                ability3=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                abilityButtons(i,ability3);
                possibleAbilities.add(ability3);
            }
            if(i==3)
            {
                //or change it to punch i mean the name
                ability4=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
                possibleAbilities.add(ability4);
            }

        }

    }
    public void abilityButtons(int i, JButton a)  {
        if (theGame.getCurrentChampion().getAbilities().get(i).getCastArea().equals(AreaOfEffect.SINGLETARGET))
        {
            iability=i;

            a.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        if (theGame.getCurrentChampion().getMana() < theGame.getCurrentChampion().getAbilities().get(i).getManaCost())
                            throw new NotEnoughResourcesException(
                                    "you need at least " + theGame.getCurrentChampion().getAbilities().get(i).getManaCost() + " mana to cast this ability");
                        else {
                            if (theGame.getCurrentChampion().getCurrentActionPoints() < theGame.getCurrentChampion().getAbilities().get(i).getRequiredActionPoints())
                                throw new NotEnoughResourcesException(
                                        "you need at least " + theGame.getCurrentChampion().getAbilities().get(i).getRequiredActionPoints() + " action points to cast this ability");
                            else
                            {if (theGame.isCurrChampSilenced())
                                throw new AbilityUseException("You can not cast an ability while being silenced");
                            else
                            {
                                if (theGame.getCurrentChampion().getAbilities().get(i).getCurrentCooldown() > 0)
                                throw new AbilityUseException("You can not use an ability while it is in cooldown");


                            else
                            {
                                JOptionPane.showMessageDialog(null, "Please choose a cell","Single target ability" , JOptionPane.PLAIN_MESSAGE);
                                flag = true;
                            }
                            }
                            }
                        }
                    }
                    catch(NotEnoughResourcesException ex){
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                    catch(AbilityUseException ex){
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }








        } }); }
        else //direction + add if then empty else
        {
            if (theGame.getCurrentChampion().getAbilities().get(i).getCastArea().equals(AreaOfEffect.DIRECTIONAL)) {
                a.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            if (theGame.getCurrentChampion().getMana() < theGame.getCurrentChampion().getAbilities().get(i).getManaCost())
                                throw new NotEnoughResourcesException(
                                        "you need at least " + theGame.getCurrentChampion().getAbilities().get(i).getManaCost() + " mana to cast this ability");
                            else {
                                if (theGame.getCurrentChampion().getCurrentActionPoints() < theGame.getCurrentChampion().getAbilities().get(i).getRequiredActionPoints())
                                    throw new NotEnoughResourcesException(
                                            "you need at least " + theGame.getCurrentChampion().getAbilities().get(i).getRequiredActionPoints() + " action points to cast this ability");
                                else
                                {if (theGame.isCurrChampSilenced())
                                    throw new AbilityUseException("You can not cast an ability while being silenced");
                                else
                                {
                                    if (theGame.getCurrentChampion().getAbilities().get(i).getCurrentCooldown() > 0)
                                        throw new AbilityUseException("You can not use an ability while it is in cooldown");


                                    else
                                    {
                                        String[] options = new String[] {"UP", "DOWN", "LEFT", "RIGHT"};
                                        int response = JOptionPane.showOptionDialog(null, "Please choose a direction", "Direction Choice",
                                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                null, options, options[0]);

                                        if( options[response].equals("UP"))
                                        {
                                            try {
                                                theGame.castAbility(theGame.getCurrentChampion().getAbilities().get(i),Direction.UP);
                                                gameOver();
                                                updatePanel();

                                            } catch (NotEnoughResourcesException ex) {
                                                ex.printStackTrace();
                                            } catch (AbilityUseException ex) {
                                                ex.printStackTrace();
                                            } catch (CloneNotSupportedException ex) {
                                                ex.printStackTrace();
                                            }

                                        }

                                        if( options[response].equals("DOWN"))
                                        {
                                            try {
                                                theGame.castAbility(theGame.getCurrentChampion().getAbilities().get(i),Direction.DOWN);
                                                gameOver();
                                                updatePanel();

                                            } catch (NotEnoughResourcesException ex) {
                                                ex.printStackTrace();
                                            } catch (AbilityUseException ex) {
                                                ex.printStackTrace();
                                            } catch (CloneNotSupportedException ex) {
                                                ex.printStackTrace();
                                            }

                                        }

                                        if( options[response].equals("RIGHT"))
                                        {
                                            try {
                                                theGame.castAbility(theGame.getCurrentChampion().getAbilities().get(i),Direction.RIGHT);
                                                gameOver();
                                                updatePanel();

                                            } catch (NotEnoughResourcesException ex) {
                                                ex.printStackTrace();
                                            } catch (AbilityUseException ex) {
                                                ex.printStackTrace();
                                            } catch (CloneNotSupportedException ex) {
                                                ex.printStackTrace();
                                            }

                                        }
                                        if( options[response].equals("LEFT"))
                                        {
                                            try {
                                                theGame.castAbility(theGame.getCurrentChampion().getAbilities().get(i),Direction.LEFT);
                                                gameOver();
                                                updatePanel();

                                            } catch (NotEnoughResourcesException ex) {
                                                ex.printStackTrace();
                                            } catch (AbilityUseException ex) {
                                                ex.printStackTrace();
                                            } catch (CloneNotSupportedException ex) {
                                                ex.printStackTrace();
                                            }

                                        }

                                    }
                                }
                                }
                            }
                        }
                        catch(NotEnoughResourcesException ex){
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                        catch(AbilityUseException ex){
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }



                    } });

                  }
                   else {
                a.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            theGame.castAbility(theGame.getCurrentChampion().getAbilities().get(i));
                            gameOver();
                            updatePanel();

                        } catch (NotEnoughResourcesException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        } catch (AbilityUseException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        } catch (CloneNotSupportedException ex) {
                            ex.printStackTrace();
                        }

                    }
                });
            }
        }

        }


    public void redrawTheBoard(){
        //theBoard.removeAll();
        for(int i=4;i>=0;i--)
        {for(int j=0;j<5;j++){
            NewButton cell;
            if(theGame.getBoard()[i][j]==null)
            {
                cell =new NewButton("", i,j);
                cell.setBackground(Color.GRAY);
            }
            else{
                if(theGame.getBoard()[i][j] instanceof Cover)
                {
                    cell =new NewButton("Cover"+"\n"+"HP: "+((Cover)theGame.getBoard()[i][j]).getCurrentHP(), i ,j);
                    cell.setBackground(Color.GREEN);
                }
                else
                {
                    if(theGame.getFirstPlayer().getTeam().contains((Champion)theGame.getBoard()[i][j]) )
                    {
                        cell=new NewButton(((Champion)theGame.getBoard()[i][j]).getName() , i, j);
                       // cell.setBackground(Color.BLUE);
                        if ((((Champion)theGame.getBoard()[i][j]).getName()).equals(theGame.getCurrentChampion().getName()))
                            cell.setBackground(Color.YELLOW);
                        else
                            cell.setBackground(Color.BLUE);
                    }
                    else
                    {
                        cell=new NewButton(((Champion)theGame.getBoard()[i][j]).getName() ,i,j);
                        //cell.setBackground(Color.RED);
                        if ((((Champion)theGame.getBoard()[i][j]).getName()).equals(theGame.getCurrentChampion().getName()))
                            cell.setBackground(Color.YELLOW);
                        else
                            cell.setBackground(Color.RED);
                    }
                }
            }
            theBoard.add(cell);
            cell.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (flag)  {
                        flag=false;
                        int j1= ((NewButton) e.getSource()).j ;
                        int i1= ((NewButton) e.getSource()).i ;
                        try {
                            theGame.castAbility(theGame.getCurrentChampion().getAbilities().get(iability),i1,j1);
                            gameOver();
                            updatePanel();

                        } catch (NotEnoughResourcesException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        } catch (AbilityUseException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        } catch (InvalidTargetException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        } catch (CloneNotSupportedException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                    } }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        }

    }





    public void updatePanel(){
        theBoard.removeAll();
        possibleAbilities.removeAll();

        this.redrawTheBoard();
        updateButtons();
        playersDetails.setText(this.player1Details()+"\n"+this.player2Details());
        currentChampDetails.setText(theGame.getCurrentChampion().champDetails()+"\n"+this.abilityOfCurrentChampDetails());
        this.revalidate();
        this.repaint();
    }


















/////change .getclass to remove the model.world......thing in the panel-- ask if okay to keep that way
    //// check spacong after applied effects
    public String player1Details(){
        String leaderAbilityUsed = "";
        if(theGame.isFirstLeaderAbilityUsed())
            leaderAbilityUsed="PLAYER 1 LEADER ABILITY USED";
        else
            leaderAbilityUsed="PLAYER 1 LEADER ABILITY NOT USED"+"\n";

        String p1Details="FIRST PLAYER DETAILS"+"\n"+"player1: "+theGame.getFirstPlayer().getName()+"\n";
        p1Details+=leaderAbilityUsed;
        for(int i=0;i<theGame.getFirstPlayer().getTeam().size();i++){
            String allAboutChamp = "";
            String leaderStatus = "";
           // String type = "" + theGame.getFirstPlayer().getTeam().get(i).getClass();
            if(theGame.getFirstPlayer().getTeam().get(i)!= null)
            {
                if (theGame.getFirstPlayer().getLeader().equals(theGame.getFirstPlayer().getTeam().get(i)))
                    leaderStatus = "IS LEADER" + "\n";
                else
                    leaderStatus = "NOT LEADER" + "\n";

                allAboutChamp = theGame.getFirstPlayer().getTeam().get(i).champDetails() + leaderStatus+
                        theGame.getFirstPlayer().getTeam().get(i).champListOfAbilities()+"\n";
            }
            p1Details+=allAboutChamp+"\n";
        }

    return p1Details;
    }

    public String player2Details(){
        String leaderAbilityUsed = "";
        if(theGame.isSecondLeaderAbilityUsed())
            leaderAbilityUsed="PLAYER 2 LEADER ABILITY USED";
        else
            leaderAbilityUsed="PLAYER 2 LEADER ABILITY NOT USED"+"\n";

        String p2Details="SECOND PLAYER DETAILS"+"\n"+"player2 : "+theGame.getSecondPlayer().getName()+"\n";
        p2Details+=leaderAbilityUsed;
        for(int i=0;i<theGame.getSecondPlayer().getTeam().size();i++){
            String allAboutChamp = "";
            String leaderStatus = "";
            //String type = "" + theGame.getSecondPlayer().getTeam().get(i).getClass();
            if(theGame.getSecondPlayer().getTeam().get(i)!= null)
            {
                if (theGame.getFirstPlayer().getLeader().equals(theGame.getSecondPlayer().getTeam().get(i)))
                    leaderStatus = "IS LEADER" + "\n";
                else
                    leaderStatus = "NOT LEADER" + "\n";

                allAboutChamp = theGame.getSecondPlayer().getTeam().get(i).champDetails() +leaderStatus+
                        theGame.getSecondPlayer().getTeam().get(i).champListOfAbilities()+"\n";
            }
            p2Details+=allAboutChamp+"\n";
        }
        return p2Details;
    }

    public String orderDetails(){
        String res="ORDER STATUS:  "+"\n";
        PriorityQueue pqtemp=new PriorityQueue(theGame.getTurnOrder().size());

        int m=0;
        while(!(theGame.getTurnOrder().isEmpty())){
            if (m==0) {
                if(theGame.getFirstPlayer().getTeam().contains(theGame.getCurrentChampion()) )
                {
                    res +=  "Current player:  " + theGame.getFirstPlayer().getName() + "\n" +
                            "CURRENT CHAMP: " + ((Champion) theGame.getTurnOrder().peekMin()).getName() + " " + "\n" ;

                }
                else
                {
                    res +=   "Current player:  " + theGame.getSecondPlayer().getName() + "\n" +
                            "CURRENT CHAMP: " + ((Champion) theGame.getTurnOrder().peekMin()).getName() + " " + "\n" ;
                }

              /*  res += "CURRENT CHAMP: " + ((Champion) theGame.getTurnOrder().peekMin()).getName() + " ";
                pqtemp.insert((Champion) theGame.getTurnOrder().remove()); */
            }
            else
                {
                    res+=" "+ m + ")" +((Champion)theGame.getTurnOrder().peekMin()).getName() + " "; //fix counter here
                    pqtemp.insert((Champion)theGame.getTurnOrder().remove());
                }
               m++;
            }
        while(!(pqtemp.isEmpty()))
            theGame.getTurnOrder().insert((Champion)pqtemp.remove());
        return res;
    }


   public String abilityOfCurrentChampDetails()
   {
       String startText="CURRENT CHAMPION DETAILS "+"\n";
        String type="";
        int n=1;
        for(int i=0;i<theGame.getCurrentChampion().getAbilities().size();i++){
            if (theGame.getCurrentChampion().getAbilities().get(i) instanceof DamagingAbility)
                type= "DamagingAbility";
            else{
                if  (theGame.getCurrentChampion().getAbilities().get(i) instanceof HealingAbility)
                    type="HealingAbility";
                else
                    type="CrowdControlAbility";

            }
            startText+="\n"+"Ability"+""+n+"\n"+ "Name: "+theGame.getCurrentChampion().getAbilities().get(i).getName()+"\n"+"Type: "+type+
                    theGame.getCurrentChampion().getAbilities().get(i).abilityDetailsForChamp()+"\n";

        n++;


        }

       return startText;
   }
   public void addTextToScroll(String s,JTextArea TextAreaToScroll){
       TextAreaToScroll.append(s);

   }

   public void redrawPossibleActionsPanel(){
        possibleAbilities.removeAll();
       for(int i=0;i<theGame.getCurrentChampion().getAbilities().size();i++){
           if(i==0)
           {
               ability1=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
               abilityButtons(i,ability1);
               possibleAbilities.add(ability1);
           }
           if(i==1)
           {
               ability2=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
               abilityButtons(i,ability2);
               possibleAbilities.add(ability2);
           }
           if(i==2)
           {
               ability3=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
               abilityButtons(i,ability3);
               possibleAbilities.add(ability3);
           }
           if(i==3)
           {
               //or change it to punch i mean the name
               ability4=new JButton(theGame.getCurrentChampion().getAbilities().get(i).getName());
               abilityButtons(i,ability4);
               possibleAbilities.add(ability4);
           }

       }


   }


public void  gameOver(){

        JOptionPane gameOver;
        if (theGame.checkGameOver()==theGame.getFirstPlayer()) {
            gameOver=new JOptionPane(theGame.getFirstPlayer().getName() + " WON");
            JDialog dialog=gameOver.createDialog(null,"GAME OVER");
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
            System.exit(0);
        }
    if (theGame.checkGameOver()==theGame.getSecondPlayer())

    {
        JOptionPane.showMessageDialog(null,theGame.getSecondPlayer().getName()+" WON","Game Over",JOptionPane.PLAIN_MESSAGE);
       System.exit(0);
    }




}





}
