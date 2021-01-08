package gamecontrol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Character1;
import model.Key;
import model.PacmanGame;
import model.Vector1;
import view.PacmanMain;

import java.util.Random;

public class GameController {
	
	public List<Character1> characterList;
	private Key key;
	private JButton pacman;
	private JButton pinky;
	private PacmanGame pacmanGame;
	private Timer timer;
	// ----------AStar---------------
	private List<Vector1> pathToDes;
	private int index;
	private List<Vector1> temp;
	private Vector1 ghostLoc;
	private Vector1 pacmanLoc;
	private static double timeBuff;
	private int perFrame;
	private int incre = 0;
	// ----------AStar---------------
	
	public GameController(JPanel panel) {
		perFrame = 180;
		this.pacmanGame = PacmanGame.getInstance();
		characterList = PacmanGame.getCharacterList();
		pacman = characterList.get(0);
		key = new Key(pacman, PacmanGame.getTilesRepresentation());
		pinky = characterList.get(1);
		((Character1) pinky).setAlgo(new Algorithm("Pinky"));
		pacman.addKeyListener(key);
		pinky.addKeyListener(key);
		run();
	}
	
	// +++++++++++++++++++++++++++++++++++++++++++++++Astar+++++++++++++++++++++++++++++++++++++++++++++++
	public void movePinky(Vector1 pacLoc) {
//		System.out.println("Siba");
		index = 0;
		pathToDes = new ArrayList<Vector1>();
		pacmanLoc = new Vector1(pacLoc.getX()/16, pacLoc.getY()/16);
		int ranNum;
		
//		timeBuff = 0;
		if(timeBuff > 0) {
			Random random = new Random();
			
			while(true) {
				ranNum = random.nextInt(5);
//				System.out.println("++++++Random+++++++" + ranNum);
				if(ranNum == 0 && PacmanGame.getTilesRepresentation()[pinky.getY()/16 - 1][pinky.getX()/16] != 1 && PacmanGame.getTilesRepresentation()[pinky.getY()/16 - 1][pinky.getX()/16] != 2) {
					pathToDes.add(new Vector1(pinky.getX()/16, pinky.getY()/16 - 1));
					break;
				}
				if(ranNum == 1 && PacmanGame.getTilesRepresentation()[pinky.getY()/16 + 1][pinky.getX()/16] != 1 && PacmanGame.getTilesRepresentation()[pinky.getY()/16 + 1][pinky.getX()/16] != 2) {
					pathToDes.add(new Vector1(pinky.getX()/16, pinky.getY()/16 + 1));
					break;
				}
				if(ranNum == 2 && PacmanGame.getTilesRepresentation()[pinky.getY()/16][pinky.getX()/16 - 1] != 1 && PacmanGame.getTilesRepresentation()[pinky.getY()/16][pinky.getX()/16 - 1] != 2) {
					pathToDes.add(new Vector1(pinky.getX()/16 - 1, pinky.getY()/16));
					break;
				}
				if(ranNum == 3 && PacmanGame.getTilesRepresentation()[pinky.getY()/16][pinky.getX()/16 + 1] != 1 && PacmanGame.getTilesRepresentation()[pinky.getY()/16][pinky.getX()/16 + 1] != 2) {
					pathToDes.add(new Vector1(pinky.getX()/16 + 1, pinky.getY()/16));
					break;
				}
//				System.out.println("++++++Random+++++++" + ranNum);
			}
						
			if(pinky.getX()/16 == 13 && pinky.getY()/16 == 14) {
				pathToDes.add(0, new Vector1(13, 14));
				pathToDes.add(1, new Vector1(13, 13));
				pathToDes.add(2, new Vector1(13, 12));
				pathToDes.add(3, new Vector1(13, 11));
			}
			
		} else {
			if(PacmanGame.getTilesRepresentation()[pinky.getX()/16][pinky.getX()/16] != 2) {
				// Nếu đường thông suốt thì đi thẳng. 
				if(pinky.getX() == pacLoc.getX() && pinky.getY() < pacLoc.getY()) {
					for(int i = pinky.getY()/16; i <= pacLoc.getY()/16; i++) {
						if(PacmanGame.getTilesRepresentation()[i][pinky.getX()/16] != 1 || PacmanGame.getTilesRepresentation()[i][pinky.getX()/16] != 2) {
							pathToDes.add(new Vector1(pinky.getX()/16, i));
							continue;
						}
						break;
					}
					if(!pathToDes.isEmpty() && pathToDes.size() == (pacLoc.getY()/16 - pinky.getY()/16 + 1)) return;
				} else if(pinky.getX() == pacLoc.getX() && pinky.getY() > pacLoc.getY()) {
					for(int i = pinky.getY()/16; i >= pacLoc.getY()/16; i--) {
						if(PacmanGame.getTilesRepresentation()[i][pinky.getX()/16] != 1 || PacmanGame.getTilesRepresentation()[i][pinky.getX()/16] != 2) {
							pathToDes.add(new Vector1(pinky.getX()/16, i));
							continue;
						}
						break;
					}
					if(!pathToDes.isEmpty() && pathToDes.size() == (pinky.getY()/16 - pacLoc.getY()/16 + 1)) return;
				} else if(pinky.getY() == pacLoc.getY() && pinky.getX() < pacLoc.getX()) {
					for(int i = pacLoc.getX()/16; i <= pinky.getX()/16; i++) {
						if(PacmanGame.getTilesRepresentation()[pinky.getY()/16][i] != 1 || PacmanGame.getTilesRepresentation()[pinky.getY()/16][i] != 2) {
							pathToDes.add(new Vector1(i, pinky.getY()/16));
							continue;
						}
						break;
					}
					if(!pathToDes.isEmpty() && pathToDes.size() == (pinky.getX()/16 - pacLoc.getX()/16 + 1)) return;
				} else if(pinky.getY() == pacLoc.getY() && pinky.getX() > pacLoc.getX()) {
					for(int i = pinky.getX()/16; i >= pacLoc.getX()/16; i--) {
						if(PacmanGame.getTilesRepresentation()[pinky.getY()/16][i] != 1 || PacmanGame.getTilesRepresentation()[pinky.getY()/16][i] != 2) {
							pathToDes.add(new Vector1(i, pinky.getY()/16));
							continue;
						}
						break;
					}
					if(!pathToDes.isEmpty() && pathToDes.size() == (pinky.getX()/16 - pacLoc.getX()/16 + 1)) return;
				}
			}
			
			pathToDes.clear();
//			System.out.println("*********************");
			if(pinky.getX()/16 == 13 && pinky.getY()/16 == 14) {
				ghostLoc = new Vector1(13, 11);
			} else {
				ghostLoc = new Vector1(pinky.getX()/16, pinky.getY()/16);
			}
			long startTime = System.currentTimeMillis();
			temp = ((Character1) pinky).getAlgo().AStar(ghostLoc, pacmanLoc);
			long endTime = System.currentTimeMillis();
			System.out.println("Số nút phải duyệt Astar : temp.size());
			System.out.println("Thời gian duyệt : " + (endTime - startTime));
			System.out.println("Số điểm hoa quả gặp phải nếu đi theo đường đấy : " +  );
			
			if(pinky.getX()/16 == 13 && pinky.getY()/16 == 14) {
				temp.add(0, new Vector1(13, 11));
			} else {
				temp.add(0, new Vector1(pinky.getX()/16, pinky.getY()/16));
			}
			temp.add(pacmanLoc);
			
			pathToDes.add(temp.get(0));
			
//			for(int i = 0; i < temp.size(); i++) {
//				System.out.println("######" + temp.get(i).getX() + " : " + temp.get(i).getY() + "######");
//			}
			
			for(int i = 0; i < temp.size()-1; i++) {
				createPathToDes(temp.get(i), temp.get(i+1));
				if(Key.isPress() == true) return;
			}
			
			if(pinky.getX()/16 == 13 && pinky.getY()/16 == 14) {
//				pathToDes.add(0, new Vector1(13, 14));
				pathToDes.add(1, new Vector1(13, 13));
				pathToDes.add(2, new Vector1(13, 12));
			}	
//			System.out.println("*********************");
			
//			for (int i = 0; i < pathToDes.size(); i++) {
//				System.out.println("+++++" + pathToDes.get(i).getX() + " : " + pathToDes.get(i).getY() + "+++++++");
//				System.out.println("+++++Ghost" + pinky.getX()/16 + " : " + pinky.getY()/16 + "+++++++");
//			}

		}
	}
	
	private void createPathToDes(Vector1 start, Vector1 des) {
		Vector1 curr = new Vector1(start.getX(), start.getY());
		if(start.getX() == des.getX()) {
			while(curr.getY() != des.getY()) {
				if(curr.getY() > des.getY()) {
					pathToDes.add(new Vector1(curr.getX(), curr.getY() - 1));
					curr.setY(curr.getY() - 1);
				} else {
					pathToDes.add(new Vector1(curr.getX(), curr.getY() + 1));
					curr.setY(curr.getY() + 1);
				}
				if(Key.isPress() == true) return;
			}
		} else if(curr.getY() == des.getY()) {
			while(curr.getX() != des.getX()) {
				if(curr.getX() > des.getX()) {
					if(curr.getY() == 14 && des.getX() < 7 && curr.getX() > 21) {
						if(curr.getX() == 27) {
							pathToDes.add(new Vector1(0, curr.getY()));
							curr.setX(0);
						} else {
							pathToDes.add(new Vector1(curr.getX() + 1, curr.getY()));
							curr.setX(curr.getX() + 1);
						}
					} else {
						pathToDes.add(new Vector1(curr.getX()-1, curr.getY()));
						curr.setX(curr.getX() - 1);
					}
				} else {
					if(curr.getY() == 14 && curr.getX() < 7 && des.getX() > 21) {
						if(curr.getX() == 0) {
							pathToDes.add(new Vector1(27, curr.getY()));
							curr.setX(27);
						} else {
							pathToDes.add(new Vector1(curr.getX() - 1, curr.getY()));
							curr.setX(curr.getX() - 1);
						}
					} else {
						pathToDes.add(new Vector1(curr.getX()+1, curr.getY()));
						curr.setX(curr.getX() + 1);
					}
				}
				if(Key.isPress() == true) return;
			}
		} 
	}
	
	public void checkLoss() {
		if(pinky.getX() == pacman.getX() && pinky.getY() == pacman.getY()) {
			if(timeBuff > 0) {
				pinky.setBounds(13, 14, 25, 25);
				pinky.setIcon(new ImageIcon("images/pinky.png"));
			} else {
				JOptionPane.showMessageDialog(null, "You Loss");
				timer.stop();
			}
		}
		
	}
	
	public void checkWin() {
		for(int i = 0; i < PacmanGame.getTilesRepresentation().length; i++) {
			for (int j = 0; j < PacmanGame.getTilesRepresentation()[0].length; j++) {
				if(PacmanGame.getTilesRepresentation()[i][j] == 0 || PacmanGame.getTilesRepresentation()[i][j] == 5) {
					return;		
				}
			}
		}
		JOptionPane.showMessageDialog(null, "You win :) Congratulations!");
		timer.stop();
	}
	
	// +++++++++++++++++++++++++++++++++++++++++++++++Astar+++++++++++++++++++++++++++++++++++++++++++++++
	public void run() {
		timer = new Timer(perFrame, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(timeBuff > perFrame) timeBuff -= perFrame;
				else timeBuff = 0;
				
				if(perFrame >= 80 && (Integer.parseInt(PacmanMain.score.getText()) - incre) >= 50 && Integer.parseInt(PacmanMain.score.getText()) != 0) {
					incre += 50;
					perFrame -= 10;
				}
				
				// +++++++++++++++++++++++++++++++++++++++++++++++Astar+++++++++++++++++++++++++++++++++++++++++++++++

				if(index < pathToDes.size() && pathToDes.get(index) != null) {
					if(pinky.getX() == pathToDes.get(index).getX()*16) {
						if(pinky.getY() < pathToDes.get(index).getY()*16)
							pinky.setIcon(new ImageIcon("images/pinkyD.png"));
						else pinky.setIcon(new ImageIcon("images/pinkyU.png"));
					} else {
						if(pinky.getX() < pathToDes.get(index).getX()*16)
							pinky.setIcon(new ImageIcon("images/pinkyL.png"));
						else pinky.setIcon(new ImageIcon("images/pinkyR.png"));
					}
					if(PacmanGame.getTilesRepresentation()[pathToDes.get(index).getY()][pathToDes.get(index).getX()] == 1 || PacmanGame.getTilesRepresentation()[pathToDes.get(index).getY()][pathToDes.get(index).getX()] == 2) movePinky(new Vector1(pacman.getX(), pacman.getY()));
					pinky.setBounds(pathToDes.get(index).getX()*16, pathToDes.get(index).getY()*16, 25, 25);
					index++;
					checkLoss();
					if(timeBuff > 0 || (Key.isPress() == true && Key.isRelease() == false && (((pathToDes.size() - index) > 3 && index % 3 == 0) || (pathToDes.size() - index) < 3)) || (Key.isRelease() == true && Key.isPress() == false && (pathToDes.get(pathToDes.size() - 1).getX() != pacman.getX()/16 || pathToDes.get(pathToDes.size() - 1).getY() != pacman.getY()/16) && index == pathToDes.size())) {
						Key.setPress(false);
						Key.setRelease(true);
						movePinky(new Vector1(pacman.getX(), pacman.getY()));
					}
					checkWin();
				}
				// +++++++++++++++++++++++++++++++++++++++++++++++Astar+++++++++++++++++++++++++++++++++++++++++++++++

			}
		});
		timer.start();
	}
	
	public static void setTimeBuff() {
		timeBuff = 1500;
	}
}
