package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Advent01 {

	private static final String filenamePath = "files/Advent/file01.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		String[] elfs = fileContents.split("\\r?\\n\\r?\\n");

		// solving the first part of the puzzle
		ArrayList<Integer> caloriesByElf = new ArrayList<>();

		for (String elf : elfs) {
			int elfCalories = 0;
			for (String snack : elf.split("\\r?\\n")) {
				elfCalories += Integer.parseInt(snack);
			}
			caloriesByElf.add(elfCalories);

		}

		// sorting
		Collections.sort(caloriesByElf, Collections.reverseOrder());

		System.out.println("ANSWER1: top: " + caloriesByElf.get(0).toString());
		// Part1 Secco: 72602
		// Part1 Mac: 67450

		// solving the second part of the puzzle
		int topThree = caloriesByElf.get(0) + caloriesByElf.get(1) + caloriesByElf.get(2);
		System.out.println("ANSWER2: topThree: " + topThree);
		// Part2 Secco: 207410
		// Part2 Mac: 199357

	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}
}
