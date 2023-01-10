package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Advent08 {

	private static final String filenamePath = "files/Advent/file08Secco.txt";

	public static void main(String[] args) throws Exception {
		// reading the input file
		String input = readFileAsString(filenamePath);

		// stack arrays
		int cols = input.split("\\r?\\n")[0].length();
		int rows = input.split("\\r?\\n").length;

		Map<Coord, Integer> treesMap = new HashMap<>();

		int i = 0;
		for (String line : input.split("\\r?\\n")) {
			for (int j = 0; j < line.length(); j++) {
				treesMap.put(new Coord(i, j), Integer.valueOf(line.charAt(j) + ""));
			}
			i++;
		}

		System.out.println("ANSWER1: " + part(treesMap, rows, cols));
		// MAC: 1854
		// SECCO: 1809

		System.out.println("ANSWER2: " + part2(treesMap, rows, cols));
		// MAC: 527340
		// SECCO:479400
	}

	private static int part(Map<Coord, Integer> treesMap, int rows, int cols) {

		Map<Coord, Integer> treesVilibility = new HashMap<>();

		// FROM LEFT
		for (int i = 0; i < rows; i++) {
			int highestFromTheLeft = -1;
			for (int j = 0; j < cols; j++) {
				if (highestFromTheLeft < treesMap.get(new Coord(i, j))) {
					treesVilibility.put(new Coord(i, j), 1);
					highestFromTheLeft = treesMap.get(new Coord(i, j));
				}
			}
		}

		// FROM RIGHT
		for (int i = 0; i < rows; i++) {
			int highestFromTheRight = -1;
			for (int j = cols - 1; j >= 0; j--) {
				if (highestFromTheRight < treesMap.get(new Coord(i, j))) {
					treesVilibility.put(new Coord(i, j), 1);
					highestFromTheRight = treesMap.get(new Coord(i, j));
				}
			}
		}

		// FROM TOP
		for (int j = 0; j < cols; j++) {
			int highestFromTheTop = -1;
			for (int i = 0; i < rows; i++) {

				if (highestFromTheTop < treesMap.get(new Coord(i, j))) {
					treesVilibility.put(new Coord(i, j), 1);
					highestFromTheTop = treesMap.get(new Coord(i, j));
				}
			}
		}

		// FROM BOTTOOM
		for (int j = cols - 1; j >= 0; j--) {
			int highestFromTheBottom = -1;
			for (int i = rows - 1; i >= 0; i--) {

				if (highestFromTheBottom < treesMap.get(new Coord(i, j))) {
					treesVilibility.put(new Coord(i, j), 1);
					highestFromTheBottom = treesMap.get(new Coord(i, j));
				}
			}
		}

		return treesVilibility.size();
	}

	private static int part2(Map<Coord, Integer> treesMap, int rows, int cols) {
		int highestVisibility = 0;

		for (int i = 1; i < rows - 1; i++) {
			for (int j = 1; j < cols - 1; j++) {

				int treesVisibleToLeft = 0;
				int treesVisibleToRight = 0;
				int treesVisibleToTop = 0;
				int treesVisibleToBottom = 0;

				boolean firstEqualToLeft = true;
				for (int k = j - 1; k >= 0; k--) {
					if (treesMap.get(new Coord(i, k)) < treesMap.get(new Coord(i, j)) && firstEqualToLeft) {
						treesVisibleToLeft++;
					} else if (treesMap.get(new Coord(i, k)) >= treesMap.get(new Coord(i, j)) && firstEqualToLeft
							&& firstEqualToLeft) {
						treesVisibleToLeft++;
						firstEqualToLeft = false;
					}
				}

				boolean firstEqualToRight = true;
				for (int k = j + 1; k < cols; k++) {
					if (treesMap.get(new Coord(i, k)) < treesMap.get(new Coord(i, j)) && firstEqualToRight) {
						treesVisibleToRight++;
					} else if (treesMap.get(new Coord(i, k)) >= treesMap.get(new Coord(i, j)) && firstEqualToRight) {
						treesVisibleToRight++;
						firstEqualToRight = false;
					}

				}

				boolean firstEqualToTheTop = true;
				for (int k = i - 1; k >= 0; k--) {
					if (treesMap.get(new Coord(k, j)) < treesMap.get(new Coord(i, j)) && firstEqualToTheTop) {
						treesVisibleToTop++;
					} else if (treesMap.get(new Coord(k, j)) >= treesMap.get(new Coord(i, j)) && firstEqualToTheTop) {
						treesVisibleToTop++;
						firstEqualToTheTop = false;
					}
				}

				boolean firstEqualToTheBottom = true;
				for (int k = i + 1; k < rows; k++) {
					if (treesMap.get(new Coord(k, j)) < treesMap.get(new Coord(i, j)) && firstEqualToTheBottom) {
						treesVisibleToBottom++;
					} else if (treesMap.get(new Coord(k, j)) >= treesMap.get(new Coord(i, j))
							&& firstEqualToTheBottom) {
						treesVisibleToBottom++;
						firstEqualToTheBottom = false;
					}
				}

				int currentVisibility = treesVisibleToLeft * treesVisibleToRight * treesVisibleToTop
						* treesVisibleToBottom;

				if (highestVisibility < currentVisibility) {
					highestVisibility = currentVisibility;
				}

			}

		}

		return highestVisibility;
	}

	private static final record Coord(int x, int y) {
	}

	private static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}
}
