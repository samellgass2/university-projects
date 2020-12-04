package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;



public class Professor {
    private static int money = 0;
    private static boolean inEncounter = false;
    private static int correctAnswer = '1';
    private static boolean finishedRiddle = false;
    private static int currentFloor = 1;


    public static void interactWithProf(TETile[][] world,
                                        int x, int y, TERenderer ter, boolean rerender) {
        inEncounter = true;
        TETile prof = world[y][x];
        whichEncounter(prof, rerender);
    }

    public static String whichProf() {
        return whichProf(currFloor());
    }

    public static String whichProf(int n) {
        switch (n) {
            case 1: return "John Denero";
            case 2: return "Paul Hilfinger";
            case 3: return "Josh Hug";
            case 4: return "David Wagner";
            case 5: return "Dan Garcia";
            case 6: return "Nicholas Weaver";
            case 7: return "Anant Sahai";
            case 8: return "Ani Adhikari";
            default: return "Unknown Instructor";
        }
    }

    public static TETile whichProfTile(int n) {
        switch (n) {
            case 1: return Tileset.DENERO;
            case 2: return Tileset.HILFINGER;
            case 3: return Tileset.HUG;
            case 4: return Tileset.WAGNER;
            case 5: return Tileset.GARCIA;
            case 6: return Tileset.WEAVER;
            case 7: return Tileset.SAHAI;
            case 8: return Tileset.ADHIKARI;
            default: return Tileset.PROFESSOR;
        }
    }

    public static TETile whichProfTile() {
        return whichProfTile(currFloor());
    }

    public static void whichEncounter(TETile prof, boolean rerender) {
        switch (currFloor()) {
            case 1: johnDeneroEncounter(prof, rerender);
                return;
            case 2: paulHilfingerEncounter(prof, rerender);
                return;
            case 3: joshHugEncounter(prof, rerender);
                return;
            case 4: davidWagnerEncounter(prof, rerender);
                return;
            case 5: danGarciaEncounter(prof, rerender);
                return;
            case 6: nicholasWeaverEncounter(prof, rerender);
                return;
            case 7: anantSahaicounter(prof, rerender);
                return;
            case 8: aniAdhikariEncounter(prof, rerender);
                return;
            default:
                return;
        }
    }

    public static void johnDeneroEncounter(TETile prof, boolean rerender) {
        correctAnswer = '1';
        if (rerender) {
            Menus.encounterUI(prof, rerender);

            Menus.encounterUIPopup("John steps back from the massive desktop where "
                    + "he was working, wiping sweat from his brow.", rerender);
            StdDraw.pause(3000);
            Menus.professorSpeech("Welcome to my riddle, student, he says with a kind smile. "
                    + "My question will challenge your knowledge of HashMaps.", rerender);
            StdDraw.pause(4000);
            Menus.clearProfSpeech(rerender);
            Menus.professorSpeech("If you were to place an Object into a Java HashMap "
                    + "and then change some attribute of the Object, "
                    + "would you be able to find it again?", rerender);
            StdDraw.pause(3000);
            Menus.encounterUIPopup("John is awaiting your answer. Is it "
                    + "(1): only if the hashcode doesn't depend on that attribute, "
                    + "(2): never, (3): always, or (4): if sidechaining with a BST?", rerender);
            Menus.countDown(rerender);
        }

    }

    public static void paulHilfingerEncounter(TETile prof, boolean rerender) {
        correctAnswer = '4';
        if (rerender) {
            Menus.encounterUI(prof, rerender);

            Menus.encounterUIPopup("Hilfinger steps away from his "
                    + "computer, wide eyes locked on you", rerender);
            StdDraw.pause(3000);
            Menus.professorSpeech("welcome student, he says, "
                    + "without breaking eye contact", rerender);
            StdDraw.pause(4000);
            Menus.clearProfSpeech(rerender);
            Menus.professorSpeech("No beating around the bush: if I wanted to use a sort "
                    + "with optimal runtime, which should I use?", rerender);
            StdDraw.pause(3000);
            Menus.encounterUIPopup("Paul raises an eyebrow and waits for you to say "
                    + "(1): quicksort, "
                    + "(2): radix MSD sort, (3): insertion sort, or "
                    + "(4): it depends on the situation?", rerender);
            Menus.countDown(rerender);
        }

    }

    public static void nicholasWeaverEncounter(TETile prof, boolean rerender) {
        correctAnswer = '3';
        if (rerender) {
            Menus.encounterUI(prof, rerender);

            Menus.encounterUIPopup("Nicholas smiles at you, lowering himself"
                    + " to the floor from where he was floating", rerender);
            StdDraw.pause(3000);
            Menus.professorSpeech("Welcome to my meditation chamber,"
                    + " young computer scientist", rerender);
            StdDraw.pause(4000);
            Menus.clearProfSpeech(rerender);
            Menus.professorSpeech("What are tries, a beautiful "
                    + "little data structure, ideal for?", rerender);
            StdDraw.pause(3000);
            Menus.encounterUIPopup("Nicholas smiles at you, is it (1): for numerical data, "
                    + "(2): for categorical data, (3): for prefix operations, or "
                    + "(4): for all of the above?", rerender);
            Menus.countDown(rerender);
        }
    }

    public static void davidWagnerEncounter(TETile prof, boolean rerender) {
        correctAnswer = '2';
        if (rerender) {
            Menus.encounterUI(prof, rerender);

            Menus.encounterUIPopup("David turns off his three separate set of "
                    + "lasers and security and steps toward you", rerender);
            StdDraw.pause(3000);
            Menus.professorSpeech("Welcome student, he says, I'm glad you were"
                    + " able to find your way to my safehouse", rerender);
            StdDraw.pause(4000);
            Menus.clearProfSpeech(rerender);
            Menus.professorSpeech("My question is simple: every Object in Java has all "
                    + "of these methods by default but one: equals(), toPrimitive(), "
                    + "hashCode(), and toString(). Which is untrue?", rerender);
            StdDraw.pause(3000);
            Menus.encounterUIPopup("You work up the courage to answer, is it: (1): equals(), "
                    + "(2): toPrimitive():, (3): hashCode(), (4): toString()?", rerender);
            Menus.countDown(rerender);
        }
    }

    public static void danGarciaEncounter(TETile prof, boolean rerender) {
        correctAnswer = '4';
        if (rerender) {
            Menus.encounterUI(prof, rerender);

            Menus.encounterUIPopup("Dan sits up in his comfy armchair,"
                    + " a fire crackling beside him", rerender);
            StdDraw.pause(3000);
            Menus.professorSpeech("Welcome, student. It is my pleasure to "
                    + "pass on my knowledge to you in this place.", rerender);
            StdDraw.pause(4000);
            Menus.clearProfSpeech(rerender);
            Menus.professorSpeech("My question: what does an Object NOT need to "
                    + "have a for-each loop: hasNext() method, next() method, extends"
                    + " iterable, or compareTo() method?", rerender);
            StdDraw.pause(3000);
            Menus.encounterUIPopup("Dan is waiting, is it (1): hasNext(), (2): next(),"
                    + " (3): extends iterable, or (4): compareTo()?", rerender);
            Menus.countDown(rerender);
        }
    }

    public static void joshHugEncounter(TETile prof, boolean rerender) {
        correctAnswer = '4';
        if (rerender) {
            Menus.encounterUI(prof, rerender);

            Menus.encounterUIPopup("Josh steps away from his Just Dance console, "
                    + "wiping sweat off his kind brow", rerender);
            StdDraw.pause(3000);
            Menus.professorSpeech("Ah, you made it to a curious version of me inside a "
                    + "game I assigned to be created, he says with a smile", rerender);
            StdDraw.pause(4000);
            Menus.clearProfSpeech(rerender);
            Menus.professorSpeech("My question to you: remember, what "
                    + "does the 'ledger of harms' refer to?", rerender);
            StdDraw.pause(3000);
            Menus.encounterUIPopup("Josh is waiting, is it (1): all wrongdoings of CEOs, ("
                    + "2): silicon valley's death count, (3): people who've failed cs61b,"
                    + " or (4): negative social externalities of tech companies?", rerender);
            Menus.countDown(rerender);
        }
    }

    public static void anantSahaicounter(TETile prof, boolean rerender) {
        correctAnswer = '2';
        if (rerender) {
            Menus.encounterUI(prof, rerender);

            Menus.encounterUIPopup("Sahai hardly looks up from the book he's buried in,"
                    + " but he acknowledges your entry with a brief nod", rerender);
            StdDraw.pause(3000);
            Menus.professorSpeech("Yes, yes, welcome. My question should be tri"
                    + "vial for you if you've made it this far", rerender);
            StdDraw.pause(4000);
            Menus.clearProfSpeech(rerender);
            Menus.professorSpeech("My question: which is the best hashCode("
                    + ") function possible for a String? return 0, "
                    + "return Random.nextInt(100), return string."
                    + "length, or return null?", rerender);
            StdDraw.pause(3000);
            Menus.encounterUIPopup("He's waiting, is it (1): return 0, (2): return Random."
                    + "nextInt(100), (3): return string.length, or (4)"
                    + ": return null?", rerender);
            Menus.countDown(rerender);
        }
    }

    public static void aniAdhikariEncounter(TETile prof, boolean rerender) {
        correctAnswer = '1';
        if (rerender) {
            Menus.encounterUI(prof, rerender);

            Menus.encounterUIPopup("Adhikari floats down gently on a soft cloud"
                    + " of data that chirps at you when you get close to it", rerender);
            StdDraw.pause(3000);
            Menus.professorSpeech("Welcome to my place of pure data and refuge, "
                    + "student, she says with a smile", rerender);
            StdDraw.pause(4000);
            Menus.clearProfSpeech(rerender);
            Menus.professorSpeech("My inquiry for you: in a statistical"
                    + " study with a P-value of 0.01 "
                    + "and a cutoff of 0.05, what can you do necessarily?", rerender);
            StdDraw.pause(3000);
            Menus.encounterUIPopup("Though this isn't really CS, you must answer. Is it "
                    + "(1): reject the null, (2): accept the null, (3): accept the alternative, "
                    + "(4): reject the alternative?", rerender);
            Menus.countDown(rerender);
        }
    }

    public static int getMoney() {
        return money;
    }

    public static void addMoney(int n) {
        money += n;
    }

    public static boolean inEncounter() {
        return inEncounter;
    }

    public static void endEncounter(boolean succeeded) {
        inEncounter = false;
        if (succeeded) {
            finishedRiddle = true;
        }
    }

    public static int correctAnswer() {
        return correctAnswer;
    }

    public static boolean finishedRiddle() {
        return finishedRiddle;
    }

    public static void resetRiddle() {
        finishedRiddle = false;
    }

    public static int currFloor() {
        return currentFloor;
    }

    public static void incrementFloor() {
        currentFloor += 1;
    }


}
