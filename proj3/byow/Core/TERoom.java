package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class TERoom {

    public static void newRoom(TETile[][] world, int startX, int startY, int width, int height) {
        for (int xPos = 0; xPos < width; xPos++) {
            for (int yPos = 0; yPos < height; yPos++) {
                if (edgeTile(xPos, yPos, width, height)) {
                    world[startX + xPos][startY + yPos] = Tileset.TREE;
                } else {
                    world[startX + xPos][startY + yPos] = Tileset.GRASS;
                }
            }
        }
    }

    private static boolean edgeTile(int xPos, int yPos, int width, int height) {
        if (xPos == 0 || xPos == width - 1) {
            return true;
        } else if (yPos == 0 || yPos == height - 1) {
            return true;
        }
        return false;
    }

    public static void newHorHallway(TETile[][] world, int xPos, int yPos, int length) {
        if (world[yPos][xPos].equals(Tileset.TREE)) {
            world[yPos][xPos] = Tileset.GRASS;
            return;
        }
        if (length == 0) {
            world[yPos][xPos] = Tileset.TREE;
            return;
        }
        if (world[yPos][xPos].equals(Tileset.SAND)) {
            world[yPos][xPos - 1] = Tileset.TREE;
            return;
        }
        world[yPos][xPos] = Tileset.GRASS;
        world[yPos + 1][xPos] = Tileset.TREE;
        world[yPos - 1][xPos] = Tileset.TREE;
        newHorHallway(world, xPos + 1, yPos, length - 1);
    }

    public static void newVerHallway(TETile[][] world, int xPos, int yPos, int length) {
        if (world[yPos][xPos].equals(Tileset.TREE)) {
            world[yPos][xPos] = Tileset.GRASS;
            return;
        }
        if (length == 0) {
            world[yPos][xPos] = Tileset.TREE;
            return;
        }
        if (world[yPos][xPos].equals(Tileset.SAND)) {
            world[yPos - 1][xPos] = Tileset.TREE;
            return;
        }
        world[yPos][xPos] = Tileset.GRASS;
        world[yPos][xPos + 1] = Tileset.TREE;
        world[yPos][xPos - 1] = Tileset.TREE;
        newVerHallway(world, xPos, yPos + 1, length - 1);
    }

}
