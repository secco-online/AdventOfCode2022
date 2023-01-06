package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Advent20 {

	private static final String filenamePath = "files/Advent/file20Secco.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		ArrayList<Code> codes = new ArrayList<>();
		int index = 1;
		for (String string : fileContents.split("\\r?\\n")) {
			codes.add(new Code(Integer.parseInt(string), index++));
		}
		System.out.println(codes.size());

		// solving puzzle 1
		System.out.println("ANSWER1 " + part(codes));
		// SECCO: 13289
		// MAC: 4151
		
		System.out.println("ANSWER2 " + part2(codes));

		// SECCO: 2865721299243
		// MAC: 7848878698663

	}

	private static final record Code(long value, int id) {
	}

	private static long part(ArrayList<Code> codes) {
		var moved = new ArrayList<>(codes);

		for (Code code : codes) {
			int idx = moved.indexOf(code);
			moved.remove(idx);
			long dif = code.value % moved.size();
			int nIdx = (int) ((dif + idx + moved.size()) % moved.size());
			moved.add(nIdx, code);
		}
		int zeroIdx = -1;
		for (int i = 0; i < moved.size() && zeroIdx < 0; i++) {
			if (moved.get(i).value == 0) {
				zeroIdx = i;
				System.out.println("   0: " + moved.get(i));
			}
		}

		Code v1000 = moved.get((zeroIdx + 1000) % moved.size());
		Code v2000 = moved.get((zeroIdx + 2000) % moved.size());
		Code v3000 = moved.get((zeroIdx + 3000) % moved.size());

		System.out.println("1000: " + v1000);
		System.out.println("2000: " + v2000);
		System.out.println("3000: " + v3000);

		return v1000.value + v2000.value + v3000.value;
	}

	private static long part2(ArrayList<Code> codes) {

		ArrayList<Code> decrypted = new ArrayList<>();
		for (Code code : codes) {
			decrypted.add(new Code(code.value * 811589153, code.id));
		}

		ArrayList<Code> moved = new ArrayList<>(decrypted);
		for (int i = 0; i < 10; i++) {
			for (Code code : decrypted) {
				int idx = moved.indexOf(code);
				moved.remove(idx);
				long dif = code.value % moved.size();
				int nIdx = (int) ((dif + idx + moved.size()) % moved.size());
				moved.add(nIdx, code);
			}
		}
		int zeroIdx = -1;
		for (int i = 0; i < moved.size() && zeroIdx < 0; i++) {
			if (moved.get(i).value == 0) {
				zeroIdx = i;
				System.out.println("   0: " + moved.get(i));
			}
		}

		Code v1000 = moved.get((zeroIdx + 1000) % moved.size());
		Code v2000 = moved.get((zeroIdx + 2000) % moved.size());
		Code v3000 = moved.get((zeroIdx + 3000) % moved.size());

		System.out.println("1000: " + v1000);
		System.out.println("2000: " + v2000);
		System.out.println("3000: " + v3000);

		return v1000.value + v2000.value + v3000.value;
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
