package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Advent05 {

	private static final String filenamePath = "files/Advent/file05.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String input = readFileAsString(filenamePath);

		String[] stacksAndMoves = input.split("\\r?\\n\\r?\\n");

		ArrayList<Move> moves = new ArrayList<>();
		for (String oneLine : stacksAndMoves[1].split("\\r?\\n")) {
			moves.add(Move.parse(oneLine));
		}

		System.out.println("ANSWER1: " + moveStacks(readStacks(stacksAndMoves[0]), moves, false));
		// TOTAL SECCO: QNHWJVJZW
		// TOTAL MAC: BSDMQFLSP
		System.out.println("ANSWER2: " + moveStacks(readStacks(stacksAndMoves[0]), moves, true));
		// TOTAL SECCO: BPCZJLFJW
		// TOTAL MAC: PGSQBFLDP

	}

	private static String moveStacks(Map<Integer, String> stackMap, ArrayList<Move> moves, boolean carryingMultiple) {

		for (Move move : moves) {

			String s = stackMap.get(move.source);
			String d = stackMap.get(move.dest);

			String what = s.substring(0, move.many);
			String sNew = s.substring(move.many);

			String dNew;
			if (carryingMultiple) {
				dNew = what + d;
			} else {
				String whatReordered = "";
				for (int i = 0; i < what.length(); i++) {
					whatReordered = what.charAt(i) + whatReordered;
				}
				dNew = whatReordered + d;

			}
			stackMap.put(move.dest, dNew);
			stackMap.put(move.source, sNew);
		}

		String onTop = "";
		for (int i = 1; i <= stackMap.size(); i++) {
			onTop += stackMap.get(i).charAt(0);
		}

		return onTop;
	}

	private static Map<Integer, String> readStacks(String stacks) {
		Map<Integer, String> stackMap = new HashMap<>();
		for (int i = 1; i <= 9; i++) {
			stackMap.put(i, "");
		}
		int r = 0;
		for (String oneLine : stacks.split("\\r?\\n")) {
			// reading stacks
			if (r < 8) {
				// top of stack at the beginning of the string
				stackMap.put(1, (stackMap.get(1) + oneLine.charAt(1)).trim());
				stackMap.put(2, (stackMap.get(2) + oneLine.charAt(5)).trim());
				stackMap.put(3, (stackMap.get(3) + oneLine.charAt(9)).trim());
				stackMap.put(4, (stackMap.get(4) + oneLine.charAt(13)).trim());
				stackMap.put(5, (stackMap.get(5) + oneLine.charAt(17)).trim());
				stackMap.put(6, (stackMap.get(6) + oneLine.charAt(21)).trim());
				stackMap.put(7, (stackMap.get(7) + oneLine.charAt(25)).trim());
				stackMap.put(8, (stackMap.get(8) + oneLine.charAt(29)).trim());
				stackMap.put(9, (stackMap.get(9) + oneLine.charAt(33)).trim());
			}
			r++;
		}
		return stackMap;
	}

	private static final record Move(int source, int dest, int many) {
		static Move parse(String line) {
			String[] move = line.split(" ");
			return new Move(Integer.parseInt(move[3]), Integer.parseInt(move[5]), Integer.parseInt(move[1]));
		}

	}

	private static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}
}
