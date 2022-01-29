package org.newlanguageservice.antlrtutorial;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class GrammarTests {
	@Test
	public void simpleGrammarTests() {
		assertEquals(0, new DrawCanvas().processALanguage(null, "MoveTo(0,0)\nLineTo(1,1)").getExceptions().size());
	}

	@Test
	public void testOfAddingAVariable() {
		assertEquals(0, new DrawCanvas().processALanguage(null, "var t: int= 5 + 5 ").getExceptions().size());
	}

	@Test
	public void testToMoveWithVarRef() {
		assertEquals(0, new DrawCanvas().processALanguage(null, "var x:int = 1 MoveTo(x,x)").getExceptions().size());
	}

	@Test
	public void testSimplePointEquation() {
		assertEquals(0, new DrawCanvas().processALanguage(null, "var x:Point = (1,1) +(2,2)").getExceptions().size());
	}

	@Test(expected = LanguageException.class)
	public void testAnExceptionInPointEquation() {
		assertEquals(0, new DrawCanvas().processALanguage(null, "var x:Point = (1,1) * (2,2)").getExceptions().size());
	}
}
