package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {


    public TETile[][] addHexagon(int sideLength) {
        TETile[][] mrHex = new TETile[2*sideLength][heightCalc(sideLength)];
        return mrHex;
    }

    private boolean isNothing(int row, int col, int sideLength) {
        int height = heightCalc(sideLength);
        if (row < sideLength / 2) {
            return false;
        } else {
            return true;
        }
    }

    private int heightCalc(int sideLength) {
        return sideLength + 2 * (sideLength - 1);
    }

}
