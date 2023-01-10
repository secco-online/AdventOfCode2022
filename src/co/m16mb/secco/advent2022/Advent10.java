package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Advent10 {

	private static final String filenamePath = "files/Advent/file10Secco.txt";

	private static final int ROWS = 6;
	private static final int COLS = 40;

	public static void main(String[] args) throws Exception {

		String input = readFileAsString(filenamePath);

		Map<Integer, Integer> map = calculateDuringCycleValues(input);

		System.out.println("ANSWER1 " + part1(map));
		// SECCO: 12520
		// MAC: 13440

		System.out.println("ANSWER2 " + part2(map));
		// SECCO: EHPZPJGL
		// MAC: PBZGRAZA

	}

	private static int part1(Map<Integer, Integer> map) {
		int signalStrength = 0;
		for (int i = 20; i <= 220; i += COLS) {
			signalStrength += i * map.get(i);
		}
		return signalStrength;
	}

	private static String part2(Map<Integer, Integer> map) {
		int cycle = 1;
		String stringToShow = "\n";
		
		for (int x = 0; x < ROWS; x++) {
			for (int y = 0; y < COLS; y++) {
				
				int registerX = map.get(cycle);		
				stringToShow += Math.abs(registerX - y) <= 1 ? "#" : ".";
				cycle++;
			}
			stringToShow += "\n";
		}

		return stringToShow;
	}

	private static Map<Integer, Integer> calculateDuringCycleValues(String input) {
		Map<Integer, Integer> duringCycle = new HashMap<Integer, Integer>();
		var registerX = 1;
		var cycle = 1;
		for (var line : input.lines().toList()) {
			if (line.equals("noop")) {
				duringCycle.put(cycle++, registerX);
			} else {
				duringCycle.put(cycle++, registerX);
				duringCycle.put(cycle++, registerX);
				registerX += Integer.parseInt(line.split(" ")[1]);
			}
		}
		return duringCycle;
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}

}
