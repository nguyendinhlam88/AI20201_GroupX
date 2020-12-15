package view;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gamecontrol.GameController;
import model.Character1;
import model.Item;
import model.Key;
import model.PacmanGame;
import model.Vector1;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PacmanMain extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel play_pause;
	private boolean isPlay = false;
	private List<Character1> characterList;
	private PacmanGame pacmanGame;
	public static Item[][] itemList = new Item[31][28];
	public static JLabel score;
	private GameController game;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PacmanMain frame = new PacmanMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PacmanMain() {
		setLocationRelativeTo(null);
		panel = new JPanel();
		pacmanGame = PacmanGame.getInstance();
		characterList = PacmanGame.getCharacterList();
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/pacmanL.png"));
		setTitle("Pacman");
		initComponents();
		game = new GameController(panel);
		PacmanGame.updateTilesRepresentation();
		if(characterList.get(0).getX() != characterList.get(1).getX() || characterList.get(0).getY() != characterList.get(1).getY()) {
			game.movePinky();
		}
	}
	
	public void initComponents() {
	    setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 448, 563);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		panel.setBounds(0, 0, 454, 497);
		contentPane.add(panel);
		panel.setLayout(null);
		
		updateScreen(panel);
		
		JLabel lbBackground = new JLabel("");
		lbBackground.setHorizontalAlignment(SwingConstants.CENTER);
		lbBackground.setIcon(new ImageIcon("images/map.png"));
		lbBackground.setBounds(0, 0, 448, 507);
		panel.add(lbBackground);
		
		play_pause = new JLabel("");
		play_pause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isPlay == true) {
					play_pause.setIcon(new ImageIcon("images/pause.png"));
					isPlay = false;
					
				} else {
					play_pause.setIcon(new ImageIcon("images/play.png"));
					isPlay = true;
				}
			}
		});
		play_pause.setIcon(new ImageIcon("images/pause.png"));
		play_pause.setBounds(73, 503, 37, 33);
		contentPane.add(play_pause);
		
		JLabel lbScore = new JLabel("Score");
		lbScore.setFont(new Font("Bradley Hand", Font.PLAIN, 25));
		lbScore.setForeground(new Color(255, 255, 255));
		lbScore.setBounds(122, 509, 61, 27);
		contentPane.add(lbScore);
		
		score = new JLabel();
		score.setText(String.valueOf(characterList.get(0).getScore()));
		score.setForeground(new Color(255, 255, 0));
		score.setFont(new Font("Bradley Hand", Font.BOLD | Font.ITALIC, 25));
		score.setBounds(195, 509, 56, 20);
		contentPane.add(score);
		
		JLabel lbLives = new JLabel("Lives");
		lbLives.setForeground(new Color(255, 255, 255));
		lbLives.setFont(new Font("Bradley Hand", Font.PLAIN, 25));
		lbLives.setBounds(264, 509, 61, 27);
		contentPane.add(lbLives);
		
		JLabel lbPacManFake1 = new JLabel("");
		lbPacManFake1.setIcon(new ImageIcon("images/pacmanR.png"));
		lbPacManFake1.setBounds(331, 509, 26, 27);
		contentPane.add(lbPacManFake1);
		
		JLabel lbPacmanPake2 = new JLabel("");
		lbPacmanPake2.setIcon(new ImageIcon("images/pacmanR.png"));
		lbPacmanPake2.setBounds(369, 509, 26, 27);
		contentPane.add(lbPacmanPake2);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(316, 509, 26, 27);
		contentPane.add(lblNewLabel_2);
		
		JLabel lbPlay = new JLabel("Play");
		lbPlay.setForeground(Color.WHITE);
		lbPlay.setFont(new Font("Bradley Hand", Font.PLAIN, 25));
		lbPlay.setBounds(0, 509, 61, 27);
		contentPane.add(lbPlay);
	}
	
	private void updateScreen(JPanel panel) {
		Item item = null;
		Vector1 pos = null;
		int[][] tilesRepresentation = null;
		
		tilesRepresentation = PacmanGame.getTilesRepresentation();
		
		// i : là hàng,j là cột.
		for(int i = 0; i < tilesRepresentation.length; i++) {
			for(int j = 0; j < tilesRepresentation[0].length; j++) {
				item = new Item();
				itemList[i][j] = item;
				pos = new Vector1(j * 16, i * 16);
				item.setPos(pos);
				item.setHorizontalAlignment(SwingConstants.CENTER);
				item.setBounds(j * 16, i * 16, 16, 16);
				panel.add(item);
				
				if(tilesRepresentation[i][j] == 6) {
					item.setItemName("Space");
				} else if(tilesRepresentation[i][j] == 1) {
					item.setItemName("Wall");
				} else if(tilesRepresentation[i][j] == 0) {
					item.setItemName("Dot");
					item.setIcon(new ImageIcon("images/dot2.png"));
				} else if(tilesRepresentation[i][j] == 8) {
					item.setItemName("BigDot");
					item.setIcon(new ImageIcon("images/dot1.png"));
				}
			}
		}
		
		for(Character1 character : characterList) {
			panel.add(character);
		}
	}
}
