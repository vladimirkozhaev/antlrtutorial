package org.newlanguageservice.antlrtutorial;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class GrammarTests {
	@Test
	public void simpleGrammarTests() {
		assertEquals(0,new DrawCanvas().processALanguage(null, "MoveTo(0,0)\nLineTo(1,1)").getExceptions().size());
	}
}
