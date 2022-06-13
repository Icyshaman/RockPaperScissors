package rps.entity;

public class RockPaperScissors {
	private static String highestScoreUserName;
	private String currentUserName;
	private static int highestScore;
	private int currentScore;
	private int currentUserHighestScore;

	public RockPaperScissors(String currentUserName, int currentUserHighestScore) {
		this.currentUserName = currentUserName;
		this.currentUserHighestScore = currentUserHighestScore;
	}

	public static String getHighestScoreUserName() {
		return highestScoreUserName;
	}

	public static void setHighestScoreUserName(String highestScoreUserName) {
		RockPaperScissors.highestScoreUserName = highestScoreUserName;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public static int getHighestScore() {
		return highestScore;
	}

	public static void setHighestScore(int highestScore) {
		RockPaperScissors.highestScore = highestScore;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public int getCurrentUserHighestScore() {
		return currentUserHighestScore;
	}

	public void setCurrentUserHighestScore(int currentUserHighestScore) {
		this.currentUserHighestScore = currentUserHighestScore;
	}

}
