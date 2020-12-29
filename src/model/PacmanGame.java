package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import view.PacmanMain;

public class PacmanGame {
	
	private static PacmanGame instance;
	private static List<Character1> characterList;
	/* map size : 28 * 31
	 * 1 : wall
	 * 0 : dot
	 * 6 : blank
	 * 8 : big dot
	 * 2 : for ghost(Pacman can't move in here).  
	 */
	
	private static int[][] tilesRepresentation = { 
	  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 
	  {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, 
	  {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1}, 
	  {1, 8, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 8, 1}, 
	  {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1}, 
	  {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, 
	  {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1}, 
	  {1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1}, 
	  {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 6, 1, 1, 6, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 6, 1, 1, 6, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 6, 1, 1, 1, 2, 2, 1, 1, 1, 6, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 6, 1, 2, 2, 2, 2, 2, 2, 1, 6, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 1, 2, 2, 2, 2, 2, 2, 1, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 6, 1, 2, 2, 2, 2, 2, 2, 1, 6, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {1, 1, 1, 1, 1, 1, 0, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 6, 1, 1, 0, 1, 1, 1, 1, 1, 1}, 
	  {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, 
	  {1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1}, 
	  {1, 8, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 8, 1}, 
	  {1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 6, 6, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1}, 
	  {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1}, 
	  {1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1}, 
	  {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1}, 
	  {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1}, 
	  {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1}, 
	  {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, 
	  {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
	
	private PacmanGame() {
		characterList = new ArrayList<Character1>();
		createCharater();
//		updateTilesRepresentation();
	}
	
	private List<JLabel> createCharater() {
		Character1 character = null;
		
		character = new Character1("Pacman");
		character.setBounds(208, 368, 25, 25);
		character.setIcon(new ImageIcon("images/pacmanL.png"));
		characterList.add(character);
		
		character = new Character1("Pinky");
		character.setBounds(208, 224, 25, 25);
		character.setIcon(new ImageIcon("images/pinky.png"));
		characterList.add(character);
		
		return null;
	}

	public static List<Character1> getCharacterList() {
		return PacmanGame.characterList;
	}

	public static int[][] getTilesRepresentation() {
		return PacmanGame.tilesRepresentation;
	}

	public static void setTilesRepresentation(int[][] tilesRepresentation) {
		PacmanGame.tilesRepresentation = tilesRepresentation;
	}
	
	public static PacmanGame getInstance() {
		if(instance == null) {
			instance = new PacmanGame();
		}
		return instance;
	}
	
	/* Update các ngã rẽ  
	 * 5 : ngã rẽ + có dot
	 * 4 : ngã rẽ thôi
	 */
	public static void updateTilesRepresentation() {
		for(int i = 1; i < tilesRepresentation.length; i++) {
			for(int j = 1; j < tilesRepresentation[0].length; j++) {
				int nLeft = 0, nRight = 0, nUp = 0, nDown = 0;
				int nRoad = 0;
				if(tilesRepresentation[i][j] == 1 || j == 0 || j == 27) continue;
				if(tilesRepresentation[i-1][j] == 0 || tilesRepresentation[i-1][j] == 6 || tilesRepresentation[i-1][j] == 8) nUp++;
				if(tilesRepresentation[i+1][j] == 0 || tilesRepresentation[i+1][j] == 6 || tilesRepresentation[i+1][j] == 8) nDown++;
				if(tilesRepresentation[i][j-1] == 0 || tilesRepresentation[i][j-1] == 6 || tilesRepresentation[i][j-1] == 8) nLeft++;
				if(tilesRepresentation[i][j+1] == 0 || tilesRepresentation[i][j+1] == 6 || tilesRepresentation[i][j+1] == 8) nRight++;
				nRoad = nLeft + nRight + nUp + nDown;
				if(nRoad >= 2) {
					if(nRoad == 2 && (nLeft == nRight || nUp == nDown)) continue;
					if(PacmanMain.itemList[i][j].getItemName().equalsIgnoreCase("Dot")) tilesRepresentation[i][j] = 5;
					tilesRepresentation[i][j] = 4; 
				}
			}	
		}
	}
}
