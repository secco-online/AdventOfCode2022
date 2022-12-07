package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Advent03 {

	private static final String filenamePath = "files/Advent/file03.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		// solving the first part of the puzzle
		String listOfDuplicateItems = "";

		for (Iterator<String> it = inputArray.iterator(); it.hasNext();) {
			String line = it.next();
			int lineLength = line.length();
			String beginning = line.substring(0, lineLength / 2);
			String ending = line.substring(lineLength / 2);

			String objectFound = "";

			for (int i = 0; i < beginning.length(); i++) {
				if (ending.contains(beginning.charAt(i) + "")) {
					objectFound = beginning.charAt(i) + "";
				}
			}
			// System.out.println(beginning + " + " + ending + " objectFound " +
			// objectFound);
			listOfDuplicateItems += objectFound;
		}
		System.out.println("listOfDuplicateItems " + listOfDuplicateItems);
		System.out.println("listOfDuplicateItems.length() " + listOfDuplicateItems.length());

		int totalPriority = 0;
		for (int i = 0; i < listOfDuplicateItems.length(); i++) {
			totalPriority += getPriorityFromChar(listOfDuplicateItems.charAt(i));
		}
		System.out.println("ANSWER1 totalPriority " + totalPriority);
		// Secco 7795
		// Mac: 7872

		// solving the second part of the puzzle
		String listOfBadges = "";
		for (int i = 0; i < inputArray.size(); i += 3) {
			/*
			 * System.out.println("GROUP " + ((i/3)+1));
			 * System.out.println("Elf1 " + inputArray2.get(i));
			 * System.out.println("Elf2 " + inputArray2.get(i+1));
			 * System.out.println("Elf3 " + inputArray2.get(i+2));
			 */
			String objectFound = "";

			for (int j = 0; j < inputArray.get(i).length(); j++) {
				if (inputArray.get(i + 1).contains(inputArray.get(i).charAt(j) + "")
						&& inputArray.get(i + 2).contains(inputArray.get(i).charAt(j) + "")) {
					// count priority for only one object objectFound +=
					// obj[0].charAt(i) + "";
					objectFound = inputArray.get(i).charAt(j) + "";
				}
			}
			listOfBadges += objectFound;
			// System.out.println("objectFound BEDGE " + objectFound);

		}

		int bedgePriority = 0;
		for (int i = 0; i < listOfBadges.length(); i++) {
			bedgePriority += getPriorityFromChar(listOfBadges.charAt(i));
		}
		System.out.println("listOfBadges " + listOfBadges);
		System.out.println("listOfBadges.length() " + listOfBadges.length());
		System.out.println("ANSWER2 bedgePriority " + bedgePriority);
		// Secco: 2703
		// Mac: 2497
	}

	private static int getPriorityFromChar(char c) {
		int intValue = c;
		if (intValue >= 65 && intValue <= 90) {
			intValue -= 38;
		} else if (intValue >= 97 && intValue <= 122) {
			intValue -= 96;
		}
		// System.out.println("Priority " + intValue + " for char " +c );
		return intValue;
	}

	private static ArrayList<String> readFileToArray(String filename) {
		ArrayList<String> inputArray = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				inputArray.add(line);
				// System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Rows in file: " + inputArray.size());
		return inputArray;
	}
}
