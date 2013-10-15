package net.com.FloodIt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

/**
 * @author sbecht
 *
 */
/**
 * @author sbecht
 *
 */
public class Grid {

	private Tile tiles[][];
	private ArrayList<Color> colors;
	private int size;
	private ArrayList<Tile> flood;
	private ArrayList<Tile> flood2;
	private Color flood2Color;

	public ArrayList<Tile> getFlood() {
		return flood;
	}

	public ArrayList<Tile> getFlood2() {
		return flood2;
	}

	public void setFlood(ArrayList<Tile> flood) {
		this.flood = flood;
	}

	public Color getFloodColor() {
		return flood2Color;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] grid) {
		this.tiles = grid;
	}

	public ArrayList<Color> getColors() {
		return colors;
	}

	public void setColors(ArrayList<Color> colors) {
		this.colors = colors;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @param size
	 * @param c
	 */
	public Grid(int size, ArrayList<Color> c) {
		this.tiles = new Tile[size][size];
		this.colors = c;
		this.size = size;
		makeGrid();
		this.flood = new ArrayList<Tile>();
		this.flood.add(tiles[0][0]);
		//only create for two player
		this.flood2 = new ArrayList<Tile>();
		Tile flood2start = tiles[size-1][size - 1];
		flood2start.setIsFlood(true);
		flood2start.setIsMine(true);
		flood2start.setIsBoundary(true);
		this.flood2.add(flood2start);

	}

	/**
	 * Creates a new grid with random colors
	 */
	public void makeGrid() {
		Random colorGen = new Random();
		for (int i=0; i < this.size; i++) {
			for (int j=0; j < this.size; j++) {
				int randomNum = colorGen.nextInt(colors.size());
				tiles[i][j] = new Tile(i,j, colors.get(randomNum));
			}
		}
	}

	/**
	 * To string method for easy debugging of grid
	 */
	public void printGrid() {
		for (int i=0; i < this.size; i++) {
			for (int j=0; j < this.size; j++) {
				System.out.println(i + " " + j + " " + tiles[i][j].getColor() + " " + tiles[i][j].getIsFlood());
			}
		}
	}

	/**
	 * @param color
	 */
	public void floodTiles(Color color,Player player) {
		//Track color of Flood2 for AI.
		if (player.getName() == "Player 2") {
			this.flood2Color = color;	
		}
		player.setCurrentColor(color);
		int counter = 0;
		ArrayList<Tile> boundary = getBoundaryTiles(player.getName());
		for (Tile b : boundary) {
			ArrayList<Tile> adjacent = getAdjacentTiles(b);
			counter = addToFlood(color, counter, adjacent, player);
		}
		this.updateBoundary(player.getName());
		updateColor(color, player.getName());
		if (counter > 0) {
			this.floodTiles(color, player);
		}
	}

	/**
	 * @param color
	 */
	private void updateColor(Color color, String pname) {
		if (pname == "Player 1"){
			for (Tile f : this.flood) {
				f.setColor(color);
			}
		}else{
			for (Tile f : this.flood2) {
				f.setColor(color);
			}
		}
	}

	/**
	 * Returns number of tiles added to the flood
	 * @param color
	 * @param counter
	 * @param adjacent
	 * @return
	 */
	private int addToFlood(Color color, int counter, ArrayList<Tile> adjacent, Player player) {
		for (Tile a : adjacent) {
			if (a.getColor() == color) {
				a.setIsFlood(true);
				if (player.getName() == "Player 1"){
					this.flood.add(a);
					player.myTiles = this.flood;
				}else {
					if (!this.flood2.contains(a)) {
						this.flood2.add(a);
						player.myTiles = this.flood2;
					}
				}
				counter++;
			}
		}
		return counter;
	}

	/**
	 * @return
	 */
	private ArrayList<Tile> getBoundaryTiles(String pname) {
		ArrayList<Tile> boundary = new ArrayList<Tile>();
		if (pname == "Player 1"){
			for (Tile t : flood) {
				if (t.getIsBoundary()) {
					boundary.add(t);
				}
			}
		}else{
			for (Tile t : flood2){
				if (t.getIsBoundary()) {
					boundary.add(t);
				}
			}
		}
		return boundary;
	}


	/**
	 * @param t
	 * @return
	 * Gets tiles adjacent to boundary that are not part of the flood
	 */
	private ArrayList<Tile> getAdjacentTiles(Tile t) {
		ArrayList<Tile> adjacent = new ArrayList<Tile>();
		int xpos = t.getxPosition();
		int ypos = t.getyPosition();
		if (xpos > 0) {
			if (!tiles[xpos - 1][ypos].getIsFlood()) {
				adjacent.add(tiles[xpos - 1][ypos]);
			}
		}
		if (xpos < (this.size-1)) {
			if (!tiles[xpos + 1][ypos].getIsFlood()) {
				adjacent.add(tiles[xpos + 1][ypos]);	
			}
		}
		if (ypos > 0) {
			if (!tiles[xpos][ypos - 1].getIsFlood()) {
				adjacent.add(tiles[xpos][ypos - 1]);	
			}
		}
		if (ypos < (this.size-1)) {
			if (!tiles[xpos][ypos + 1].getIsFlood()) {
				adjacent.add(tiles[xpos][ypos + 1]);
			}
		}
		return adjacent;
	}


	/**
	 * Updates isBoundary on all tiles in the flood 
	 */
	private void updateBoundary(String pname) {
		if (pname == "Player 1"){
			for (Tile t : this.flood) {
				ArrayList<Tile> adjacent = this.getAdjacentTiles(t);
				for (Tile a : adjacent) {
					if (!a.getIsFlood()) {
						t.setIsBoundary(true);
						break;
					}
				}
			}
		}else{
			for (Tile t : this.flood2) {
				ArrayList<Tile> adjacent = this.getAdjacentTiles(t);
				for (Tile a : adjacent) {
					if (!a.getIsFlood()) {
						t.setIsBoundary(true);
						break;
					}
				}
			}			
		}
	}

}
