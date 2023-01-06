package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Advent03 {

	private static final String filenamePath = "files/Advent/file03.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String input = readFileAsString(filenamePath);

		System.out.println("ANSWER1 totalPriority " + part(input));
		// Secco 7795
		// Mac: 7872

		System.out.println("ANSWER2 bedgePriority " + part2(input));
		// Secco: 2703
		// Mac: 2497
	}

	private static int part(String input) {
		String listOfDuplicateItems = "";

		for (String line : input.split("\\r?\\n")) {
			int lineLength = line.length();
			String beginning = line.substring(0, lineLength / 2);
			String ending = line.substring(lineLength / 2);

			String objectFound = "";

			for (int i = 0; i < beginning.length(); i++) {
				if (ending.contains(beginning.charAt(i) + "")) {
					objectFound = beginning.charAt(i) + "";
				}
			}
			listOfDuplicateItems += objectFound;
		}
		System.out.println("listOfDuplicateItems " + listOfDuplicateItems);
		System.out.println("listOfDuplicateItems.length() " + listOfDuplicateItems.length());

		int totalPriority = 0;
		for (int i = 0; i < listOfDuplicateItems.length(); i++) {
			totalPriority += getPriorityFromChar(listOfDuplicateItems.charAt(i));
		}
		return totalPriority;
	}

	private static int part2(String input) {
		String listOfBadges = "";
		String[] lines = input.split("\\r?\\n");
		for (int i = 0; i < lines.length; i += 3) {
			String objectFound = "";

			for (int j = 0; j < lines[i].length(); j++) {
				if (lines[i + 1].contains(lines[i].charAt(j) + "") && lines[i + 2].contains(lines[i].charAt(j) + "")) {
					objectFound = lines[i].charAt(j) + "";
				}
			}
			listOfBadges += objectFound;
		}

		int bedgePriority = 0;
		for (int i = 0; i < listOfBadges.length(); i++) {
			bedgePriority += getPriorityFromChar(listOfBadges.charAt(i));
		}
		System.out.println("listOfBadges " + listOfBadges);
		System.out.println("listOfBadges.length() " + listOfBadges.length());
		return bedgePriority;
	}

	private static int getPriorityFromChar(char c) {
		int intValue = c;
		if (intValue >= 65 && intValue <= 90) {
			intValue -= 38;
		} else if (intValue >= 97 && intValue <= 122) {
			intValue -= 96;
		}
		return intValue;
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}
}
