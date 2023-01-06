package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Advent09 {
	private static final String filenamePath = "files/Advent/file09.txt";

	public static void main(String[] args) throws Exception {
		List<Move> moves = new ArrayList<>();
		for (String oneLine : readFileAsString(filenamePath).split("\\r?\\n")) {
			moves.add(Move.parse(oneLine));
		}

		System.out.println(part1(moves));
		// SECCO 6314
		// MAC 6087
		System.out.println(part2(moves));
		// SECCO 2504
		// MAC 2493

	}

	private static int part1(List<Move> moves) {
		return moveRope(moves, getRopeKnots(2));
	}

	private static int part2(List<Move> moves) {
		return moveRope(moves, getRopeKnots(10));
	}

	private static Coord[] getRopeKnots(int length) {
		var knots = new Coord[length];
		for (int i = 0; i < length; ++i) {
			knots[i] = new Coord(0, 0);
		}
		return knots;
	}

	private static int moveRope(List<Move> moves, Coord[] knots) {
		Set<Coord> tailVisited = new HashSet<>();
		tailVisited.add(new Coord(0, 0));
		for (Move move : moves) {
			for (int s = 0; s < move.steps; s++) {
				knots[0] = knots[0].moveOneStep(move.xDif, move.yDif);
				for (int t = 1; t < knots.length; t++) {
					knots[t] = calcTail(knots[t - 1], knots[t]);
				}
				tailVisited.add(knots[knots.length - 1]);
			}
		}
		return tailVisited.size();
	}

	private static Coord calcTail(Coord prev, Coord tail) {
		if (tail.isTouching(prev)) {
			return tail;
		} else if (tail.y == prev.y) {
			return tail.moveOneStep(tail.x < prev.x ? 1 : -1, 0);
		} else if (tail.x == prev.x) {
			return tail.moveOneStep(0, tail.y < prev.y ? 1 : -1);
		} else if (tail.y < prev.y) {
			return tail.moveOneStep(tail.x < prev.x ? 1 : -1, 1);
		} else {
			return tail.moveOneStep(tail.x < prev.x ? 1 : -1, -1);
		}
	}

	private static final record Move(int xDif, int yDif, int steps) {
		static Move parse(String line) {
			var parts = line.split(" ");
			var steps = Integer.parseInt(parts[1]);
			return switch (parts[0]) {
			case "U" -> new Move(0, -1, steps);
			case "D" -> new Move(0, 1, steps);
			case "L" -> new Move(-1, 0, steps);
			case "R" -> new Move(1, 0, steps);
			default -> throw new IllegalStateException("What to do with: " + parts[0]);
			};
		}
	}

	private static final record Coord(int x, int y) {
		boolean isTouching(Coord coord) {
			return Math.abs(x - coord.x) <= 1 && Math.abs(y - coord.y) <= 1;
		}

		Coord moveOneStep(int xDif, int yDif) {
			return new Coord(x + xDif, y + yDif);
		}
	}

////////////////////////////////////////////////
////////////////////////////////////////////////
////////////////////////////////////////////////
	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Silesize: " + data.length());
		return data;
	}
}