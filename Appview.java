package net.com.FloodIt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/**
 * @author ltseng
 *
 */
public class Appview {

	private Window appWindow;
	private Grid appGrid;
	public JButton buttons[][];
	private Game appGame;
	private JPanel leftPanel;
	private JPanel topPanel;
	private JPanel gridPanel;
	private JFrame mainFrame;
	public JLabel TurnsLeft;
	public JLabel P1Score;
	public JLabel P2Score;
	private HashMap <String,Integer> comboBoxConverter;
	private ArrayList<Color> colorBucketList;
	/**
	 * @param args
	 */
	public void CreateAppview(){
		final int GAME_WIDTH = 1280; 
		final int GAME_HEIGHT =800; 
		this.leftPanel=new JPanel();
		this.mainFrame=new JFrame();
		this.topPanel = new JPanel();
		this.appWindow = new Window(mainFrame);
		topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.Y_AXIS));

		leftPanel.setBackground(Color.darkGray);
		leftPanel.setPreferredSize(new Dimension(640, 500));

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Start Button 
		JButton StartButton = new JButton("Start Game");

		topPanel.add(StartButton);		


		//Array of number colors
		String[] numColorList= {"Four Colors", "Five Colors","Six Colors"};
		JComboBox<String> numColorBox= new JComboBox<String>(numColorList);
		numColorBox.setSelectedIndex(0);
		numColorBox.setName("NumColor");
		topPanel.add(numColorBox);

		//Array of grid sizes
		String[] gridSizeList= {"10 x 10", "15 x 15","20 x 20"};
		JComboBox<String> gridSizeBox= new JComboBox<String>(gridSizeList);
		gridSizeBox.setSelectedIndex(0);
		gridSizeBox.setName("GridSize");
		topPanel.add(gridSizeBox);

		//Array of game modes
		String[] gameModeList= {"One Player", "Human vs. Human","Human vs. Computer"};
		JComboBox<String> gameModeBox= new JComboBox<String>(gameModeList);
		gameModeBox.setSelectedIndex(0);
		gameModeBox.setName("GameMode");
		topPanel.add(gameModeBox);        

		//Frame Stuff
		mainFrame.getContentPane().add(BorderLayout.WEST, leftPanel);
		mainFrame.getContentPane().add(BorderLayout.NORTH, topPanel);
		// Create an empty grid layout of N x N Labels
		this.gridPanel = new JPanel(new GridLayout());
		gridPanel.setPreferredSize(new Dimension(150, 150));
		mainFrame.getContentPane().add(BorderLayout.CENTER, gridPanel);
		mainFrame.setSize(GAME_WIDTH, GAME_HEIGHT);
		mainFrame.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		//Adding event handler																																																																																																																				
		StartButton.addActionListener( new StartActionListener() );

		// Pack Everything
		mainFrame.pack();
		mainFrame.setVisible(true);
		appWindow.pack();

		createMaps();
	}

	/**
	 * Sets the mapping from combobox options to integers
	 * Puts all the possible colorbucket options into an ArrayList
	 */
	public void createMaps(){
		this.comboBoxConverter=new HashMap<String,Integer>();

		//Setting Key Value Pairs
		comboBoxConverter.put("Four Colors",4 );
		comboBoxConverter.put("Five Colors",5 );
		comboBoxConverter.put("Six Colors",6 );
		comboBoxConverter.put("10 x 10",10);
		comboBoxConverter.put("15 x 15",15);
		comboBoxConverter.put("20 x 20",20);
		comboBoxConverter.put("One Player",0);
		comboBoxConverter.put("Human vs. Human",1 );
		comboBoxConverter.put("Human vs. Computer",2 );

		this.colorBucketList = new ArrayList<Color>();
		colorBucketList.add(Color.red);
		colorBucketList.add(Color.blue);
		colorBucketList.add(Color.yellow);
		colorBucketList.add(Color.green);
		colorBucketList.add(Color.cyan);
		colorBucketList.add(Color.pink);


	}

	public void CreateGame(){
		leftPanel.removeAll();

		int numberColors = 0;
		int GameMode = 0;
		int gridSize = 0;

		for (Component c:topPanel.getComponents()){
			if (c.getName()!=null){
				@SuppressWarnings("unchecked")
				JComboBox <String>currentBox = (JComboBox <String>) c;
				if (c.getName() == "NumColor"){
					String SelectedItem = (String) currentBox.getSelectedItem();
					numberColors=comboBoxConverter.get(SelectedItem);
				}
				if (c.getName() == "GameMode"){
					String SelectedItem = (String) currentBox.getSelectedItem();
					GameMode=comboBoxConverter.get(SelectedItem);
				}
				if (c.getName() == "GridSize"){
					String SelectedItem = (String) currentBox.getSelectedItem();
					gridSize=comboBoxConverter.get(SelectedItem);
				}
			}
		}

		//Create buckets
		for (int i = 0; i<numberColors; i++){
			JButton currentBucket = new JButton();
			currentBucket.setPreferredSize(new Dimension(100,50));
			currentBucket.setBackground(colorBucketList.get(i));
			currentBucket.addActionListener( new BucketActionListener() );
			leftPanel.add(currentBucket);
		}

		// Create Grid
		buttons = new JButton[gridSize][gridSize];
		createGrid(mainFrame, gridSize, (numberColors*gridSize)/2, numberColors, GameMode);

		//Single-player
		if (GameMode == 0){
			//Label for turn left 
			int turnsLeft = appGame.getTurnsLeft();
			TurnsLeft = new JLabel("# Turns left: " + turnsLeft);
			TurnsLeft.setPreferredSize(new Dimension(150, 100));
			TurnsLeft.setForeground(Color.cyan);
			leftPanel.add(TurnsLeft); 
		}
		else{
			// Need to add logic to change the display for multiplayer games
			int player1score = 0;
			int player2score = 0;
			P1Score = new JLabel("Player 1 Score: " + player1score);
			P2Score = new JLabel("Player 2 Score: " + player2score);
			P1Score.setPreferredSize(new Dimension(150, 100));
			P2Score.setPreferredSize(new Dimension(150, 100));
			P1Score.setForeground(Color.cyan);
			P2Score.setForeground(Color.cyan);
			leftPanel.add(P1Score);
			leftPanel.add(P2Score);
		}        

		mainFrame.pack();
		mainFrame.setVisible(true);
		appWindow.pack();
	}



	/**
	 * Given certain parameters, create a new grid and populate it with different-colored buttons
	 * @param frame
	 * @param size
	 * @param maxMoves
	 */
	public void createGrid(JFrame frame, int size, int maxMoves, int numColors, int GameMode) {
		List<Color> colorList = colorBucketList.subList(0, numColors);
		ArrayList<Color> colors = new ArrayList<Color>(colorList);
		Game appGame = new Game(size, colors, maxMoves, GameMode);
		this.appGame = appGame;
		this.appGrid = appGame.getGrid();

		// reset the grid display
		gridPanel.removeAll();
		gridPanel.setLayout(new GridLayout(size,size));

		// Fill the grid layout with the <Tile>s from the <Grid> object
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Tile tile = this.appGrid.getTiles()[i][j];
				Color color = tile.getColor();
				JButton tileButton = new JButton();

				// Putting the tileButton to buttons array
				buttons[i][j] = tileButton;
				tileButton.setBackground( color );
				gridPanel.add( tileButton );
			}
		}
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Resets the game grid to a new grid state.
	 */
	public void updateGrid(){
		int size = appGrid.getSize();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// Get the color from the backend
				Color color = appGrid.getTiles()[i][j].getColor();

				//Get the button from the frontend
				JButton buttonToBeChanged = buttons[i][j]; 

				// Mash 'em together            	
				buttonToBeChanged.setBackground( color );
			}
		}
	}


	public static void main(String[] args) {
		Appview appview = new Appview();
		appview.CreateAppview();
	}


	public class StartActionListener implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CreateGame();
		}		
	}


	public class BucketActionListener implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			Color c = button.getBackground();
			//Single player
			if (appGame.gamemode == 0){
				doFloodOnePlayer(c);
			}

			//Two player
			else{
				if (c != appGame.getCurrentPlayer().getOpponent().getCurrentColor()){
					doFloodTwoPlayer(c);

					if (appGame.gamemode == 1) {
						appGame.changePlayer();
					}
					else {
						appGame.changePlayer();
						//Get next ai move
						Color aiColor = appGame.getPlayerList().get(1).getAi().takeTurn(appGame);
						System.out.println(aiColor);
						Color myColor = appGame.getCurrentPlayer().getCurrentColor();
						Color theirColor = appGame.getPlayerList().get(0).getCurrentColor();
						if (aiColor == myColor || aiColor == theirColor) {
							ArrayList<Color> availableColors = new ArrayList<Color> (appGame.getGrid().getColors());
							availableColors.remove(myColor);
							availableColors.remove(theirColor);
							aiColor = availableColors.get(0);
						}
						System.out.println(aiColor);
						int aiWinState = appGame.onClickFlood(aiColor);
						updateFloodDisplay(aiColor);
						if (aiWinState == 0){
							updateScoreDisplay();
							appGame.changePlayer();
						}
						else if (aiWinState == 1) {
							JOptionPane.showMessageDialog(null,"You Won!");
						}
						else{
							JOptionPane.showMessageDialog(null, "The Computer Won...");
						}						
						updateScoreDisplay();

					}
					updateScoreDisplay();

				}
			}
		}

		/**
		 * @param c
		 */
		private void doFloodTwoPlayer(Color c) {
			int winState = appGame.onClickFlood(c);
			updateFloodDisplay(c);

			if (winState == 0){
				updateScoreDisplay();					
			}
			else if (winState == 1) {
				JOptionPane.showMessageDialog(null,"Player 1 Won!");
			}
			else{
				JOptionPane.showMessageDialog(null, "Player 2 Won!");
			}
		}

		/**
		 * @param c
		 */
		private void doFloodOnePlayer(Color c) {
			int winState = appGame.onClickFlood(c);
			updateFloodDisplay(c);

			if (winState == 0){					
				int turnsLeft = appGame.getTurnsLeft();
				TurnsLeft.setText("# Turns left: " + turnsLeft);
			}
			else if (winState == 1) {
				JOptionPane.showMessageDialog(null,"You've Won!");
			}
			else{
				JOptionPane.showMessageDialog(null, "You've Lost...");
			}
		}

		/**
		 * 
		 */
		private void updateScoreDisplay() {
			appGame.getCurrentPlayer().updateScore();
			Integer score = appGame.getCurrentPlayer().getScore();
			if (appGame.getCurrentPlayer().getName() == "Player 1") {
				P1Score.setText("Player 1: " + score.toString());
			}
			else {
				P2Score.setText("Player 2: " +score.toString());
			}
		}

		/**
		 * Update the tiles in the flood to the pressed color
		 * @param c
		 */
		private void updateFloodDisplay(Color c) {
			if (appGame.getCurrentPlayer().getName() == "Player 1") {
				for (Tile t: appGrid.getFlood()){
					JButton buttonToBeChanged = buttons[t.getxPosition()][t.getyPosition()];
					buttonToBeChanged.setBackground(c);
				}
			}
			else {
				for (Tile t2: appGrid.getFlood2()){
					JButton buttonToBeChanged = buttons[t2.getxPosition()][t2.getyPosition()];
					buttonToBeChanged.setBackground(c);
				}
			}
		}
	}
}
