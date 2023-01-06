package co.m16mb.secco.advent2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Advent22 {

	private static final String filenamePath = "files/Advent/file22Secco.txt";

	private static final char WALL = '#';
	private static final char SPACE = '.';

	public static void main(String[] args) throws Exception {

		// reading the input file
		String fileContents = readFileAsString(filenamePath);

		String[] parts = fileContents.split("\\r?\\n\\r?\\n");

		// solving puzzle 1
		Map<Point, Character> caveMap = new HashMap<>();

		String[] linesMap = parts[0].split("\\r?\\n");
		int startingOffset = Integer.MAX_VALUE;
		for (int y = 0; y < linesMap.length; y++) {
			char[] line = linesMap[y].toCharArray();
			for (int x = 0; x < line.length; x++) {
				switch (line[x]) {
				case ' ': {
					break;
				}
				case WALL: {
					if (y == 0 && startingOffset > x)
						startingOffset = x;
					caveMap.put(new Point(x + 1, y + 1), line[x]);
					break;
				}
				case SPACE: {
					if (y == 0 && startingOffset > x)
						startingOffset = x;
					caveMap.put(new Point(x + 1, y + 1), line[x]);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + line[x]);
				}
			}
		}

		Point startingPoint = new Point(startingOffset + 1, 1);
		System.out.println("START: " + startingPoint);

		char[] commands = parts[1].toCharArray();
		ArrayList<Command> commandsArray = new ArrayList<>();
		String buffer = "";
		for (int i = 0; i < commands.length; i++) {

			if (commands[i] >= '0' && commands[i] <= '9') {
				buffer += commands[i];
				if (commands.length - 1 == i)
					commandsArray.add(new Command(true, Integer.parseInt(buffer), '0'));
			} else {
				// if(buffer.length()>0)
				commandsArray.add(new Command(true, Integer.parseInt(buffer), '0'));
				buffer = "";
				commandsArray.add(new Command(false, -1, commands[i]));
			}
		}

		System.out.println(commands);

		System.out.println("ANSWER1 " + part(new Cave(caveMap, commandsArray, startingPoint, 0, startingOffset, true)));

		// SECCO: 76332
		// MAC: 76332
		System.out
				.println("ANSWER2 " + part(new Cave(caveMap, commandsArray, startingPoint, 0, startingOffset, false)));

		// SECCO: 144012
		// MAC: 144012

	}

	private static class Cave {

		final int cubeSize;
		final Map<Point, Character> caveMap;
		ArrayList<Command> commandsArray;
		int currentCommand = 0;

		Point currentPoint;
		int currentDirection;
		final boolean part1;

		boolean performNextMove() {

			Command command = getNextCommand();

			if (command == null) {
				return false;
			}

			if (command.isMove) {
				// System.out.println("MOVE " + command);

				for (int i = 0; i < command.distance; i++) {
					Point nextMove = currentPoint.getPointAt(currentDirection, 1);
					if (caveMap.get(nextMove) == null) {
						// get the point on opposite side, if not a wall
						OppositePointDirection oppositePoint;
						if (part1)
							oppositePoint = getOppositeSidePoint(currentPoint, currentDirection, cubeSize);
						else
							oppositePoint = nextOnCube(currentPoint, currentDirection);
						if (isWall(oppositePoint.point)) {
							// System.out.println("Found opposite point from : " + currentPoint + " to " +
							// oppositePoint
							// + " but it is a wall. STOP" + " dir " + currentDirection);
							return true;
						} else {

							// System.out.println("Found opposite point from : " + currentPoint + " to " +
							// oppositePoint
							// + " and it is not wall. CONTINUE" + " dir " + currentDirection + " still for
							// "
							// + (command.distance - i));
							currentPoint = oppositePoint.point;
							currentDirection = oppositePoint.direction;
						}

					} else if (!isWall(nextMove)) {
						// there is space, move and record position
						currentPoint = nextMove;
					} else if (isWall(nextMove)) {
						// hit the wall, stop
						// System.out.println("Wall hit. STOP at " + currentPoint + " dir "
						// +currentDirection);

						return true;
					}
				}
				// System.out.println("Finished moving. Stopped at " + currentPoint + " dir "
				// +currentDirection);
			} else {
				// System.out.println("TURN " + command);
				currentDirection = changeDirection(command.directionChange);
			}

			// System.out.println("Dir: " + currentDirection + " pos " + currentPoint);
			return true;
		}

		Command getNextCommand() {
			// System.out.println("Command: " + currentCommand + " " +
			// commandsArray.get(currentCommand));
			if (currentCommand >= commandsArray.size())
				return null;
			else
				return commandsArray.get(currentCommand++);
		}

		Cave(Map<Point, Character> map, ArrayList<Command> commandsArray, Point currentPoint, int currentDirection,
				int cubeSize, boolean part1) {
			this.caveMap = map;
			this.commandsArray = commandsArray;
			this.currentDirection = currentDirection;
			this.currentPoint = currentPoint;
			this.cubeSize = cubeSize;
			this.part1 = part1;

		}

		OppositePointDirection getOppositeSidePoint(Point fromPoint, int direction, int cubeSize) {

			int oppositeDirection = (direction + 2) % 4;

			boolean looking = true;
			Point latestFound = fromPoint;
			
			while (looking) {
				Point newPoint = latestFound.getPointAt(oppositeDirection, cubeSize);
				if (caveMap.containsKey(newPoint)) {

					latestFound = newPoint;
				} else {
					looking = false;
					return new OppositePointDirection(latestFound.getPointAt(oppositeDirection, cubeSize - 1),
							direction);
				}
			}
			throw new IllegalStateException();
		}


		boolean isWall(Point p) {
			switch (caveMap.get(p)) {
			case WALL: {
				return true;
			}
			case SPACE: {
				return false;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + caveMap.get(p));
			}
		}

		int changeDirection(char turnTo) {
			switch (turnTo) {
			case 'R': {
				return (currentDirection + 1) % 4;
			}
			case 'L': {
				return (currentDirection - 1 + 4) % 4;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + turnTo);
			}

		}
		
		

	    private static OppositePointDirection nextOnCube(Point curr, int direction) {
	        if (direction == Point.RIGHT) {
	            return stepRightOnCube(curr);
	        } else if (direction == Point.LEFT) {
	            return stepLeftOnCube(curr);
	        } else if (direction == Point.DOWN) {
	            return stepDownOnCube(curr);
	        } else if (direction == Point.UP) {
	            return stepUpOnCube(curr);
	        }
	        throw new IllegalStateException();
	    }

	    private static OppositePointDirection stepUpOnCube(Point curr) {
	        return switch (curr.getFace()) {
	        case "A" -> new OppositePointDirection(new Point(1, curr.x - 50 + 150), Point.RIGHT);
	        case "B" -> new OppositePointDirection(new Point(curr.x - 100, 200), Point.UP);
	        case "D" -> new OppositePointDirection(new Point(51, curr.x + 50), Point.RIGHT);
	        default -> throw new IllegalStateException();
	        };
	    }

	    private static OppositePointDirection stepDownOnCube(Point curr) {
	        return switch (curr.getFace()) {
	        case "B" -> new OppositePointDirection(new Point(100, curr.x - 50), Point.LEFT);
	        case "E" -> new OppositePointDirection(new Point(50, curr.x - 50 + 150), Point.LEFT);
	        case "F" -> new OppositePointDirection(new Point(curr.x + 100, 1), Point.DOWN);
	        default -> throw new IllegalStateException();
	        };
	    }

	    private static OppositePointDirection stepLeftOnCube(Point curr) {
	        return switch (curr.getFace()) {
	        case "A" -> new OppositePointDirection(new Point(1, 151 - curr.y), Point.RIGHT);
	        case "C" -> new OppositePointDirection(new Point(curr.y - 50, 101), Point.DOWN);
	        case "D" -> new OppositePointDirection(new Point(51, 151 - curr.y), Point.RIGHT);
	        case "F" -> new OppositePointDirection(new Point(curr.y - 150 + 50, 1), Point.DOWN);
	        default -> throw new IllegalStateException();
	        };
	    }

	    private static OppositePointDirection stepRightOnCube(Point curr) {
	        return switch (curr.getFace()) {
	        case "B" -> new OppositePointDirection(new Point(100, 151 - curr.y), Point.LEFT);
	        case "C" -> new OppositePointDirection(new Point(curr.y - 50 + 100, 50), Point.UP);
	        case "E" -> new OppositePointDirection(new Point(150, 151 - curr.y), Point.LEFT);
	        case "F" -> new OppositePointDirection(new Point(curr.y - 150 + 50, 150), Point.UP);
	        default -> throw new IllegalStateException();
	        };
	    }

	}

	private static final record Command(boolean isMove, int distance, char directionChange) {

	}

	private static final record OppositePointDirection(Point point, int direction) {

	}

	private static final record Point(int x, int y) {
		final static int RIGHT = 0;
		final static int DOWN = 1;
		final static int LEFT = 2;
		final static int UP = 3;
		
		Point getPointAt(int direction, int shift) {
			switch (direction) {
			case 0: {
				return new Point(x + shift, y);
			}
			case 1: {
				return new Point(x, y + shift);
			}
			case 2: {
				return new Point(x - shift, y);
			}
			case 3: {
				return new Point(x, y - shift);
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + direction);
			}

		}
		String getFace() {
            if (y < 51 && x < 101) {
                return "A";
            } else if (y < 51 && x > 100) {
                return "B";
            } else if (y > 50 && y < 101) {
                return "C";
            } else if (y > 100 && y < 151 && x < 51) {
                return "D";
            } else if (y > 100 && y < 151 && x > 50) {
                return "E";
            } else {
                return "F";
            }
        }
	}

	private static int part(Cave cave) {

		boolean moving = true;

		while (moving) {
			moving = cave.performNextMove();
		}
		System.out.println("FINISHED");

		return cave.currentPoint.y * 1000 + cave.currentPoint.x * 4 + cave.currentDirection;
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
