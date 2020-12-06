package model;

public class Vector1 {
	private int x;
	private int y;
	
	public Vector1(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int distance(Vector1 v) {
		int xx, yy, dist;
		
		xx = this.x - v.getX();
		yy = this.y - v.getY();
		dist = (int) Math.sqrt(xx*xx + yy*yy);
		
		return dist;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
