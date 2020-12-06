package view;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Item;
import model.PacmanGame;
import model.Vector1;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;

public class PacmanMain extends JFrame {

	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private List<JLabel> danhSachThucAn;

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
		danhSachThucAn = new ArrayList<JLabel>();
		initComponents();
	}
	
	public void initComponents() {
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
		lbBackground.setIcon(new ImageIcon("/Users/datascience/Documents/Eclipse/AI20201GroupX/images/map.png"));
		lbBackground.setBounds(0, 0, 448, 507);
		panel.add(lbBackground);
		
		JLabel lbScore = new JLabel("Score");
		lbScore.setFont(new Font("Bradley Hand", Font.PLAIN, 25));
		lbScore.setForeground(new Color(255, 255, 255));
		lbScore.setBounds(10, 509, 61, 27);
		contentPane.add(lbScore);
		
		JLabel score = new JLabel("0");
		score.setForeground(new Color(255, 255, 0));
		score.setFont(new Font("Bradley Hand", Font.BOLD | Font.ITALIC, 25));
		score.setBounds(88, 514, 26, 16);
		contentPane.add(score);
		
		JLabel lbLives = new JLabel("Lives");
		lbLives.setForeground(new Color(255, 255, 255));
		lbLives.setFont(new Font("Bradley Hand", Font.PLAIN, 25));
		lbLives.setBounds(191, 509, 61, 27);
		contentPane.add(lbLives);
		
		JLabel lbPacManFake1 = new JLabel("");
		lbPacManFake1.setIcon(new ImageIcon("/Users/datascience/Documents/Eclipse/AI20201GroupX/images/pacman5.png"));
		lbPacManFake1.setBounds(264, 509, 26, 27);
		contentPane.add(lbPacManFake1);
		
		JLabel lbPacmanPake2 = new JLabel("");
		lbPacmanPake2.setIcon(new ImageIcon("/Users/datascience/Documents/Eclipse/AI20201GroupX/images/pacman5.png"));
		lbPacmanPake2.setBounds(295, 509, 26, 27);
		contentPane.add(lbPacmanPake2);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(316, 509, 26, 27);
		contentPane.add(lblNewLabel_2);
	}
	
	private void updateScreen(JPanel panel) {
		Item item = null;
		Vector1 pos = null;
		PacmanGame pacMan = null;
		int[][] tilesRepresentation = null;
		
		pacMan = new PacmanGame();
		tilesRepresentation = pacMan.getTilesRepresentation();
		
		// i : là hàng,j là cột.
		for(int i = 0; i < tilesRepresentation.length; i++) {
			for(int j = 0; j < tilesRepresentation[0].length; j++) {
				item = new Item();
				danhSachThucAn.add(item);
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
					item.setIcon(new ImageIcon("/Users/datascience/Documents/Eclipse/AI20201GroupX/images/dot2.png"));
				} else if(tilesRepresentation[i][j] == 8) {
					item.setItemName("BigDot");
					item.setIcon(new ImageIcon("/Users/datascience/Documents/Eclipse/AI20201GroupX/images/dot1.png"));
				} else if(tilesRepresentation[i][j] == 10){
					item.setBounds(j * 16, i * 16, 25, 25);
					item.setItemName("Pacman");
					item.setIcon(new ImageIcon("/Users/datascience/Documents/Eclipse/AI20201GroupX/images/Pacman5.png"));
				}
			}
		}
	}
}
