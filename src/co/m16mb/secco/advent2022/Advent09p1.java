package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Advent09p1 {

	private static final String filenamePath = "files/Advent/file09Secco.txt";

	public static void main(String[] args) throws IOException {
		{
		}
		// reading the input file
		ArrayList<String[]> inputArray = readFileToArray(filenamePath);

		// stack arrays
		int cols = 2000;
		int rows = 2000;

		int[][] visitedByTail = new int[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				visitedByTail[i][j] = 0;
			}
		}

		int[][] headTailCoordinates = new int[10][2];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 2; j++) {
				headTailCoordinates[i][j] = 0;
			}
		}

		int headX = 0;
		int headY = 0;
		int tailX = 0;
		int tailY = 0;

		visitedByTail[1000 + tailX][1000 + tailY] = 1;

		for (String[] oneLine : inputArray) {
			int moveBy = Integer.parseInt(oneLine[1]);
			String direction = oneLine[0];
			for (int i = 0; i < moveBy; i++) {
				// move head coordinates
				if ("R".equals(direction)) {
					headX++;
					headTailCoordinates[0][0]++;
				} else if ("U".equals(direction)) {
					headY++;
					headTailCoordinates[0][1]++;
				} else if ("D".equals(direction)) {
					headY--;
					headTailCoordinates[0][1]--;
				} else if ("L".equals(direction)) {
					headX--;
					headTailCoordinates[0][0]--;
				}

				// calculate distance of tail

				if (headY == tailY && (headX == tailX + 1 || headX == tailX - 1)) {
					System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
							+ " do nothing, tail one to left or right");

				} else if (headX == tailX && (headY == tailY + 1 || headY == tailY - 1)) {
					System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
							+ " do nothing, tail one to up or down");
				} else if ((headX == tailX + 1 || headX == tailX - 1) && (headY == tailY + 1 || headY == tailY - 1)) {
					System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
							+ " do nothing, tail one accross");
				} else {
					// System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY:
					// " + tailY + " MOVE TAIL");

					if (headY == tailY && headX == tailX + 2) {
						System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
								+ " MOVING TAIL RIGHT");
						tailX++;
					} else if (headY == tailY && headX == tailX - 2) {
						System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
								+ " MOVING TAIL LEFT");
						tailX--;
					} else if (headX == tailX && headY == tailY + 2) {
						System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
								+ " MOVING TAIL UP");
						tailY++;
					} else if (headX == tailX && headY == tailY - 2) {
						System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
								+ " MOVING TAIL DOWN");
						tailY--;
					} else if ((headX == tailX - 1 && headY == tailY + 2)
							|| (headX == tailX - 2 && headY == tailY + 1)) {
						System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
								+ " MOVING TAIL UP-LEFT");
						tailX--;
						tailY++;
					} else if ((headX == tailX - 2 && headY == tailY - 1)
							|| (headX == tailX - 1 && headY == tailY - 2)) {
						System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
								+ " MOVING TAIL DOWN-LEFT");
						tailX--;
						tailY--;
					} else if ((headX == tailX + 1 && headY == tailY - 2)
							|| (headX == tailX + 2 && headY == tailY - 1)) {
						System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
								+ " MOVING TAIL DOWN-RIGHT");
						tailX++;
						tailY--;
					} else if ((headX == tailX + 2 && headY == tailY + 1)
							|| (headX == tailX + 1 && headY == tailY + 2)) {
						System.out.println("HX: " + headX + " HY: " + headY + " TX: " + tailX + " TY: " + tailY
								+ " MOVING TAIL UP-RIGHT");
						tailX++;
						tailY++;
					}

					visitedByTail[1000 + tailX][1000 + tailY] = 1;
				}

			}

			if (true) {

			}
		}

		int visitedPlaces = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				visitedPlaces += visitedByTail[i][j];
			}
		}

		System.out.println("ANSWER 1: " + visitedPlaces);

		// MAC: 6087
		// SECCO: 6314
	}

	private static ArrayList<String[]> readFileToArray(String filename) {
		ArrayList<String[]> inputArray = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				inputArray.add(line.split(" "));
				// System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.println("Rows in file: " + inputArray.size());
		return inputArray;
	}
}
