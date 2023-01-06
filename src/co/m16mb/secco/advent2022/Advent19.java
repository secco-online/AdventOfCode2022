package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Advent19 {

	private static final String filenamePath = "files/Advent/file19Secco.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		Map<Integer, Blueprint> bluprintMap = new HashMap<>();

		for (String blueprint : fileContents.split("\\r?\\n")) {
			Pattern pattern = Pattern.compile(
					"Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.");

			Matcher matcher = pattern.matcher(blueprint);
			while (matcher.find()) {

				int id = Integer.parseInt(matcher.group(1));
				int oreRobotCostXOre = Integer.parseInt(matcher.group(2));
				int clayRobotCostXOre = Integer.parseInt(matcher.group(3));

				int obsidianRobotCostXOre = Integer.parseInt(matcher.group(4));
				int obsidianRobotCostXClay = Integer.parseInt(matcher.group(5));

				int geodeRobotCostXOre = Integer.parseInt(matcher.group(6));
				int geodeRobotCostXObsidian = Integer.parseInt(matcher.group(7));

				bluprintMap.put(id, new Blueprint(id, oreRobotCostXOre, clayRobotCostXOre, obsidianRobotCostXOre,
						obsidianRobotCostXClay, geodeRobotCostXOre, geodeRobotCostXObsidian));
			}

		}
		// solving puzzle 1
		System.out.println("ANSWER1 " + part(bluprintMap));

		// SECCO: 1294
		// MAC: 1725

		System.out.println("ANSWER2 " + part2(bluprintMap));

		//id1: 10
		
		// SECCO: 13640
		// MAC: 15510

	}

	private static int part(Map<Integer, Blueprint> bluprintMap) {

		int qualityLevel = 0;
		for (Map.Entry<Integer, Blueprint> entry : bluprintMap.entrySet()) {
			Blueprint blueprint = entry.getValue();
			int geodesFound = findMaxGeodeForBlueprint(blueprint, 24);
			qualityLevel += (blueprint.id * geodesFound);
			System.out.println(blueprint.id + " GEODES FOUND " + geodesFound);
		}
		return qualityLevel;
	}

	private static int part2(Map<Integer, Blueprint> bluprintMap) {

		int multiply = 1;
		for (Map.Entry<Integer, Blueprint> entry : bluprintMap.entrySet()) {
			Blueprint blueprint = entry.getValue();
			//System.out.println(blueprint);
			if (blueprint.id >= 1 && blueprint.id <= 3) {
				int geodesFound = findMaxGeodeForBlueprint(blueprint, 32);
				System.out.println(blueprint.id + " GEODES FOUND " + geodesFound);
				multiply *= geodesFound;
			}
		}
		return multiply;
	}

	private static final record StepFactory(int minutesLeft, int ore, int clay, int obsidian, int geode,
			int oreProducingRobots, int clayProducingRobots, int obsidianProducingRobots, int geodeOpeningRobots) {

		StepFactory createNoNewRobot() {
			return new StepFactory(minutesLeft - 1, ore + oreProducingRobots, clay + clayProducingRobots,
					obsidian + obsidianProducingRobots, geode + geodeOpeningRobots, oreProducingRobots,
					clayProducingRobots, obsidianProducingRobots, geodeOpeningRobots);
		}

		StepFactory createNewOreRobot(int costsXore) {
			return new StepFactory(minutesLeft - 1, ore - costsXore + oreProducingRobots, clay + clayProducingRobots,
					obsidian + obsidianProducingRobots, geode + geodeOpeningRobots, oreProducingRobots + 1,
					clayProducingRobots, obsidianProducingRobots, geodeOpeningRobots);
		}

		StepFactory createNewClayRobot(int costsXore) {
			return new StepFactory(minutesLeft - 1, ore - costsXore + oreProducingRobots, clay + clayProducingRobots,
					obsidian + obsidianProducingRobots, geode + geodeOpeningRobots, oreProducingRobots,
					clayProducingRobots + 1, obsidianProducingRobots, geodeOpeningRobots);
		}

		StepFactory createNewObsidianRobot(int costsXore, int costsXClay) {
			return new StepFactory(minutesLeft - 1, ore - costsXore + oreProducingRobots,
					clay - costsXClay + clayProducingRobots, obsidian + obsidianProducingRobots,
					geode + geodeOpeningRobots, oreProducingRobots, clayProducingRobots, obsidianProducingRobots + 1,
					geodeOpeningRobots);
		}

		StepFactory createNewGeodeRobot(int costsXore, int costsXObsidian) {
			return new StepFactory(minutesLeft - 1, ore - costsXore + oreProducingRobots, clay + clayProducingRobots,
					obsidian - costsXObsidian + obsidianProducingRobots, geode + geodeOpeningRobots, oreProducingRobots,
					clayProducingRobots, obsidianProducingRobots, geodeOpeningRobots + 1);
		}

		int maxPerformance() {

			int geodsByRobotEveryMinute = 0;
			for (int i = 0; i < minutesLeft; i++) {
				geodsByRobotEveryMinute += (i * geodeOpeningRobots);
			}

			return geode + geodsByRobotEveryMinute;
		}

	}

	private static int findMaxGeodeForBlueprint(Blueprint blueprint, int minutes) {
		Queue<StepFactory> queue = new ArrayDeque<>();
		Set<StepFactory> visited = new HashSet<>();
		queue.add(new StepFactory(minutes, 0, 0, 0, 0, 1, 0, 0, 0));

		int maxGeode = Integer.MIN_VALUE;

		int maxOre = Math.max(blueprint.oreRobotCostXOre, Math.max(blueprint.clayRobotCostXOre,
				Math.max(blueprint.obsidianRobotCostXOre, blueprint.geodeRobotCostXOre)));
		while (!queue.isEmpty()) {
			StepFactory current = queue.poll();
			if (queue.size() % 10_000_000 == 0 && queue.size() > 0)
				System.out.println(
						"step.size: " + queue.size() + "\t" + current.minutesLeft + "\t" + maxGeode + "\t" + current);
			if (current.minutesLeft > 0) {

				if(current.maxPerformance()<maxGeode) continue;
				
				if (blueprint.geodeRobotCostXOre <= current.ore
						&& blueprint.geodeRobotCostXObsidian <= current.obsidian) {

					StepFactory sf = current.createNewGeodeRobot(blueprint.geodeRobotCostXOre,
							blueprint.geodeRobotCostXObsidian);
					if (!visited.contains(sf)) {
						queue.add(sf);
						visited.add(sf);
					}

				} else {

					if (blueprint.obsidianRobotCostXOre <= current.ore
							&& blueprint.obsidianRobotCostXClay <= current.clay
							&& current.obsidianProducingRobots < blueprint.geodeRobotCostXObsidian) {

						StepFactory sf = current.createNewObsidianRobot(blueprint.obsidianRobotCostXOre,
								blueprint.obsidianRobotCostXClay);
						if (!visited.contains(sf)) {
							queue.add(sf);
							visited.add(sf);
						}
					}

					if (blueprint.clayRobotCostXOre <= current.ore
							&& current.clayProducingRobots < blueprint.obsidianRobotCostXClay) {
						StepFactory sf = current.createNewClayRobot(blueprint.clayRobotCostXOre);
						if (!visited.contains(sf)) {
							queue.add(sf);
							visited.add(sf);
						}
					}

					if (blueprint.oreRobotCostXOre <= current.ore && current.oreProducingRobots < maxOre) {
						StepFactory sf = current.createNewOreRobot(blueprint.oreRobotCostXOre);
						if (!visited.contains(sf)) {
							queue.add(sf);
							visited.add(sf);
						}
					}

					// add also a waiting and producing step
					queue.add(current.createNoNewRobot());
				}
			}

			if (maxGeode < current.geode) {
				maxGeode = current.geode;
			}

		}

		// return the steps of last path

		return maxGeode;
	}

	private final static record Blueprint(int id, int oreRobotCostXOre, int clayRobotCostXOre,
			int obsidianRobotCostXOre, int obsidianRobotCostXClay, int geodeRobotCostXOre,
			int geodeRobotCostXObsidian) {
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
