package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Advent13 {

	private static final String filenamePath = "files/Advent/file13Secco.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		// solving puzzle 1
		System.out.println("ANSWER1 " + part(inputArray));

		// SECCO: 6369
		// MAC: 5013
		System.out.println("ANSWER2 " + part2(inputArray));

		// SECCO: 25472
		// MAC: 25038

	}

	private static int part2(ArrayList<String> inputArray) {
		inputArray.add("[[2]]");
		inputArray.add("[[6]]");
		ArrayList<String> newValues = new ArrayList<>();
		for (String string : inputArray) {
			if (!string.isEmpty())
				newValues.add(string.replaceAll("\\]", "").replaceAll("\\[", ""));
		}
		Collections.sort(newValues);

		int indexOf2 = 0;
		int indexOf6 = 0;
		int index = 1;
		int count10s = 0;
		for (String string : newValues) {
			if (string.equals("6"))
				indexOf6 = index;
			if (string.equals("2"))
				indexOf2 = index;
			if (string.startsWith("10"))
				count10s++;
			// System.out.println(string);
			index++;
		}

		return (indexOf2 - count10s) * (indexOf6 - count10s);
	}

	private static int part(ArrayList<String> inputArray) {

		ArrayList<Integer> pairsInRightOrder = new ArrayList<>();
		int index = 1;

		for (int r = 0; r < inputArray.size(); r += 3) {
			String left = inputArray.get(r);
			String right = inputArray.get(r + 1);

			Node leftNode = new Node(left, 0, left.length());
			Node rightNode = new Node(right, 0, right.length());
			if (compare(leftNode, rightNode) >= 1) {
				pairsInRightOrder.add(index);
				// System.out.println(index + " in order");
			} else {
				// System.out.println(index + " NOT in order");
			}
			index++;

		}

		int sum = 0;
		for (Integer integer : pairsInRightOrder) {
			// System.out.println(integer);
			sum += integer;
		}

		return sum;
	}

	private static int compare(Node left, Node right) {
		// System.out.println("compare left " + left.value + " right " + right.value);
		// right side bigger then 1
		// right side longer then 1

		if (!left.isArray() && !right.isArray()) {
			// compare ints
			return right.value - left.value;

		} else if (left.isArray() && right.isArray()) {
			// compare arrays

		} else if (!left.isArray() && right.isArray()) {
			// change to list
			left.nodes = List.of(new Node(left.value));
			// compare arrays

		} else if (left.isArray() && !right.isArray()) {
			//// change to list
			right.nodes = List.of(new Node(right.value));
			// compare arrays
		}

		// System.out.println("compareArrays left " + left.nodes.size() + " right " +
		// right.nodes.size());
		int length = left.nodes.size() > right.nodes.size() ? left.nodes.size() : right.nodes.size();
		for (int i = 0; i < length; i++) {
			if (i == left.nodes.size()) {
				// System.out.println("left finished right wins on length");
				return 1;
			} else if (i == right.nodes.size()) {
				// System.out.println("right finished, left wins on length");
				return -1;
			} else {
				int result = compare(left.nodes.get(i), right.nodes.get(i));
				if (result != 0) {
					return result;
				}
			}
		}
		return 0;
	}

	private static int findClosingParentesis(String text, int openPos) {
		int closePos = openPos;
		int counter = 1;
		while (counter > 0) {
			char c = text.toCharArray()[++closePos];
			if (c == '[') {
				counter++;
			} else if (c == ']') {
				counter--;
			}
		}
		return closePos + 1;
	}

	private static class Node {
		int value = -1;
		List<Node> nodes = new ArrayList<>();

		boolean isArray() {
			return value == -1;
		}

		public Node(int value) {
			this.value = value;
		}

		public Node(String line, int start, int end) {
			this.parseArray(line, start, end);
		}

		private void parseArray(String line, int start, int end) {

			int closing = 0;
			if (line.startsWith("["))
				closing = findClosingParentesis(line, 0);
			if (line.startsWith("[") && closing == line.length()) {
				// System.out.println("parseArray " + line.substring(start, end));
				int i = start + 1;
				String str = "";
				while (i < end) {

					if (line.charAt(i) == ',') {
						if (str.length() > 0) {
							this.nodes.add(new Node(Integer.parseInt(str)));
							// System.out.println("parseArray , str " + str);
							str = "";
						}
						i++;
					} else if (line.charAt(i) == ']') {
						if (str.length() > 0) {
							this.nodes.add(new Node(Integer.parseInt(str)));
							// System.out.println("parseArray ] str " + str);
							str = "";
						}
						return;
					} else if (line.charAt(i) == '[') {
						int endingOfSubArray = findClosingParentesis(line, i);
						this.nodes.add(new Node(line, i, endingOfSubArray));
						i = endingOfSubArray + 1;
					} else {
						str += line.charAt(i);
						i++;
					}

				}

			} else {
				throw new IllegalStateException();
			}

		}

	}

	private static ArrayList<String> readFileToArray(String filename) {
		ArrayList<String> inputArray = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				inputArray.add(line);
//				System.out.println(line);
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
