package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Advent07 {

	private static final String filenamePath = "files/Advent/file07Secco.txt";

	public static void main(String[] args) throws Exception {

		String input = readFileAsString(filenamePath);

		Map<String, Directory> map = readDirectories(input);

		System.out.println("ANSWER1 " + part1(map));
		// SECCO: 1989474
		// MAC: 1297683

		System.out.println("ANSWER2 " + part2(map));
		// SECCO: 1111607
		// MAC: 5756764
	}

	private static long part1(Map<String, Directory> map) {
		long totalSizeLessThan10000 = 0;
		for (Map.Entry<String, Directory> entry : map.entrySet()) {
			if (entry.getValue().dirSize <= 100000) {
				totalSizeLessThan10000 += entry.getValue().dirSize;
			}
		}
		return totalSizeLessThan10000;
	}

	private static long part2(Map<String, Directory> map) {

		long biggestDirOnFileSystem = 0;
		for (Map.Entry<String, Directory> entry : map.entrySet()) {
			Directory d = entry.getValue();
			if (d.dirSize > biggestDirOnFileSystem) {
				biggestDirOnFileSystem = d.dirSize;
				// System.out.println(d.dirName + " " + d.dirSize);
			}
		}

		long spaceNeeded = -(70000000 - biggestDirOnFileSystem - 30000000);
		System.out.println("biggestDirOnFileSystem " + biggestDirOnFileSystem + " | space available "
				+ (70000000 - biggestDirOnFileSystem + " | spaceNeeded " + spaceNeeded));

		// smallest of all that are bigger than space needed
		long biggestDirToBeDeleted = biggestDirOnFileSystem;
		for (Map.Entry<String, Directory> entry : map.entrySet()) {
			long currentDirSize = entry.getValue().dirSize;
			if (currentDirSize > spaceNeeded) {
				if (currentDirSize < biggestDirToBeDeleted) {
					biggestDirToBeDeleted = currentDirSize;
				}
			}
		}

		return biggestDirToBeDeleted;
	}

	private static Map<String, Directory> readDirectories(String input) {
		Map<String, Directory> map = new HashMap<>();
		String currentDir = "";
		for (String line : input.split("\\r?\\n")) {
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
		return map;
	}

	private static class Directory {
		long dirSize = 0;
		String parentDirName = "";
		String dirName = "";
		ArrayList<String> childDirs = new ArrayList<String>();

		public Directory(String name, String parentName) {
			this.dirName = parentName + "_" + name;
			this.parentDirName = parentName;
		}
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		System.out.println("Filesize: " + data.length());
		return data;
	}

}
