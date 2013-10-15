package net.com.FloodIt;

import java.awt.Color;
import java.util.ArrayList;

public class Game implements Cloneable {

	private Grid grid;
	private int turn;
	public int maxMoves;
	private ArrayList<Player> PlayerList;
	public int gamemode;
	private Player currentPlayer;

	public ArrayList<Player> getPlayerList() {
		return PlayerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		PlayerList = playerList;
	}

	/**
	 * @return the currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}


	/**
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


	/**
	 * Creates the Player List and populates the scores
	 * @param GameMode
	 * @param size
	 */
	public void createPlayers(int size){

		this.PlayerList = new ArrayList<Player>();

		Player player1 = new Player("Player 1");
		player1.setCurrentColor(grid.getTiles()[size -1][size-1].getColor());
		player1.myTiles.add(grid.getTiles()[0][0]);
		PlayerList.add(player1);

		Player player2 = null;
		// Create Players
		switch (this.gamemode){
		case 0:
			PlayerList.get(0).setOpponent(null);
			break;
		case 1:
			player2 = new Player("Player 2");
			player2.setOpponent(PlayerList.get(0));
			PlayerList.get(0).setOpponent(player2);
			break;
		case 2:
			player2 = new Player("Player 2");
			player2.setOpponent(PlayerList.get(0));
			player2.setAi(new AI());
			PlayerList.get(0).setOpponent(player2);
			break;
		}

		if (player2 != null){
			player2.setCurrentColor(grid.getTiles()[size -1][size-1].getColor());
			player2.myTiles.add(grid.getTiles()[size-1][size-1]);
			PlayerList.add(player2);
		}

	}


	/**
	 * Single Player Mode - true=win, null=do nothing, false=lose
	 * Multi Player Mode - 0 = no one's won, 1 = Player 1 won, 2 = Player 2 won
	 * @return
	 */
	public int checkWinState() {
		if (this.gamemode == 0){
			if ((this.grid.getFlood().size()) >= Math.pow(this.grid.getSize(),2)) {
				return 1;
			}
			else if (this.turn == this.maxMoves) {
				return -1;
			}
			else return 0;
		}else{
			if (this.grid.getFlood().size() + this.grid.getFlood2().size() 
					>= Math.pow(this.grid.getSize(), 2)){
				if (this.grid.getFlood().size()>this.grid.getFlood2().size()){
					return 1;
				}else{
					return 2;
				}
			}else{
				return 0;
			}
		}
	}

	public int getTurnsLeft(){
		int numbturnsleft = maxMoves - turn;
		return numbturnsleft;
	}	

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * @param size
	 * @param colors
	 * @param maxmoves
	 */
	public Game(int size, ArrayList<Color> colors, int maxmoves, int GameMode) {
		startState(size, colors, maxmoves, GameMode);
		createPlayers(size);
		this.currentPlayer = this.PlayerList.get(0);
		//Floods the same color adjacent to initial flood tile
		for (Player p : this.PlayerList) {
			this.getGrid().floodTiles(p.myTiles.get(0).getColor(), p);
		}
	}

	/**
	 * @param size
	 * @param colors
	 * @param maxmoves
	 * @param GameMode
	 */
	public void startState(int size, ArrayList<Color> colors, int maxmoves, int GameMode) {
		this.grid = new Grid(size, colors);
		this.gamemode = GameMode;
		this.maxMoves = maxmoves;
		this.turn = 0;
		this.grid.makeGrid();
	}

	/**
	 * On click, floodTiles is called, turns is incremented
	 * Checks win
	 * @param color
	 */
	public int onClickFlood(Color color) {
		this.grid.floodTiles(color, this.currentPlayer);
		this.turn++;
		int winState = this.checkWinState();
		/*
		if (winState != 0) {
			//Reset board with new colors
			startState(this.grid.getSize(), this.grid.getColors(), this.maxMoves,this.gamemode);
		}
*/
		//System.out.println(this.currentPlayer);
		return winState;
	}

	public void changePlayer(){
		//System.out.println(PlayerList.size());
		if (this.gamemode != 0){
			if (this.currentPlayer.getName() == "Player 1"){
				this.currentPlayer = this.PlayerList.get(1);
			}else{
				this.currentPlayer = this.PlayerList.get(0);
			}
		}
	}

	/**
	 * Simulates a flood for the AI
	 * @param color
	 */
	public void floodSimulator(Color color) {
		this.grid.floodTiles(color, this.PlayerList.get(1));
	}

}
