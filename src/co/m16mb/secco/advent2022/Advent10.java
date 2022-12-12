package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Advent10 {

	private static final String filenamePath = "files/Advent/file10Secco.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String[]> inputArray = readFileToArray(filenamePath);

		int registerX = 1;
		// "addx V" //2 cycles
		// "noop" //1 cycle
		int cyclesDone = 0;

		// solving puzzle 1
		int signalStrength = 0;

		int ROWS = 6;
		String[] screen = new String[ROWS];
		for (int r = 0; r < ROWS; r++) {
			screen[r] = "";
		}
		for (Iterator<String[]> it = inputArray.iterator(); it.hasNext();) {
			String[] line = it.next();

			int valueToAdd = 0;

			int screenRow = cyclesDone / 40;
			int screenCol = cyclesDone % 40;
			System.out.println(" cycles done " + cyclesDone + " registerX " + registerX);

			if ("noop".equals(line[0])) {
				if (registerX - 1 <= screenCol && registerX + 1 >= screenCol) {
					screen[screenRow] += "#";
				} else
					screen[screenRow] += ".";

				cyclesDone++;

			} else if ("addx".equals(line[0])) {
				if (registerX - 1 <= screenCol && registerX + 1 >= screenCol) {
					screen[screenRow] += "#";
				} else
					screen[screenRow] += ".";

				int overflow = 0;
				if (screen[screenRow].length() == 40) {
					overflow++;
				}

				if (registerX - 1 <= screenCol + 1 && registerX + 1 >= screenCol + 1) {
					screen[screenRow + overflow] += "#";
				} else
					screen[screenRow + overflow] += ".";

				valueToAdd = Integer.parseInt(line[1]);
				cyclesDone += 2;

			}

			int modulo = (cyclesDone + 20) % 40;
			if (modulo == 0) {// || (modulo==1 && "addx".equals(line[0]))
				// System.out.println(check +" modulo " + modulo + " cycles done " + cyclesDone
				// + " registerX " +registerX);
				int thisSignalStrength = ((cyclesDone - modulo) * registerX);
				// System.out.println(check +" thisSignalStrength " + thisSignalStrength);
				signalStrength += thisSignalStrength;
			} else if (modulo == 1 && "addx".equals(line[0])) {
				// System.out.println(check +" modulo " + modulo + " cycles done " + cyclesDone
				// + " registerX " + (registerX));
				int thisSignalStrength = ((cyclesDone - modulo) * (registerX));
				// System.out.println(check +" thisSignalStrength " + thisSignalStrength);
				signalStrength += thisSignalStrength;
			}
			registerX += valueToAdd;

		}

		System.out.println("registerX " + registerX);
		System.out.println("cyclesDone " + cyclesDone);

		System.out.println("ANSWER1 " + signalStrength);

		// SECCO: 12520
		// MAC: 13440

		// solving puzzle 2

		for (int r = 0; r < ROWS; r++) {
			System.out.println(screen[r]);
		}

		// SECCO: EHPZPJGL
		// MAC: PBZGRAZA

	}

	private static ArrayList<String[]> readFileToArray(String filename) {
		ArrayList<String[]> inputArray = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				inputArray.add(line.split(" "));
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
