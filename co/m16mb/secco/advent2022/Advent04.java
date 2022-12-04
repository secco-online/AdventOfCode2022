package co.m16mb.secco.advent2022;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVReader;

public class Advent04 {

	private static final String filenamePath = "files/Advent/file04Secco.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		CSVReader reader = new CSVReader(new FileReader(filenamePath), ',');
		ArrayList<String[]> inputArray = new ArrayList<>();
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			inputArray.add(nextLine);
		}
		reader.close();
		System.out.println("Rows in file: " + inputArray.size());

		// transforming the String array to int array
		Iterator<String[]> it = inputArray.iterator();
		int[] intArray = new int[inputArray.size() * 4]; // two numbers for two
															// assignments
		int progressive = 0;
		while (it.hasNext()) {
			String[] oneLine = it.next();

			String assignment1 = oneLine[0];
			String beginning1 = assignment1.substring(0, assignment1.indexOf("-"));
			String ending1 = assignment1.substring(assignment1.indexOf("-") + 1);
			// System.out.println("beginning1: " + beginning1 + " ending1 " +
			// ending1);
			intArray[progressive++] = Integer.parseInt(beginning1);
			intArray[progressive++] = Integer.parseInt(ending1);

			String assignment2 = oneLine[1];
			String beginning2 = assignment2.substring(0, assignment2.indexOf("-"));
			String ending2 = assignment2.substring(assignment2.indexOf("-") + 1);
			// System.out.println("beginning2: " + beginning2 + " ending2 " +
			// ending2);
			intArray[progressive++] = Integer.parseInt(beginning2);
			intArray[progressive++] = Integer.parseInt(ending2);
		}

		// solving the first part of the puzzle
		int sumOfFullOverlaps = 0;
		for (int i = 0; i < intArray.length; i += 4) {
			if (intArray[i] <= intArray[i + 2] && intArray[i + 1] >= intArray[i + 3]) {
				sumOfFullOverlaps++;
				// System.out.println("Full Overlap 1>=2: " + intArray[i] + "-"
				// + intArray[i + 1] + "," + intArray[i + 2]
				// + "-" + intArray[i + 3]);
			} else if (intArray[i] >= intArray[i + 2] && intArray[i + 1] <= intArray[i + 3]) {
				sumOfFullOverlaps++;
				// System.out.println("Full Overlap 2>=1: " + intArray[i] + "-"
				// + intArray[i + 1] + "," + intArray[i + 2]
				// + "-" + intArray[i + 3]);
			} else {
				// System.out.println("No Full Overlap  : " + intArray[i] + "-"
				// + intArray[i + 1] + "," + intArray[i + 2]
				// + "-" + intArray[i + 3]);
			}

		}
		System.out.println("ANSWER1: sumOfFullOverlaps " + sumOfFullOverlaps);
		// TOTAL SECCO: 462
		// TOTAL MAC: 528

		// solving the second part of the puzzle
		int sumOfSomeOverlaps = 0;
		for (int i = 0; i < intArray.length; i += 4) {
			if (intArray[i] > intArray[i + 3] || intArray[i + 1] < intArray[i + 2]) {
				// System.out.println("No Overlap  : " + intArray[i] + "-" +
				// intArray[i + 1] + "," + intArray[i + 2]
				// + "-" + intArray[i + 3]);
			} else {
				sumOfSomeOverlaps++;
				// System.out.println("Some Overlap: " + intArray[i] + "-" +
				// intArray[i + 1] + "," + intArray[i + 2]
				// + "-" + intArray[i + 3]);
			}

		}
		System.out.println("ANSWER2: sumOfSomeOverlaps " + sumOfSomeOverlaps);
		// TOTAL SECCO: 835
		// TOTAL MAC: 881

	}
}
