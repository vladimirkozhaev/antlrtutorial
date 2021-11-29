package org.newlanguageservice.antlrtutorial;

public class Variable {
	String name;
	private Type type;
	Object value;

	public Variable(String varName) {
		super();
		this.name = varName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	

}
