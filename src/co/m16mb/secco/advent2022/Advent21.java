package co.m16mb.secco.advent2022;

import java.nio.channels.IllegalSelectorException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Advent21 {

	private static final String filenamePath = "files/Advent/file21Secco.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		Map<String, Monkey> monkeys = new HashMap<>();
		for (String string : fileContents.split("\\r?\\n")) {
			String[] line = string.split(" ");
			String id = line[0].replaceAll(":", "");
			if (line.length == 2) {
				// just number
				monkeys.put(id, new Monkey(id, true, Integer.parseInt(line[1]), null, null, null));
			} else if (line.length == 4) {
				// just number
				monkeys.put(id, new Monkey(id, false, Integer.MIN_VALUE, line[1], line[3], line[2]));
			} else
				throw new IllegalSelectorException();

		}

		// solving puzzle 1
		System.out.println("ANSWER1 " + part(monkeys));

		// SECCO: 43699799094202
		// MAC: 24947355373338

		System.out.println("ANSWER2 " + part2(monkeys));

		// SECCO: 3375719472770
		// MAC: 3876907167495

	}

	private static final record Monkey(String id, boolean justNumber, long value, String otherID1, String otherID2,
			String operation) {

		long getMonkeysNumber(long num1, long num2) {
			if (justNumber) {
				return value;
			} else {
				if ("+".equals(operation)) {
					return num1 + num2;
				} else if ("-".equals(operation)) {
					return num1 - num2;
				} else if ("*".equals(operation)) {
					return num1 * num2;
				} else if ("/".equals(operation)) {
					return num1 / num2;
				} else {
					throw new IllegalStateException();
				}
			}
		}

		long getFromExpectedValue(String id, long otherNumber, long expectedValue, boolean calculateValue1) {

			if ("root".equals(id)) {
				return otherNumber;
			} else if ("+".equals(operation)) {
				return expectedValue - otherNumber;
			} else if ("-".equals(operation)) {

				// return num1 - num2;
				if (calculateValue1) {
					return otherNumber + expectedValue;
				} else {
					return otherNumber - expectedValue;
				}
			} else if ("*".equals(operation)) {
				return expectedValue / otherNumber;
			} else if ("/".equals(operation)) {
				// return num1 / num2;
				if (calculateValue1) {
					return otherNumber * expectedValue;
				} else {
					return otherNumber / expectedValue;
				}
			} else {
				throw new IllegalStateException();
			}

		}

	}

	private static long part(Map<String, Monkey> monkeys) {

		long calculatedNumber = getResultFromMonkey(monkeys, "root");

		return calculatedNumber;
	}

	private static long part2(Map<String, Monkey> monkeys) {

		Map<String, PathToHumn> pathToHuman = new HashMap<>();

		getResultFromMonkey2(monkeys, "root", pathToHuman);

		String monkeyID = "root";
		long expectedValue = 0;
		while (!"humn".equals(monkeyID)) {
			PathToHumn current = pathToHuman.get(monkeyID);

			// System.out.println(current);
			expectedValue = current.monkey.getFromExpectedValue(monkeyID, current.otherValue, expectedValue,
					current.path1ContainsHumn);
			// System.out.println("monkeyID " + monkeyID + " - " + expectedValue);
			if (current.path1ContainsHumn) {
				monkeyID = current.monkey.otherID1;
			} else {
				monkeyID = current.monkey.otherID2;
			}

		}

		return expectedValue;
	}

	private static final record ReturnValue(long value, boolean branchContainsHumn) {
	};

	private static final record PathToHumn(long otherValue, boolean path1ContainsHumn, Monkey monkey) {
	};

	private static long getResultFromMonkey(Map<String, Monkey> monkeys, String id) {

		if (monkeys.get(id).justNumber)
			return monkeys.get(id).value;
		else {
			long value1 = getResultFromMonkey(monkeys, monkeys.get(id).otherID1);
			long value2 = getResultFromMonkey(monkeys, monkeys.get(id).otherID2);
			return monkeys.get(id).getMonkeysNumber(value1, value2);
		}
	}

	private static ReturnValue getResultFromMonkey2(Map<String, Monkey> monkeys, String id,
			Map<String, PathToHumn> pathToHuman) {

		if (monkeys.get(id).justNumber) {
			if ("humn".equals(id)) {
				pathToHuman.put(id, new PathToHumn(monkeys.get(id).value, true, monkeys.get(id)));
				return new ReturnValue(monkeys.get(id).value, true);
			} else {
				return new ReturnValue(monkeys.get(id).value, false);
			}
		} else {
			ReturnValue value1 = getResultFromMonkey2(monkeys, monkeys.get(id).otherID1, pathToHuman);
			ReturnValue value2 = getResultFromMonkey2(monkeys, monkeys.get(id).otherID2, pathToHuman);
			long result = monkeys.get(id).getMonkeysNumber(value1.value, value2.value);
			if (value1.branchContainsHumn) {
				pathToHuman.put(id, new PathToHumn(value2.value, true, monkeys.get(id)));
			} else if (value2.branchContainsHumn) {
				pathToHuman.put(id, new PathToHumn(value1.value, false, monkeys.get(id)));
			}

			return new ReturnValue(result, value1.branchContainsHumn || value2.branchContainsHumn);

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
