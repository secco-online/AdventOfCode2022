package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Advent12 {

	private static final String filenamePath = "files/Advent/file12.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		System.out.println("ANSWER1 " + part1(inputArray));

		// MAC 352
		// SECCO 339
		System.out.println("ANSWER2 " + part2(inputArray));

		// System.out.println("ANSWER2 " + part2(inputArray));
		// MAC 345
		// SECCO 332

	}

	private static int part1(ArrayList<String> inputArray) {
		Point startingPoint = getStartPoint('S', inputArray);
		return findThePathFrom(startingPoint, inputArray);
	}

	private static int part2(ArrayList<String> inputArray) {
		ArrayList<Point> startingPoints = getAllStartPoints('a', inputArray);

		int shortestPathFromA = Integer.MAX_VALUE;
		for (Point point : startingPoints) {
			int currentLenght = findThePathFrom(point, inputArray);
			if (currentLenght < shortestPathFromA)
				shortestPathFromA = currentLenght;
		}

		return shortestPathFromA;
	}

	private final static Point getStartPoint(char startingPoint, ArrayList<String> inputArray) {

		for (int y = 0; y < inputArray.size(); y++) {
			String line = inputArray.get(y);
			int x = line.indexOf(startingPoint);
			if (x != -1) {
				return new Point(x, y);
			}
		}

		throw new IllegalStateException("No starting point");
	}

	private final static ArrayList<Point> getAllStartPoints(char startingPoint, ArrayList<String> inputArray) {
		ArrayList<Point> startingPoints = new ArrayList<>();

		for (int y = 0; y < inputArray.size(); y++) {
			String line = inputArray.get(y);
			int x = line.indexOf(startingPoint);
			if (x != -1) {
				startingPoints.add(new Point(x, y));
			}
		}
		return startingPoints;
	}

	private static int findThePathFrom(Point startingPoint, List<String> inputArray) {
		HashSet<Point> visited = new HashSet<>();
		Queue<Path> queue = new LinkedList<>();
		queue.add(new Path(startingPoint, 0));
		while (!queue.isEmpty() && queue.peek().endPoint().getValue(inputArray) != 'E') {
			var next = queue.poll();
			List<Point> neighbours = next.endPoint().getNeighbours();
			for (Point point : neighbours) {
				if (point.isValid(inputArray) && !visited.contains(point)
						&& point.isReachableFrom(next.endPoint(), inputArray)) {
					queue.add(new Path(point, next.stepCount() + 1));
					visited.add(point);
				}
			}
		}
		if (queue.isEmpty()) {
			return Integer.MAX_VALUE;
		}
		/*
		 * int counter = 0; for (Path path : queue) { System.out.println(counter++ +
		 * " queue size " + path.endPoint() + " steps " + path.stepCount() + " value " +
		 * path.endPoint().getValue(inputArray)); }
		 */

		// return the steps of last path

		return queue.poll().stepCount();
	}

	private static final record Point(int x, int y) {
		List<Point> getNeighbours() {
			return List.of(new Point(x - 1, y), new Point(x + 1, y), new Point(x, y - 1), new Point(x, y + 1));
		}

		boolean isValid(List<String> inputArray) {
			return y >= 0 && y < inputArray.size() && x >= 0 && x < inputArray.get(y).length();
		}

		char getValue(List<String> inputArray) {
			return inputArray.get(y).charAt(x);
		}

		boolean isReachableFrom(Point from, List<String> lines) {
			char fromValue = from.getValue(lines);
			char toValue = getValue(lines);

			// to be able to start
			if (fromValue == 'S') {
				fromValue = 'a';
			}

			// to be able to reach the end
			if (toValue == 'E') {
				toValue = 'z';
			}

			return (int) toValue <= (int) fromValue + 1;
		}
	}

	private static final record Path(Point endPoint, int stepCount) {

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
