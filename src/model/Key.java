package model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import gamecontrol.GameController;
import view.PacmanMain;

public class Key implements KeyListener {

	private JButton btn;
	private int[][] tilesRepresentation;
	private static boolean press = false;
	private static boolean release = true;

	public Key(JButton btn, int[][] tilesRepresentation) {
		this.btn = btn;
		this.tilesRepresentation = tilesRepresentation;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int xx = btn.getX()/16;
		int yy = btn.getY()/16;

		if((xx == 0 && e.getKeyCode() == KeyEvent.VK_LEFT)  || (xx == 27 && e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			btn.setBounds((tilesRepresentation[0].length - 1 - xx)*16, btn.getY(), 25, 25);
			if(xx == 0) btn.setIcon(new ImageIcon("images/pacmanL.png"));
			else btn.setIcon(new ImageIcon("images/pacmanR.png"));
			press = true;
			release = false;
		} else if(e.getKeyCode() == KeyEvent.VK_UP && tilesRepresentation[yy-1][xx] != 1 && tilesRepresentation[yy-1][xx] != 2) {
			btn.setBounds(btn.getX(), btn.getY() - 16, 25, 25);
			updateScore(yy-1, xx);
			btn.setIcon(new ImageIcon("images/pacmanU.png"));
			press = true;
			release = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN && tilesRepresentation[yy+1][xx] != 1 && tilesRepresentation[yy+1][xx] != 2) {
			btn.setBounds(btn.getX(), btn.getY() + 16, 25, 25);
			btn.setIcon(new ImageIcon("images/pacmanD.png"));
			updateScore(yy+1, xx);
			press = true;
			release = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT && tilesRepresentation[yy][xx-1] != 1 && tilesRepresentation[yy][xx-1] != 2) {
			btn.setBounds(btn.getX() - 16, btn.getY(), 25, 25);
			btn.setIcon(new ImageIcon("images/pacmanL.png"));
			updateScore(yy, xx-1);
			press = true;
			release = false;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT && tilesRepresentation[yy][xx+1] != 1 && tilesRepresentation[yy][xx+1] != 2) {
			btn.setBounds(btn.getX() + 16, btn.getY(), 25, 25);
			btn.setIcon(new ImageIcon("images/pacmanR.png"));
			updateScore(yy, xx+1);
			press = true;
			release = false;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		press = false;
		release = true;
	}	
	
	public void updateScore(int xx, int yy) {
		if (tilesRepresentation[xx][yy] == 0 || tilesRepresentation[xx][yy] == 8 || tilesRepresentation[xx][yy] == 5) {
			if(tilesRepresentation[xx][yy] == 5) tilesRepresentation[xx][yy] = 4;
			else tilesRepresentation[xx][yy] = 6;
			PacmanGame.setTilesRepresentation(tilesRepresentation);
		}
		
		if(PacmanMain.itemList[xx][yy].getItemName().equals("Dot")) {
			Character1.score += 1;
			PacmanMain.itemList[xx][yy].setIcon(null);
			PacmanMain.itemList[xx][yy].setItemName("Space");
 		} else if(PacmanMain.itemList[xx][yy].getItemName().equals("BigDot")) {
 			Character1.score += 3;
 			PacmanMain.itemList[xx][yy].setIcon(null);
 			PacmanMain.itemList[xx][yy].setItemName("Space");
 			GameController.setTimeBuff();
 		}
		PacmanMain.score.setText(String.valueOf(Character1.score));
	}

	public static boolean isPress() {
		return press;
	}

	public static void setPress(boolean press) {
		Key.press = press;
	}

	public static boolean isRelease() {
		return release;
	}

	public static void setRelease(boolean release) {
		Key.release = release;
	}
}
