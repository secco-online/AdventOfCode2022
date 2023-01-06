package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Advent25 {

	private static final String filenamePath = "files/Advent/file25Secco.txt";

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		System.out.println("ANSWER1 " + part(fileContents));

		// SECCO: 2-2--02=1---1200=0-1
		// MAC: 2=-0=1-0012-=-2=0=01
		System.out.println("ANSWER2 just click!");

		// SECCO:
		// MAC:

	}

	private static long getNumber5formatFromCoded(char value, int offsetFromRight) {
		long power = getPowerOf5(offsetFromRight);
		long calculatedNumber = 0;
		switch (value) {
		case '2': {
			calculatedNumber = 2 * power;
			break;
		}
		case '1': {
			calculatedNumber = 1 * power;
			break;
		}
		case '0': {
			calculatedNumber = 0;
			break;
		}
		case '-': {
			calculatedNumber = -1 * power;
			break;
		}
		case '=': {
			calculatedNumber = -2 * power;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + value);
		}
		return calculatedNumber;

	}

	private static final String toSnafu(long value) {
		StringBuilder sb = new StringBuilder();
		long num = value;
		while (num > 0) {
			int r = (int) (num % 5L);
			String newDigit = "";
			switch (r) {
			case 0: {
				newDigit = "0";
				break;
			}
			case 1: {
				newDigit = "1";
				break;
			}
			case 2: {
				newDigit = "2";
				break;
			}
			case 3: {
				newDigit = "=";
				break;
			}
			case 4: {
				newDigit = "-";
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + value);
			}

			sb.append(newDigit);
			if (r > 2) {
				num += 5L;
			}
			num /= 5;
		}
		return sb.reverse().toString();
	}

	private static long getPowerOf5(int offsetFromRight) {
		long power = 1;
		for (int i = 0; i < offsetFromRight; i++) {
			power *= 5;
		}
		return power;
	}

	private static String part(String fileContents) {
		long sum = 0;
		for (String line : fileContents.split("\\r?\\n")) {
			char[] lineArray = line.toCharArray();
			long currentTransformedNumner = 0;
			for (int i = 0; i < lineArray.length; i++) {
				char charAt = lineArray[i];
				int offset = lineArray.length - i - 1;
				long numberPiece = getNumber5formatFromCoded(charAt, offset);
				// System.out.println("char " + charAt + " offset " + offset + " munberPiece " +
				// numberPiece) ;
				currentTransformedNumner += numberPiece;
			}
			System.out.println(line + " -> " + currentTransformedNumner);
			sum += currentTransformedNumner;
			System.out.println(" -> " + sum);

		}

		return toSnafu(sum);
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
