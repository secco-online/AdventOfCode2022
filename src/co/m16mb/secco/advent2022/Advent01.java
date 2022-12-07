package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Advent01 {

	private static final String filenamePath = "files/Advent/file01.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		// solving the first part of the puzzle
		ArrayList<Integer> outputArray = new ArrayList<>();

		int elfCalories = 0;
		for (Iterator<String> it = inputArray.iterator(); it.hasNext();) {
			String line = it.next();
			if ("".equals(line.trim())) {
				outputArray.add(elfCalories);
				elfCalories = 0;
			} else {
				elfCalories += Integer.parseInt(line);
			}
		}

		// sorting
		Collections.sort(outputArray, Collections.reverseOrder());

		System.out.println("ANSWER1: top: " + outputArray.get(0).toString());
		// Part1 Secco: 72602
		// Part1 Mac: 67450

		// solving the second part of the puzzle
		int topThree = outputArray.get(0) + outputArray.get(1) + outputArray.get(2);
		System.out.println("ANSWER2: topThree: " + topThree);
		// Part2 Secco: 207410
		// Part2 Mac: 199357

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
