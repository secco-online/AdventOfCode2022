package co.m16mb.secco.advent2022;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVReader;

public class Advent01 {

	private static final String filenamePath = "files/Advent/file01.txt";

	public static void main(String[] args) throws IOException {

		// reading the input file
		CSVReader reader = new CSVReader(new FileReader(filenamePath), ';');
		ArrayList<String> inputArray = new ArrayList<>();
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			inputArray.add(nextLine[0]);
		}
		reader.close();

		// solving the first part of the puzzle
		Iterator<String> it = inputArray.iterator();

		int elfID = 1;
		int elfCalories = 0;
		ArrayList<Elf> outputArray = new ArrayList<>();

		while (it.hasNext()) {
			String line = it.next();
			if ("".equals(line.trim())) {

				outputArray.add(new Elf(elfID, elfCalories));

				elfID++;
				elfCalories = 0;
			} else {
				elfCalories += Integer.parseInt(line);
			}
		}

		// sorting
		Collections.sort(outputArray, Collections.reverseOrder());

		System.out.println("ANSWER1: top: " + outputArray.get(0).toString());
		// Part1 Secco: 72602
		// Part1 Mac: 67450

		// solving the second part of the puzzle
		int topThree = outputArray.get(0).elfCalories + outputArray.get(1).elfCalories + outputArray.get(2).elfCalories;
		System.out.println("ANSWER2: topThree: " + topThree);
		// Part2 Secco: 207410
		// Part2 Mac: 199357

	}

	private static class Elf implements Comparable<Elf> {
		int elfID = 0;
		int elfCalories = 0;

		public Elf(int id, int cal) {
			this.elfID = id;
			this.elfCalories = cal;
		}

		@Override
		public int compareTo(Elf elf) {
			return this.elfCalories - elf.elfCalories;
		}

		@Override
		public String toString() {
			return "elfID " + elfID + " elfCalories " + elfCalories;
		}
	}

}
