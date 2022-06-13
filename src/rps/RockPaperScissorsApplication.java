package rps;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import rps.entity.RockPaperScissors;
import rps.service.ConsoleControls;
import rps.service.RockPaperScissorsDAOService;
import rps.service.RockPaperScissorsExceptions;

public class RockPaperScissorsApplication {

	public static void updateHighScore(RockPaperScissorsDAOService rpsDao) {
		RockPaperScissors.setHighestScore(rpsDao.getHighestScore());
		RockPaperScissors.setHighestScoreUserName(rpsDao.getHighestScoreUserName());
	}

	public static void main(String[] args) {
		RockPaperScissorsDAOService rpsDao = new RockPaperScissorsDAOService();
		rpsDao.createFile();
		Scanner sc = new Scanner(System.in);
		boolean isReturningUser;
		String isReturningUserString;
		homePage: do {
			System.out.println(
					ConsoleControls.TEXT_RED + "Welcome To The Rock Paper Scissors GAME" + ConsoleControls.TEXT_RESET);
			System.out.print("\n\nAre you a returning user (yes/no): ");
			isReturningUserString = sc.next().toLowerCase();
			if (!(isReturningUserString.equals("yes") || isReturningUserString.equals("no"))) {
				try {
					throw new RockPaperScissorsExceptions("Please enter valid input (yes/no)");
				} catch (RockPaperScissorsExceptions e) {
					System.out.println(e.getMessage());
					ConsoleControls.holdConsole();
					continue homePage;
				}
			}
			isReturningUser = (isReturningUserString.equals("yes")) ? true : false;
			String userName;
			int userHighestScore = 0;
			System.out.print("\n\nEnter UserName (starts with alphabet and may contain digits): ");
			userName = sc.next();
			if (isReturningUser) {
				if (!rpsDao.verifyUser(userName)) {
					System.out.println(
							ConsoleControls.TEXT_RED + "\n\nNo such user exist. Game will auto restart in 2 seconds..."
									+ ConsoleControls.TEXT_RESET);
					ConsoleControls.holdConsole();
					continue homePage;
				} else {
					System.out.println(ConsoleControls.TEXT_RED + "\n\nWelcome Back! " + ConsoleControls.TEXT_BLUE
							+ userName + ConsoleControls.TEXT_RESET);
					userHighestScore = rpsDao.getScore(userName);
					ConsoleControls.holdConsole();
				}
			} else {
				if (!((int) userName.charAt(0) >= 65 && (int) userName.charAt(0) <= 90)) {
					try {
						throw new RockPaperScissorsExceptions(
								"Invalid User Name (User name must start with capital letter)");
					} catch (RockPaperScissorsExceptions e) {
						System.out.println(e.getMessage());
						ConsoleControls.holdConsole();
						continue homePage;
					}
				} else if (!(userName.matches("^[a-zA-Z0-9]*$"))) {
					try {
						throw new RockPaperScissorsExceptions(
								"Invalid User Name (User name must contain alphabets and digits only)");
					} catch (RockPaperScissorsExceptions e) {
						System.out.println(e.getMessage());
						ConsoleControls.holdConsole();
						continue homePage;
					}
				} else if (rpsDao.verifyUser(userName)) {
					try {
						throw new RockPaperScissorsExceptions("User name is already taken, try another one");
					} catch (RockPaperScissorsExceptions e) {
						System.out.println(e.getMessage());
						ConsoleControls.holdConsole();
						continue homePage;
					}
				} else {
					rpsDao.createRecord(userName);
				}
			}
			RockPaperScissors currentUser = new RockPaperScissors(userName, userHighestScore);
			updateHighScore(rpsDao);
			Random rand = new Random();
			gamePage: do {
				ConsoleControls.clearConsole();
				System.out.println("Current Score: " + currentUser.getCurrentScore() + "\t\t\t" + "Highest Score: "
						+ RockPaperScissors.getHighestScore() + " set by "
						+ RockPaperScissors.getHighestScoreUserName());
				System.out.println("Following keys are associated with each moves: ");
				System.out.println("Rock\t\t\t1");
				System.out.println("Paper\t\t\t2");
				System.out.println("Scissors\t\t3");
				System.out.println("Get All Record\t\t4");
				System.out.println("Get Individual Record\t5");
				System.out.println("Logout\t\t\t6");
				System.out.println("Exit Game\t\t7");
				System.out.print("\nEnter your choice: ");
				byte userChoice = sc.nextByte();
				byte computerChoice = (byte) rand.nextInt(1, 4);

				switch (userChoice) {
				case 1: {
					if (computerChoice == 3) {
						currentUser.setCurrentScore(currentUser.getCurrentScore() + 1);
						System.out.println("You won!");
					} else if (computerChoice == 2) {
						currentUser.setCurrentScore(0);
						System.out.println("Hard Luck, Try Again");
					} else {
						System.out.println("It's a tie");
					}
					ConsoleControls.holdConsole();
					break;
				}
				case 2: {
					if (computerChoice == 1) {
						currentUser.setCurrentScore(currentUser.getCurrentScore() + 1);
						System.out.println("You won!");
					} else if (computerChoice == 3) {
						currentUser.setCurrentScore(0);
						System.out.println("Hard Luck, Try Again");
					} else {
						System.out.println("It's a tie");
					}
					ConsoleControls.holdConsole();
					break;
				}
				case 3: {
					if (computerChoice == 2) {
						currentUser.setCurrentScore(currentUser.getCurrentScore() + 1);
						System.out.println("You won!");
					} else if (computerChoice == 1) {
						currentUser.setCurrentScore(0);
						System.out.println("Hard Luck, Try Again");
					} else {
						System.out.println("It's a tie");
					}
					ConsoleControls.holdConsole();
					break;
				}
				case 4: {
					HashMap<String, Integer> records = rpsDao.getAllRecords();
					for (Map.Entry<String, Integer> entry : records.entrySet()) {
						System.out.println(entry.getKey() + "\t\t\t" + entry.getValue());
					}
					ConsoleControls.holdConsole();
					break;
				}
				case 5: {
					System.out.print("Enter the userName whose record to search: ");
					String userNameToSearch = sc.next();
					Map.Entry<String, Integer> indivRecord = rpsDao.getIndividualRecord(userNameToSearch);
					if (indivRecord == null) {
						try {
							throw new RockPaperScissorsExceptions("No such user exist");
						} catch (RockPaperScissorsExceptions e) {
							System.out.println(e.getMessage());
							ConsoleControls.holdConsole();
							continue gamePage;
						}
					}
					System.out.println(indivRecord.getKey() + "\t\t\t" + indivRecord.getValue());
					ConsoleControls.holdConsole();
					break;
				}
				case 6: {
					System.out.println("Thank You! for playing");
					ConsoleControls.holdConsole();
					break gamePage;
				}
				case 7: {
					System.out.println("Thank You! for playing");
					break homePage;
				}
				default: {
					System.out.println("Invalid Input");
					ConsoleControls.holdConsole();
					continue gamePage;
				}

				}
				if (currentUser.getCurrentScore() > currentUser.getCurrentUserHighestScore()) {
					rpsDao.updateRecord(currentUser.getCurrentUserName(), currentUser.getCurrentScore());
				}
				if (currentUser.getCurrentScore() > RockPaperScissors.getHighestScore()) {
					updateHighScore(rpsDao);
				}
			} while (true);
		} while (true);
		sc.close();
	}

}
