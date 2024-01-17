package poly.Lab2;

public class JUnitMessage {
	private String message;

	public JUnitMessage(String message) {
		this.message = message;
	}

	public String printMessage() {
		System.out.println(message);
		int divide = 1 / 0;
		return String.valueOf(divide);
	}

	public String printHiMessage() {
		message = "Hi!" + message;
		System.out.println(message);
		return message;
	}

}
