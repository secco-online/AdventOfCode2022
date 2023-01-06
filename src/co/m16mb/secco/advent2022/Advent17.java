package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Advent17 {

	private static final String filenamePath = "files/Advent/file17Secco.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String jetWinds = readFileAsString(filenamePath);

		// solving puzzle 1

		System.out.println("ANSWER1 " + part(jetWinds));

		// SECCO: 3232
		// MAC: 3059
		long p2 = part2(jetWinds);
		System.out.println("ANSWER2 " + p2);
		// SECCO: 1585632183915
		// MAC: 1500874635587

	}

	private static final int LEFT_WALL = 0;
	private static final int RIGHT_WALL = 6;
	private static final int BOTTOM = 0;

	private static final int STARTING_OFFSET_FROM_LEFT = 2;
	private static final int STARTING_OFFSET_ABOVE_TOWER = 3;

	private static long part(String jetWinds) {
		Data corridor = new Data("", 0, 0);
		int windIteration = 0;
		for (int iter = 0; iter < 2022; iter++) {
			Data rock = new Data(ROCK_ORDER[(iter % ROCK_ORDER.length)], STARTING_OFFSET_FROM_LEFT,
					corridor.minY() - STARTING_OFFSET_ABOVE_TOWER);
			windIteration = oneRockFalling(corridor, windIteration, rock, jetWinds);

		}
		return Math.abs(corridor.minY());

	}

	private static long part2(String jetWinds) {
		var<Pointers, Long> rows = new HashMap<RockWindPair, Long>();
		var<Pointers, Integer> heights = new HashMap<RockWindPair, Integer>();
		var corridor = new Data("", 0, 0);
		var ip = 0;
		var calculatedHeight = Long.MIN_VALUE;
		var jumped = false;
		for (var iter = 0L; iter < 1000000000000L; ++iter) {
			// System.out.println("iter " + iter);
			Data rock = new Data(ROCK_ORDER[(int) (iter % ROCK_ORDER.length)], STARTING_OFFSET_FROM_LEFT,
					corridor.minY() - STARTING_OFFSET_ABOVE_TOWER);
			ip = oneRockFalling(corridor, ip, rock, jetWinds);

			if (!jumped && corridor.topRowFull()) {
				RockWindPair rockWindPair = new RockWindPair((int) (iter % ROCK_ORDER.length), ip % jetWinds.length());
				if (!jumped && rows.containsKey(rockWindPair)) {
					var heightRepeat = -corridor.minY() - heights.get(rockWindPair);
					var repeatLength = iter - rows.get(rockWindPair);
					var skipSize = (1000000000000L - iter) / repeatLength;
					calculatedHeight = skipSize * heightRepeat;
					System.out.println("!!!!!!!!!!!!!! " + rockWindPair + " ss " + skipSize + "  rl " + repeatLength);
					System.out.println("jumped by " + (skipSize * repeatLength));
					iter += skipSize * repeatLength;
					jumped = true;
				} else {
					rows.put(rockWindPair, iter);
					heights.put(rockWindPair, -corridor.minY());

				}
			}

		}

		for (Map.Entry<RockWindPair, Long> entry : rows.entrySet()) {
			RockWindPair key = entry.getKey();
			Long val = entry.getValue();
			System.out.println("Row " + key + " - " + val);
		}
		for (Map.Entry<RockWindPair, Integer> entry : heights.entrySet()) {
			RockWindPair key = entry.getKey();
			Integer val = entry.getValue();
			System.out.println("Height " + key + " - " + val);
		}

		return Math.abs(corridor.minY()) + calculatedHeight;

	}

	private static int oneRockFalling(Data corridor, int windIteration, Data rock, String jetWinds) {

		boolean valid = true;
		int rockTransX = 0;
		int rockTransY = 0;
		while (valid) {
			char push = jetWinds.charAt(windIteration++ % jetWinds.length());
			int xTrans = 0;
			if (push == '>') {
				xTrans = 1;
			} else {
				xTrans = -1;
			}
			rockTransX += xTrans;
			var pushed = rock.transformD(rockTransX, rockTransY);
			if (!corridor.isValid(pushed)) {
				rockTransX -= xTrans;
			}
			rockTransY += 1;
			var fallen = rock.transformD(rockTransX, rockTransY);
			if (!corridor.isValid(fallen)) {
				rockTransY -= 1;
				valid = false;
				corridor.merge(rock.transformD(rockTransX, rockTransY));

			}
		}
		return windIteration;
	}

	private static final record RockWindPair(int rock, int wind) {

	}

	private static final class Data {
		Map<Coord, Character> matrix = new HashMap<>();
		int minY = 0;
		int maxY = 0;

		public Data(String desc, int x, int y) {
			var lines = desc.lines().toList();
			for (int i = 0; i < lines.size(); ++i) {
				String line = lines.get(i);
				for (int j = 0; j < line.length(); ++j) {
					if (line.charAt(j) == '#') {
						matrix.put(new Coord(x + j, y + i - lines.size()), '#');
					}
				}
			}
			minY = findMinY();
			maxY = findMaxY();
		}

		Data(Map<Coord, Character> matrix) {
			this.matrix = matrix;
			this.minY = findMinY();
			this.maxY = findMaxY();
		}

		int minX() {
			return matrix.keySet().stream().mapToInt(Coord::x).min().orElseThrow();
		}

		int maxX() {
			return matrix.keySet().stream().mapToInt(Coord::x).max().orElseThrow();
		}

		int minY() {
			return minY;
		}

		int maxY() {
			return maxY;
		}

		private int findMinY() {
			return matrix.keySet().stream().mapToInt(Coord::y).min().orElse(0);
		}

		private int findMaxY() {
			return matrix.keySet().stream().mapToInt(Coord::y).max().orElse(0);
		}

		Map<Coord, Character> transform(int rockTransX, int rockTransY) {
			Map<Coord, Character> result = new HashMap<>();
			for (var e : matrix.entrySet()) {
				result.put(new Coord(e.getKey().x() + rockTransX, e.getKey().y() + rockTransY), e.getValue());
			}
			return result;
		}

		Data transformD(int rockTransX, int rockTransY) {
			return new Data(transform(rockTransX, rockTransY));
		}

		void merge(Data d) {
			matrix.putAll(d.matrix);
			minY = Math.min(minY, d.minY);
		}

		boolean isValid(Data unit) {
			if (unit.minX() < LEFT_WALL || RIGHT_WALL < unit.maxX()) {
				return false;
			}
			if (unit.maxY() == BOTTOM) {
				return false;
			}
			for (var k : unit.matrix.keySet()) {
				if (matrix.containsKey(k)) {
					return false;
				}
			}
			return true;
		}

		boolean topRowFull() {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < 7; ++x) {
				Character c = matrix.get(new Coord(x, minY));
				c = c == null ? ' ' : c;
				sb.append(c);
			}
			return sb.toString().equals("#######");
		}

	}

	private static final record Coord(int x, int y) {

	}

	private static final String ROCK_HORIZONTAL = "####";
	private static final String ROCK_CROSS = """
			.#.
			###
			.#.""";
	private static final String ROCK_L = """
			..#
			..#
			###""";
	private static final String ROCK_VERTICAL = """
			#
			#
			#
			#""";
	private static final String ROCK_SQUARE = """
			##
			##""";
	private static final String[] ROCK_ORDER = new String[] { ROCK_HORIZONTAL, ROCK_CROSS, ROCK_L, ROCK_VERTICAL,
			ROCK_SQUARE };

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
