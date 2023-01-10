package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Advent06 {

	private static final String filenamePath = "files/Advent/file06Secco.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String input = readFileAsString(filenamePath);

		System.out.println("ANSWER1: " + findMarkerPosition(input, 4));
		// TOTAL SECCO: 1625
		// TOTAL MAC: 1647

		System.out.println("ANSWER2: " + findMarkerPosition(input, 14));
		// TOTAL SECCO: 2250
		// TOTAL MAC: 2447

	}

	private static int findMarkerPosition(String inputMessage, int markerLenght) {
		String marker = "";
		for (int i = 0; i < inputMessage.length(); i++) {
			int position = marker.indexOf(inputMessage.charAt(i));
			if (position > -1) {
				// found a char in the buffer, cutting the buffer
				marker = marker.substring(position + 1);
			}
			marker += inputMessage.charAt(i);
			if (marker.length() == markerLenght) {
				System.out.println(marker);
				return i + 1;
			}
		}
		throw new IllegalStateException("Marker not found!");
	}

	private static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}
}
