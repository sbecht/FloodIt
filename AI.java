/**
 * 
 */
package net.com.FloodIt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * @author sbecht
 *
 */
public class AI {

	//bounded breadth first search
	//assumes human makes move that will get them the most tiles that turn

	private Stack<Node> stack = new Stack<Node>();
	private Color currentColor;
	private Color winningColor;
	private int currentCount;
	private int winningCount;
	private int maxDepth;
	private ArrayList<Color> gameColors;
	private HashMap<Color, Integer> counts;
	private Node rootNode;
	
	/**
	 * 
	 */
	public AI() {
		maxDepth = 4;
		
	}

	/**
	 * @param game
	 */
	@SuppressWarnings("unchecked")
	public Color takeTurn(Game game) {
		this.gameColors = game.getGrid().getColors();
		this.rootNode = new Node(gameDeepCopy(game), (ArrayList<Color>) gameColors.clone());
		stack.push(rootNode);
		winningColor = rootNode.nodeGame.getGrid().getFloodColor();
		winningCount = rootNode.nodeGame.getGrid().getFlood().size();
		//remove AI and opponent's flood color from first allowable move
		rootNode.colors.remove(game.getPlayerList().get(0).getCurrentColor());
		rootNode.colors.remove(game.getGrid().getFloodColor());
		//HashMap<Color, Integer> counts = new HashMap<Color, Integer>();
		return buildSearchStack(0);
	}

	/**
	 * @return
	 */
	private Game gameDeepCopy(Game g) {
		return new Game(g.getGrid().getSize(), g.getGrid().getColors(), g.maxMoves, g.gamemode);
	}

	/**
	 * @param currentDepth
	 */
	private Color buildSearchStack(int currentDepth) {
		
		if (stack.isEmpty()) {
			return winningColor;
			//return findMax();
			
		}
		Node currentNode = stack.peek();
		setCurrentColor(currentDepth, currentNode);
			
		if (currentDepth == maxDepth) {
			currentCount = currentNode.nodeGame.getGrid().getFlood2().size();
			//counts.put(currentColor, currentCount);
			stack.pop();
			currentDepth--;
			if (currentCount > winningCount) {
				winningCount = currentCount;
				winningColor = currentColor;
			}
		}
		else if (currentNode.colors.isEmpty()) {
			stack.pop();
			currentDepth--;
		}
		
		else {
			@SuppressWarnings("unchecked")
			Node newNode = new Node(gameDeepCopy(currentNode.nodeGame), (ArrayList<Color>) this.gameColors.clone()); 
			Color firstColor = currentNode.colors.get(0);
			int tempCount = newNode.nodeGame.getGrid().getFlood2().size();
			newNode.nodeGame.floodSimulator(firstColor);
			if (newNode.nodeGame.getGrid().getFlood2().size() != tempCount) {
				stack.push(newNode);
				currentDepth++;
			}
			currentNode.colors.remove(firstColor);
			
			
		}
		return buildSearchStack(currentDepth);
	}

	/**
	 * @param currentDepth
	 * @param currentNode
	 */
	private void setCurrentColor(int currentDepth, Node currentNode) {
		if (currentDepth == 1) {
			currentColor = currentNode.nodeGame.getGrid().getFloodColor();
		}
	}
	
	/**
	 * Test method for improving AI, not being used currently
	 * @return
	 */
	/*private Color findMax() {
		Entry<Color, Integer> x = counts.entrySet().iterator().next();
		int tempMax = x.getValue();
		Color tempColor = x.getKey();
		
		for (Entry<Color, Integer> entry : counts.entrySet())
			if (entry.getValue() > tempMax && entry.getKey() !=	rootNode.nodeGame.getPlayerList().get(0).getCurrentColor()) {
				tempMax = entry.getValue();
				tempColor = entry.getKey();
			}
		return tempColor;
	}*/
}

class Node {
	public Game nodeGame;
	public ArrayList<Color> colors;

	/**
	 * @param game
	 */
	public Node(Game game, ArrayList<Color> gameColors) {
		nodeGame = game;
		colors = gameColors;
	}

}


