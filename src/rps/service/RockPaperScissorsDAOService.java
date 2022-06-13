package rps.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RockPaperScissorsDAOService {
	private static final String FILE_PATH = "D://RPS_RECORDS.txt";

	public void createFile() {
		File fObj = new File(FILE_PATH);
		try {
			fObj.createNewFile();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void createRecord(String userName) {
		FileWriter fwObj = null;
		try {
			fwObj = new FileWriter(FILE_PATH, true);
			fwObj.write(userName + " " + 0 + "\n");
		} catch (IOException e) {
			System.out.println("Sorry for the trouble caused, can't access the file right now.");
		} finally {
			try {
				fwObj.close();
			} catch (IOException e) {
				System.out.println("Sorry for the trouble caused, can't close the file right now.");
			}
		}
	}

	public HashMap<String, Integer> getAllRecords() {
		BufferedReader bfReader = null;
		try {
			bfReader = new BufferedReader(new FileReader(FILE_PATH));
		} catch (FileNotFoundException e) {
			createFile();
		}
		String fileContent = "";
		try {
			String s;
			while ((s = bfReader.readLine()) != null) {
				fileContent += ("\n" + s);
			}
		} catch (IOException e) {
			System.out.println("Sorry for the trouble caused, can't access the file right now.");
		} finally {
			try {
				bfReader.close();
			} catch (IOException e) {
				System.out.println("Sorry for the trouble caused, can't close the file right now.");
			}
		}
		String[] recordString = fileContent.split("\n");
		HashMap<String, Integer> record = new HashMap<String, Integer>();
		String individualRecord[] = new String[2];
		for (String entry : recordString) {
			if (!entry.isEmpty()) {
				individualRecord = entry.split(" ");
				record.put(individualRecord[0], Integer.parseInt(individualRecord[1].trim()));
			}
		}

		return record;
	}

	public Map.Entry<String, Integer> getIndividualRecord(String userName) {
		HashMap<String, Integer> records = getAllRecords();
		Map.Entry<String, Integer> individualRecord = null;
		for (Map.Entry<String, Integer> entry : records.entrySet()) {
			if (entry.getKey().equals(userName)) {
				individualRecord = entry;
				break;
			}
		}
		return individualRecord;
	}

	public boolean verifyUser(String userName) {
		HashMap<String, Integer> records = getAllRecords();
		return records.containsKey(userName);
	}

	public int getHighestScore() {
		HashMap<String, Integer> records = getAllRecords();
		int highestScore = (Collections.max(records.values()));
		return highestScore;
	}

	public String getHighestScoreUserName() {
		HashMap<String, Integer> records = getAllRecords();
		int highestScore = (Collections.max(records.values()));
		String userWithHighestScore = null;
		for (Map.Entry<String, Integer> entry : records.entrySet()) {
			if (entry.getValue() == highestScore) {
				userWithHighestScore = entry.getKey();
				break;
			}
		}
		return userWithHighestScore;
	}

	public int getScore(String userName) {
		HashMap<String, Integer> records = getAllRecords();
		return records.get(userName);
	}

	public void updateRecord(String currentUserName, int currentScore) {
		HashMap<String, Integer> records = getAllRecords();
		records.put(currentUserName, currentScore);
		updateFile(records);

	}

	public void updateFile(HashMap<String, Integer> records) {
		FileWriter fwObj = null;
		try {
			fwObj = new FileWriter(FILE_PATH);
			for (Map.Entry<String, Integer> entry : records.entrySet()) {
				fwObj.write(entry.getKey() + " " + entry.getValue() + "\n");
			}
		} catch (IOException e) {
			System.out.println("Sorry for the trouble caused, can't access the file right now.");
		} finally {
			try {
				fwObj.close();
			} catch (IOException e) {
				System.out.println("Sorry for the trouble caused, can't close the file right now.");
			}
		}
	}

}
