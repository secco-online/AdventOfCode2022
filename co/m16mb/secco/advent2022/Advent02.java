package co.m16mb.secco.advent2022;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVReader;

public class Advent02 {

	private static final String filenamePath = "files/Advent/file02.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		CSVReader reader = new CSVReader(new FileReader(filenamePath), ' ');
		ArrayList<String[]> inputArray = new ArrayList<String[]>();
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			inputArray.add(nextLine);
		}
		reader.close();
		System.out.println("Rows in file: " + inputArray.size());

		// solving the first part of the puzzle
		{
			Iterator<String[]> it = inputArray.iterator();

			// A for Rock, B for Paper, and C for Scissors
			// in response: X for Rock, Y for Paper, and Z for Scissors
			// score for the shape you selected (1 for Rock, 2 for Paper, and 3
			// for Scissors)
			// score for the outcome of the round (0 if you lost, 3 if the round
			// was a draw, and 6 if you won)

			// X Y Z
			// A 3 6 0
			// B 0 3 6
			// C 6 0 3

			int myPoints = 0;
			int myPointsResult = 0;

			while (it.hasNext()) {
				String[] obj = it.next();
				if ("X".equalsIgnoreCase(obj[1]))
					myPoints += 1;
				else if ("Y".equalsIgnoreCase(obj[1]))
					myPoints += 2;
				else if ("Z".equalsIgnoreCase(obj[1]))
					myPoints += 3;

				if ("A".equalsIgnoreCase(obj[0]) && "X".equalsIgnoreCase(obj[1]))
					myPointsResult += 3; // draw rock
				else if ("B".equalsIgnoreCase(obj[0]) && "Y".equalsIgnoreCase(obj[1]))
					myPointsResult += 3; // draw paper
				else if ("C".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
					myPointsResult += 3; // draw scissors

				else if ("A".equalsIgnoreCase(obj[0]) && "Y".equalsIgnoreCase(obj[1]))
					myPointsResult += 6; // win
				else if ("B".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
					myPointsResult += 6; // win
				else if ("C".equalsIgnoreCase(obj[0]) && "X".equalsIgnoreCase(obj[1]))
					myPointsResult += 6; // win

			}
			System.out.println("MyPoints: " + myPoints);
			System.out.println("MyPointsResult: " + myPointsResult);

			System.out.println("ANSWER1: MyPoints TOTAL: " + (myPoints + myPointsResult));
			// TOTAL SECCO: 11475
			// TOTAL MAC: 13565

		}

		// solving the first part of the puzzle
		{
			Iterator<String[]> it = inputArray.iterator();

			// A for Rock, B for Paper, and C for Scissors
			// in response: X for Rock, Y for Paper, and Z for Scissors
			// score for the shape you selected (1 for Rock, 2 for Paper, and 3
			// for Scissors)
			// score for the outcome of the round (0 if you lost, 3 if the round
			// was a draw, and 6 if you won)

			// X Y Z
			// A 3 6 0
			// B 0 3 6
			// C 6 0 3

			// X Y Z
			// A Z3+0 X1+3 Y2+6
			// B X1+0 Y2+3 Z3+6
			// C Y2+0 Z3+3 X1+6

			int myPointsTotal = 0;

			while (it.hasNext()) {
				String[] obj = it.next();

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
					myPointsTotal += 8; // won
				else if ("B".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
					myPointsTotal += 9; // won
				else if ("C".equalsIgnoreCase(obj[0]) && "Z".equalsIgnoreCase(obj[1]))
					myPointsTotal += 7; // won

			}
			System.out.println("ALSWER2: MyPointTotal: " + myPointsTotal);
			// TOTAL SECCO: 16862
			// TOTAL MAC: 12424

		}

	}

}
