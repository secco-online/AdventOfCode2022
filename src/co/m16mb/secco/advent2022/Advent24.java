package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Advent24 {

	private static final String filenamePath = "files/Advent/file24Secco.txt";

	public static void main(String[] args) throws Exception {

		String fileContents = readFileAsString(filenamePath);

		List<BlizardMap> blizardSteps = readBlizardMap(fileContents);
		System.out.println("ANSWER1: " + part1(blizardSteps));
		// MAC 332
		// SECCO 260

		System.out.println("ANSWER2: " + part2(blizardSteps));
		// MAC 942
		// SECCO 747
	}

	private static int part1(List<BlizardMap> blizardSteps) {
		PositionInTime exit = findTripLenght(blizardSteps, new PositionInTime(0, blizardSteps.get(0).start),
				blizardSteps.get(0).exit);
		return exit.minute();
	}

	private static int part2(List<BlizardMap> blizardSteps) {
		PositionInTime start = new PositionInTime(0, blizardSteps.get(0).start);
		PositionInTime exit = findTripLenght(blizardSteps, start, blizardSteps.get(0).exit);
		PositionInTime backToStart = findTripLenght(blizardSteps, exit, blizardSteps.get(0).start);
		PositionInTime end = findTripLenght(blizardSteps, backToStart, blizardSteps.get(0).exit);
		return end.minute();
	}

	private static List<BlizardMap> readBlizardMap(String input) {
		List<String> lines = input.lines().toList();
		BlizardMap bm = new BlizardMap();
		bm.minX = 1;
		bm.minY = 1;
		bm.maxX = lines.get(0).length() - 2;
		bm.maxY = lines.size() - 2;
		bm.start = new Coord(1, 0);
		bm.exit = new Coord(bm.maxX, bm.maxY + 1);
		for (int y = 1; y <= bm.maxY; ++y) {
			String line = lines.get(y);
			for (int x = 1; x <= bm.maxX; ++x) {
				char c = line.charAt(x);
				if (c != '.') {
					Blizard b = new Blizard(new Coord(x, y), c);
					bm.blizards.add(b);
					bm.map.add(b.coord);
				}
			}
		}
		List<BlizardMap> blizardSteps = new ArrayList<>();
		blizardSteps.add(bm);
		return blizardSteps;
	}

	private static PositionInTime findTripLenght(List<BlizardMap> blizardSteps, PositionInTime initial, Coord end) {
		Queue<PositionInTime> positionInTimeQueue = new ArrayDeque<>();
		Set<PositionInTime> visited = new HashSet<>();
		positionInTimeQueue.add(initial);
		visited.add(initial);
		PositionInTime found = null;

		while (found == null && !positionInTimeQueue.isEmpty()) {
			PositionInTime next = positionInTimeQueue.poll();
			BlizardMap blizards = getBlizardAt(next.minute, blizardSteps);
			if (blizards.isValid(next.coord)) {
				if (next.coord.equals(end)) {
					found = next;
				} else {
					next.coord.nextSteps().stream().map(n -> new PositionInTime(next.minute + 1, n)).forEach(n -> {
						if (!visited.contains(n)) {
							visited.add(n);
							positionInTimeQueue.add(n);
						}
					});
					PositionInTime stay = new PositionInTime(next.minute + 1, next.coord);
					if (!visited.contains(stay)) {
						visited.add(stay);
						positionInTimeQueue.add(stay);
					}
				}
			}
		}
		return found;
	}

	private static BlizardMap getBlizardAt(int at, List<BlizardMap> blizardSteps) {
		if (at < blizardSteps.size()) {
			return blizardSteps.get(at);
		}
		BlizardMap last = blizardSteps.get(blizardSteps.size() - 1);
		while (blizardSteps.size() <= at) {
			blizardSteps.add(last.next());
		}
		return blizardSteps.get(at);
	}

	private static final record Coord(int x, int y) {
		List<Coord> nextSteps() {
			return List.of(new Coord(x - 1, y), new Coord(x + 1, y), new Coord(x, y + 1), new Coord(x, y - 1));
		}
	}

	private static final record Blizard(Coord coord, char direction) {
		Blizard step(BlizardMap map) {
			Coord next;
			if (direction == 'v') {
				next = new Coord(coord.x, coord.y + 1);
			} else if (direction == '^') {
				next = new Coord(coord.x, coord.y - 1);
			} else if (direction == '>') {
				next = new Coord(coord.x + 1, coord.y);
			} else {
				next = new Coord(coord.x - 1, coord.y);
			}
			if (map.minX <= next.x && map.maxX >= next.x && map.minY <= next.y && map.maxY >= next.y) {
				return new Blizard(next, direction);
			} else {
				if (direction == 'v') {
					next = new Coord(coord.x, map.minY);
				} else if (direction == '^') {
					next = new Coord(coord.x, map.maxY);
				} else if (direction == '>') {
					next = new Coord(map.minX, coord.y);
				} else {
					next = new Coord(map.maxX, coord.y);
				}
				return new Blizard(next, direction);
			}
		}
	}

	private static final class BlizardMap {
		Set<Coord> map = new HashSet<>();
		List<Blizard> blizards = new ArrayList<>();
		int minX;
		int maxX;
		int minY;
		int maxY;
		Coord start;
		Coord exit;

		BlizardMap next() {
			List<Blizard> nextBlizzards = blizards.stream().map(b -> b.step(this)).toList();
			Set<Coord> coords = nextBlizzards.stream().map(Blizard::coord).distinct().collect(Collectors.toSet());
			BlizardMap bm = new BlizardMap();
			bm.minX = minX;
			bm.minY = minY;
			bm.maxX = maxX;
			bm.maxY = maxY;
			bm.start = start;
			bm.exit = exit;
			bm.blizards = nextBlizzards;
			bm.map = coords;
			return bm;
		}

		boolean isValid(Coord coord) {
			return coord.equals(start) || coord.equals(exit)
					|| !map.contains(coord) && coord.x <= maxX && coord.y <= maxY && coord.x >= minX && coord.y >= minY;
		}
	}

	private static final record PositionInTime(int minute, Coord coord) {

	}

	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}

}