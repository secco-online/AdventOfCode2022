package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Advent11 {

	private static final int EMPTY_ITEM = 0;

	private static final String filenamePath = "files/Advent/file11.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		System.out.println("ANSWER1 " + part(getMonkeys(inputArray), 20, false));
		// SECCO: 98280
		// MAC: 90882

		System.out.println("ANSWER2 " + part(getMonkeys(inputArray), 10000, true));
		// SECCO: 17673687232
		// MAC: 30893109657

	}

	private static Map<Long, Monkey> getMonkeys(ArrayList<String> inputArray) {
		Map<Long, Monkey> monkeys = new HashMap<Long, Monkey>();

		for (int row = 0; row < inputArray.size(); row += 7) {
			Monkey monkey = new Monkey();

			String line0 = inputArray.get(row);
			line0 = line0.replaceAll("Monkey ", "");
			line0 = line0.replaceAll(":", "");
			monkey.monkeyID = Long.parseLong(line0);

			String line1 = inputArray.get(row + 1);
			line1 = line1.replaceAll("  Starting items: ", "");

			for (String string : line1.split(",")) {
				monkey.addItem(Long.parseLong(string.trim()));
			}

			String line2 = inputArray.get(row + 2);
			line2 = line2.replaceAll("  Operation: new = ", "");
			monkey.operation = line2;

			String line3 = inputArray.get(row + 3);
			line3 = line3.replaceAll("  Test: divisible by ", "");
			monkey.testDivisiblyBy = Long.parseLong(line3);

			String line4 = inputArray.get(row + 4);
			line4 = line4.replaceAll("    If true: throw to monkey ", "");
			monkey.idMonkeyTrue = Long.parseLong(line4);

			String line5 = inputArray.get(row + 5);
			line5 = line5.replaceAll("    If false: throw to monkey ", "");
			monkey.idMonkeyFalse = Long.parseLong(line5);

			monkeys.put(monkey.monkeyID, monkey);
		}
		/*
		 * for (Map.Entry<Integer, Monkey> entry : monkeys.entrySet()) {
		 * System.out.println(entry.getValue());
		 * 
		 * }
		 */
		return monkeys;

	}

	private static long part(Map<Long, Monkey> monkeys, int rounds, boolean myCalmingPill) {

		long myDivision = 1;
		if (myCalmingPill) {
			for (Map.Entry<Long, Monkey> entry : monkeys.entrySet()) {
				Monkey monkey = entry.getValue();
				myDivision = myDivision * monkey.testDivisiblyBy;
			}
			System.out.println("myDivision " + myDivision);
		}
		for (int r = 0; r < rounds; r++) {

			for (Map.Entry<Long, Monkey> entry : monkeys.entrySet()) {
				Monkey monkey = entry.getValue();
				monkey.inspectAllItems(myDivision);

				// System.out.println("After Inspect" + monkey);
				for (int i = 0; i < monkey.items.length; i++) {
					long item = monkey.items[i];
					if (item != EMPTY_ITEM) {
						long nextMonkey = monkey.test(item);
						monkey.items[i] = EMPTY_ITEM;

						monkeys.get(nextMonkey).addItem(item);

					}
				}

			}
			/*
			 * if (r == 0 || r == 19 || r == 999 || r == 9999) {
			 * 
			 * System.out.println("AFTER ROUND " + (r + 1));
			 * 
			 * for (Map.Entry<Long, Monkey> entry : monkeys.entrySet()) {
			 * System.out.println(entry.getValue());
			 * 
			 * }
			 * 
			 * }
			 */
		}

		List<Monkey> monkeysList = new ArrayList<Monkey>();
		for (Map.Entry<Long, Monkey> entry : monkeys.entrySet()) {
			Monkey monkey = entry.getValue();
			monkeysList.add(monkey);
		}

		Collections.sort(monkeysList, Collections.reverseOrder());
		System.out.println("inspectedItems 1st " + monkeysList.get(0).inspectedItems);
		System.out.println("inspectedItems 2nd " + monkeysList.get(1).inspectedItems);

		return (monkeysList.get(0).inspectedItems * monkeysList.get(1).inspectedItems);
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

	static class Monkey implements Comparable<Monkey> {
		long[] items = new long[100];
		{
			for (int i = 0; i < items.length; i++) {
				items[i] = EMPTY_ITEM;
			}
		}

		long monkeyID = -1;
		String operation;
		long testDivisiblyBy = 1;
		long idMonkeyTrue = -1;
		long idMonkeyFalse = -1;

		long inspectedItems = 0;

		void inspectAllItems(long myDivision) {

			for (int i = 0; i < items.length; i++) {
				if (items[i] > EMPTY_ITEM) {
					long newWorryLevel = inspectItem(items[i], myDivision);
					items[i] = newWorryLevel;
				}
			}
		}

		boolean addItem(long worryLevel) {
			// System.out.println("additem" + worryLevel);
			for (int i = 0; i < items.length; i++) {
				if (items[i] == EMPTY_ITEM) {
					items[i] = worryLevel;
					return true;
				}
			}
			System.err.println("NOT ADDED ");
			return false;
		}

		private long inspectItem(long worryLevel, long myDivision) {

			String[] operationArray = operation.split(" ");
			// System.out.println("inspectItem wl: " + worryLevel + " oper " + operation);
			if (operationArray[1].equals("*") && operationArray[2].equals("old")) {
				worryLevel = worryLevel * worryLevel;
			} else if (operationArray[1].equals("+") && operationArray[2].equals("old")) {
				worryLevel = worryLevel + worryLevel;
			} else if (operationArray[1].equals("*") && !operationArray[2].equals("old")) {
				worryLevel = worryLevel * Long.parseLong(operationArray[2]);
			} else if (operationArray[1].equals("+") && !operationArray[2].equals("old")) {
				worryLevel = worryLevel + Long.parseLong(operationArray[2]);
			} else
				System.err.println("WRONG OPERATION " + operation);

			// System.out.println("inspectItem afrer op wl: " + worryLevel + " oper " +
			// operation);

			// gets bored
			if (myDivision != 1) {
				// part 2
				worryLevel = worryLevel % myDivision;
			} else {
				// part 1
				worryLevel = worryLevel / 3;
			}

			// System.out.println("inspectItem /3 wl: " + worryLevel + " oper " +
			// operation);

			inspectedItems++;
			return worryLevel;
		}

		long test(long worryLevel) {

			if (worryLevel % testDivisiblyBy == 0) {
				return idMonkeyTrue;
			} else
				return idMonkeyFalse;
		}

		@Override
		public String toString() {

			String string = "monkeyID " + monkeyID + ", operation " + operation + ", testDivisiblyBy " + testDivisiblyBy
					+ ", idMonkeyTrue " + idMonkeyTrue + ", idMonkeyFalse " + idMonkeyFalse + ", inspectedItems "
					+ inspectedItems + ", items [";

			for (long item : items) {
				string += item + ",";
			}
			string += "]";

			return string;
		}

		@Override
		public int compareTo(Monkey o) {
			return (int) (this.inspectedItems - o.inspectedItems);
		}

	}
}
