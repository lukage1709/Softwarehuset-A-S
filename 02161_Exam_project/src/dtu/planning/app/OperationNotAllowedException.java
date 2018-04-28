package dtu.planning.app;

public class OperationNotAllowedException extends Exception {

	public OperationNotAllowedException(String errorMessage) {
		super(errorMessage);
	}
}
