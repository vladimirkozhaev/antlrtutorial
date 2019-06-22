package org.newlanguageservice.antlrtutorial;

import org.newlanguageservice.ch1.CuttingLanguageBaseVisitor;
import org.newlanguageservice.ch1.CuttingLanguageParser.LineToContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.MoveToContext;

import javafx.scene.canvas.GraphicsContext;

public class CuttingLanguageVisitor extends CuttingLanguageBaseVisitor<Point> {
	private Point point;
	private GraphicsContext  gc;
	
	
	public CuttingLanguageVisitor(Point point, GraphicsContext gc) {
		
		this.point = point;
		this.gc=gc;
	}

	@Override
	public Point visitMoveTo(MoveToContext ctx) {
		int x = Integer.parseInt(ctx.x.getText());
		int y = Integer.parseInt(ctx.y.getText());
		gc.moveTo(x, y);
		gc.save();
		return point.setX(x).setY(y);
	}

	@Override
	public Point visitLineTo(LineToContext ctx) {
		int x = Integer.parseInt(ctx.x.getText());
		int y = Integer.parseInt(ctx.y.getText());
		gc.strokeLine(point.getX(), point.getY(), x, y);
		gc.save();
		return point.setX(x).setY(y);
	}
}
