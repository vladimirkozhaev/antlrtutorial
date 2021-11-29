package org.newlanguageservice.antlrtutorial;

import org.antlr.v4.runtime.ParserRuleContext;

public class LanguageException extends RuntimeException {
	private final ParserRuleContext ctx;
	
	public LanguageException(ParserRuleContext ctx,String message) {
		super(message);
		this.ctx = ctx;

	}

	public ParserRuleContext getCtx() {
		return ctx;
	}

}
