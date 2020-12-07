package model;

import javax.swing.JButton;
import gamecontrol.Algorithm;

public class Character1 extends JButton{
	
	private String characterName;
	public static int score = 0; // For pacman
	private Vector1 pos;
	private Algorithm algo;
	
	public Character1(String characterName) {
		this.characterName = characterName;
	}
	
	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public Vector1 getPos() {
		return pos;
	}

	public void setPos(Vector1 pos) {
		this.pos = pos;
	}
	
	public void move() {
		
	}

	public Algorithm getAlgo() {
		return algo;
	}

	public void setAlgo(Algorithm algo) {
		this.algo = algo;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
