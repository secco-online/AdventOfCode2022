package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Advent05 {

	private static final String filenamePath = "files/Advent/file05.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		// stack arrays
		Iterator<String> it = inputArray.iterator();
		int progressive = 0;
		String[] stacksArray = new String[9];
		String[] stacksArray2 = new String[9];
		for (int i = 0; i < stacksArray.length; i++) {
			stacksArray[i] = "";
		}

		while (it.hasNext()) {
			String oneLine = it.next();

			// reading stacks
			if (progressive < 8) {
				// top of stack at the beginning of the string
				stacksArray[0] = stacksArray[0] + oneLine.charAt(1);
				stacksArray[1] = stacksArray[1] + oneLine.charAt(5);
				stacksArray[2] = stacksArray[2] + oneLine.charAt(9);
				stacksArray[3] = stacksArray[3] + oneLine.charAt(13);
				stacksArray[4] = stacksArray[4] + oneLine.charAt(17);
				stacksArray[5] = stacksArray[5] + oneLine.charAt(21);
				stacksArray[6] = stacksArray[6] + oneLine.charAt(25);
				stacksArray[7] = stacksArray[7] + oneLine.charAt(29);
				stacksArray[8] = stacksArray[8] + oneLine.charAt(33);
			}

			// displaying stacks
			if (progressive == 9) {
				for (int i = 0; i < stacksArray.length; i++) {
					stacksArray[i] = stacksArray[i].trim();
					// copy for the second part of the exercise
					stacksArray2[i] = new String(stacksArray[i]);
					System.out.println("stacksArray[i]: " + stacksArray[i]);
				}
			}

			// reading moves and solving puzzles
			if (progressive > 9) {
				String[] move = oneLine.split(" ");
				// System.out.println(" many: " + move[1] + " from: " + move[3]
				// + " to: " + move[5]);
				{
					// solving the first part of the puzzle
					String s = stacksArray[Integer.parseInt(move[3]) - 1];
					String d = stacksArray[Integer.parseInt(move[5]) - 1];
					int many = Integer.parseInt(move[1]);
					String what = s.substring(0, many);
					String whatReordered = "";
					for (int i = 0; i < what.length(); i++) {
						whatReordered = what.charAt(i) + whatReordered;
					}
					String sNew = s.substring(many);
					String dNew = whatReordered + d;
					// System.out.println("s: " + s + " d: " + d +
					// " moved(whatReordered): " + whatReordered + " sNew: "
					// + sNew + " dNew: " + dNew);
					stacksArray[Integer.parseInt(move[5]) - 1] = dNew;
					stacksArray[Integer.parseInt(move[3]) - 1] = sNew;
				}

				{
					// solving the second part of the puzzle
					String s = stacksArray2[Integer.parseInt(move[3]) - 1];
					String d = stacksArray2[Integer.parseInt(move[5]) - 1];
					int many = Integer.parseInt(move[1]);
					String what = s.substring(0, many);
					String sNew = s.substring(many);
					String dNew = what + d;
					// System.out.println("s: " + s + " d: " + d +
					// " moved(what): " + what + " sNew: " + sNew + " dNew: "
					// + dNew);
					stacksArray2[Integer.parseInt(move[5]) - 1] = dNew;
					stacksArray2[Integer.parseInt(move[3]) - 1] = sNew;
				}

			}

			progressive++;
		}

		String onTop1 = "";
		// displaying stacks
		for (int i = 0; i < stacksArray.length; i++) {
			stacksArray[i] = stacksArray[i].trim();
			System.out.println("stacksArray[i]: " + stacksArray[i]);
			onTop1 += stacksArray[i].charAt(0);
		}

		System.out.println("ANSWER1: onTop1 " + onTop1);
		// TOTAL SECCO: QNHWJVJZW
		// TOTAL MAC: BSDMQFLSP

		String onTop2 = "";
		// displaying stacks
		for (int i = 0; i < stacksArray2.length; i++) {
			stacksArray2[i] = stacksArray2[i].trim();
			System.out.println("stacksArray2[i]: " + stacksArray2[i]);
			onTop2 += stacksArray2[i].charAt(0);
		}

		System.out.println("ANSWER2: onTop2 " + onTop2);
		// TOTAL SECCO: BPCZJLFJW
		// TOTAL MAC: PGSQBFLDP

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
