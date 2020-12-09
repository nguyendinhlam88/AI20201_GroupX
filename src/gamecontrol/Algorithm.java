package gamecontrol;

import java.util.ArrayList;
import java.util.List;
import model.Character1;
import model.PacmanGame;
import model.Vector1;

public class Algorithm {
	ArrayList<Vector1> pathToDes;
	private int[][] tilesRepresentation;
	// --------------For AStar--------------
	private String characterName;
	private List<Character1> characterList = PacmanGame.getCharacterList();
	private int[][] sample = new int[4][5]; // 0 : Up, 1 : Down, 2 : Left, 3 : Right
	private double[] weight = new double[5];
	private Vector1 currU, currD, currL, currR; // Ghost
	private Vector1 currUP, currDP, currLP, currRP; // Pacman
	private int[][] checked = new int[31][28];
	private int[] preCost = new int[5];
	// -------------------------------------
	public Algorithm(String characterName) {
		weight[0] = 1;
		weight[1] = 0;
		weight[2] = 0;
		weight[3] = 0;
		weight[4] = 1;
		
		this.characterName = characterName;
	}
	
	/*
	 *  Input : 
	 *  	+ cost : Chi phí từ đầu đến điểm hiện tại(x0).
	 *  	+ score : Điểm kiếm được nếu pacman đi theo đường(x1).
	 *  	+ nBigBot : Số lượng BigDot(x2).
  	 *      + nGhost : Số lượng ghost trên đường đi(x3)
  	 *      + distance : khoảng cách Manhattan(x4).
	 *  Output : danh sách các ngã rẽ nối tiếp nhau.
	 */
	
	private void nearCornerForGhost(Vector1 curr, Vector1 pacLoc) {
		currU= new Vector1(curr.getX(), curr.getY());
		currD= new Vector1(curr.getX(), curr.getY());
		currL= new Vector1(curr.getX(), curr.getY());
		currR= new Vector1(curr.getX(), curr.getY());
		
		// Check hướng Up.
		currU.setY(currU.getY()-1);
		int presentU = tilesRepresentation[currU.getY()][currU.getX()];
		if(presentU == 1 || presentU == 2) this.sample[0][4] = 0;
		else if((presentU == 5 || presentU == 4) && checked[currU.getY()][currU.getX()] == 0) {
			this.sample[0][0] += 1;
			if(presentU == 5) this.sample[0][1] += 1;
			for(int i = 0; i < characterList.size(); i++) {
				if(!characterList.get(i).getCharacterName().equals(characterName)) {
					if(characterList.get(i).getX() == currU.getX()*16 && characterList.get(i).getY() == currU.getY()*16) this.sample[0][3] += 1;
				}
			}
			this.sample[0][4] = Math.abs(currU.getX() - pacLoc.getX()) + Math.abs(currU.getY() - pacLoc.getY());
		} else {
			while(presentU != 5 && presentU != 4) {
				// add cost
				this.sample[0][0] += 1;
				// add score
				if(presentU == 0) {
					this.sample[0][1] += 1;
				}
				// add score
				if(presentU == 8) {
					this.sample[0][1] += 3;
					this.sample[0][2] +=1;
				}
				// Check ghost.
				for(int i = 0; i < characterList.size(); i++) {
					if(!characterList.get(i).getCharacterName().equals(characterName)) {
						if(characterList.get(i).getX() == currU.getX()*16 && characterList.get(i).getY() == currU.getY()*16) this.sample[0][3] += 1;
					}
				}
				currU.setY(currU.getY()-1);
				presentU = tilesRepresentation[currU.getY()][currU.getX()];
				if(presentU == 1 || presentU == 2) {
					this.sample[0][4] = 0;
					break;
				}
			}
			if(presentU != 1 && presentU != 2) {
				this.sample[0][0] += 1;
				if(presentU == 5) this.sample[0][1] += 1;
				for(int i = 0; i < characterList.size(); i++) {
					if(!characterList.get(i).getCharacterName().equals(characterName)) {
						if(characterList.get(i).getX() == currU.getX()*16 && characterList.get(i).getY() == currU.getY()*16) this.sample[0][3] += 1;
					}
				}
				this.sample[0][4] = Math.abs(currU.getX() - pacLoc.getX()) + Math.abs(currU.getY() - pacLoc.getY());
			}
		}
//		System.out.println("-------------------------------");
//		System.out.println("pac :" + pacLoc.getX() + " : " + pacLoc.getY());
//		System.out.println("curr :" + curr.getX() + " : " + curr.getY());
//		System.out.println("currU :" + currU.getX() + " : " + currU.getY());
		
		// Check hướng Down.
		currD.setY(currD.getY()+1);
		int presentD = tilesRepresentation[currD.getY()][currD.getX()];
		if(presentD == 1 || presentD == 2) this.sample[1][4] = 0;
		else if((presentD == 5 || presentD == 4) && checked[currD.getY()][currD.getX()] == 0) {
			this.sample[1][0] += 1;
			if(presentD == 5) this.sample[1][1] += 1;
			for(int i = 1; i < characterList.size(); i++) {
				if(!characterList.get(i).getCharacterName().equals(characterName)) {
					if(characterList.get(i).getX() == currD.getX()*16 && characterList.get(i).getY() == currD.getY()*16) this.sample[1][3] += 1;
				}
			}
			this.sample[1][4] = Math.abs(currD.getX() - pacLoc.getX()) + Math.abs(currD.getY() - pacLoc.getY());
		} else {
			while(presentD != 5 && presentD != 4) {
				// add cost
				this.sample[1][0] += 1;
				// add score
				if(presentD == 0) {
					this.sample[1][1] += 1;
				}
				// add score
				if(presentD == 8) {
					this.sample[1][1] += 3;
					this.sample[1][2] +=1;
				}
				// Check ghost.
				for(int i = 0; i < characterList.size(); i++) {
					if(!characterList.get(i).getCharacterName().equals(characterName)) {
						if(characterList.get(i).getX() == currD.getX()*16 && characterList.get(i).getY() == currD.getY()*16) this.sample[1][3] += 1;
					}
				}
				currD.setY(currD.getY()+1);
				presentD = tilesRepresentation[currD.getY()][currD.getX()];
				if(presentD == 1 || presentD == 2) {
					this.sample[1][4] = 0;
					break;
				}
			}
			if(presentD != 1 && presentD != 2) {
				this.sample[1][0] += 1;
				if(presentD == 5) this.sample[1][1] += 1;
				for(int i = 0; i < characterList.size(); i++) {
					if(!characterList.get(i).getCharacterName().equals(characterName)) {
						if(characterList.get(i).getX() == currD.getX()*16 && characterList.get(i).getY() == currD.getY()*16) this.sample[0][3] += 1;
					}
				}
				this.sample[1][4] = Math.abs(currD.getX() - pacLoc.getX()) + Math.abs(currD.getY() - pacLoc.getY());
			}
		}
//		System.out.println("currD :" + currD.getX() + " : " + currD.getY());
		
		// Check Left.
		if(currL.getX() == 0) {
			currL.setX(27);
		} else {
			currL.setX(currL.getX()-1);
		}
		int presentL = tilesRepresentation[currL.getY()][currL.getX()];
		if(presentL == 1 || presentL == 2) this.sample[2][4] = 0;
		else if(((presentL == 5 || presentL == 4)) || ((presentL == 5 || presentL == 4) && checked[currL.getY()][currL.getX()] == 1)) {
			this.sample[2][0] += 1;
			if(presentL == 5) this.sample[2][1] += 1;
			for(int i = 0; i < characterList.size(); i++) {
				if(!characterList.get(i).getCharacterName().equals(characterName)) {
					if(characterList.get(i).getX() == currL.getX()*16 && characterList.get(i).getY() == currL.getY()*16) this.sample[2][3] += 1;
				}
			}
			this.sample[2][4] = Math.abs(currL.getX() - pacLoc.getX()) + Math.abs(currL.getY() - pacLoc.getY());
		} else {
			while(presentL != 5 && presentL != 4) {
				// add cost
				this.sample[2][0] += 1;
				// add score
				if(presentL == 0) {
					this.sample[2][1] += 1;
				}
				// add score
				if(presentL == 8) {
					this.sample[2][1] += 3;
					this.sample[2][2] +=1;
				}
				// Check ghost.
				for(int i = 0; i < characterList.size(); i++) {
					if(!characterList.get(i).getCharacterName().equals(characterName)) {
						if(characterList.get(i).getX() == currL.getX()*16 && characterList.get(i).getY() == currL.getY()*16) this.sample[2][3] += 1;
					}
				}
				if(currL.getX() == 0) {
					currL.setX(27);
				} else {
					currL.setX(currL.getX()-1);
				}
				presentL = tilesRepresentation[currL.getY()][currL.getX()];
				if(presentL == 1 || presentL == 2) {
					this.sample[2][4] = 0;
					break;
				}
			}
			if(presentL != 1 && presentL != 2) {
				this.sample[2][0] += 1;
				if(presentL == 5) this.sample[2][1] += 1;
				for(int i = 0; i < characterList.size(); i++) {
					if(!characterList.get(i).getCharacterName().equals(characterName)) {
						if(characterList.get(i).getX() == currL.getX()*16 && characterList.get(i).getY() == currL.getY()*16) this.sample[2][3] += 1;
					}
				}
				this.sample[2][4] = Math.abs(currL.getX() - pacLoc.getX()) + Math.abs(currL.getY() - pacLoc.getY());
			}
		}
//		System.out.println("currL :" + currL.getX() + " : " + currL.getY());
		
		// Check Right.
		if(currR.getX() == 27) {
			currR.setX(0);
		} else {
			currR.setX(currR.getX()+1);
		}
		int presentR = tilesRepresentation[currR.getY()][currR.getX()];
		if(presentR == 1 || presentR == 2) this.sample[3][4] = 0;
		else if((presentR == 5 || presentR == 4) && checked[currR.getY()][currR.getX()] == 0) {
			this.sample[3][0] += 1;
			if(presentR == 5) this.sample[3][1] += 1;
			for(int i = 0; i < characterList.size(); i++) {
				if(!characterList.get(i).getCharacterName().equals(characterName)) {
					if(characterList.get(i).getX() == currR.getX()*16 && characterList.get(i).getY() == currR.getY()*16) this.sample[3][3] += 1;
				}
			}
			this.sample[3][4] = Math.abs(currR.getX() - pacLoc.getX()) + Math.abs(currR.getY() - pacLoc.getY());
		} else {
			while(presentR != 5 && presentR != 4) {
				// add cost
				this.sample[3][0] += 1;
				// add score
				if(presentR == 0) {
					this.sample[3][1] += 1;
				}
				// add score
				if(presentR == 8) {
					this.sample[3][1] += 3;
					this.sample[3][2] +=1;
				}
				// Check ghost.
				for(int i = 0; i < characterList.size(); i++) {
					if(!characterList.get(i).getCharacterName().equals(characterName)) {
						if(characterList.get(i).getX() == currR.getX()*16 && characterList.get(i).getY() == currR.getY()*16) this.sample[3][3] += 1;
					}
				}
				if(currR.getX() == 27) {
					currR.setX(0);
				} else {
					currR.setX(currR.getX() + 1);
				}
				presentR = tilesRepresentation[currR.getY()][currR.getX()];
				if(presentR == 1 || presentR == 2) {
					this.sample[3][4] = 0;
					break;
				}
			}
			if(presentR != 1 && presentR != 2) {
				this.sample[3][0] += 1;
				if(presentR == 5) this.sample[3][1] += 1;
				for(int i = 0; i < characterList.size(); i++) {
					if(!characterList.get(i).getCharacterName().equals(characterName)) {
						if(characterList.get(i).getX() == currR.getX()*16 && characterList.get(i).getY() == currR.getY()*16) this.sample[3][3] += 1;
					}
				}
				this.sample[3][4] = Math.abs(currR.getX() - pacLoc.getX()) + Math.abs(currR.getY() - pacLoc.getY());
			}
		}
//		System.out.println("currR :" + currR.getX() + " : " + currR.getY());
//		System.out.println("-------------------------------------");
	}
	
	public void nearCornerForPacman(Vector1 pacLoc) {
		currUP = new Vector1(pacLoc.getX(), pacLoc.getY());
		currDP = new Vector1(pacLoc.getX(), pacLoc.getY());
		currLP = new Vector1(pacLoc.getX(), pacLoc.getY());
		currRP = new Vector1(pacLoc.getX(), pacLoc.getY());
		
		// Check hướng Up.
		currUP.setY(currUP.getY()-1);
		int presentUP = tilesRepresentation[currUP.getY()][currUP.getX()];
		if(presentUP == 1 || presentUP == 2 || presentUP == 5 || presentUP == 4);
		else {
			while(presentUP != 5 && presentUP != 4) {
				currUP.setY(currUP.getY()-1);
				presentUP = tilesRepresentation[currUP.getY()][currUP.getX()];
			}
		}
		
		// Check hướng Down.
		currDP.setY(currDP.getY()+1);
		int presentDP = tilesRepresentation[currDP.getY()][currDP.getX()];
		if(presentDP == 1 || presentDP == 2 || presentDP == 5 || presentDP == 4);
		else {
			while(presentUP != 5 && presentUP != 4) {
				currDP.setY(currDP.getY()+1);
				presentDP = tilesRepresentation[currDP.getY()][currDP.getX()];
			}
		}
		
		// Check Left.
		if(currLP.getX() == 0) {
			currLP.setX(27);
		} else {
			currLP.setX(currLP.getX()-1);
		}
		int presentLP = tilesRepresentation[currLP.getY()][currLP.getX()];
		if(presentLP == 1 || presentLP == 2 || presentLP == 5 || presentLP == 4);
		else {
			while(presentLP != 5 && presentLP != 4) {
				if(currLP.getX() == 0) {
					currLP.setX(27);
				} else {
					currLP.setX(currLP.getX()-1);
				}
				presentLP = tilesRepresentation[currLP.getY()][currLP.getX()];
			}
		}
		
		// Check Right.
		if(currRP.getX() == 0) {
			currRP.setX(27);
		} else {
			currRP.setX(currRP.getX()+1);
		}
		int presentRP = tilesRepresentation[currRP.getY()][currRP.getX()];
		if(presentRP == 1 || presentRP == 2 || presentRP == 5 || presentRP == 4);
		else {
			while(presentRP != 5 && presentRP != 4) {
				if(currRP.getX() == 27) {
					currLP.setX(0);
				} else {
					currRP.setX(currRP.getX()+1);
				}
				presentRP = tilesRepresentation[currRP.getY()][currRP.getX()];
			}
		}
	}
	
	boolean checkEnd(Vector1 curr) {
		if(curr.getX() == currUP.getX() && curr.getY() == currUP.getY()) return false;
		if(curr.getX() == currDP.getX() && curr.getY() == currDP.getY()) return false;
		if(curr.getX() == currLP.getX() && curr.getY() == currLP.getY()) return false;
		if(curr.getX() == currRP.getX() && curr.getY() == currRP.getY()) return false;
		return true;
	}
	
	public ArrayList<Vector1> AStar(Vector1 ghostLoc, Vector1 pacLoc) {
		int direct;
		initSample();
		initCheck();
		this.pathToDes = new ArrayList<Vector1>();
		Vector1 curr = new Vector1(ghostLoc.getX(), ghostLoc.getY());
		tilesRepresentation = PacmanGame.getTilesRepresentation();
		nearCornerForPacman(pacLoc);
		while(checkEnd(curr)) {
			initSample();
			nearCornerForGhost(curr, pacLoc);
			direct = computeCost();
			if(direct == 0) curr = new Vector1(currU.getX(), currU.getY());
			if(direct == 1) curr = new Vector1(currD.getX(), currD.getY());
			if(direct == 2) curr = new Vector1(currL.getX(), currL.getY());
			if(direct == 3) curr = new Vector1(currR.getX(), currR.getY());
			pathToDes.add(curr);
			checked[curr.getY()][curr.getX()] = 1;
		}	
		return pathToDes;
	}
	
	private void initSample() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				this.sample[i][j] = 0;
			}
		}
	}
	
	private void initCheck() {
		for(int i = 0; i < 31; i++) {
			for(int j = 0; j < 28; j++) {
				checked[i][j] = 0;
			}
		}
		for(int i = 0; i < 4; i++) {
			preCost[i] = 0; 
		}
	}
	
	private int computeCost() {
		double[] result = new double[4];
		
		double cost = 0;
		double min = 10000;
		int index = -1;
		for(int i = 0; i < sample.length; i++) {
			cost = 0;
			for(int j = 0; j < sample[0].length; j++) {
				cost += (sample[i][j] + preCost[j])*weight[j];
			}
			result[i] = cost;
			if(result[i] <= min && result[i] > 0) {
				if(i == 0 && (checked[currU.getY()][currU.getX()] == 1 || sample[i][4] == 0)) continue;
				if(i == 1 && (checked[currD.getY()][currD.getX()] == 1 || sample[i][4] == 0)) continue;
				if(i == 2 && (checked[currL.getY()][currL.getX()] == 1 || sample[i][4] == 0)) continue;
				if(i == 3 && (checked[currR.getY()][currR.getX()] == 1 || sample[i][4] == 0)) continue;
				min = result[i];
				index = i;
			}
		}
		
		for(int j = 0; j < 5; j++) {
			preCost[j] += sample[index][j];
		}
		
		return index;
	}
}
