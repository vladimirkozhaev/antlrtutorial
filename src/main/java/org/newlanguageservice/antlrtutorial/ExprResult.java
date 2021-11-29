package org.newlanguageservice.antlrtutorial;

public class ExprResult {
	private final Type type;
	private final Object result;
	public ExprResult(Type type, Object result) {
		super();
		this.type = type;
		this.result = result;
	}
	
	public Type getType() {
		return type;
	}
	public Object getResult() {
		return result;
	}
	
	
}
