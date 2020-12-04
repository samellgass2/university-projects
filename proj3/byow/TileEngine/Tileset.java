package byow.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('Ѫ', Color.white, Color.black, "Basic Avatar");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");

    public static final TETile AXE = new TETile('Γ', Color.white, Color.black, "axe");
    public static final TETile PROFESSOR = new TETile('ƍ', Color.pink, Color.black, "professor");
    public static final TETile KEY = new TETile('ȹ', Color.cyan, Color.black, "key");
    public static final TETile BOLT = new TETile('ϟ', new Color(245, 215, 62),
            Color.black, "lightning bolt");

    public static final TETile LARROW = new TETile('⇦', Color.white, Color.black, "left arrow");
    public static final TETile RARROW = new TETile('⇨', Color.white, Color.black, "right arrow");
    public static final TETile STAIRS = new TETile('⇧', Color.white, Color.black, "up arrow");
    public static final TETile DARROW = new TETile('⇩', Color.white, Color.black, "down arrow");
    public static final TETile SMALLCOIN = new TETile('◎', Color.yellow, Color.black, "smallcoin");
    public static final TETile BIGCOIN = new TETile('◎', Color.red, Color.black, "bigcoin");

    public static final TETile KING = new TETile('♔', Color.white, Color.black, "King Avatar");
    public static final TETile QUEEN = new TETile('♕', Color.white, Color.black, "queen");
    public static final TETile ROOK = new TETile('♖', Color.white, Color.black, "rook");
    public static final TETile BISHOP = new TETile('♗', Color.white, Color.black, "bishop");
    public static final TETile KNIGHT = new TETile('♘', Color.white, Color.black, "knight");
    public static final TETile PAWN = new TETile('♙', Color.white, Color.black, "pawn");

    public static final TETile DENERO = new TETile('ƍ', Color.pink, Color.BLACK, "professor");
    public static final TETile HILFINGER =  new TETile('ƍ', Color.cyan, Color.BLACK, "professor");
    public static final TETile HUG = new TETile('♔', Color.lightGray, Color.black, "professor");
    public static final TETile WAGNER = new TETile('♙', Color.cyan, Color.black, "professor");
    public static final TETile GARCIA = new TETile('♙', Color.magenta, Color.black, "professor");
    public static final TETile WEAVER = new TETile('♖', Color.yellow, Color.black, "professor");
    public static final TETile SAHAI = new TETile('♖', Color.cyan, Color.black, "professor");
    public static final TETile ADHIKARI = new TETile('♕', Color.pink, Color.black, "professor");


}


