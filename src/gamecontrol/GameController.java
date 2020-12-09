package gamecontrol;

import java.util.List;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JPanel;
import model.Character1;
import model.Key;
import model.PacmanGame;
import model.Vector1;

public class GameController {
	
	public List<Character1> characterList;
	private Key key;
	private JButton pacman;
	private JButton pinky;
	private PacmanGame pacmanGame;
	private Timer timer;
	
	public GameController(JPanel panel) {
		pacmanGame = PacmanGame.getInstance();
		characterList = PacmanGame.getCharacterList();
		pacman = characterList.get(0);
		key = new Key(pacman, PacmanGame.getTilesRepresentation());
		pacman.addKeyListener(key);
		pinky = characterList.get(1);
		((Character1) pinky).setAlgo(new Algorithm("Pinky"));
	}
	
	public void movePinky() {
		if(pinky.getX() != pacman.getX() || pinky.getY() != pacman.getY()) {
			Vector1 ghostLoc = new Vector1(pinky.getX()/16, pinky.getY()/16);
			Vector1 pacmanLoc = new Vector1(pacman.getX()/16, pacman.getY()/16);
			List<Vector1> pathToDes = ((Character1) pinky).getAlgo().AStar(ghostLoc, pacmanLoc);
			System.out.println("--------------------");
			for(int i = 0; i < pathToDes.size(); i++) {
				System.out.println(pathToDes.get(i).getX() + " : "  + pathToDes.get(i).getY());
			}
			System.out.println("--------------------");
		}
	}
}
