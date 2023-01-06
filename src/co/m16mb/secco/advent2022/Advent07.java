package co.m16mb.secco.advent2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Advent07 {

	private static final String filenamePath = "files/Advent/file07Secco.txt";
	private static Map<String, Directory> map = new HashMap<>();

	public static void main(String[] args) throws IOException {

		// reading the input file
		ArrayList<String> inputArray = readFileToArray(filenamePath);

		// solving puzzle 1
		String currentDir = "";
		for (Iterator<String> it = inputArray.iterator(); it.hasNext();) {
			String line = it.next();
			String[] array = line.split(" ");
			// System.out.println("line: " + line);
			if (line.startsWith("$ cd")) {
				// changing dir up
				if (array[2].equals("..")) {
					String parent = map.get(currentDir).parentDirName;
					// System.out.println("DIR UP from " + currentDir + " to " + parent);

					// adding sub folders to current folder
					Directory d = map.get(currentDir);
					for (String child : d.childDirs) {
						// System.out.println("Looking for " + child);
						d.dirSize += map.get(child).dirSize;
					}

					currentDir = parent;

				} else {
					// changing directory down to a new one
					map.put(currentDir + "_" + array[2], new Directory(array[2], currentDir));
					currentDir = currentDir + "_" + array[2];
					// System.out.println("added dir : " + array[2]);
				}
			} else if (line.startsWith("$ ls")) {
				// System.out.println("DO NOTHING");
			} else if (line.startsWith("dir")) {
				// System.out.println("Adding Child " + array[1]);
				map.get(currentDir).childDirs.add(currentDir + "_" + array[1]);
			} else {
				// System.out.println("Adding size " + array[0]);
				long filesize = Long.parseLong(array[0]);
				map.get(currentDir).dirSize += filesize;
			}

		}
		
		long totalSizeLessThan10000 = 0;
		for (Map.Entry<String, Directory> entry : map.entrySet()) {
			if (entry.getValue().dirSize <= 100000) {
				// System.out.println("new size " +entry.getKey() +"
				// "+entry.getValue().dirSize);

				totalSizeLessThan10000 += entry.getValue().dirSize;
				// System.out.println("totalSizeLessThan10000 " +totalSizeLessThan10000);
			}
		}

		System.out.println("ANSWER1 " + totalSizeLessThan10000);

		// SECCO: 1989474
		// MAC: 1297683

		// solving puzzle 2
		
		long biggestDir = 0;
		for (Map.Entry<String, Directory> entry : map.entrySet()) {
			/*
			 * System.out.println("Key = " + entry.getKey() + ", parent = " +
			 * entry.getValue().parentDirName +", size = " + entry.getValue().dirSize);
			 */
			if (entry.getValue().dirSize > biggestDir) {
				// System.out.println("new size " + entry.getKey() + " " +
				// entry.getValue().dirSize);
				biggestDir = entry.getValue().dirSize;
				// System.out.println("totalSizeLessThan10000 " +totalSizeLessThan10000);
			}
		}

		System.out.println("biggestDir " + biggestDir);
		System.out.println("space available " + (70000000 - biggestDir));
		long spaceNeeded = -(70000000 - biggestDir - 30000000);
		System.out.println("spaceNeeded " + spaceNeeded);

		long biggestDirFound = biggestDir;
		for (Map.Entry<String, Directory> entry : map.entrySet()) {
			long currentDirSize = entry.getValue().dirSize;
			if (currentDirSize > spaceNeeded) {
				// System.out.println("spaceNeeded smaller than " + currentDirSize);
				if (currentDirSize < biggestDirFound) {

					biggestDirFound = currentDirSize;

				}
				// System.out.println("totalSizeLessThan10000 " +totalSizeLessThan10000);
			}
		}

		System.out.println("ANSWER2 smallest of all that are bigger than space needed " + biggestDirFound);
		// SECCO: 1111607
		// MAC: 5756764

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

	static class Directory {
		long dirSize = 0;
		String parentDirName = "";
		String dirName = "";
		ArrayList<String> childDirs = new ArrayList<String>();

		public Directory(String name, String parentName) {
			this.dirName = parentName + "_" + name;
			this.parentDirName = parentName;
		}

		public long compareTo(Directory o) {
			return o.dirSize - this.dirSize;
		}
	}
}
