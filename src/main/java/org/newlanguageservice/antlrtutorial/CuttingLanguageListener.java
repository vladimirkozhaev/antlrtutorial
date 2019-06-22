package org.newlanguageservice.antlrtutorial;

import java.util.ArrayList;
import java.util.List;

import org.newlanguageservice.ch1.CuttingLanguageBaseListener;
import org.newlanguageservice.ch1.CuttingLanguageParser.LineToContext;
import org.newlanguageservice.ch1.CuttingLanguageParser.MoveToContext;

public class CuttingLanguageListener extends CuttingLanguageBaseListener {
	List<LexemeWithCoords> lexemes=new ArrayList<>();
	@Override
	public void enterLineTo(LineToContext ctx) {
		lexemes.add(new LexemeWithCoords(
				new Point(ctx.lineToName.getStartIndex(),
						ctx.lineToName.getStopIndex()+1), 
							"LineTo"));
	}

	@Override
	public void enterMoveTo(MoveToContext ctx) {
		lexemes.add(
				new LexemeWithCoords(
						new Point(ctx.moveToName.getStartIndex(),
								ctx.moveToName.getStopIndex()+1), "MoveTo"));
	}

	public List<LexemeWithCoords> getLexemes() {
		return lexemes;
	}
}
