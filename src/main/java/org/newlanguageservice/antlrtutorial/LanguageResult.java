package org.newlanguageservice.antlrtutorial;

import java.util.List;

public class LanguageResult {
	private final ExprResult exprResult;
	private final List<LanguageException> exceptions;
	public LanguageResult(ExprResult exprResult, List<LanguageException> exception) {
		super();
		this.exprResult = exprResult;
		this.exceptions = exception;
	}
	public ExprResult getExprResult() {
		return exprResult;
	}
	public List<LanguageException> getExceptions() {
		return exceptions;
	}
	
}
