package part3;

import java.awt.Color;

public class MyShape {

	String type;
	int startX, startY, endX, endY;
	Color shape_color;
	boolean is_filled;
	
	public MyShape(String type){
		this.type = type;
	}
	
	public void setStartPosition(int x, int y){
		this.startX = x;
		this.startY = y;
	}
	
	public void setEndPosition(int x, int y){
		this.endX = x;
		this.endY = y;
	}

	public int getEndX() {
		return endX;
	}

	public String getType() {
		return type;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getEndY() {
		return endY;
	}
	
	
	
}
