package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Advent02 {

	private static final String filenamePath = "files/Advent/file02.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String input = readFileAsString(filenamePath);

		System.out.println("ANSWER1: " + part1(input));
		// TOTAL SECCO: 11475
		// TOTAL MAC: 13565

		// solving the second part of the puzzle
		System.out.println("ALSWER2: " + part2(input));
		// TOTAL SECCO: 16862
		// TOTAL MAC: 12424

	}

	private static int part1(String input) {
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
		for (String line : input.split("\\r?\\n")) {
			String[] obj = line.split(" ");

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
		return myPointsResult;
	}

	private static int part2(String input) {

		// . X .. Y .. Z
		// A Z3+0 X1+3 Y2+6
		// B X1+0 Y2+3 Z3+6
		// C Y2+0 Z3+3 X1+6

		int myPointsTotal = 0;

		for (String line : input.split("\\r?\\n")) {
			String[] obj = line.split(" ");

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
		return myPointsTotal;
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}

}
