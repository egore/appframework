package de.egore911.appframework.ui.exceptions;

public class NullArgumentException extends BadArgumentException {

	private static final long serialVersionUID = 7812375776293537525L;

	public NullArgumentException(String parameter) {
		super(parameter + " must not be null");
	}

}
