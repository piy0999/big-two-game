import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @author piyushjha
 *This class builds the GUI for the BigTwoCard game, it implements the CardGameTable interface and handles all the user interactions.
 *
 */
public class BigTwoTable implements CardGameTable{
	
	private BigTwoClient game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenu menu2;
	private JMenuItem menuItem1;
	private JMenuItem menuItem2;
	private JPanel panel2;
	private JScrollPane jScrollPane;
	private JTextField jTextField1;
	private JTextArea jTextArea2;
	private JScrollPane jScrollPane2;

	
	/**
	 * This is the constructor function for creating the BigTwoTable GUI for the BigTwo game. 
	 * @param game
	 * The CardGame parameter is a parameter that is an object for the CardGame interface to implement a BigTwo game methods. 
	 */
	public BigTwoTable(BigTwoClient game)
	{	
		
		this.game = game;
		cardImages = new Image[4][13];
		// add images to the cardImages array
		for(int i = 0; i < 4 ; i++)
		{
			for(int j = 0; j < 13; j++)
			{
				try {
					cardImages[i][j] = ImageIO.read(getClass().getResourceAsStream((j+1) +"_"+i+".gif"));
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}
		}
		
		avatars = new Image[4];
		
		try {
				avatars[0] = ImageIO.read(getClass().getResourceAsStream("batman.png"));
				avatars[1] = ImageIO.read(getClass().getResourceAsStream("flash.png"));
				avatars[2] = ImageIO.read(getClass().getResourceAsStream("green.png"));
				avatars[3] = ImageIO.read(getClass().getResourceAsStream("superman.png"));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		

		
		try {
			cardBackImage = ImageIO.read(getClass().getResourceAsStream("b.gif"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		// initialize boolean selected array
		selected = new boolean[13];
		frame = new JFrame();
		bigTwoPanel = new BigTwoPanel();
		msgArea = new JTextArea();
		jTextArea2 = new JTextArea();
		playButton = new JButton("Play");
		passButton = new JButton("Pass");
		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		menuItem1 = new JMenuItem("CONNECT");
		menuItem2 = new JMenuItem("QUIT");
		menu2 = new JMenu("Message");
		panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jScrollPane = new JScrollPane(msgArea);
		jTextField1 = new JTextField(40);
		jTextField1.addActionListener(new TextFieldListener());
		JLabel jLabel1 = new JLabel("Message:");
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3,BoxLayout.Y_AXIS));
		jScrollPane2 = new JScrollPane(jTextArea2);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bigTwoPanel.setPreferredSize(new Dimension(550,550));
		frame.add(bigTwoPanel, BorderLayout.WEST);
		panel3.setPreferredSize(new Dimension(500, 500));
		panel3.add(jScrollPane);
		panel3.add(jScrollPane2);
		frame.add(panel3, BorderLayout.EAST);
		menuBar.add(menu);
		menuBar.add(menu2);
		menu.add(menuItem1);
		menu.add(menuItem2);
		frame.setJMenuBar(menuBar);
		panel2.add(playButton);
		panel2.add(passButton);
		panel2.add(jLabel1);
		panel2.add(jTextField1);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.setSize(1050, 800);
		frame.setVisible(true);
		panel2.repaint();
		
		new PlayButtonListener();
		new PassButtonListener();
		new ConnectMenuItemListener();
		new QuitMenuItemListener();

		
		msgArea.setEditable(false);
		jTextArea2.setEditable(false);
		
	
		
	}

	
	/**
	 * Sets the index of the active player to the private activePlayer variable
	 * 
	 * @param activePlayer
	 * an integer value representing the index of the current player
	 */
	public void setActivePlayer(int activePlayer) {
		if (activePlayer < 0 || activePlayer >= game.getNumOfPlayers()) {
			this.activePlayer = -1;
		} else {
			this.activePlayer = activePlayer;
		}
		
	}


	/**
	 * Returns an array of indices of the cards selected by the currently active player
	 * 
	 * @return 
	 * an integer array of indices of the cards selected
	 */
	public int[] getSelected() {
		
		CardGamePlayer p = game.getPlayerList().get(activePlayer);
		CardList p_cards = p.getCardsInHand();
		int count = 0;
		for(int i = 0; i < p_cards.size() ; i++)
		{
			if(selected[i] == true)
			{
				count++;
			}
		}
		
		int [] idx = null; 
		if(count != 0)
		{
			idx = new int[count];
			int num = 0;
			for(int i = 0; i < p_cards.size() ; i++ )
			{
				if(selected[i] == true)
				{
					idx[num] = i;
					num++;
				}
			}
		}
		
		return idx;
	}

	/**
	 * Resets the values of boolean array of selected cards to a false value.
	 */
	public void resetSelected() {
		
		Arrays.fill(selected, false);			
		
	}

	/**
	 * Repaints the GUI.
	 */
	public void repaint() {
		
		frame.repaint();
		resetSelected();
		
	}

	/**
	 * Prints the specified string to the message (JTextArea) area of the card game table.
	 * 
	 * @param msg
	 *            the string to be printed to the message area of the card game
	 *            table
	 */
	public void printMsg(String msg) {
		
	   msgArea.append(msg + "\n");
		
	}
	
	public void printChat(String msg) {
		
	  jTextArea2.append(msg + "\n");
			
	}

	/**
	 * Clears the message area (JTextArea) of the card game table.
	 */
	public void clearMsgArea() {
		
		msgArea.setText("");
		
	}

	/**
	 * Resets the GUI.
	 */
	public void reset() {
		frame.repaint();
		resetSelected();
		clearMsgArea();
		enable();
		
	}

	/**
	 * Enables user interactions.
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
		
	}

	/**
	 * Disables user interactions.
	 */
	public void disable() {
		
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
		
	}
	
	/**
	 * @author piyushjha
	 * The BigTwoPanel class inherits a JPanel to create a panel to print the cards and enable user interaction for selecting and reselecting those cards
	 * It also implements the MouseListener interface to catch the mouseClicked events by the user and perform the necessary operations
	 */
	public class BigTwoPanel extends JPanel implements MouseListener {
		
		/**
		 * Serialization
		 */
		private static final long serialVersionUID = -2679386016864585827L;
		
		/**
		 * It is the constructor for the inner class BigTwoPanel and it adds the mouseListener to the BigTwoPanel
		 */
		public BigTwoPanel()
		{	
			addMouseListener(this);
			
		}
		
		
		/**
		 * It is used to draw all the images including the avatars the cards the player names to the BigTwoPanel. It overrides the PaintComponent method of JPanel
		 *  @param g 
		 *  	This is a Graphics object which provides functions to draw images to the BigTwoPanel
		 */
		public void paintComponent(Graphics g)
		{	
			// Begin with drawing for player 0
			g.drawString(game.getPlayerList().get(0).getName(), 15, 25);
			
			g.drawImage(avatars[0], 15, 40, this);
			
			if(activePlayer == 0)
			{	
				CardList c = game.getPlayerList().get(0).getCardsInHand();
				int [] positionX1 = new int[c.size()];
				int [] positionX2 = new int[c.size()];
				
				int x0 = 100;
				//position array for all the cards with their x coordinates 
				for(int i = 0 ; i < c.size() ; i++)
				{
					positionX1[i] = x0;
					if(i < c.size() - 1)
					{
						positionX2[i] = x0+30;
					}
					else
					{
						positionX2[i] = x0+60;
					}
					x0 = x0+30;
				}
				
				int y = 40;
				// printing the cards according to their selection status
				for(int i = 0 ; i < c.size() ; i++)
				{	
					
					if(!selected[i])
					{	
						
						g.drawImage(cardImages[c.getCard(i).getSuit()][c.getCard(i).getRank()], positionX1[i], y, 60, 100, this);
					}
					else
					{	
						
						g.drawImage(cardImages[c.getCard(i).getSuit()][c.getCard(i).getRank()], positionX1[i], y-10, 60, 100 , this);
					}
				}
			}
			else
			{	
				CardList c = game.getPlayerList().get(0).getCardsInHand();
				int [] positionX1 = new int[c.size()];
				int [] positionX2 = new int[c.size()];
				int x0 = 100;
				 
				for(int i = 0 ; i < c.size() ; i++)
				{
					positionX1[i] = x0;
					if(i < c.size() - 1)
					{
						positionX2[i] = x0+30;
					}
					else
					{
						positionX2[i] = x0+60;
					}
					x0 = x0+30;
				}
				int y = 40;
				for(int i = 0; i < c.size() ; i++)
				{
					g.drawImage(cardBackImage, positionX1[i], y, 60, 100, this);
				}
			}	
			
			g.drawString(game.getPlayerList().get(1).getName(), 15, 160);
			g.drawImage(avatars[1], 15, 185, this);
			if(activePlayer == 1)
			{	
				CardList c = game.getPlayerList().get(1).getCardsInHand();
				int [] positionX1 = new int[c.size()];
				int [] positionX2 = new int[c.size()];
				
				int x0 = 100;
				 
				for(int i = 0 ; i < c.size() ; i++)
				{
					positionX1[i] = x0;
					if(i < c.size() - 1)
					{
						positionX2[i] = x0+30;
					}
					else
					{
						positionX2[i] = x0+60;
					}
					x0 = x0+30;
				}
				
				int y = 185;
				
				for(int i = 0 ; i < c.size() ; i++)
				{	
					
					if(!selected[i])
					{	
						
						g.drawImage(cardImages[c.getCard(i).getSuit()][c.getCard(i).getRank()], positionX1[i], y, 60, 100, this);
					}
					else
					{	
						
						g.drawImage(cardImages[c.getCard(i).getSuit()][c.getCard(i).getRank()], positionX1[i], y-10, 60, 100 , this);
					}
				}
			}
			else
			{	
				CardList c = game.getPlayerList().get(1).getCardsInHand();
				int [] positionX1 = new int[c.size()];
				int [] positionX2 = new int[c.size()];
				int x0 = 100;
				for(int i = 0 ; i < c.size() ; i++)
				{
					positionX1[i] = x0;
					if(i < c.size() - 1)
					{
						positionX2[i] = x0+30;
					}
					else
					{
						positionX2[i] = x0+60;
					}
					x0 = x0+30;
				}
				int y = 185;
				for(int i = 0; i < c.size() ; i++)
				{
					g.drawImage(cardBackImage, positionX1[i], y, 60, 100, this);
				}
			}	
			g.drawString(game.getPlayerList().get(2).getName(), 15, 295);
			g.drawImage(avatars[2], 15, 320, this);
			if(activePlayer == 2)
			{	
				CardList c = game.getPlayerList().get(2).getCardsInHand();
				int [] positionX1 = new int[c.size()];
				int [] positionX2 = new int[c.size()];
				
				int x0 = 100;
				 
				for(int i = 0 ; i < c.size() ; i++)
				{
					positionX1[i] = x0;
					if(i < c.size() - 1)
					{
						positionX2[i] = x0+30;
					}
					else
					{
						positionX2[i] = x0+60;
					}
					x0 = x0+30;
				}
				
				int y = 320;
				
				for(int i = 0 ; i < c.size() ; i++)
				{	
					
					if(!selected[i])
					{	
						
						g.drawImage(cardImages[c.getCard(i).getSuit()][c.getCard(i).getRank()], positionX1[i], y, 60, 100, this);
					}
					else
					{	
						
						g.drawImage(cardImages[c.getCard(i).getSuit()][c.getCard(i).getRank()], positionX1[i], y-10, 60, 100 , this);
					}
				}
			}
			else
			{	
				CardList c = game.getPlayerList().get(2).getCardsInHand();
				int [] positionX1 = new int[c.size()];
				int [] positionX2 = new int[c.size()];
				int x0 = 100;
				for(int i = 0 ; i < c.size() ; i++)
				{
					positionX1[i] = x0;
					if(i < c.size() - 1)
					{
						positionX2[i] = x0+30;
					}
					else
					{
						positionX2[i] = x0+60;
					}
					x0 = x0+30;
				}
				int y = 320;
				for(int i = 0; i < c.size() ; i++)
				{
					g.drawImage(cardBackImage, positionX1[i], y, 60, 100, this);
				}
			}	
			g.drawString(game.getPlayerList().get(3).getName(), 15, 430);
			g.drawImage(avatars[3], 15, 455, this);
			if(activePlayer == 3)
			{	
				CardList c = game.getPlayerList().get(3).getCardsInHand();
				int [] positionX1 = new int[c.size()];
				int [] positionX2 = new int[c.size()];
				
				int x0 = 100;
				 
				for(int i = 0 ; i < c.size() ; i++)
				{
					positionX1[i] = x0;
					if(i < c.size() - 1)
					{
						positionX2[i] = x0+30;
					}
					else
					{
						positionX2[i] = x0+60;
					}
					x0 = x0+30;
				}
				
				int y = 455;
				
				for(int i = 0 ; i < c.size() ; i++)
				{	
					
					if(!selected[i])
					{	
						
						g.drawImage(cardImages[c.getCard(i).getSuit()][c.getCard(i).getRank()], positionX1[i], y, 60, 100, this);
					}
					else
					{	
						
						g.drawImage(cardImages[c.getCard(i).getSuit()][c.getCard(i).getRank()], positionX1[i], y-10, 60, 100 , this);
					}
				}
			}
			else
			{	
				CardList c = game.getPlayerList().get(3).getCardsInHand();
				int [] positionX1 = new int[c.size()];
				int [] positionX2 = new int[c.size()];
				int x0 = 100;
				for(int i = 0 ; i < c.size() ; i++)
				{
					positionX1[i] = x0;
					if(i < c.size() - 1)
					{
						positionX2[i] = x0+30;
					}
					else
					{
						positionX2[i] = x0+60;
					}
					x0 = x0+30;
				}
				int y = 455;
				for(int i = 0; i < c.size() ; i++)
				{
					g.drawImage(cardBackImage, positionX1[i], y, 60, 100, this);
				}
			}	
			
			
			if(!game.getHandsOnTable().isEmpty())
			{	
				
				Hand lastHand = game.getHandsOnTable().get(game.getHandsOnTable().size() - 1);
				g.drawString("Cards Played by " + lastHand.getPlayer().getName(), 15, 570);
				int [] positionX1 = new int[lastHand.size()];
				int [] positionX2 = new int[lastHand.size()];
				int x0 = 15;
				for(int i = 0 ; i < lastHand.size() ; i++)
				{
					positionX1[i] = x0;
					if(i < lastHand.size() - 1)
					{
						positionX2[i] = x0+30;
					}
					else
					{
						positionX2[i] = x0+60;
					}
					x0 = x0+30;
				}
				
				int y = 585;
				
				for(int i = 0; i < lastHand.size() ; i++)
				{
					g.drawImage(cardImages[lastHand.getCard(i).suit][lastHand.getCard(i).rank], positionX1[i], y, 60, 100, this);
				}
			}
			else
			{
				g.drawString("No cards Played Yet", 15, 570);
			}
			
			
			
		}
		
		/**
		 * It is used to catch all the mouse click events and perform events/functions accordingly .It overrides the MouseClicked method of JPanel
		 *  @param e 
		 *  	This is a MouseEvent object which has been used to get the coordinates of the mouseClick
		 */
		public void mouseClicked(MouseEvent e) {
		
		int cy = 0;
		int size = 0;
		if(activePlayer == 0)
		{	
			CardList c = game.getPlayerList().get(0).getCardsInHand();
			size = c.size();
			cy = 40;
		}
		else if(activePlayer == 1)
		{	
			CardList c = game.getPlayerList().get(1).getCardsInHand();
			size = c.size();
			cy = 185;
		}
		else if(activePlayer == 2)
		{	
			CardList c = game.getPlayerList().get(2).getCardsInHand();
			size = c.size();
			cy = 320;
		}
		else
		{	
			CardList c = game.getPlayerList().get(3).getCardsInHand();
			size = c.size();
			cy = 455;
		}
		
		if(size != 0)
		{
			int [] positionX1 = new int[size];
			int [] positionX2 = new int[size];
			int x0 = 100;
			for(int i = 0 ; i < size ; i++)
			{
				positionX1[i] = x0;
				if(i < size - 1)
				{
					positionX2[i] = x0+30;
				}
				else
				{
					positionX2[i] = x0+60;
				}
				x0 = x0+30;
			}
			
			
			int cardnum = 0;
			for(int i = 0; i < size ; i++)
			{
				if(e.getX() >= positionX1[i] && e.getX() <= positionX2[i] && e.getY() >= cy && e.getY() <= cy+100)
				{
					cardnum = i;
					break;
				}
				else if(e.getX() >= positionX1[i]+30 && e.getX() <= positionX2[i]+30 && e.getY() >= cy-10 && e.getY() <= cy)
				{
					cardnum = i;
					break;
				}
				else if(e.getX() >= positionX1[i]+30 && e.getX() <= positionX2[i]+30 && e.getY() >= cy+90 && e.getY() <= cy+100)
				{
					cardnum = i;
					break;
				}
				
			}
			
			
				if(cardnum != 12 && size != 1)
				{	
					if(!selected[cardnum] && selected[cardnum + 1])
					{	
							if(e.getX() >= positionX1[cardnum] && e.getX() <= positionX2[cardnum] && e.getY() >= cy  && e.getY() <= cy+100 )
							{	
								selected[cardnum] = true;
								
								repaint();
							}
							else if(e.getX() >= positionX1[cardnum]+30 && e.getX() <= positionX2[cardnum]+30 && e.getY() >= cy+90  && e.getY() <= cy+100 )
							{	
								selected[cardnum] = true;
							
								repaint();
							}
							else if(e.getX() >= positionX1[cardnum+1] && e.getX() <= positionX2[cardnum+1]+30 && e.getY() >= cy-10  && e.getY() <= cy)
							{	
								
								selected[cardnum+1] = false;
								
								repaint();
							}
						
					}
					else if(selected[cardnum] && !selected[cardnum+1])
					{
						if(e.getX() >= positionX1[cardnum] && e.getX() <= positionX2[cardnum] && e.getY() >= cy-10  && e.getY() <= cy+90 )
						{	
							selected[cardnum] = false;
							
							repaint();
						}
						else if(e.getX() >= positionX1[cardnum]+30 && e.getX() <= positionX2[cardnum]+30 && e.getY() >= cy-10  && e.getY() <= cy )
						{	
							selected[cardnum] = false;
						
							repaint();
						}
						else if(e.getX() >= positionX1[cardnum+1] && e.getX() <= positionX2[cardnum+1] && e.getY() >= cy  && e.getY() <= cy+100)
						{	
							
							selected[cardnum+1] = true;
							
							repaint();
						}
					}
					else if(!selected[cardnum] && !selected[cardnum+1])
					{
						if(e.getX() >= positionX1[cardnum] && e.getX() <= positionX2[cardnum] && e.getY() >= cy  && e.getY() <= cy+100 )
						{	
							selected[cardnum] = true;
							
							repaint();
						}
						else if(e.getX() >= positionX1[cardnum+1] && e.getX() <= positionX2[cardnum+1] && e.getY() >= cy  && e.getY() <= cy+100)
						{	
							
							selected[cardnum+1] = true;
							
							repaint();
						}
					}
					else if(selected[cardnum] && selected[cardnum+1])
					{
						if(e.getX() >= positionX1[cardnum] && e.getX() <= positionX2[cardnum] && e.getY() >= cy-10  && e.getY() <= cy+90 )
						{	
							selected[cardnum] = false;
							
							repaint();
						}
						else if(e.getX() >= positionX1[cardnum+1] && e.getX() <= positionX2[cardnum+1] && e.getY() >= cy-10  && e.getY() <= cy+90)
						{	
							
							selected[cardnum+1] = false;
							
							repaint();
						}
					}
					
					
				}
				else 
				{	
					if(selected[cardnum])
					{
						if(e.getX() >= positionX1[cardnum] && e.getX() <= positionX2[cardnum] && e.getY() >= cy-10  && e.getY() <= cy+90 )
						{	
							selected[cardnum] = false;
							
							repaint();
						}
					}
					else
					{
						if(e.getX() >= positionX1[cardnum] && e.getX() <= positionX2[cardnum] && e.getY() >= cy  && e.getY() <= cy+100 )
						{	
							selected[cardnum] = true;
							
							repaint();
						}
					}
					
				}	
		}
		
			
	
				
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	
	/**
	 * @author piyushjha
	 * This inner class implements the ActionListener interface and is used to detect the clicks on the playButton and call the makeMove function based on the click.
	 */
	class PlayButtonListener implements ActionListener
	{	
		/**
		 * The constructor of this class is used to add the actionListener to the playButton
		 */
		public PlayButtonListener()
		{
			playButton.addActionListener(this);
		}
		
		/**
		 * The function is overridden from the ActionListener Interface and is used to perform the requisite function when the button is clicked.
		 *  @param e
		 *  	This is a ActionEvent object to detect if some user interaction was performed on the given object
		 */
		public void actionPerformed(ActionEvent e) {
			int [] cardplayed = getSelected();
			//check whether the player has selected any cards or not
			
			if(cardplayed != null)
			{	
				game.makeMove(activePlayer, getSelected());
			}
			else
			{	
				
				printMsg("Play without selection <==Not a legal move!!!");
			}
			
			
		}
		
		
	}
	
	/**
	 * @author piyushjha
	 *This inner class implements the ActionListener interface and is used to detect the clicks on the passButton and call the makeMove function based on the click.
	 */
	class PassButtonListener implements ActionListener
	{	
		/**
		 * The constructor of this class is used to add the actionListener to the playButton
		 */
		public PassButtonListener()
		{
			passButton.addActionListener(this);
		}
		
		/**
		 * The function is overridden from the ActionListener Interface and is used to perform the requisite function when the button is clicked.
		 *  @param e
		 *  	This is a ActionEvent object to detect if some user interaction was performed on the given object
		 */
		public void actionPerformed(ActionEvent e) {
			
			game.makeMove(activePlayer, null);
			
			
		}
		
		
	}
	
	
	
	/**
	 * @author piyushjha
	 *This inner class implements the actionListener interface for the Restart Menu Item in the JMenuBar to restart the game on click. 
	 */
	class ConnectMenuItemListener implements ActionListener
	{	
		/**
		 * The constructor adds the ActionListener to the Restart Menu Item
		 */
		public ConnectMenuItemListener()
		{	
			
			menuItem1.addActionListener(this);
		}
		
		/**
		 * The function overrides the ActionPerformed function in ActionListener interface to detect the user interaction on the object and carry out necessary functions
		 *  @param e
		 *  	This is a ActionEvent object to detect if some user interaction was performed on the given object
		 */
		public void actionPerformed(ActionEvent e) {
			
			if(game.getPlayerID() == -1) // checks if the player is connected or not 
			{
				game.makeConnection();
			}
			else
			{
				printMsg("Already Connected");
			}
			
		}
		
		
	}
	
	/**
	 * @author piyushjha
	 *This inner class implements the actionListener interface for the Quit Menu Item in the JMenuBar to restart the game on click. 
	 */
	class QuitMenuItemListener implements ActionListener
	{	
		/**
		 * The constructor adds the ActionListener to the Quit Menu Item
		 */
		public QuitMenuItemListener()
		{
			menuItem2.addActionListener(this);
		}
		
		/**
		 * The function overrides the ActionPerformed function in ActionListener interface to detect the user interaction on the object and carry out necessary functions
		 *  @param e
		 *  	This is a ActionEvent object to detect if some user interaction was performed on the given object
		 */
		public void actionPerformed(ActionEvent e) {
			
			System.exit(0);
		}
		
		
	}
	
	class TextFieldListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			game.sendMessage(new CardGameMessage(7,-1,jTextField1.getText()));
			jTextField1.setText("");
		}
		
	}
	
	
}
