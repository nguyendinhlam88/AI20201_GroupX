package gamecontrol;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import model.Character1;
import model.Key;
import model.PacmanGame;

public class GameController {
	
	public List<Character1> characterList;
	private Key key;
	private JButton pacman;
	private PacmanGame pacmanGame;
	private int[][] tilesRepresentation;
	
	public GameController(JPanel panel) {
		pacmanGame = PacmanGame.getInstance();
		characterList = pacmanGame.getCharacterList();
		pacman = characterList.get(0);
		this.tilesRepresentation = PacmanGame.getTilesRepresentation();
		key = new Key(pacman, tilesRepresentation);
		pacman.addKeyListener(key);
	}
}
