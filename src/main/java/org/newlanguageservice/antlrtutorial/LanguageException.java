package org.newlanguageservice.antlrtutorial;

import org.antlr.v4.runtime.RuleContext;

public class LanguageException extends RuntimeException {
	private final RuleContext ctx;
	
	public LanguageException(RuleContext ctx,String message) {
		super(message);
		this.ctx = ctx;

	}

	public RuleContext getCtx() {
		return ctx;
	}

}
