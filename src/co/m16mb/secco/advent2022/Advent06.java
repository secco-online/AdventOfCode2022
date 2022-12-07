package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Advent06 {

	private static final String filenamePath = "files/Advent/file06Secco.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		// stack arrays
		String line = inputArray.get(0);
		
		// solving puzzle one
		String startOfPacketSet = "";
		for (int i = 0; i < line.length(); i++) {
			int position = startOfPacketSet.indexOf(line.charAt(i));
			// System.out.println("startOfPacketSet: " + startOfPacketSet);
			if (position > -1) {
				// found a char in the buffer, cutting the buffer
				// System.out.println("contains at: " + position);
				startOfPacketSet = startOfPacketSet.substring(position + 1);
			}
			startOfPacketSet += line.charAt(i);
			if (startOfPacketSet.length() == 4) {
				System.out.println("ANSWER1 startOfPacketSet: " + startOfPacketSet + " " + (i + 1));
				break;
			}
		}

		// TOTAL SECCO: 1625
		// TOTAL MAC: 1647

		// solving puzzle one
		String startOfMessageSet = "";
		for (int i = 0; i < line.length(); i++) {
			int position = startOfMessageSet.indexOf(line.charAt(i));
			// System.out.println("startOfPacketSet: " + startOfPacketSet);
			if (position > -1) {
				// found a char in the buffer, cutting the buffer
				// System.out.println("contains at: " + position);
				startOfMessageSet = startOfMessageSet.substring(position + 1);
			}
			startOfMessageSet += line.charAt(i);
			if (startOfMessageSet.length() == 14) {
				System.out.println("ANSWER2 startOfMessageSet: " + startOfMessageSet + " " + (i + 1));
				break;
			}
		}

		// TOTAL SECCO: 2250
		// TOTAL MAC: 2447

	}

	private static ArrayList<String> readFileToArray(String filename) {
		ArrayList<String> inputArray = new ArrayList<>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
				inputArray.add(line);
				//System.out.println(line);
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.println("Rows in file: " + inputArray.size());
		return inputArray;
	}
}
