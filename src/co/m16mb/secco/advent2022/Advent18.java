package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Advent18 {

	private static final String filenamePath = "files/Advent/file18Secco.txt";

	private static final String LAVA = "L";
	private static final String WATER = "W";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String input = readFileAsString(filenamePath);

		Map<Droplet, String> dropletMap = new HashMap<>();
		for (String line : input.split("\\r?\\n")) {
			dropletMap.put(Droplet.parse(line), LAVA);
		}

		System.out.println("ANSWER1 " + part(dropletMap));

		// SECCO: 3448
		// MAC: 4444

		System.out.println("ANSWER2 " + part2(dropletMap));

		// SECCO: 2052
		// MAC: 2530

	}

	private static final record Droplet(int x, int y, int z) {

		static Droplet parse(String string) {
			String[] line = string.split(",");
			int x = Integer.parseInt(line[0]);
			int y = Integer.parseInt(line[1]);
			int z = Integer.parseInt(line[2]);
			return new Droplet(x, y, z);
		}

		List<Droplet> neighbours() {
			return List.of(new Droplet(x - 1, y, z), new Droplet(x + 1, y, z), new Droplet(x, y - 1, z),
					new Droplet(x, y + 1, z), new Droplet(x, y, z - 1), new Droplet(x, y, z + 1));
		}

	}

	private static int part(Map<Droplet, String> dropletMap) {

		int countNotTouching = 0;

		for (Map.Entry<Droplet, String> entry : dropletMap.entrySet()) {
			Droplet droplet = entry.getKey();
			String val = entry.getValue();
			if (val.equals(LAVA)) {
				List<Droplet> neighbourList = droplet.neighbours();
				int neighbourCount = 0;
				for (Droplet neighbour : neighbourList) {
					if (dropletMap.containsKey(neighbour))
						neighbourCount++;
				}
				int freeWalls = 6 - neighbourCount;
				countNotTouching += freeWalls;
				//System.out.println(droplet + " free wals " + freeWalls);
			}
		}

		return countNotTouching;
	}

	private static int part2(Map<Droplet, String> dropletMap) {

		int countExternalNotTouching = 0;
		// fill tank with water
		Tank tank = Tank.getTankFitting(dropletMap);

		fillTankWithWater(dropletMap, tank);
		for (Map.Entry<Droplet, String> entry : dropletMap.entrySet()) {
			Droplet droplet = entry.getKey();
			String val = entry.getValue();
			if (val.equals(LAVA)) {
				List<Droplet> neighbourList = droplet.neighbours();
				int externalWalls = 0;
				for (Droplet neighbour : neighbourList) {
					if (dropletMap.containsKey(neighbour)) {

						if (WATER.equals(dropletMap.get(neighbour))) {
							externalWalls++;
						}

					}

				}

				countExternalNotTouching += externalWalls;
				//System.out.println(droplet + " externalWalls " + externalWalls);
			}
		}

		return countExternalNotTouching;

	}

	private static void fillTankWithWater(Map<Droplet, String> dropletMap, Tank tank) {
		Queue<Droplet> queue = new LinkedList<>();
		Set<Droplet> visited = new HashSet<>();

		Droplet startingPoint = new Droplet(tank.xMin, tank.yMin, tank.zMin);
		
		queue.add(startingPoint);
		visited.add(startingPoint);

		int water = 0;
		while (!queue.isEmpty()) {
			var droplet = queue.poll();
			if (tank.contains(droplet)) {
				dropletMap.put(droplet, WATER);
				water++;

				droplet.neighbours().forEach(n -> {
					if (!visited.contains(n) && !dropletMap.containsKey(n)) {
						queue.add(n);
						visited.add(n);
					}
				});
			}

		}
		System.out.println("WATER " + water);
	}

	private static final record Tank(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {

		boolean contains(Droplet droplet) {
			return xMin <= droplet.x && yMin <= droplet.y && zMin <= droplet.z && droplet.x <= xMax && droplet.y <= yMax
					&& droplet.z <= zMax;
		}

		static Tank getTankFitting(Map<Droplet, String> dropletMap) {

			// CALCULATE TANK
			int xMin = Integer.MAX_VALUE;
			int yMin = Integer.MAX_VALUE;
			int zMin = Integer.MAX_VALUE;
			int xMax = Integer.MIN_VALUE;
			int yMax = Integer.MIN_VALUE;
			int zMax = Integer.MIN_VALUE;
			for (Map.Entry<Droplet, String> droplet : dropletMap.entrySet()) {
				if (droplet.getKey().x < xMin)
					xMin = droplet.getKey().x;
				if (droplet.getKey().y < yMin)
					yMin = droplet.getKey().y;
				if (droplet.getKey().z < zMin)
					zMin = droplet.getKey().z;

				if (droplet.getKey().x > xMax)
					xMax = droplet.getKey().x;
				if (droplet.getKey().y > yMax)
					yMax = droplet.getKey().y;
				if (droplet.getKey().z > zMax)
					zMax = droplet.getKey().z;
			}
			xMin--;
			xMax++;
			yMin--;
			yMax++;
			zMin--;
			zMax++;
			System.out.println(" xMin " + xMin + " xMax " + xMax + " | yMin " + yMin + " yMax " + yMax + " | zMin "
					+ zMin + " zMax " + zMax);

			return new Tank(xMin, yMin, zMin, xMax, yMax, zMax);
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
