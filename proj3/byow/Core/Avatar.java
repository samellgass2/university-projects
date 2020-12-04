package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.*;

public class Avatar {
    private static ArrayList<String> inventory;
    private static HashMap<String, Character> inventorymap;
    private static HashMap<Character, TETile> skins;
    private static Point position;
    private static TETile skin = Tileset.AVATAR;
    private boolean created = false;

    public Avatar(TETile[][] world, int xPos, int yPos, Random seeder) {
        inventory = new ArrayList<>();
        position = new Point(xPos, yPos);
        world[yPos][xPos] = skin;
        inventory.add("axe");
        if (!created) {
            initializeInventoryHashMapAndSkins();
            created = true;

        }
    }

    public static void initializeInventoryHashMapAndSkins() {
        inventorymap = new HashMap<>();
        inventorymap.put("axe", 'Γ');
        inventorymap.put("key", 'ȹ');
        //ADD NEW INVENTORY ITEMS HERE FOR UI USAGE
        skins = new HashMap<>();
        skins.put('1', Tileset.AVATAR);
        skins.put('2', new TETile('Ѫ', Color.blue, Color.black, "Blue Avatar"));
        skins.put('3', new TETile('Ѫ', Color.red, Color.black, "Red Avatar"));
        skins.put('4', Tileset.KING);
        skins.put('5', new TETile('ƍ', Color.blue, Color.black, "Professor's Favorite"));
    }

    public static HashMap<String, Character> getInventorymap() {
        return inventorymap;
    }

    public static int xPos() {
        return position.x;
    }

    public static int yPos() {
        return position.y;
    }

    public static ArrayList<String> getInventory() {
        return inventory;
    }

    public static void dropFromInventory(String s) {
        inventory.remove(s);
    }

    public static void addToInventory(String s) {
        if (!inventory.contains(s)) {
            inventory.add(s);
        } else if (s.equals("axe") && inventory.contains("axe")) {
            Ax.changeDurability(50);
        }
    }

    public static void takeInput(TETile[][] world, char input, TERenderer ter, boolean rerender) {
        if (Professor.inEncounter()) {
            if (input == Professor.correctAnswer() && !Professor.finishedRiddle()) {
                Menus.answeredRight(rerender);
                WorldGenerator.makeKey(world, Engine.getSeed());
                Professor.endEncounter(true);
            } else if (input == Professor.correctAnswer()) {
                Menus.answeredRightAgain(rerender);
                Professor.endEncounter(true);
            } else {
                Menus.answeredWrong(rerender);
                Professor.endEncounter(false);
            }

            ter.initialize(Engine.WIDTH, Engine.HEIGHT);
            rerenderWorld(world, ter, rerender);
        } else if (Menus.inCustomizationMenu()) {
            if (input == '1' || input == '2' || input == '3' || input == '4' || input == '5') {
                changeSkin(skins.get(input));
                Menus.successfulSkinChange(rerender);
                Menus.changeCustomizationMenu();
                ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                rerenderWorld(world, ter, rerender);
            }
        }
        if (input == 'w' || input == 'a' || input == 's' || input == 'd') {
            moveAvatar(world, input);
            rerenderWorld(world, ter, rerender);
        } else if (input == 'x') {
            Ax.doAction(world, position.x, position.y);
            rerenderWorld(world, ter, rerender);
        } else if (input == 'e') {
            interactWith(world, ter, rerender, Engine.getSeed());
        } else if (!Menus.inCustomizationMenu() && input == 'c') {
            customizeSkin(ter, rerender);
        } else  {
            return;
        }
    }

    public static void interactWith(TETile[][] world, TERenderer ter, boolean render, Random seed) {
        TETile[] options = new TETile[4];
        options[0] = lookUp(world, position.x, position.y);
        options[1] = lookDown(world, position.x, position.y);
        options[2] = lookRight(world, position.x, position.y);
        options[3] = lookLeft(world, position.x, position.y);
        for (int i = 0; i < 4; i++) {
            if (options[i].description().equals("professor")) {
                Point profLoc = findItem(i);
                interactWithProf(world, profLoc.x, profLoc.y, ter, render);
                return;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (options[i].equals(Tileset.KEY)) {
                Point itemLoc = findItem(i);
                interactWithItem(world, ter, render, itemLoc.x, itemLoc.y, "key");
                return;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (options[i].equals(Tileset.AXE)) {
                Point itemLoc = findItem(i);
                interactWithItem(world, ter, render, itemLoc.x, itemLoc.y, "axe");
                return;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (options[i].equals(Tileset.SMALLCOIN) || options[i].equals(Tileset.BIGCOIN)) {
                Point itemLoc = findItem(i);
                interactWithItem(world, ter, render, itemLoc.x, itemLoc.y, "coin");
                return;
            }
        }
        for (TETile tile : options) {
            if (tile.equals(Tileset.STAIRS)) {
                goUpStairs(world, ter, render, seed);
                return;
            }
        }
        Menus.uiPopup("You look closer to the grass... but there's nothing there", render);

    }

    private static Point findItem(int i) {
        Point itemLoc = new Point(0, 0);
        switch (i) {
            case 0:
                itemLoc = new Point(position.x + 1, position.y);
                break;
            case 1:
                itemLoc = new Point(position.x - 1, position.y);
                break;
            case 2:
                itemLoc = new Point(position.x, position.y + 1);
                break;
            case 3:
                itemLoc = new Point(position.x, position.y - 1);
                break;
            default:
                break;
        }
        return itemLoc;
    }

    public static void interactWithProf(TETile[][] world,
                                        int x, int y, TERenderer ter, boolean rerender) {
        if (Professor.getMoney() >= moneyNeeded()) {
            Professor.interactWithProf(world, x, y, ter, rerender);
        } else {
            Menus.uiPopup("The professor looks you up and down..."
                    + " he seems dissatisfied. You need "
                    + (moneyNeeded() - Professor.getMoney())
                    + " more coins before he'll help you.", rerender);
        }

    }

    public static void interactWithItem(TETile[][] world, TERenderer ter,
                                        boolean rerender, int x, int y, String s) {
        if (world[y][x].equals(Tileset.SMALLCOIN)) {
            world[y][x] = Tileset.GRASS;
            rerenderWorld(world, ter, rerender);
            Menus.uiPopup("You see something shimmering in the grass... "
                    + "you put the coin in your pocket, "
                    + "hoping a CS mentor will take it as payment.", rerender);
            Professor.addMoney(1);
        } else if (world[y][x].equals(Tileset.BIGCOIN)) {
            world[y][x] = Tileset.GRASS;
            rerenderWorld(world, ter, rerender);
            Menus.uiPopup("You see something beautiful nestled in the grass"
                    + " and pick it up quickly before anyone else can notice.", rerender);
            Professor.addMoney(5);
        } else {
            addToInventory(s);
            world[y][x] = Tileset.GRASS;
            rerenderWorld(world, ter, rerender);
        }
    }

    public static void goUpStairs(TETile[][] world, TERenderer ter, boolean rerender, Random seed) {
        if (getInventory().contains("key")) {
            Long newSeed = seed.nextLong();
            if (rerender) {
                if (Professor.currFloor() == 8) {
                    Menus.gameOverSuccessful(rerender);
                    return;
                } else {
                    Menus.nextFloorMenu(rerender);
                    ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                }
            }
            Professor.incrementFloor();
            Professor.resetRiddle();
            WorldGenerator.populateWorld(world, new Random(newSeed));
            rerenderWorld(world, ter, rerender);
        } else {
            Menus.uiPopup("You need this floor's professor's key to move forward!", rerender);
        }
    }

    public static void moveAvatar(TETile[][] world, char input) {
        int x = position.x;
        int y = position.y;
        if (input == 'w') {
            if (lookUp(world, x, y).equals(Tileset.GRASS)) {
                moveUp(world, x, y);
            }
        } else if (input == 's') {
            if (lookDown(world, x, y).equals(Tileset.GRASS)) {
                moveDown(world, x, y);
            }
        } else if (input == 'a') {
            if (lookLeft(world, x, y).equals(Tileset.GRASS)) {
                moveLeft(world, x, y);
            }
        } else if (input == 'd') {
            if (lookRight(world, x, y).equals(Tileset.GRASS)) {
                moveRight(world, x, y);
            }
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

    public static void moveDown(TETile[][] world, int xPos, int yPos) {
        world[yPos][xPos] = Tileset.GRASS;
        world[yPos][xPos - 1] = skin;
        position = new Point(xPos - 1, yPos);
    }

    public static void moveUp(TETile[][] world, int xPos, int yPos) {
        world[yPos][xPos] = Tileset.GRASS;
        world[yPos][xPos + 1] = skin;
        position = new Point(xPos + 1, yPos);
    }

    public static void moveLeft(TETile[][] world, int xPos, int yPos) {
        world[yPos][xPos] = Tileset.GRASS;
        world[yPos - 1][xPos] = skin;
        position = new Point(xPos, yPos - 1);
    }

    public static void moveRight(TETile[][] world, int xPos, int yPos) {
        world[yPos][xPos] = Tileset.GRASS;
        world[yPos + 1][xPos] = skin;
        position = new Point(xPos, yPos + 1);
    }

    public static void customizeSkin(TERenderer ter, boolean rerender) {
        Menus.changeCustomizationMenu();
        Menus.avatarCustomMenu(rerender);
    }

    public static void rerenderWorld(TETile[][] world, TERenderer ter, boolean rerender) {
        if (rerender) {
            ter.renderFrame(world);
        }
    }

    public static TETile getSkin() {
        return skin;
    }

    public static void changeSkin(TETile newskin) {
        skin = newskin;
    }


    public static int moneyNeeded() {
        return (Professor.currFloor() * 5) + 1;
    }

    public static ArrayList<TETile> getSkins() {
        ArrayList<TETile> skinsarr = new ArrayList<>();
        for (TETile skinn : skins.values()) {
            skinsarr.add(skinn);
        }
        return skinsarr;
    }

}
