package byow.Core;


import java.awt.*;
import java.util.ArrayList;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class Menus {
    private static boolean inCustomizationMenu = false;
    public static void mainMenu() {
        basicsetupblk();
        setfontsize(24);
        StdDraw.text(50, 50, "Load Game (l)");
        StdDraw.rectangle(50, 50, 20, 5);
        StdDraw.text(50, 40, "New Game (n)");
        StdDraw.rectangle(50, 40, 20, 5);
        StdDraw.text(50, 30, "Quit Game (q)");
        StdDraw.rectangle(50, 30, 20, 5);
        setfontsize(48);
        StdDraw.text(50, 70, "CS QUEST");
        StdDraw.show();
    }

    public static void seedEntryMenu(String seedstr) {
        StdDraw.clear();
        setfontsize(30);
        StdDraw.text(50, 90, "Enter Seed");
        StdDraw.rectangle(50, 50, 20, 10);

        setfontsize(24);
        StdDraw.text(50, 30, "Must be a positive integer");
        StdDraw.text(50, 10, "Press s to finish typing and load world");
        setfontsize(18);
        StdDraw.text(50, 50, seedstr);
        StdDraw.show();
    }

    public static void nextFloorMenu(boolean rerender) {
        basicsetupblk();
        setfontsize(30);
        StdDraw.text(50, 90, "Congratulations! You completed floor " + Professor.currFloor() + "!");
        loadCurrentStats(10);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(50, 17, "The next floor is loading...");
        if (rerender) {
            StdDraw.show();
        }
        StdDraw.rectangle(50, 10, 20, 4);
        int loadstart = 32;
        for (int i = 0; i < 37; i++) {
            StdDraw.filledSquare(loadstart + i, 10, 2);
            if (rerender) {
                StdDraw.show();
            }

            StdDraw.pause(150);
        }
        StdDraw.textLeft(71, 10, "Rendering world...");
        if (rerender) {
            StdDraw.show();
        }
        StdDraw.pause(200);



    }

    public static void shutdownMenu() {
        basicsetupblk();
        setfontsize(30);
        StdDraw.text(50, 90, "Thanks for playing!");
        StdDraw.rectangle(50, 35, 15, 25);
        loadCurrentStats(0);
        StdDraw.show();
    }

    public static void loadCurrentStats(int yoffset) {
        setfontsize(24);
        StdDraw.text(50, 70 + yoffset, "Stats so far:");
        StdDraw.rectangle(50, 35 + yoffset, 15, 25);
        setfontsize(16);
        StdDraw.setPenColor(Color.red);
        StdDraw.text(50, 50 + yoffset, "Floor reached: " + Professor.currFloor());
        StdDraw.setPenColor(Color.green);
        StdDraw.text(50, 40 + yoffset,  "Trees chopped: "
                + (Ax.getMaxdurability() - Ax.getDurability()));
        StdDraw.setPenColor(Color.lightGray);
        StdDraw.text(50, 30 + yoffset, "Actions taken: " + Engine.getInput().length());
        StdDraw.setPenColor(Color.orange);
        StdDraw.text(50, 20 + yoffset, "Coins collected: " + Professor.getMoney());
    }


    public static void avatarCustomMenu(boolean rerender) {
        if (rerender) {
            basicsetupwht();
            setfontsize(24);
            StdDraw.text(50, 90, "Avatar Customization Screen");
            StdDraw.text(50, 80, "Choose from the available skins");
            StdDraw.text(50, 70, "Press (1) to (5) to switch to an unlocked skin");
            StdDraw.text(50, 10, "Press (1) to keep the same skin");
            setfontsize(60);
            ArrayList<TETile> skins = Avatar.getSkins();
            for (int i = 0; i < skins.size(); i++) {
                StdDraw.setPenColor(Color.darkGray);
                StdDraw.filledEllipse(15 + 15 * i, 50, 8, 5);
                skins.get(i).draw(15 + 15 * i, 50);
                StdDraw.setPenColor(Color.darkGray);
                StdDraw.text(15 + 15 * i, 30, "(" +  (i + 1) + "):");
            }
            StdDraw.show();
        }



    }

    public static void successfulSkinChange(boolean rerender) {
        if (rerender) {
            basicsetupwht();
            StdDraw.setPenColor(Color.black);
            setfontsize(30);
            StdDraw.text(50, 80, "Skin chosen: " + Avatar.getSkin().description());
            setfontsize(60);
            StdDraw.setPenColor(Color.lightGray);
            StdDraw.filledEllipse(50, 50, 15, 5);
            Avatar.getSkin().draw(50, 50);
            StdDraw.show();
            StdDraw.pause(2000);
        }
    }

    public static void basicsetupblk() {
        StdDraw.enableDoubleBuffering();
//        StdDraw.setCanvasSize(1000, 500);
        StdDraw.setCanvasSize(Engine.WIDTH * 16, Engine.HEIGHT * 16);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
    }

    public static void basicsetupwht() {
        StdDraw.enableDoubleBuffering();
//        StdDraw.setCanvasSize(1000, 500);
        StdDraw.setCanvasSize(Engine.WIDTH * 16, Engine.HEIGHT * 16);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
    }

    public static void setfontsize(int n) {
        Font font = new Font("Arial", Font.BOLD, n);
        StdDraw.setFont(font);
    }

    public static void uiPopup(String s, boolean rerender) {
        StdDraw.setPenColor(Color.darkGray);
        int strings = 1;
        if (s.length() >= 200) {
            strings = 4;
        } else if (s.length() >= 100) {
            strings = 2;
        }
        int charsperbox = s.length() / strings;
        StdDraw.filledRectangle(50, 2, 15, 2);
        StdDraw.setPenColor(Color.white);
        for (int i = 0; i < strings; i++) {
            StdDraw.text(50, 3 - i, s.substring((i * charsperbox), (i + 1) * charsperbox));
        }
//        StdDraw.text(50, 2, s);
        if (rerender) {
            StdDraw.show();
        }
    }

    public static void encounterUI(TETile prof, boolean rerender) {
        char profchar = prof.character();
        TETile avatarskin = Avatar.getSkin();
        basicsetupwht();
        setfontsize(30);
        StdDraw.text(70, 90, Professor.whichProf());
        StdDraw.filledEllipse(20, 20, 15, 10);
        StdDraw.filledEllipse(70, 70, 15, 10);
        setfontsize(140);
        StdDraw.setPenColor(Color.lightGray);

        prof.draw(70, 74);
        avatarskin.draw(20, 20);
        if (rerender) {
            StdDraw.show();
        }
        setfontsize(14);

    }

    public static void professorSpeech(String s, boolean rerender) {
        setfontsize(16);
        StdDraw.setPenColor(Color.black);
        StdDraw.rectangle(20, 70, 15, 30);
        int strings = 1;
        if (s.length() >= 190) {
            strings = 6;
        } else if (s.length() >= 120) {
            strings = 4;
        } else if (s.length() >= 80) {
            strings = 2;
        }
        int charsperbox = s.length() / strings;
        for (int i = 0; i < strings; i++) {
            StdDraw.text(20, 65 - 3 * i, s.substring((i * charsperbox), (i + 1) * charsperbox));
        }
        StdDraw.show();
    }

    public static void clearProfSpeech(boolean rerender) {
        StdDraw.setPenColor(Color.white);
        StdDraw.filledRectangle(20, 70, 15, 30);
        StdDraw.setPenColor(Color.black);
        StdDraw.rectangle(20, 70, 15, 30);
        StdDraw.show();
    }

    public static void encounterUIPopup(String s, boolean rerender) {
        setfontsize(16);
        StdDraw.setPenColor(Color.darkGray);
        int strings = 1;
        if (s.length() >= 150) {
            strings = 4;
        } else if (s.length() >= 90) {
            strings = 2;
        }
        int charsperbox = s.length() / strings;
        StdDraw.filledRectangle(50, 12, 18, 8);
        StdDraw.setPenColor(Color.white);
        for (int i = 0; i < strings; i++) {
            StdDraw.text(50, 14 - 3 * i, s.substring((i * charsperbox), (i + 1) * charsperbox));
        }
//        StdDraw.text(50, 2, s);
        if (rerender) {
            StdDraw.show();
        }
    }

    public static void answeredRight(boolean rerender) {
        encounterUIPopup("", rerender);
        clearProfSpeech(rerender);
        professorSpeech("Congratulations son, that's it. I will now make visible "
                + "to you my hidden key somewhere on this floor.", rerender);
        if (rerender) {
            StdDraw.show();
            StdDraw.pause(4000);
        }
    }

    public static void answeredRightAgain(boolean rerender) {
        encounterUIPopup("", rerender);
        clearProfSpeech(rerender);
        professorSpeech("Congratulations son, you've still got it. Always a pleasure.", rerender);
        if (rerender) {
            StdDraw.show();
            StdDraw.pause(4000);
        }
    }

    public static void answeredWrong(boolean rerender) {
        encounterUIPopup("", rerender);
        clearProfSpeech(rerender);
        professorSpeech("Sorry son, that's not it. Return to try again.", rerender);
        if (rerender) {
            StdDraw.show();
            StdDraw.pause(4000);
        }

    }

    public static void countDown(boolean rerender) {
        StdDraw.setPenColor(Color.black);
        setfontsize(30);
        StdDraw.text(92, 70, "Countdown:");
        StdDraw.show();
        for (int i = 10; i > 0; i--) {
            StdDraw.text(92, 26 + 4 * i, Integer.toString(i));
            StdDraw.pause(1000);
            StdDraw.show();
        }
    }

    public static boolean validMouseTile() {
        int x = (int) Math.ceil(StdDraw.mouseX());
        int y = (int) Math.ceil(StdDraw.mouseY());
        if (x >= 0 && x < Engine.WIDTH && y >= 0 && y < Engine.HEIGHT) {
            return true;
        }
        return false;
    }

    public static TETile currMouseTile(TETile[][] world) {
        int x = (int) Math.floor(StdDraw.mouseX());
        int y = (int) Math.floor(StdDraw.mouseY());
//        System.out.println(Integer.toString(x) + Integer.toString(y));
        return world[x][y];
    }

    public static String currTileText(TETile tile) {
        if (tile.equals(Tileset.GRASS)) {
            return "Grass: a soft, green grass blowing in the wind";
        } else if (tile.equals(Tileset.TREE)) {
            return "Tree: a thick, solid oak ready to be chopped down";
        } else if (tile.equals(Avatar.getSkin())) {
            return "You: a young CS major and adventurer";
        } else if (tile.description().equals("professor")) {
            return "Professor: an ocean of knowledge, a Berkeley CS master";
        } else if (tile.equals(Tileset.SAND)) {
            return "Sand: coarse sand that can't be traversed";
        } else if (tile.equals(Tileset.SMALLCOIN)) {
            return "Coin: a small offering for a professor, worth $1";
        } else if (tile.equals(Tileset.BIGCOIN)) {
            return "Coin: a large offering for a professor, worth $5";
        } else if (tile.equals(Tileset.KEY)) {
            return "Key: a glimmering key, your ticket to the next floor";
        } else if (tile.equals(Tileset.STAIRS)) {
            return "Stairs: the stairway to the next floor";
        } else if (tile.equals(Tileset.AXE)) {
            return "Axe: a brand new axe, ready for chopping";
        } else if (tile.equals(Tileset.WATER)) {
            return "Water: you can barely hear the distant waves crashing...";
        } else {
            return "";
        }
    }

    public static void changeCustomizationMenu() {
        inCustomizationMenu = !inCustomizationMenu;
    }

    public static boolean inCustomizationMenu() {
        return inCustomizationMenu;
    }

    public static void gameOverSuccessful(boolean rerender) {
        if (rerender) {
            basicsetupblk();
            setfontsize(30);
            StdDraw.text(50, 50, "Finally... you've done it. "
                    + "You've learned all there is to know about CS");
            StdDraw.show();
            StdDraw.pause(2000);
            StdDraw.clear(Color.black);
            StdDraw.text(50, 70, "You've completed the CS Quest!!!");
            for (int i = 1; i < 9; i++) {
                StdDraw.setPenColor(Color.darkGray);
                StdDraw.filledEllipse(10 + 10 * i, 35, 8, 4);
                setfontsize(20);
                StdDraw.setPenColor(Color.white);
                StdDraw.text(10 + 10 * i, 45, Professor.whichProf(i));
                setfontsize(50);
                Professor.whichProfTile(i).draw(10 + 10 * i, 35);
            }
            StdDraw.show();
            for (int i = 1; i < 9; i++) {
                setfontsize(20);
                StdDraw.setPenColor(Color.white);
                if (i == 2 || i == 6) {
                    StdDraw.text(10 + 10 * i, 22, "Good work!");
                } else if (i == 3 || i == 7) {
                    StdDraw.text(10 + 10 * i, 27, "Congratulations!");
                } else if (i == 1 || i == 4) {
                    StdDraw.text(10 + 10 * i, 25, "Great job!");
                } else {
                    StdDraw.text(10 + 10 * i, 22, "Superb!");
                }
                StdDraw.show();
                StdDraw.pause(500);
            }
            StdDraw.pause(2000);
            shutdownMenu();
        }
    }

    public static void howToPlay(boolean rerender) {
        basicsetupwht();
        setfontsize(30);
        StdDraw.text(50, 90, "Welcome to CS Quest! Here are your main goals:");
        StdDraw.line(0, 88, 100, 88);
        setfontsize(20);
        StdDraw.textLeft(17, 80, "Your main goal is to answer "
                + "each floor's professor's riddle correctly");

        StdDraw.textLeft(17, 65, "To earn time with a professor,"
                + "you must collect coins like these ones every floor");
        StdDraw.textLeft(17, 50, "Your main tool is an axe you begin the game with: "
                + "it has limited durability, so use it wisely");
        StdDraw.textLeft(17, 45, "The axe can chop down all trees around you "
                + "with a press of 'x'. Collect more durability each floor");
        StdDraw.textLeft(17, 30, "Finally, collect keys after each successful "
                + "riddle completion to reach the next floor.");
        StdDraw.textLeft(17, 20, "Reach floor 9 to win, good luck!");
        setfontsize(30);
        StdDraw.textLeft(80, 80, "Controls:");
        setfontsize(20);
        StdDraw.textLeft(80, 75, "'w', 'a', 's', 'd' to move");
        StdDraw.textLeft(80, 70, "'e' to interact");
        StdDraw.textLeft(80, 65, "'x' to chop");
        StdDraw.textLeft(80, 60, "'1', '2', '3', '4' to answer");
        StdDraw.textLeft(80, 55, "'c' to customize");
        StdDraw.rectangle(87, 68, 10, 16);

        setfontsize(60);
        StdDraw.setPenColor(Color.pink);
        StdDraw.text(10, 80, "ƍ");
        StdDraw.setPenColor(Color.yellow);
        StdDraw.text(6, 65, "◎");
        StdDraw.setPenColor(Color.red);
        StdDraw.text(14, 65, "◎");
        StdDraw.setPenColor(Color.black);
        StdDraw.text(10, 50, "Γ");
        StdDraw.setPenColor(Color.cyan);
        StdDraw.text(10, 30, "ȹ");
        if (rerender) {
            StdDraw.show();
        }
        StdDraw.setPenColor(Color.black);
        StdDraw.rectangle(50, 10, 20, 4);
        int loadstart = 32;
        for (int i = 0; i < 73; i++) {
            StdDraw.filledSquare(loadstart + (0.5 * i), 10, 2);
            if (rerender) {
                StdDraw.show();
            }

            StdDraw.pause(300);
        }
        setfontsize(20);
        StdDraw.textLeft(71, 10, "Rendering world...");
        if (rerender) {
            StdDraw.show();
        }
        StdDraw.pause(1000);

    }
}
