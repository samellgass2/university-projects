package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;



public class Testgeneration {
    

    public static void keyboardInteractivityTest() {
        Menus.howToPlay(true);
        Menus.basicsetupblk();
    }

    public static void gradescopetest() {
        TERenderer ter = new TERenderer();
        String gsstring = "n7193300625454684331saaawasdaawd:q"
                + "lwsd";

        TETile[][] world2 = Engine.interactWithInputString("n7193300625454684331saaawasdaawdwsd");
        TETile[][] world = Engine.interactWithInputString("n7193300625454684331saaawasdaawd:q");
        world = Engine.interactWithInputString("lwsd");
        boolean theythesamedoe = true;
        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                if (world[i][j].character() != world2[i][j].character()) {
                    System.out.println(world[i][j].character() + world2[i][j].character());
                    theythesamedoe = false;
                    break;
                }
            }
        }
        System.out.println(theythesamedoe);

    }

    public static void main(String[] args) {
//        graphicaltests();
        keyboardInteractivityTest();
//        gradescopetest();
//        generationStringTest();
    }

}
