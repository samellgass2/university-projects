package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Engine {
    private static boolean seedadded = false;
    private static Random seed;
    private static String inputSoFar = "";
    private static File filename = new File("./Savedworld.txt");
    private static TETile[][] finalWorldFrame = null;
    public static final char[] NUMBERS = "1234567890".toCharArray();
    public static final char[] ACTIONS = "awsdxec12345".toCharArray();


    /* Feel free to change the width and height. */
    public static final int WIDTH = 90;
    public static final int HEIGHT = 50;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public static void interactWithKeyboard() {
        TERenderer ter = new TERenderer();
//        ter.initialize(Engine.WIDTH, Engine.HEIGHT);
        boolean gameinprogress = true;
        boolean initialized = false;
        Menus.mainMenu();
        while (gameinprogress) {
            if (StdDraw.hasNextKeyTyped()) {
                char next = StdDraw.nextKeyTyped();
                next = Character.toLowerCase(next);
                if (next == 'n' && !initialized) {
                    KeyboardInputSource seeder = new KeyboardInputSource();
                    String seedstr = extractseed(seeder);
                    inputSoFar += "n" + seedstr + "s";
                    Menus.howToPlay(true);
                    seedWorld(seedstr);
                    ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                    ter.renderFrame(finalWorldFrame);
                    initialized = true;
                } else if (next == 'l' && !initialized) {
                    seedWorld("");
                    ter.initialize(Engine.WIDTH, Engine.HEIGHT);
                    ter.renderFrame(finalWorldFrame);
                    initialized = true;
                } else if (next == 'q' && !initialized) {
                    gameinprogress = false;
                    pushToSavedWorld(inputSoFar);
                    Menus.shutdownMenu();
                    break;
                } else if (initialized) {
                    if (next == ':') {
                        while (gameinprogress) {
                            if (StdDraw.hasNextKeyTyped()) {
                                next = StdDraw.nextKeyTyped();
                                next = Character.toLowerCase(next);
                                break;
                            }
                        }
                        if (next == 'q') {
                            gameinprogress = false;
                            pushToSavedWorld(inputSoFar);
                            Menus.shutdownMenu();
                            break;
                        }
                    }
                    Avatar.takeInput(finalWorldFrame, next, ter, true);
                    inputSoFar += next;
                }

            } else if (initialized) {
                TERenderer.renderCurrentTile(finalWorldFrame);
                if (!Professor.inEncounter() && !Menus.inCustomizationMenu()) {
                    StdDraw.show();
                }
            }
        }
    }

    public static void interactWithInput(KeyboardInputSource device, TERenderer ter) {
        char next = '!';
        while (device.possibleNextInput() && next != 'q') {
            next = device.getNextKey();
            Avatar.takeInput(finalWorldFrame, next, ter, true);
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        TERenderer ter = new TERenderer();

        pushToSavedWorld(inputSoFar);
        voidInputSoFar();
        StringInputDevice stringCheez = new StringInputDevice(input);
        String inputstring = "";
        String seedstr = "";
        char next = '!';
        if (stringCheez.possibleNextInput()) {
            next = stringCheez.getNextKey();
            next = Character.toLowerCase(next);
            if (next == 'n') {
                seedstr = extractseed(stringCheez);
            }
        }
        seedWorld(seedstr);

        next = '!';
        boolean nextinchars = false;

        while (stringCheez.possibleNextInput()) {
            nextinchars = false;
            next = stringCheez.getNextKey();
            next = Character.toLowerCase(next);
            for (char charic : ACTIONS) {
                if (charic == next) {
                    nextinchars = true;
                }
            }
            if (nextinchars) {
                Avatar.takeInput(finalWorldFrame, next, ter, false);
                inputstring += next;
            } else if (next == ':') {
                if (stringCheez.possibleNextInput()) {
                    next = stringCheez.getNextKey();
                    next = Character.toLowerCase(next);
                    if (next == 'q') {
                        break;
                    }
                }
            }
        }
        if (!seedstr.equals("")) {
            seedstr = "n" + seedstr + "s";
            addToInputString(seedstr);
        }

        addToInputString(inputstring);
        pushToSavedWorld(inputSoFar);
        return finalWorldFrame;
    }

    public static void addToInputString(String s) {
        for (int i = 0; i < s.length(); i++) {
            char next = s.charAt(i);
            next = Character.toLowerCase(next);
            if (next == ':' && Character.toLowerCase(s.charAt(i + 1)) == 'q') {
                i += 1;
            } else if (next == 'l') {
                continue;
            } else {
                inputSoFar += next;
            }
        }
    }

    public static String pullFromSavedWorld() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String output = "";
        while (scanner.hasNext()) {
            output += scanner.next();
        }
        return output;
    }

    public static void pushToSavedWorld(String s) {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter("./Savedworld.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myWriter.write(s);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extractseed(InputSource device) {
        InputSource stringCheez = device;
        String seedstr = "";
        boolean uiactive = false;
        KeyboardInputSource reference = new KeyboardInputSource();
        char next = '!';
        if (device.getClass().equals(reference.getClass())) {
            uiactive = true;
            Menus.basicsetupwht();
            Menus.seedEntryMenu(seedstr);
        }
        while (stringCheez.possibleNextInput()) {
            next = stringCheez.getNextKey();
            next = Character.toLowerCase(next);
            if (next == 's') {
                break;
            }
            for (char num : NUMBERS) {
                if (next == num) {
                    seedstr += next;
                    if (uiactive) {
                        Menus.seedEntryMenu(seedstr);
                    }
                }
            }
        }
        return seedstr;
    }

    public static void seedWorld(String seedstr) {
        if (!seedstr.equals("")) {
            long seeder = Long.parseLong(seedstr);
            seed = new Random(seeder);

            finalWorldFrame = new TETile[Engine.WIDTH][Engine.HEIGHT];
            WorldGenerator.populateWorld(finalWorldFrame, seed);
        } else {
            loadFromInputString(pullFromSavedWorld());
        }
    }

    public static Random getSeed() {
        return seed;
    }

    private static void voidInputSoFar() {
        inputSoFar = "";
    }

    private static TETile[][] loadFromInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        TERenderer ter = new TERenderer();
        StringInputDevice stringCheez = new StringInputDevice(input);
        String inputstring = "";
        String seedstr = "";
        char next = '!';
        if (stringCheez.possibleNextInput()) {
            next = stringCheez.getNextKey();
            next = Character.toLowerCase(next);
            if (next == 'n') {
                seedstr = extractseed(stringCheez);
            }
        }
        seedWorld(seedstr);

        next = '!';
        boolean nextinchars = false;

        while (stringCheez.possibleNextInput()) {
            nextinchars = false;
            next = stringCheez.getNextKey();
            next = Character.toLowerCase(next);
            for (char charic : ACTIONS) {
                if (charic == next) {
                    nextinchars = true;
                }
            }
            if (nextinchars) {
                Avatar.takeInput(finalWorldFrame, next, ter, false);
                inputstring += next;
            } else if (next == ':') {
                if (stringCheez.possibleNextInput()) {
                    next = stringCheez.getNextKey();
                    next = Character.toLowerCase(next);
                    if (next == 'q') {
                        break;
                    }
                }
            }
        }
        if (!seedstr.equals("")) {
            seedstr = "n" + seedstr + "s";
            addToInputString(seedstr);
        }

        addToInputString(inputstring);
        pushToSavedWorld(inputSoFar);
        return finalWorldFrame;
    }

    public static String getInput() {
        return inputSoFar;
    }
}
