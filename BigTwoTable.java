import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;

/**
 * @author Gao Ning
 * @since 2016, 11, 10
 * @version 1.0
 * This class handles the GUI
 *
 */
public class BigTwoTable implements CardGameTable {
	/**
	 * @param game game is a reference to a card game associates wirh this table
	 * a constructor
	 */
	/**
	 * @param game
	 * @author Gao Ning
	 * Constructor
	 */
	public BigTwoTable(CardGame game)
	{
		this.game=game;
	}
	private CardGame game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel textAreaPanel;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private JTextArea chatArea;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;
	private JTextField outgoing;
	private String chatString;
	private JMenuItem connect;
	
	/**
	 * this method enable the connect button
	 * 
	 */
	public void connectEnable()
	{
		connect.setEnabled(true);
	}
	/**
	 * this method disable the connect button
	 * 
	 */
	public void connectDisable()
	{
		connect.setEnabled(false);
	}
	/**
	 * this method asks the user to input the server ip address
	 */
	public String getServerIPAddress()
	{
		String s;
		s=(String)JOptionPane.showInputDialog(null, "Please input the server address: \n");
		return s;
	}
	/**
	 * this method asks the user to input the server TCP port
	 */
	public int getServerTCP ()
	{
		int s;
		String port;
		port=JOptionPane.showInputDialog(null, "Please input the server TCP port: \n");
		if(port==null||port.length()==0)
		{
			return -1;
		}
		else
		{
			s=Integer.parseInt(port);
			return s;
		}
		
	}
	/**
	 * this method asks the user to input his name and returns the name
	 */
	public String getUserName ()
	{
		String s;
		s=(String)JOptionPane.showInputDialog(null, "Please input your name: \n");
		return s;
	}
	/**
	 * @param activePlayer
	 * set the activePlayer
	 * @author Gao Ning
	 */
	public void setActivePlayer (int activePlayer)
	{
		this.activePlayer=activePlayer;
	}
	/**
	 * @author Gao Ning
	 * return the selected cards' indices
	 */
	public int[] getSelected()
	{
		int numOfSelected=0;
		for (int i=0; i<selected.length; ++i)
		{
			if(selected[i])
			{
				++numOfSelected;
			}
		}
		int[] selectedInd=new int [numOfSelected];
		int count=0;
		for(int i=0; i<selected.length; ++i)
		{
			if(selected[i])
			{
				selectedInd[count]=i;
				++count;
			}
		}
		return selectedInd;
	}
	/**
	 * @author Gao Ning
	 * reset the selected[] 
	 */
	public void resetSelected()
	{
		int length=game.getPlayerList().get(((BigTwoClient)game).getPlayerID()).getNumOfCards();
		selected=new boolean[length];
		for(int i=0; i<length; ++i)
		{
			selected[i]=false;
		}
	}
	/**
	 * @author Gao Ning
	 * repaint the frame
	 */
	public void repaint()
	{
		frame.repaint();
	}
	/** @param String msg
	 * @author Gao Ning
	 * print message to the JTextArea
	 */
	public void printMsg(String msg)
	{
		msgArea.append(msg);
	}
	
	/**
	 * @author Gao Ning
	 * @param msg
	 * 
	 * print the chat message
	 */
	public void printChatMsg(String msg)
	{
		chatArea.append(msg);
	}
	public void clearChatMsgArea()
	{
		chatArea.setText("");
	}
	 
	/**
	 * this method print the end of game message
	 * 
	 */
	public void printEndGameMsg()
	{
		String endOfGame="";
		for(int i=0; i<game.getNumOfPlayers(); ++i)
		{
			endOfGame+=game.getPlayerList().get(i).getName()+": ";
			if(game.getPlayerList().get(i).getNumOfCards()!=0)
			{
				for(int j=0; j<game.getPlayerList().get(i).getNumOfCards(); ++j)
				{
					endOfGame+=" ["+game.getPlayerList().get(i).getCardsInHand().getCard(j).toString()+"]";
				}
				endOfGame+="\n";
			}
			else
			{
				endOfGame+=" wins\n";
			}
		}
		JOptionPane.showMessageDialog(null, "GameOver!!\n"+endOfGame);
	}
	
	/**
	 * @author Gao Ning
	 * clear all he message
	 */
	public void clearMsgArea()
	{
		msgArea.setText("");
	}
	
	/**
	 * @author Gao Ning
	 * Enable the playButton, passButton, bigTwoPanel, msgArea
	 */
	public void enable()
	{
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}
	/**
	 * @author Gao Ning
	 * Disable the playButton, passButton, bigTwoPanel, msgArea
	 */
	public void disable()
	{
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
	}
	/**
	 * @author Gao Ning
	 * Inner Class
	 * deal with the detailed implementation of GUI
	 *
	 */
	public class BigTwoPanel extends JPanel implements MouseListener
	{
		/*the following code deals with the game table*/
		Image dealer=new ImageIcon("maleCasinoDealer1.jpg").getImage();
		public void paintComponent(Graphics g)
		{
			g.drawImage(dealer, 0, 0, this.getWidth(), this.getHeight(), null);
			/* the following code deals with the last hand on the table*/
			
			int handsPlayed=game.getHandsOnTable().size();
			/*int lineHeight=(this.getHeight()/6)*(game.getNumOfPlayers()+1)+this.getHeight()/100;*/
			g.setColor(new Color(20, 50, 20));
			/*g.drawLine(0,lineHeight ,this.getWidth(),lineHeight );*/
			
			if(handsPlayed!=0)
			{
				String PlayerOflastHand="Last hand played by "+game.getHandsOnTable().get(handsPlayed-1).getPlayer().getName();
				int fontSize1=this.getHeight()/120+this.getWidth()/120;
				g.setFont(new Font("TimesRoman", Font.BOLD, fontSize1));
				g.drawString(PlayerOflastHand, this.getWidth()/32, (this.getHeight()/6)*(game.getNumOfPlayers()+1)+this.getHeight()/90);
				for (int i=0; i<game.getHandsOnTable().get(handsPlayed-1).size();++i)
				{
					int rank=game.getHandsOnTable().get(handsPlayed-1).getCard(i).getRank();
					int suit=game.getHandsOnTable().get(handsPlayed-1).getCard(i).getSuit();
					g.drawImage(cardImages[suit][rank],	this.getWidth()/6+this.getWidth()*i/25,	(this.getHeight()/6)*(game.getNumOfPlayers()+1)+this.getHeight()/80,	this.getWidth()/18, this.getHeight()/10,this);
					
				}
			}
			
			
			
			
			
			
			
			
			/*the following code deals with the table*/
			g.setColor(Color.cyan);
			int fontSize=this.getHeight()/75+this.getWidth()/120;
			g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
			for(int j=0; j<game.getNumOfPlayers(); ++j)
			{
				
				//String defaultNames[]={"Player0", "Player1", "Player2", "Player3"};
				String nameOfPlayer=null;
				if(game.getPlayerList().get(j).getName()!=null&&(game.getPlayerList().get(j).getName()!=""))
				{
					nameOfPlayer=game.getPlayerList().get(j).getName();
				}
				if(nameOfPlayer!=null)
				{
					/*draw player Name*/
					if(j==activePlayer)
					{
						
						g.drawString("You", this.getWidth()/32, (this.getHeight()/6)*(j+1)-5);
					}
					else
					{	
						g.drawString(nameOfPlayer, this.getWidth()/32, (this.getHeight()/6)*(j+1)-5);
					}

					/*draw the player images*/
					g.drawImage(avatars[j], this.getWidth()/32, (this.getHeight()/6)*(j+1), this.getWidth()/16, this.getHeight()/8,this);
					
					/*draw the card image*/
					if(j==activePlayer)
					{
						for(int i=0; i<game.getPlayerList().get(activePlayer).getNumOfCards(); ++i)
						{
							int rank=game.getPlayerList().get(activePlayer).getCardsInHand().getCard(i).getRank();
							int suit=game.getPlayerList().get(activePlayer).getCardsInHand().getCard(i).getSuit();
							if(!selected[i])
							{
								g.drawImage(cardImages[suit][rank],	this.getWidth()/6+this.getWidth()*i/20,	(this.getHeight()/6)*(activePlayer+1),	this.getWidth()/16, this.getHeight()/8,this);
							}
							else
							{
								g.drawImage(cardImages[suit][rank],	this.getWidth()/6+this.getWidth()*i/20,	(this.getHeight()/6)*(activePlayer+1)-30,	this.getWidth()/16, this.getHeight()/8,this);
							}
						}
					}
					else 
					{
						/*draw the player images*/
						g.drawImage(avatars[j], this.getWidth()/32, (this.getHeight()/6)*(j+1), this.getWidth()/16, this.getHeight()/8,this);
						for (int i=0; i<game.getPlayerList().get(j).getNumOfCards(); ++i)
						{
							g.drawImage(cardBackImage,	this.getWidth()/6+this.getWidth()*i/20,	(this.getHeight()/6)*(j+1),	this.getWidth()/16, this.getHeight()/8,this);
						}
					}
				}	
				
			}
		}

		/**
		 * @author Gao Ning
		 * Deal with the mouseClicked problem, compute the user selected cards, interact with BigTwoPanel 
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int x=e.getX();
			int y=e.getY();
			for (int i=game.getPlayerList().get(activePlayer).getNumOfCards()-1; i>=0; --i)
			{
				if(!selected[i])
				{
					if((x>(this.getWidth()/6+this.getWidth()*i/20)&&x<(getWidth()/6+this.getWidth()/16+this.getWidth()*i/20))&&(y>(this.getHeight()/6)*(activePlayer+1)&&y<((this.getHeight()/6)*(activePlayer+1)+this.getHeight()/8)))
					{
						selected[i]=!selected[i];
						frame.repaint();
						i=-1;
					}
				}
				else
				{
					if((x>this.getWidth()/6+this.getWidth()*i/20&&x<(getWidth()/6+getWidth()/16+this.getWidth()*i/20))&&(y>((this.getHeight()/6)*(activePlayer+1)-30)&&y<((this.getHeight()/6)*(activePlayer+1)+this.getHeight()/8-30)))
					{
						selected[i]=!selected[i];
						frame.repaint();
						i=-1;
					}
				}
			}
		}
		/**
		 * nothing happen for this event
		 */
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * nothing happen for this event
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * nothing happen for this event
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		/**
		 * nothing happen for this event
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * @author gao Ning
	 * Inner class
	 * when play button clicked, call checkMove, push the game forward
	 *
	 */
	public class playButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(getSelected().length==0)
			{
				msgArea.append("Click pass to pass this turn, click the desired cards then click play to make a move\n");
			}
			else
			{
				//to be solved
				if(activePlayer==game.getCurrentIdx())
				{
					game.makeMove(activePlayer, getSelected());
					resetSelected();
				}
				else
				{
					printMsg("Please wait until its your turn\n");
				}
			}
		}
		
	}
	/**
	 * @author Gao Ning
	 * Inner class
	 * actionListioner for the pass button
	 * call checkMove method
	 * push the game forward
	 *
	 */
	public class passButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			//to be solved 
			if(activePlayer==game.getCurrentIdx())
			{
				game.makeMove(activePlayer, null);
			}
			else
			{
				printMsg("Please wait until its your turn\n");
			}
		}
	}
	/**
	 * @author Gao Ning
	 * 
	 * Inner class
	 * ActionListener for the Restart menuItem
	 * restart the game
	 */
	public  class ConnectMenuItemListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			((BigTwoClient)game).disconnect();
			((BigTwoClient)game).setServerIP(getServerIPAddress());
			((BigTwoClient)game).setServerPort(getServerTCP());
			if(((BigTwoClient)game).getServerIP()!=null&&((BigTwoClient)game).getServerPort()!=-1&&((BigTwoClient)game).getServerIP()!="")
			{
				((BigTwoClient)game).makeConnection();
			}
			else
			{
				printMsg("Invalid IP or Port.\n");
			}
		}
		
		
	}
	
	/**
	 * @author Gao Ning
	 * handle the case that the enter is clicked
	 *
	 */
	public class EnterListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			chatString=outgoing.getText();
			((BigTwoClient)game).sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, chatString));
			outgoing.setText("");
			outgoing.requestFocus();
		}
		
	}
	/**
	 * @author Gao Ning
	 * Inner class
	 * ActionListener for the Quit menuItem 
	 * quit the game
	 *
	 */
	public class QuitMenuItemListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
	}
	
	/**
	 * @author Gao Ning
	 * 
	 * handle the action that the message is entered
	 *
	 */
	public class MessageEnterListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			chatString=outgoing.getText();
			((BigTwoClient)game).sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, chatString));
			outgoing.setText("");
			outgoing.requestFocus();
		}
		
	}
	/**
	 * @author Gao Nng
	 * release the GUI
	 */
	public void releaseGUI()
	{
		
		resetSelected();
		setActivePlayer(game.getCurrentIdx());
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bigTwoPanel=new BigTwoPanel();
		bigTwoPanel.addMouseListener((BigTwoPanel)bigTwoPanel);
		playButton=new JButton ("play");
		passButton=new JButton ("pass");
		msgArea=new JTextArea();
		chatArea=new JTextArea();
		playButton.addActionListener(new playButtonListener());
		passButton.addActionListener(new passButtonListener());
		
		/*this part get all the images ready*/
		
		Image player0=new ImageIcon("yao.jpg").getImage();
		Image player1=new ImageIcon("lian.jpg").getImage();
		Image player2=new ImageIcon("xiongmaolian.png").getImage();
		Image player3 =new ImageIcon("yuanlian.jpg").getImage();
		avatars=new Image[4];
		avatars[0]=player0;
		avatars[1]=player1;
		avatars[2]=player2;
		avatars[3]=player3;
		String suits[]={"d", "c","h", "s"};
		String ranks[]={"a","2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k"};
		cardImages=new Image[4][13];
		for(int i=0; i<4; ++i)
		{
			for(int j=0; j<13; ++j)
			{
				cardImages[i][j]=new ImageIcon(ranks[j]+suits[i]+".gif").getImage();
			}
		}
		cardBackImage=new ImageIcon("b.gif").getImage();
		
		/*this part deals with the textArea*/
		
		msgArea=new JTextArea(30, 21);//subject to further amendment
		msgArea.setLineWrap(true);
		DefaultCaret caret=(DefaultCaret)msgArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane msgScroller =new JScrollPane(msgArea);
		msgScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		msgScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		//chat Area
		JLabel chatMsgLabel =new JLabel("Message");
		chatArea=new JTextArea(30, 21);//subject to further amendment
		
	    chatArea.setLineWrap(true);
		DefaultCaret chatCaret=(DefaultCaret)chatArea.getCaret();
		chatCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane chatScroller =new JScrollPane(chatArea);
		chatScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//JTextField read in user input
		outgoing = new JTextField(20);
		chatMsgLabel.setLabelFor(outgoing);
		outgoing.addActionListener(new EnterListener());
		
		
		textAreaPanel=new JPanel();
		textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));
		textAreaPanel.add(msgScroller);
		textAreaPanel.add(chatScroller);
		textAreaPanel.add(chatMsgLabel);
		textAreaPanel.add(outgoing);
		
		
		
		/*this part deals with the menu*/
		
		//Game menu
		JMenuBar menuBar=new JMenuBar();
		JMenu menu =new JMenu("Game");
		JMenuItem quit=new JMenuItem("Quit");
		connect =new JMenuItem("Connect");
		quit.addActionListener(new QuitMenuItemListener());
		connect.addActionListener(new ConnectMenuItemListener());
		menu.add(connect);
		menu.add(quit);
		
		//Message Menu
		
		JMenu msgMenu =new JMenu("Message");
		JMenuItem enter = new JMenuItem("Enter");
		enter.addActionListener(new MessageEnterListener());
		msgMenu.add(enter);
		

		menuBar.add(menu);
		menuBar.add(msgMenu);
		
		/*this part add all the elements to the frame*/
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.add(playButton);
		buttonPanel.add(passButton);
		bigTwoPanel.setLayout(new BorderLayout());
		bigTwoPanel.add(buttonPanel, BorderLayout.SOUTH);
		frame.add(bigTwoPanel, BorderLayout.CENTER);
		//frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.add(textAreaPanel, BorderLayout.EAST);
		frame.add(menuBar, BorderLayout.NORTH);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		
		
	}
	/**
	 * reset the table
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		activePlayer=((BigTwoClient)game).getPlayerID();
		resetSelected();
		clearChatMsgArea();
		clearMsgArea();
	}
}
