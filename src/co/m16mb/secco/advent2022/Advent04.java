package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Advent04 {

	private static final String filenamePath = "files/Advent/file04Secco.txt";

	public static void main(String[] args) throws Exception {

		String input = readFileAsString(filenamePath);

		int part1SumOfFullOverlaps = 0;
		int part2SumOfSomeOverlaps = 0;
		for (String string : input.split("\\r?\\n")) {
			String[] oneLine = string.split(",");
			String[] assignmentArray1 = oneLine[0].split("-");
			int beg1 = Integer.parseInt(assignmentArray1[0]);
			int end1 = Integer.parseInt(assignmentArray1[1]);
			String[] assignmentArray2 = oneLine[1].split("-");
			int beg2 = Integer.parseInt(assignmentArray2[0]);
			int end2 = Integer.parseInt(assignmentArray2[1]);

			// solving the first part of the puzzle
			if (beg1 <= beg2 && end1 >= end2) {
				part1SumOfFullOverlaps++;
			} else if (beg1 >= beg2 && end1 <= end2) {
				part1SumOfFullOverlaps++;
			}

			// solving the second part of the puzzle
			if (beg1 <= end2 && end1 >= beg2) {
				part2SumOfSomeOverlaps++;
			}
		}

		System.out.println("ANSWER1: " + part1SumOfFullOverlaps);
		// TOTAL SECCO: 462
		// TOTAL MAC: 528

		System.out.println("ANSWER2: " + part2SumOfSomeOverlaps);
		// TOTAL SECCO: 835
		// TOTAL MAC: 881

	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}
}
