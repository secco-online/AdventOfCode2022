package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Advent04 {

	private static final String filenamePath = "files/Advent/file04.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		int sumOfFullOverlaps = 0;
		int sumOfSomeOverlaps = 0;
		for (Iterator<String> it = inputArray.iterator(); it.hasNext();) {
			String[] oneLine = it.next().split(",");
			String[] assignmentArray1 = oneLine[0].split("-");
			int beg1 = Integer.parseInt(assignmentArray1[0]);
			int end1 = Integer.parseInt(assignmentArray1[1]);
			String[] assignmentArray2 = oneLine[1].split("-");
			int beg2 = Integer.parseInt(assignmentArray2[0]);
			int end2 = Integer.parseInt(assignmentArray2[1]);

			// solving the first part of the puzzle
			if (beg1 <= beg2 && end1 >= end2) {
				sumOfFullOverlaps++;
				// System.out.println("Full Overlap 1>=2:");
			} else if (beg1 >= beg2 && end1 <= end2) {
				sumOfFullOverlaps++;
				// System.out.println("Full Overlap 2>=1:");
			} else {
				// System.out.println("No Full Overlap  : ");
			}

			// solving the second part of the puzzle
			if (beg1 > end2 || end1 < beg2) {
				// System.out.println("No Overlap  : ");
			} else {
				sumOfSomeOverlaps++;
				// System.out.println("Some Overlap: ");
			}

		}

		System.out.println("ANSWER1: sumOfFullOverlaps " + sumOfFullOverlaps);
		// TOTAL SECCO: 462
		// TOTAL MAC: 528

		System.out.println("ANSWER2: sumOfSomeOverlaps " + sumOfSomeOverlaps);
		// TOTAL SECCO: 8351
		// TOTAL MAC: 881

	}

	private static ArrayList<String> readFileToArray(String filename) {
		ArrayList<String> inputArray = new ArrayList<String>();

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
