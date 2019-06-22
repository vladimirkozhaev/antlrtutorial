package org.newlanguageservice.antlrtutorial;

public class LexemeWithCoords {
	Point coords;
	String lexemeName;
	public LexemeWithCoords(Point coords, String lexemeName) {
		super();
		this.coords = coords;
		this.lexemeName = lexemeName;
	}
	public Point getCoords() {
		return coords;
	}
	public String getLexemeName() {
		return lexemeName;
	}
	@Override
	public String toString() {
		return "LexemeWithCoords [coords=" + coords + ", lexemeName=" + lexemeName + "]";
	}
	
	
	
}
