package model;

import javax.swing.JLabel;

public class Item extends JLabel {
	
	private String itemName;
	private Vector1 pos;
	
	public Item() {
		
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Vector1 getPos() {
		return pos;
	}


	public void setPos(Vector1 pos) {
		this.pos = pos;
	}
}
