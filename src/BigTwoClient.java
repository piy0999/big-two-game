import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BigTwoClient implements CardGame, NetworkGame {
	
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx;
	private BigTwoTable table;
	private ArrayList<Card> cards;
	private Thread readerThread; 

	
	public BigTwoClient()
	{
		handsOnTable = new ArrayList<Hand>();
		playerList = new ArrayList<CardGamePlayer>();
		CardGamePlayer p1 = new CardGamePlayer();
		playerList.add(p1);
		CardGamePlayer p2 = new CardGamePlayer();
		playerList.add(p2);
		CardGamePlayer p3 = new CardGamePlayer();
		playerList.add(p3);
		CardGamePlayer p4 = new CardGamePlayer();
		playerList.add(p4);
		cards = new ArrayList<Card>();
		this.table = new BigTwoTable(this);
		this.numOfPlayers = 4;
		String player = JOptionPane.showInputDialog("Please Enter Your Name");
		setPlayerName(player);
		System.out.println(playerName);
		makeConnection();
	}

	@Override
	public int getPlayerID() {
		return this.playerID;
	}

	@Override
	public void setPlayerID(int playerID) {
		// TODO Auto-generated method stub
		this.playerID = playerID;
		
	}

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return this.playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		// TODO Auto-generated method stub
		this.playerName = playerName;
	}

	@Override
	public String getServerIP() {
		// TODO Auto-generated method stub
		return this.serverIP;
	}

	@Override
	public void setServerIP(String serverIP) {
		// TODO Auto-generated method stub
		this.serverIP = serverIP;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return this.serverPort;
	}

	@Override
	public void setServerPort(int serverPort) {
		// TODO Auto-generated method stub
		this.serverPort = serverPort;
	}

	@Override
	public void makeConnection() {
		// TODO Auto-generated method stub
		try {
			setServerIP("127.0.0.1");
			setServerPort(2396);
			sock = new Socket(getServerIP(), getServerPort());
			oos = new ObjectOutputStream(sock.getOutputStream());
			readerThread = new Thread(new ServerHandler());
			readerThread.start();
			CardGameMessage joinObject = new CardGameMessage(1,-1,getPlayerName());
			sendMessage(joinObject);
			CardGameMessage readyObject = new CardGameMessage(4,-1,null);
			sendMessage(readyObject);
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public synchronized void parseMessage(GameMessage message) {
		// TODO Auto-generated method stub
		
		if(message.getType() == 0)
		{	
			setPlayerID(message.getPlayerID());
			table.setActivePlayer(message.getPlayerID());
			if(message.getData() != null)
			{
				String [] names = (String [])message.getData();
				for(int i = 0 ; i < names.length ; i++)
				{
					playerList.get(i).setName(names[i]);
				}
			}
		}
		else if(message.getType() == 1)
		{	
		
			playerList.get(message.getPlayerID()).setName((String)message.getData());
			
			
		}
		else if(message.getType() == 2)
		{
			table.printMsg("SERVER IS FULL");
		}
		else if(message.getType() == 3)
		{
			playerList.get(message.getPlayerID()).setName("");
			
			if(!endOfGame())
			{
				table.disable();
				sendMessage(new CardGameMessage(4,-1,null));
			}
		}
		else if(message.getType() == 4)
		{	
			int ID = message.getPlayerID();
			String msg = "Player with ID " + ID + " is ready";
			table.printMsg(msg);
			
		}
		else if(message.getType() == 5)
		{
			this.deck = (BigTwoDeck)message.getData();
			start((BigTwoDeck)message.getData());
			table.reset();
			table.printMsg("The Game Begins");
		}
		else if(message.getType() == 6)
		{	
			if(currentIdx == message.getPlayerID())
			{
				checkMove(message.getPlayerID(), (int [])message.getData());
			}
			else
			{
				table.printMsg("NOT LEGAL PLAYER");
				table.resetSelected();
				table.repaint();
			}
			
		}
		else if(message.getType() == 7)
		{
			table.printChat((String)message.getData());
		}
		
	}

	@Override
	public void sendMessage(GameMessage message) {
		
		try {
			// sends the message to the server
			oos.writeObject(message);
			
			} catch (Exception ex) {
			ex.printStackTrace();
			}
		
	}
	
	class ServerHandler implements Runnable{
		
		ObjectInputStream ois;
		@Override
		public void run() {
		try {
			ois = new ObjectInputStream(sock.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			try {
			// reads incoming messages from the server
				while(true)
				{
					try{
						parseMessage((CardGameMessage)ois.readObject());
						
					}catch(EOFException ex1){
						break;
					}
					
				} 
			
			} 
			catch (Exception ex) {
			ex.printStackTrace();
			}
			
		}
		
		
		
	}

	@Override
	public int getNumOfPlayers() {
		// TODO Auto-generated method stub
		return this.numOfPlayers;
	}

	@Override
	public Deck getDeck() {
		
		return this.deck;
	}

	@Override
	public ArrayList<CardGamePlayer> getPlayerList() {
		
		return this.playerList;
	}

	@Override
	public ArrayList<Hand> getHandsOnTable() {
		// TODO Auto-generated method stub
		return this.handsOnTable;
	}

	@Override
	public int getCurrentIdx() {
		// TODO Auto-generated method stub
		return this.currentIdx;
	}

	@Override
	public void start(Deck deck) {
		//remove all cards from the players
		
		for(int i = 0; i < playerList.size() ; i++)
		{
			playerList.get(i).removeAllCards();
		}
		//remove all cards on table
		handsOnTable.clear();
		//assigns cards to players
		CardGamePlayer p1 = playerList.get(0); 
		for(int j = 0; j < 13 ; j++)
		{
			p1.addCard(deck.getCard(j));
		}
		p1.sortCardsInHand();
		CardGamePlayer p2 = playerList.get(1);
		for(int j = 13; j < 26 ; j++)
		{
			p2.addCard(deck.getCard(j));
		}
		p2.sortCardsInHand();
		CardGamePlayer p3 = playerList.get(2);
		for(int j = 26; j < 39 ; j++)
		{
			p3.addCard(deck.getCard(j));
		}
		p3.sortCardsInHand();
		CardGamePlayer p4 = playerList.get(3);
		for(int j = 39; j < 52 ; j++)
		{
			p4.addCard(deck.getCard(j));
		}
		p4.sortCardsInHand();
		Card tod = new Card(0,2); //three of diamonds
		// set the current index to the player who has three of diamonds
		if(p1.getCardsInHand().contains(tod))
		{
			currentIdx = 0;
		}
		else if(p2.getCardsInHand().contains(tod))
		{
			currentIdx = 1;
			
		}
		else if(p3.getCardsInHand().contains(tod))
		{
			currentIdx = 2;
		
		}
		else
		{
			currentIdx = 3;
			
		}
		
		table.repaint();	
		
		
		
		
	}

	@Override
	public void makeMove(int playerID, int[] cardIdx) {
		
		CardGameMessage moveObject = new CardGameMessage(6,-1,cardIdx);
		sendMessage(moveObject);
		
		
	}

	@Override
	public void checkMove(int playerID, int[] cardIdx) {
		// TODO Auto-generated method stub
		
		table.printMsg("Player " + playerList.get(playerID).getName() + "'s turn:");
		
		int playerAtpass = -1; //checking whether to pass or not
		 
		Card tod = new Card(0,2);
		
		
		table.repaint();
		
		
		CardGamePlayer firstPlayer = playerList.get(playerID);
		if(handsOnTable.size() == 0)
		{
			playerAtpass = -1;
		}
		else
		{
			Hand passHand = handsOnTable.get(handsOnTable.size()-1);
			String player = passHand.getPlayer().getName();
			
			if(player.equals(playerList.get(0).getName()))
			{
				playerAtpass = 0;
			}
			else if(player.equals(playerList.get(1).getName()))
			{
				playerAtpass = 1;
			}
			else if(player.equals(playerList.get(2).getName()))
			{
				playerAtpass = 2;
			}
			else
			{
				playerAtpass = 3;
			}
			
		}
		
		// !buttonCheck means pass is clicked
		// checking if the user has entered any value or not / if the value entered is out of the range of possible values
		if(cardIdx == null)
		{	
			//implements printing of pass or not
			
			if(currentIdx != playerAtpass && !deck.contains(tod))
			{	
				
				String msg = "{pass}";
				table.printMsg(msg);
				currentIdx = (currentIdx + 1)%4;	
					
			}
			else
			{	

				String msg1 = "{pass}";
				String msg = "<==Not a legal move!!!";
				String fin = msg1 + msg;
				table.printMsg(fin);
									
			}
			
		}
		else
		{	
			
			CardList cardsofp1 = firstPlayer.play(cardIdx);
			
			Hand h1 = BigTwoClient.composeHand(firstPlayer,cardsofp1);
			//implements the logic for the first player by checking if the deck contains three of diamonds or not
			if(deck.contains(tod))
			{
				if(h1 != null && cardsofp1.contains(tod))
				{	
					
					handsOnTable.add(h1);
					String msg = "{" + h1.getType() + "} ";
					
					for(int i = 0 ; i < h1.size() ; i++)
					{
						cards.add(h1.getCard(i));
					}
					String cd = cardsPrint();
					String printed = msg + cd;
					table.printMsg(printed);
					cards.clear();
					
					firstPlayer.removeCards(cardsofp1);
					for(int i = 0; i < cardsofp1.size() ; i++)
					{	
						Card rc = cardsofp1.getCard(i);
						deck.removeCard(rc);
					}
					
					currentIdx = (currentIdx + 1)%4;
					
					
				}
				else
				{	
					if(h1 != null)
					{
						String msg1 = "{" + h1.getType() + "} ";
						
						for(int i = 0 ; i < h1.size() ; i++)
						{
							cards.add(h1.getCard(i));
						}
						String cd = cardsPrint();
						String printed = msg1 + cd;
						String msg = "<==Not a legal move!!!";
						String fin = printed + msg;
						table.printMsg(fin);
						cards.clear();
					}
					else
					{
						for(int i = 0 ; i < cardsofp1.size() ; i++)
						{
							cards.add(cardsofp1.getCard(i));
						}
						String cd = cardsPrint();
						String msg = "<==Not a legal move!!!";
						String fin = cd + msg;
						table.printMsg(fin);
						cards.clear();
					}
					
						
				}
			}
			else
			{	//for all other players except first player if not passed
				boolean beat = true;
				if(handsOnTable.size() > 0 && currentIdx != playerAtpass && h1!=null) 
				{	
					
					beat = h1.beats(handsOnTable.get(handsOnTable.size()-1)); //checking if the given hands beats the previous hand or not.
				}
				
				if(h1 != null && beat)
				{	
					//adding the hand if beaten and legal
					handsOnTable.add(h1);
					String msg = "{" + h1.getType() + "} ";
					for(int i = 0 ; i < h1.size() ; i++)
					{
						cards.add(h1.getCard(i));
					}
					String cd = cardsPrint();
					String printed = msg + cd;
					table.printMsg(printed);
					cards.clear();
					
					firstPlayer.removeCards(cardsofp1);
					for(int i = 0; i < cardsofp1.size() ; i++)
					{	
						Card rc = cardsofp1.getCard(i);
						deck.removeCard(rc);
					}
					
					currentIdx = (currentIdx + 1)%4;
					
					
				}
				else
				{	
					if(h1 != null)
					{
						String msg1 = "{" + h1.getType() + "} ";
						
						for(int i = 0 ; i < h1.size() ; i++)
						{
							cards.add(h1.getCard(i));
						}
						String cd = cardsPrint();
						String printed = msg1 + cd;
						String msg = "<==Not a legal move!!!";
						String fin = printed + msg;
						table.printMsg(fin);
						cards.clear();
					}
					else
					{
						for(int i = 0 ; i < cardsofp1.size() ; i++)
						{
							cards.add(cardsofp1.getCard(i));
						}
						String cd = cardsPrint();
						String msg = "<==Not a legal move!!!";
						String fin = cd + msg;
						table.printMsg(fin);
						cards.clear();
					}
					
					
						
				}
			}
			
			
			
		}
		
		//table.setActivePlayer(currentIdx);
		
		if(endOfGame())
		{
			table.setActivePlayer(-1);
			table.repaint();
			
			String message = "Game Ends \n  " ;
			
			for(int i = 0 ; i < playerList.size() ; i++)
			{
				if(getPlayerList().get(i).getNumOfCards() == 0)
				{
					message += getPlayerList().get(i).getName() + " wins the game. \n";
				}
				else
				{
					message += getPlayerList().get(i).getName() + " has " + getPlayerList().get(i).getNumOfCards() + " cards in hand. \n";
				}
			}
			
			int option = JOptionPane.showOptionDialog(null, message, null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if(option == JOptionPane.OK_OPTION)
			{
				sendMessage(new CardGameMessage(4,-1,null));
			}
			
			table.disable();
		}
	}

	@Override
	public boolean endOfGame() {
		if(!playerList.get(0).getCardsInHand().isEmpty() && !playerList.get(1).getCardsInHand().isEmpty() && !playerList.get(2).getCardsInHand().isEmpty() && !playerList.get(3).getCardsInHand().isEmpty())
		{
			return false; // if the game is not over yet as none of the players have 0 cards
		}
		
		return true;
	}
	
	/**
	 * This method implements the printing of the cards icon on the JTextArea 
	 * @return
	 * 	Returns a string with all the card icons
	 */
	public String cardsPrint() {
		
		String fin = "";
		if (cards.size() > 0) {
			for (int i = 0; i < cards.size(); i++) 
			{
				String string = "";
				
				string = string + "[" + cards.get(i) + "]";
				
				if (i % 13 != 0) 
				{
					string = " " + string;
				}
				
				fin = fin + string;
			}
			
			return fin;
		} 
		
		return fin;
		
	}
	
	/**
	 * This method composes a hand for the given player with the set of cards selected by that player
	 * @param player
	 * CardGamePlayer object containing the given player.
	 * @param cards
	 * The list of cards selected by the given player for composing the hand
	 * @return
	 * A hand object for the valid hand that can be composed out of the given cards. 
	 * Null if no valid hand can be composed
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards)
	{	
		// checking according to the order of winning for the hands
		Hand s = new Single(player,cards);
		Hand p = new Pair(player,cards);
		Hand t = new Triple(player,cards);
		Hand sf = new StraightFlush(player,cards);
		Hand q = new Quad(player,cards);
		Hand fh = new FullHouse(player,cards);
		Hand f = new Flush(player,cards);
		Hand st = new Straight(player,cards);
		
		if(s.isValid())
		{
			return s;
		}
		else if(p.isValid())
		{
			return p;
		}
		else if(t.isValid())
		{
			return t;
		}
		else if(sf.isValid())
		{
			return sf;
		}
		else if(q.isValid())
		{
			return q;
		}
		else if(fh.isValid())
		{
			return fh;
		}
		else if(f.isValid())
		{
			return f;
		}
		else if(st.isValid())
		{
			return st;
		}
		else
		{
			return null;
		}
		
		
		
	}
	
	/**
	 * Main method for starting the game by calling the bigTwo class object, creating a BigTwo deck, shuffling the cards and calling the start method using those cards.
	 * @param args
	 * It is a command line argument
	 */
	public static void main(String[] args)
	{
		new BigTwoClient();
	}

	

}
