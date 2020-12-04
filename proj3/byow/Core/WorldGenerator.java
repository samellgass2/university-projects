package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class WorldGenerator {

    public static void waterPlanet(TETile[][] world) {
        for (int y = 0; y < world.length; y++) {
            for (int x = 0; x < world[y].length; x++) {
                if (sandTile(x, y)) {
                    world[y][x] = Tileset.SAND;
                } else {
                    world[y][x] = Tileset.WATER;
                }
            }
        }
    }

    public static boolean sandTile(int y, int x) {
        if (y >= 3 && y <= (Engine.HEIGHT - 4)) {
            if (x >= 3 && x <= 4 || x >= (Engine.WIDTH - 5) && x <= (Engine.WIDTH - 4)) {
                return true;
            }
        }
        if (x >= 3 && x <= (Engine.WIDTH - 4)) {
            if (y >= 3 && y <= 4 || y >= (Engine.HEIGHT - 5) && y <= (Engine.HEIGHT - 4)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validPlacement(int xStart, int yStart, int width, int height) {
        if (xStart <= 4 || yStart <= 4) {
            return false;
        } else if (xStart + width <= (Engine.WIDTH - 5) && yStart + height <= (Engine.HEIGHT - 5)) {
            return true;
        }
        return false;
    }

    public static void openify(TETile[][] world) {
        for (int x = 0; x < Engine.WIDTH; x++) {
            for (int y = 0; y < Engine.HEIGHT; y++) {
                if (openable(world, y, x)) {
                    world[x][y] = Tileset.GRASS;
                }
            }
        }
    }

    public static boolean openable(TETile[][] world, int x, int y) {
        TETile grass = Tileset.GRASS;
        if (!validPlacement(y, x, 0, 0)) {
            return false;
        }
        if (world[y][x].equals(Tileset.TREE)) {
            if (world[y][x + 1].equals(grass) && world[y][x - 1].equals(grass)) {
                return true;
            } else {
                return world[y + 1][x].equals(grass) && world[y - 1][x].equals(grass);
            }
        }
        return false;
    }

    public static void randomOpenify(TETile[][] world, int num, Random seed) {
        int timeout = 100;
        int numOpened = 0;
        for (int i = 0; i < timeout || numOpened < num; i++) {
            int newX = randomX(seed);
            int newY = randomY(seed);
            if (randomOpenable(world, newY, newX)) {
                world[newX][newY] = Tileset.GRASS;
                numOpened++;
            }
        }
    }

    public static boolean randomOpenable(TETile[][] world, int xPos, int yPos) {
        boolean openable = true;
        if (!validPlacement(yPos, xPos, 0, 0)) {
            openable = false;
        }
        if (world[yPos][xPos].equals(Tileset.TREE)) {
            if (world[yPos + 1][xPos].equals(Tileset.WATER)
                    || world[yPos + 1][xPos].equals(Tileset.SAND)) {
                openable = false;
            }
            if (world[yPos - 1][xPos].equals(Tileset.WATER)
                    || world[yPos - 1][xPos].equals(Tileset.SAND)) {
                openable = false;
            }
            if (world[yPos][xPos + 1].equals(Tileset.WATER)
                    || world[yPos][xPos + 1].equals(Tileset.SAND)) {
                openable = false;
            }
            if (world[yPos][xPos - 1].equals(Tileset.WATER)
                    || world[yPos][xPos - 1].equals(Tileset.SAND)) {
                openable = false;
            }
        } else {
            openable = false;
        }
        return openable;
    }

    public static void addRandomRoom(TETile[][] world, int x, int y, int width, int height) {
        if (validPlacement(x, y, width, height)) {
            TERoom.newRoom(world, x, y, width, height);
        }
    }

    public static void addRandomRoom(TETile[][] world, int width, int height, Random random) {
        int startX = randomX(random);
        int startY = randomY(random);
        addRandomRoom(world, startX, startY, width, height);
    }

    public static void cleanup(TETile[][] world) {
        for (int x = 0; x < Engine.WIDTH; x++) {
            for (int y = 0; y < Engine.HEIGHT; y++) {
                if (validPlacement(x, y, 0, 0)) {
                    if (world[x][y].equals(Tileset.WATER)) {
                        world[x][y] = Tileset.SAND;
                    }
                }
            }
        }
    }

    public static void findHallway(TETile[][] world, int numHalls, int maxLen, Random seed) {
        int timeout = 200;
        int halls = 0;
        for (int i = 0; halls < numHalls || i < timeout; i++) {
            int newX = randomX(seed);
            int newY = randomY(seed);
            if (hallwayable(world, newY, newX, maxLen, seed)) {
                halls++;
            }

        }
    }

    public static boolean hallwayable(TETile[][] world, int x, int y, int maxLen, Random seed) {
        boolean hallwayable = false;
        if (world[y][x].equals(Tileset.TREE)) {
            if (world[y][x + 1].equals(Tileset.WATER)) {
                hallwayable = true;
                int length = seed.nextInt(maxLen - 2) + 2;
                world[y][x] = Tileset.GRASS;
                TERoom.newHorHallway(world, x, y, length);
            } else if (world[y + 1][x].equals(Tileset.WATER)) {
                hallwayable = true;
                int length = seed.nextInt(maxLen - 2) + 2;
                world[y][x] = Tileset.GRASS;
                TERoom.newVerHallway(world, x, y, length);
            }
        }
        return hallwayable;
    }

    public static void makeAvatar(TETile[][] world, Random seed) {
        boolean placed = false;
        while (!placed) {
            int startY = randomX(seed);
            int startX = randomY(seed);
            if (world[startY][startX].equals(Tileset.GRASS)) {
                placed = true;
                Avatar myavatar = new Avatar(world, startX, startY, seed);
            }
        }
    }

    public static void makeProfessor(TETile[][] world, Random seed) {
        TETile prof = Professor.whichProfTile();
        makeTile(world, seed, prof);
    }

    public static void makeStairs(TETile[][] world, Random seed) {
        makeTile(world, seed, Tileset.STAIRS);
    }

    public static void makeKey(TETile[][] world, Random seed) {
        makeTile(world, seed, Tileset.KEY);
    }

    public static void makeAxe(TETile[][] world, Random seed) {
        makeTile(world, seed, Tileset.AXE);
    }

    public static void makeTile(TETile[][] world, Random seed, TETile placedtile) {
        boolean placed = false;
        while (!placed) {
            int startY = randomX(seed);
            int startX = randomY(seed);
            if (world[startY][startX].equals(Tileset.GRASS)) {
                placed = true;
                world[startY][startX] = placedtile;
            }
        }
    }


    public static void populateWorld(TETile[][] world, Random seed) {
        WorldGenerator.waterPlanet(world);
        int numBigRooms = seed.nextInt(15) + 15;
        int numSmallRooms = seed.nextInt(40) + 25;

        for (int i = 0; i < numBigRooms; i++) {
            int width = seed.nextInt(2) + 7;
            int height = seed.nextInt(2) + 7;
            WorldGenerator.addRandomRoom(world, width, height, seed);
        }
        for (int j = 0; j < numSmallRooms; j++) {
            int width = seed.nextInt(5) + 3;
            int height = seed.nextInt(5) + 3;
            WorldGenerator.addRandomRoom(world, width, height, seed);
        }
        for (int k = 0; k < Professor.currFloor() + 7; k++) {
            makeTile(world, seed, Tileset.SMALLCOIN);
        }

        for (int h = 0; h < Professor.currFloor() - 1; h++) {
            makeTile(world, seed, Tileset.BIGCOIN);
        }
        int numOpens = seed.nextInt(35) + 35;
        WorldGenerator.randomOpenify(world, 20, seed);
        WorldGenerator.openify(world);
        WorldGenerator.findHallway(world, 80, 50, seed);
        WorldGenerator.randomOpenify(world, numOpens, seed);
        WorldGenerator.cleanup(world);
        makeAvatar(world, seed);
        makeStairs(world, seed);
        if (Professor.currFloor() > 1) {
            makeAxe(world, seed);
        }
        makeProfessor(world, seed);
    }

    public static int randomX(Random random) {
        return random.nextInt(Engine.WIDTH - 8) + 4;
    }
    public static int randomY(Random random) {
        return random.nextInt(Engine.HEIGHT - 8) + 4;
    }
}
