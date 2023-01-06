package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Advent16 {

	private static final String filenamePath = "files/Advent/file16Secco.txt";

	public static void main(String[] args) throws Exception {

		Map<String, Valve> valves = new HashMap<>();
		for (String oneLine : readFileAsString(filenamePath).split("\\r?\\n")) {
			Valve v = Valve.parse(oneLine);
			valves.put(v.id, v);
		}

		int p1 = part(valves);
		System.out.println("ANSWER1 " + p1);
		// SECCO: 1754
		// MAC: 2181
		int p2 = part2(valves);
		System.out.println("ANSWER2 " + p2);
		// SECCO: 2474
		// MAC: 2824
	}

	private static int part(Map<String, Valve> valves) {
		State best = findMaxFLow("AA", 30, valves, Set.of());
		System.out.println("BEST " + best);
		return releaseToTheLimit(30, best);
	}

	private static int part2(Map<String, Valve> valves) {
		State me = findMaxFLow("AA", 26, valves, Set.of());
		System.out.println("ME " + me);
		State elephant = findMaxFLow("AA", 26, valves, me.opened());
		System.out.println("ELEPHANT " + elephant);
		return releaseToTheLimit(26, me) + releaseToTheLimit(26, elephant);
	}

	private static int releaseToTheLimit(int limit, State state) {
		if (state.minute() < limit) {
			return state.released() + (limit - state.minute()) * state.releases();
		}
		return state.released();
	}

	private static final record State(String at, int minute, int releases, int released, Set<String> opened) {
		Set<String> open(String id) {
			var c = new HashSet<>(opened());
			c.add(id);
			return c;
		}
	}

	private static State findMaxFLow(String startAt, int limit, Map<String, Valve> valveMap, Set<String> forbidden) {
		Map<ValvePair, Integer> distancesBetweenValves = calcDistancesBetweenValves(startAt, valveMap);

		List<String> valvesWithAnyFlow = valveMap.values().stream().filter(v -> v.flowRate() > 0).map(v -> v.id)
				.toList();
		var steps = new ArrayDeque<State>();
		steps.add(new State(startAt, 0, 0, 0, Set.of()));
		int maxReleased = Integer.MIN_VALUE;
		State bestPath = null;
		while (!steps.isEmpty()) {
			State current = steps.poll();
			// System.out.println("State " + current);
			/*
			 * var iShouldOpen = !current.opened().contains(current.at()) &&
			 * valveMap.get(current.at).flowRate() > 0;
			 * 
			 * if(iShouldOpen) { steps.add( new State(current.at, current.minute() + 1,
			 * current.releases() + valveMap.get(current.at).flowRate(), current.released()
			 * + current.releases(), current.open(current.at))); } else { for (String v :
			 * valveMap.get(current.at).available) { if (!current.opened().contains(v) &&
			 * current.minute < limit && !forbidden.contains(v) &&
			 * valveMap.get(v).flowRate()>0) { steps.add( new State(v, current.minute() + 1,
			 * current.releases() + valveMap.get(v).flowRate(), current.released() +
			 * current.releases(), current.open(v))); } } }
			 * 
			 */
			// List<String> valuables = valveMap.get(current.at).available;
			valvesWithAnyFlow.stream().filter(v -> !current.at().equals(v) && !forbidden.contains(v)).forEach(v -> {
				int distance = distancesBetweenValves.get(new ValvePair(current.at(), v));
				if (!current.opened().contains(v) && current.minute() + distance < limit) {
					steps.add(new State(v, current.minute() + distance + 1,
							current.releases() + valveMap.get(v).flowRate(),
							current.released() + (distance + 1) * current.releases(), current.open(v)));
				}
			});

			if (current.minute() <= limit) {
				int r = releaseToTheLimit(limit, current);
				if (maxReleased < r) {
					maxReleased = r;
					bestPath = current;
				}
			}
		}
		return bestPath;
	}

	private static final record Valve(String id, int flowRate, List<String> available) {

		static Valve parse(String str) {
			String[] line = str.split(" ");

			String valveID = line[1];
			int rate = Integer.parseInt(line[4].replaceAll("rate=", "").replaceAll(";", ""));
			ArrayList<String> tunnels = new ArrayList<>();
			for (int i = 9; i < line.length; i++) {
				String leadsTo = line[i].replaceAll(",", "");
				tunnels.add(leadsTo);
			}
			return new Valve(valveID, rate, tunnels);

		}

	}

	private static Map<ValvePair, Integer> calcDistancesBetweenValves(String startAt, Map<String, Valve> valveMap) {
		ArrayList<Valve> valveWithAnyFlow = new ArrayList<>(
				valveMap.values().stream().filter(v -> v.flowRate() > 0).toList());
		valveWithAnyFlow.add(valveMap.get(startAt));
		var result = new HashMap<ValvePair, Integer>();
		valveWithAnyFlow.forEach(from -> {
			valveWithAnyFlow.forEach(to -> {
				if (from != to && !to.id().equals(startAt)) {
					int price = calcDistanceBeetweenValves(from.id(), to.id(), valveMap);
					result.put(new ValvePair(from.id(), to.id()), price);
				}
			});
		});
		return result;
	}

	private static int calcDistanceBeetweenValves(String from, String to, Map<String, Valve> valveMap) {
		Queue<List<String>> queue = new ArrayDeque<>();
		Set<String> visited = new HashSet<>();
		visited.add(from);
		queue.add(List.of(from));
		while (!queue.isEmpty()) {
			List<String> next = queue.poll();
			if (next.get(0).equals(to)) {
				return next.size() - 1;
			}
			valveMap.get(next.get(0)).available().stream().filter(s -> !visited.contains(s)).forEach(n -> {
				var path = new LinkedList<>(next);
				path.addFirst(n);
				queue.add(path);
				visited.add(n);
			});
		}
		throw new IllegalArgumentException();
	}

	private static final record ValvePair(String from, String to) {

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
