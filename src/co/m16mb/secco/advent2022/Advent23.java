package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Advent23 {

	private static final String filenamePath = "files/Advent/file23Secco.txt";

	private static final Character ELF = Character.valueOf('#');

	public static void main(String[] args) throws Exception {

		String fileContents = readFileAsString(filenamePath);
		
		System.out.println("ANSWER1 " + part1(readElfMap(fileContents)));
		// MAC 4123
		// SECCO 4109
		System.out.println("ANSWER2 " + part2(readElfMap(fileContents)));
		// MAC 1029
		// SECCO 1055
	}

	private static final int part1(Map<Coord, Character> map) {
		expand(map, 10);
		return countEmptyArea(map);
	}

	private static final int part2(Map<Coord, Character> map) {
		return expand(map, Integer.MAX_VALUE);
	}

	private static Map<Coord, Character> readElfMap(String desc) {
		var map = new HashMap<Coord, Character>();
		var lines = desc.lines().toList();
		for (int y = 0; y < lines.size(); ++y) {
			var line = lines.get(y);
			for (int x = 0; x < line.length(); ++x) {
				char c = line.charAt(x);
				if (c == ELF.charValue()) {
					map.put(new Coord(x, y), ELF);
				}
			}
		}
		return map;
	}

	private static final int expand(Map<Coord, Character> map, int maxRounds) {
		boolean changed = false;
		int round = 0;
		do {
			Map<Coord, List<Coord>> proposals = new HashMap<>();
			final int fRound = round;
			map.keySet().forEach(elf -> {
				int occupiedNeighbours = elf.allNeighbors().stream().mapToInt(n -> map.containsKey(n) ? 1 : 0).sum();
				if (occupiedNeighbours != 0) {
					boolean proposed = false;
					var groups = elf.neighbourGroups();
					for (int i = 0; i < groups.size() && !proposed; ++i) {
						var ng = groups.get((i + fRound) % groups.size());
						int occupiedInGroup = ng.stream().mapToInt(n -> map.containsKey(n) ? 1 : 0).sum();
						if (occupiedInGroup == 0) {
							proposals.computeIfAbsent(ng.get(0), k -> new ArrayList<>()).add(elf);
							proposed = true;
						}
					}
				}
			});
			proposals.entrySet().stream().filter(p -> p.getValue().size() == 1).forEach(p -> {
				map.remove(p.getValue().get(0));
				map.put(p.getKey(), ELF);
			});
			++round;
			changed = proposals.size() > 0 && round < maxRounds;
		} while (changed);
		return round;
	}

	private static final int countEmptyArea(Map<Coord, Character> map) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		for (var elf : map.keySet()) {
			if (elf.x < minX) {
				minX = elf.x;
			}
			if (elf.x > maxX) {
				maxX = elf.x;
			}
			if (elf.y < minY) {
				minY = elf.y;
			}
			if (elf.y > maxY) {
				maxY = elf.y;
			}
		}
		int area = (maxX - minX + 1) * (maxY - minY + 1);
		return area - map.size();
	}

	private static final record Coord(int x, int y) {
		List<List<Coord>> neighbourGroups() {
			return List.of(List.of(new Coord(x, y - 1), new Coord(x + 1, y - 1), new Coord(x - 1, y - 1)),
					List.of(new Coord(x, y + 1), new Coord(x + 1, y + 1), new Coord(x - 1, y + 1)),
					List.of(new Coord(x - 1, y), new Coord(x - 1, y - 1), new Coord(x - 1, y + 1)),
					List.of(new Coord(x + 1, y), new Coord(x + 1, y - 1), new Coord(x + 1, y + 1)));
		}

		Set<Coord> allNeighbors() {
			return neighbourGroups().stream().flatMap(l -> l.stream()).distinct().collect(Collectors.toSet());
		}
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
