package net.com.FloodIt;

import java.awt.Color;

public class Tile {
	
	private Color color;
	final private int xPosition;
	final private int yPosition;
	private Boolean isFlood;
	private Boolean isMine;
	private Boolean isBoundary;
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Boolean getIsFlood() {
		return isFlood;
	}
	public void setIsFlood(Boolean isFlood) {
		this.isFlood = isFlood;
	}
	public Boolean getIsMine() {
		return isMine;
	}
	public void setIsMine(Boolean isMine) {
		this.isMine = isMine;
	}
	public int getxPosition() {
		return xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public Boolean getIsBoundary() {
		return isBoundary;
	}
	public void setIsBoundary(Boolean isBoundary) {
		this.isBoundary = isBoundary;
	}
	
	/**
	 * @param xpos
	 * @param ypos
	 * @param color
	 */
	public Tile(int xpos, int ypos, Color color) {
		this.xPosition = xpos;
		this.yPosition = ypos;
		this.color = color;
		//If tile is top left corner
		if (this.xPosition == 0 & this.yPosition == 0) {
			this.isFlood = true;
			this.isMine = true;
			this.isBoundary = true;
		}
		
		//The bottom right corner case is not dealt with
		//DEAL WITH IT!!!!!!
		
		else {
			this.isFlood = false;
			this.isMine = false;
			this.isBoundary = false;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String tile = "Location: " + this.getxPosition() + "," + this.getyPosition() + " Color: " + this.color;
		return tile;
		
	}
	
	

}
