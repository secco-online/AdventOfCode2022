package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Advent14 {

	private static final String filenamePath = "files/Advent/file14Secco.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String inputFile = readFileAsString(filenamePath);

		Map<Coords, Character> cave = readCave(inputFile);
		System.out.println("cave " + cave.size());

		Boundaries bounds = Boundaries.findBoundaries(cave.keySet());
		System.out.println("bounds " + bounds);

		
		System.out.println("ANSWER1 " + part(cave, bounds));

		// SECCO: 885
		// MAC: 1199

		System.out.println("ANSWER1 " + part2(cave, bounds.maxY+1));

		// SECCO: 28691
		// MAC: 23925

	}

	private static long part(Map<Coords, Character> cave, Boundaries bounds) {
		boolean cameToRest = true;
		while (cameToRest) {
			var sand = new Coords(500, 0);
			while (!cave.containsKey(sand) && cameToRest) {
				var next = sand.fall();
				if (cave.containsKey(next)) {
					next = sand.fallLeft();
					if (cave.containsKey(next)) {
						next = sand.fallRight();
					}
				}
				if (cave.containsKey(next)) {
					cave.put(sand, SAND);
				} else {
					sand = next;
					if (sand.x() < bounds.minX() || sand.x() > bounds.maxX() || sand.y() > bounds.maxY()) {
						System.out.println("cameToRest false");
						cameToRest = false;
					}
				}
			}
		}
		return cave.values().stream().filter(v -> v.charValue() == SAND).count();
	}
	

	private static long part2(Map<Coords, Character> cave, int floorLevel) {
		var source = new Coords(500, 0);
		while (!cave.containsKey(source)) {
			var sand = new Coords(500, 0);
			while (!cave.containsKey(sand)) {
				var next = sand.fall();
				if (cave.containsKey(next)) {
					next = sand.fallLeft();
					if (cave.containsKey(next)) {
						next = sand.fallRight();
					}
				}
				if (cave.containsKey(next)) {
					cave.put(sand, SAND);
				} else {
					sand = next;
					if (sand.y() == floorLevel) {
						cave.put(sand, SAND);
					}
				}
			}
		}
		return cave.values().stream().filter(v -> v.charValue() == 'o').count();
	}
	
	private static final record Coords(int x, int y) {
		
		static Coords parse(String string) {
			String[] coords = string.split(",");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);
			return new Coords(x,y);
		}
		
		Coords fall() {
			return new Coords(x, y + 1);
		}

		Coords fallLeft() {
			return new Coords(x - 1, y + 1);
		}

		Coords fallRight() {
			return new Coords(x + 1, y + 1);
		}
		
	}

	private static final record Boundaries(int minX, int maxX, int minY, int maxY) {
		static Boundaries findBoundaries(Set<Coords> coords) {
			var minX = Integer.MAX_VALUE;
			var maxX = Integer.MIN_VALUE;
			var maxY = Integer.MIN_VALUE;
			var minY = Integer.MAX_VALUE;
			for (var coord : coords) {
				if (coord.x() < minX) {
					minX = coord.x();
				}
				if (coord.x() > maxX) {
					maxX = coord.x();
				}
				if (coord.y() > maxY) {
					maxY = coord.y();
				}
				if (coord.y() < minY) {
					minY = coord.y();
				}
			}
			return new Boundaries(minX, maxX, minY, maxY);
		}
	}

	
	private static final char WALL = '#';
	private static final char SAND = 'o';

	private static Map<Coords, Character> readCave(String inputFile) {

		Map<Coords, Character> cave = new HashMap<>();

		for (String lineStr : inputFile.split("\\r?\\n")) {
			String[] line = lineStr.split(" -> ");

			Coords prevCoords = null;
			Coords currentCoords = null;
			for (int l = 0; l < line.length; l++) {
				if (currentCoords != null)
					prevCoords = currentCoords;
				currentCoords = Coords.parse(line[l]);
				if (prevCoords != null) {
					// draw a wall
					if (prevCoords.x <= currentCoords.x && prevCoords.y == currentCoords.y) {
						for (int i = prevCoords.x; i <= currentCoords.x; i++) {
							// System.out.println(prevCoords.y + " " + i);
							cave.put(new Coords(i,prevCoords.y), WALL);
						}
					} else if (prevCoords.x >= currentCoords.x && prevCoords.y == currentCoords.y) {
						for (int i = currentCoords.x; i <= prevCoords.x; i++) {
							cave.put(new Coords(i,prevCoords.y), WALL);
						}

					} else if (prevCoords.x == currentCoords.x && prevCoords.y >= currentCoords.y) {
						for (int i = currentCoords.y; i <= prevCoords.y; i++) {
							cave.put(new Coords(prevCoords.x,i),WALL);
						}

					} else if (prevCoords.x == currentCoords.x && prevCoords.y <= currentCoords.y) {
						for (int i = prevCoords.y; i <= currentCoords.y; i++) {
							cave.put(new Coords(prevCoords.x,i),WALL);
						}

					}

				}

			}

		}

		return cave;
	}
	
	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}

}
