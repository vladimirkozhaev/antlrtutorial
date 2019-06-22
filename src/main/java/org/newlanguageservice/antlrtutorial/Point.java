package org.newlanguageservice.antlrtutorial;

public class Point {
	private int x=0,y=0;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public Point setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Point setY(int y) {
		this.y = y;
		return this;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	
}
