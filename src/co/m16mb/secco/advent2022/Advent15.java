package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Advent15 {

	private static final String filenamePath = "files/Advent/file15Secco.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		ArrayList<SensorBeacon> sensorBeaconList = new ArrayList<>();
		for (Iterator<String> it = inputArray.iterator(); it.hasNext();) {
			String line = it.next();
			SensorBeacon sb = new SensorBeacon(line);
			sensorBeaconList.add(sb);
		}

		// solving puzzle 1

		System.out.println("ANSWER1 " + part(inputArray, sensorBeaconList, 2000000));
		// System.out.println("ANSWER1 " + part(inputArray, sensorBeaconList, 10));

		// SECCO: 5144286
		// MAC: 5181556

		// System.out.println("ANSWER2 " + part2(inputArray, sensorBeaconList, 20));
		System.out.println("ANSWER2 " + part2(inputArray, sensorBeaconList, 4000000));

		// SECCO: 10229191267339
		// MAC: 12817603219131

	}

	private static class SensorBeacon {
		int bX = -1;
		int bY = -1;
		int sX = -1;
		int sY = -1;

		SensorBeacon(String str) {
			super();
			String[] line = str.split(" ");

			this.sX = Integer.parseInt(line[2].replaceAll("x=", "").replaceAll(",", ""));
			this.sY = Integer.parseInt(line[3].replaceAll("y=", "").replaceAll(":", ""));
			this.bX = Integer.parseInt(line[8].replaceAll("x=", "").replaceAll(",", ""));
			this.bY = Integer.parseInt(line[9].replaceAll("y=", ""));

		}

		int getDistance() {
			return Math.abs(bX - sX) + Math.abs(bY - sY);
		}

		int getDistanceFrom(int line) {
			return Math.abs(line - sY);
		}

		int coveredOnLine(int line) {
			return (getDistance() - getDistanceFrom(line)) * 2 + 1;
		}

		boolean sensorNotCovering(long pointX, long pointY) {
			return Math.abs(sX - pointX) + Math.abs(sY - pointY) <= getDistance();
		}

	}

	private static int part(ArrayList<String> inputArray, ArrayList<SensorBeacon> list, int lineNumber) {

		Map<String, String> coveredLine = new HashMap<>();

		for (SensorBeacon sensorBeacon : list) {
			// System.out.println("sb.sX " + sensorBeacon.sX + " sb.sY " + sensorBeacon.sY);

			for (int i = 0; i < (sensorBeacon.coveredOnLine(lineNumber) + 1) / 2; i++) {
				String right = (sensorBeacon.sX + i) + "-" + lineNumber;
				// System.out.println(right + "---X");
				coveredLine.put(right, "X");
				if (i != 0) {
					String left = (sensorBeacon.sX - i) + "-" + lineNumber;
					// System.out.println(left + "---X");
					coveredLine.put(left, "X");
				}
			}
			if (sensorBeacon.bY == lineNumber) {
				String beacon = sensorBeacon.bX + "-" + sensorBeacon.bY;
				// System.out.println(beacon + "---S");
				coveredLine.put(beacon, "S");

			}

			// 4245734 too low
		}

		int countNotBeacon = 0;
		for (Map.Entry<String, String> entry : coveredLine.entrySet()) {
			String val = entry.getValue();
			if ("X".equals(val))
				countNotBeacon++;
			// System.out.println(key + " " + val);

		}

		return countNotBeacon;
	}

	private static long part2(ArrayList<String> inputArray, ArrayList<SensorBeacon> list, int lineNumber) {

		var sums = new HashSet<Long>();
		var difs = new HashSet<Long>();
		for (SensorBeacon sb : list) {
			sums.add(sb.sX + sb.sY - sb.getDistance() - 1L);
			sums.add(sb.sX + sb.sY + sb.getDistance() + 1L);
			difs.add(sb.sX - sb.sY - sb.getDistance() - 1L);
			difs.add(sb.sX - sb.sY + sb.getDistance() + 1L);
		}
		long max = lineNumber;
		sums.add(max);
		difs.add(0L);

		System.out.println("SensorBeacon " + list.size());
		System.out.println("SUMS " + sums.size());
		System.out.println("DIFS " + difs.size());

		for (long a : new HashSet<>(sums)) {
			for (long b : new HashSet<>(difs)) {
				long x = (a + b) / 2;
				long y = a - x;

				boolean noneCovering = true;
				for (SensorBeacon sb : list) {
					if (sb.sensorNotCovering(x, y)) {
						noneCovering = false;
					}

				}

				if ((a + b) % 2 == 0 && x >= 0 && x <= max && y >= 0 && y <= max && noneCovering) {
					System.out.println("x " + x);
					System.out.println("y " + y);
					return x * max + y;

				}
			}
		}

		throw new IllegalStateException();

	}

	private static ArrayList<String> readFileToArray(String filename) {
		ArrayList<String> inputArray = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				inputArray.add(line);
				// System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Rows in file: " + inputArray.size());
		return inputArray;
	}

}
