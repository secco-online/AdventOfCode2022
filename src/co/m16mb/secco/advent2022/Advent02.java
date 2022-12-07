package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Advent02 {

	private static final String filenamePath = "files/Advent/file02.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		// A for Rock, B for Paper, and C for Scissors
		// in response: X for Rock, Y for Paper, and Z for Scissors
		// score for the shape you selected (1 for Rock, 2 for Paper, and 3
		// for Scissors)
		// score for the outcome of the round (0 if you lost, 3 if the round
		// was a draw, and 6 if you won)

		// solving the first part of the puzzle
		// . X . Y . Z
		// A 3+1 6+2 0+3
		// B 0+1 3+2 6+3
		// C 6+1 0+2 3+3

		int myPointsResult = 0;
		for (Iterator<String> it = inputArray.iterator(); it.hasNext();) {
			String[] obj = it.next().split(" ");
			
			if ("A".equalsIgnoreCase(obj[0]) && "X".equalsIgnoreCase(obj[1]))
				myPointsResult += 4; // draw rock
			else if ("B".equalsIgnoreCase(obj[0]) && "Y".equalsIgnoreCase(obj[1]))
				myPointsResult += 5; // draw paper
			else if ("C".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
				myPointsResult += 6; // draw scissors

			else if ("A".equalsIgnoreCase(obj[0]) && "Y".equalsIgnoreCase(obj[1]))
				myPointsResult += 8; // win
			else if ("B".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
				myPointsResult += 9; // win
			else if ("C".equalsIgnoreCase(obj[0]) && "X".equalsIgnoreCase(obj[1]))
				myPointsResult += 7; // win

			else if ("A".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
				myPointsResult += 3; // lost with scissors
			else if ("B".equalsIgnoreCase(obj[0]) && "X".equalsIgnoreCase(obj[1]))
				myPointsResult += 1; // lost with rock
			else if ("C".equalsIgnoreCase(obj[0]) && "Y".equalsIgnoreCase(obj[1]))
				myPointsResult += 2; // lost with paper

		}

		System.out.println("ANSWER1: " + myPointsResult);
		// TOTAL SECCO: 11475
		// TOTAL MAC: 13565

		// solving the second part of the puzzle

		// . X .. Y .. Z
		// A Z3+0 X1+3 Y2+6
		// B X1+0 Y2+3 Z3+6
		// C Y2+0 Z3+3 X1+6

		int myPointsTotal = 0;

		for (Iterator<String> it = inputArray.iterator(); it.hasNext();) {
			String[] obj = it.next().split(" ");

			if ("A".equalsIgnoreCase(obj[0]) && "X".equalsIgnoreCase(obj[1]))
				myPointsTotal += 3; // lost
			else if ("B".equalsIgnoreCase(obj[0]) && "X".equalsIgnoreCase(obj[1]))
				myPointsTotal += 1; // lost
			else if ("C".equalsIgnoreCase(obj[0]) && "X".equalsIgnoreCase(obj[1]))
				myPointsTotal += 2; // lost

			else if ("A".equalsIgnoreCase(obj[0]) && "Y".equalsIgnoreCase(obj[1]))
				myPointsTotal += 4; // draw
			else if ("B".equalsIgnoreCase(obj[0]) && "Y".equalsIgnoreCase(obj[1]))
				myPointsTotal += 5; // draw
			else if ("C".equalsIgnoreCase(obj[0]) && "Y".equalsIgnoreCase(obj[1]))
				myPointsTotal += 6; // draw

			else if ("A".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
				myPointsTotal += 8; // win
			else if ("B".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
				myPointsTotal += 9; // win
			else if ("C".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
				myPointsTotal += 7; // win

		}
		System.out.println("ALSWER2: " + myPointsTotal);
		// TOTAL SECCO: 16862
		// TOTAL MAC: 12424

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
