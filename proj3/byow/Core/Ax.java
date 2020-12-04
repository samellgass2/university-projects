package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;



public class Ax {
    private static int durability = 50;
    private static int maxdurability = 50;
    private static String name = "axe";


    public static int getDurability() {
        return durability;
    }

    public static int getMaxdurability() {
        return maxdurability;
    }

    public static void changeDurability(int n) {
        durability += n;
        maxdurability += n;
    }

    public static void doAction(TETile[][] world, int xPos, int yPos) {
        chop(world, xPos, yPos);
    }

    private static void chop(TETile[][] world, int  x, int  y) {
        /* split into separate if statements to not fail 100 char style check */
        if (durability > 0) {
            if (lookLeft(world, x, y).equals(Tileset.TREE)) {
                chopLeft(world, x, y);
                durability -= 1;
            }
            if (lookRight(world, x, y).equals(Tileset.TREE)) {
                chopRight(world, x, y);
                durability -= 1;
            }
            if (lookUp(world, x, y).equals(Tileset.TREE)) {
                chopUp(world, x, y);
                durability -= 1;
            }
            if (lookDown(world, x, y).equals(Tileset.TREE)) {
                chopDown(world, x, y);
                durability -= 1;
            }
        }
        if (durability < 0) {
            Avatar.dropFromInventory("axe");
        }

    }

    public static TETile lookDown(TETile[][] world, int xPos, int yPos) {
        return world[yPos][xPos - 1];
    }

    public static TETile lookUp(TETile[][] world, int xPos, int yPos) {
        return world[yPos][xPos + 1];
    }

    public static TETile lookRight(TETile[][] world, int xPos, int yPos) {
        return world[yPos + 1][xPos];
    }

    public static TETile lookLeft(TETile[][] world, int xPos, int yPos) {
        return world[yPos - 1][xPos];
    }

    public static void chopDown(TETile[][] world, int xPos, int yPos) {
        world[yPos][xPos - 1] = Tileset.GRASS;
    }

    public static void chopUp(TETile[][] world, int xPos, int yPos) {
        world[yPos][xPos + 1] = Tileset.GRASS;
    }

    public static void chopLeft(TETile[][] world, int xPos, int yPos) {
        world[yPos - 1][xPos] = Tileset.GRASS;
    }

    public static void chopRight(TETile[][] world, int xPos, int yPos) {
        world[yPos + 1][xPos] = Tileset.GRASS;
    }
}
