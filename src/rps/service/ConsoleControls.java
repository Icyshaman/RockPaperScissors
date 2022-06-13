package rps.service;

public interface ConsoleControls {
	public String TEXT_RESET = "\u001B[0m";
	public String TEXT_BLACK = "\u001B[30m";
	public String TEXT_RED = "\u001B[31m";
	public String TEXT_GREEN = "\u001B[32m";
	public String TEXT_YELLOW = "\u001B[33m";
	public String TEXT_BLUE = "\u001B[34m";
	public String TEXT_PURPLE = "\u001B[35m";
	public String TEXT_CYAN = "\u001B[36m";
	public String TEXT_WHITE = "\u001B[37m";

	static void clearConsole() {
		for (int i = 0; i <= 10; i++) {
			System.out.println();
		}
	}

	static void holdConsole() {
		Object lockObject = new Object();
		synchronized (lockObject) {
			try {
				lockObject.wait(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		clearConsole();
	}
}
