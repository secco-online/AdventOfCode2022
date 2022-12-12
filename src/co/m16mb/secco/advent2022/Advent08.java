package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Advent08 {

	private static final String filenamePath = "files/Advent/file08Secco.txt";

	public static void main(String[] args) throws IOException {
		{
		}
		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		// stack arrays
		int cols = inputArray.get(0).length();
		int rows = inputArray.size();

		int[][] trees = new int[rows][cols];
		int[][] visibility = new int[rows][cols];

		int iterator = 0;
		for (Iterator<String> it = inputArray.iterator(); it.hasNext();) {
			String line = it.next();
			for (int j = 0; j < line.length(); j++) {
				trees[iterator][j] = Integer.valueOf(line.charAt(j) + "");
				visibility[iterator][j] = 0;
			}
			iterator++;

		}

		System.out.println();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(trees[i][j]);
			}
			System.out.println();
		}

		System.out.println();

		// FROM LEFT
		for (int i = 0; i < rows; i++) {
			int highestFromTheLeft = -1;
			for (int j = 0; j < cols; j++) {
				if (highestFromTheLeft < trees[i][j]) {
					visibility[i][j] = 1;
					highestFromTheLeft = trees[i][j];
				}
			}
		}

		// FROM RIGHT
		for (int i = 0; i < rows; i++) {
			int highestFromTheRight = -1;
			for (int j = cols - 1; j >= 0; j--) {
				if (highestFromTheRight < trees[i][j]) {
					visibility[i][j] = 1;
					highestFromTheRight = trees[i][j];
				}
			}
		}

		// FROM TOP
		for (int j = 0; j < cols; j++) {
			int highestFromTheTop = -1;
			for (int i = 0; i < rows; i++) {

				if (highestFromTheTop < trees[i][j]) {
					visibility[i][j] = 1;
					highestFromTheTop = trees[i][j];
				}
			}
		}

		// FROM BOTTOOM
		for (int j = cols - 1; j >= 0; j--) {
			int highestFromTheBottom = -1;
			for (int i = rows - 1; i >= 0; i--) {

				if (highestFromTheBottom < trees[i][j]) {
					visibility[i][j] = 1;
					highestFromTheBottom = trees[i][j];
				}
			}
		}

		int totalTreesVisible = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (visibility[i][j] == 1) {
					totalTreesVisible++;
				}
				System.out.print(visibility[i][j]);
			}
			System.out.println();
		}

		System.out.println("ANSWER1: " + totalTreesVisible);
		// MAC: 1854
		// SECCO: 1809

		int highestVisibility = 0;

		// PUZZLE 2
		for (int i = 1; i < rows - 1; i++) {
			for (int j = 1; j < cols - 1; j++) {

				int treesVisibleToLeft = 0;
				int treesVisibleToRight = 0;
				int treesVisibleToTop = 0;
				int treesVisibleToBottom = 0;

				boolean firtsEqualToLeft = true;
				for (int k = j - 1; k >= 0; k--) {
					if (trees[i][k] < trees[i][j] && firtsEqualToLeft) {
						treesVisibleToLeft++;
					} else if (trees[i][k] >= trees[i][j] && firtsEqualToLeft && firtsEqualToLeft) {
						treesVisibleToLeft++;
						firtsEqualToLeft = false;
					}
				}

				boolean firtsEqualToRight = true;
				for (int k = j + 1; k < cols; k++) {
					if (trees[i][k] < trees[i][j] && firtsEqualToRight) {
						treesVisibleToRight++;
						// System.out.println("i " + i + " j " + j+ " trees[i][j] " +trees[i][j]+ " tree
						// to right " +trees[i][k]+" smaller");
					} else if (trees[i][k] >= trees[i][j] && firtsEqualToRight) {
						treesVisibleToRight++;
						firtsEqualToRight = false;
					}

				}

				boolean firtsEqualToTheTop = true;
				for (int k = i - 1; k >= 0; k--) {
					if (trees[k][j] < trees[i][j] && firtsEqualToTheTop) {
						treesVisibleToTop++;
					} else if (trees[k][j] >= trees[i][j] && firtsEqualToTheTop) {
						treesVisibleToTop++;
						firtsEqualToTheTop = false;
					}
				}

				boolean firtsEqualToTheBottom = true;
				for (int k = i + 1; k < rows; k++) {
					if (trees[k][j] < trees[i][j] && firtsEqualToTheBottom) {
						treesVisibleToBottom++;
					} else if (trees[k][j] >= trees[i][j] && firtsEqualToTheBottom) {
						treesVisibleToBottom++;
						firtsEqualToTheBottom = false;
					}
				}

				int currentVisibility = treesVisibleToLeft * treesVisibleToRight * treesVisibleToTop
						* treesVisibleToBottom;

				if (highestVisibility < currentVisibility) {

					highestVisibility = currentVisibility;
					/*
					 * System.out.println("tree hight " + trees[i][j]);
					 * System.out.println("treesVisibleToTop " + treesVisibleToTop);
					 * System.out.println("treesVisibleToLeft " + treesVisibleToLeft);
					 * System.out.println("treesVisibleToRight " + treesVisibleToRight);
					 * System.out.println("treesVisibleToBottom " + treesVisibleToBottom);
					 * System.out.println("coordinatesCols: " + coordinatesCols);
					 * System.out.println("coordinatesRows: " + coordinatesRows);
					 * System.out.println("highestVisibility: " + highestVisibility);
					 */
				}

			}

		}

		System.out.println("ANSWER2: " + highestVisibility);
		// MAC: 527340
		// SECCO:479400
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

		// System.out.println("Rows in file: " + inputArray.size());
		return inputArray;
	}
}
