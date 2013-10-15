package net.com.FloodIt;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Player {
	private Player opponent;
    private String name;
	private AI ai;
	private int score;
    public ArrayList<Tile> myTiles;
    private Color currentColor;
	
    public Player(String pname) {
    	this.setName(pname);
    	int score = 0;
    	this.myTiles = new ArrayList<Tile>();
    }
    
    public AI getAi() {
		return ai;
	}

	public void setAi(AI ai) {
		this.ai = ai;
	}
    
    public void updateScore(){
    	this.score = myTiles.size();
    }
    
	public Player getOpponent() {
		return opponent;
	}
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	public int getScore() {
		return score;
	}

	public void addScore(int newpoints) {
		this.score += newpoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
	}

}
