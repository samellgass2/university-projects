# Build Your Own World Design Document

**Partner 1:**
Sam Ellgass

**Partner 2:**
Owen Sleigh

## Classes and Data Structures
Worldgenerator.java
-
* waterplanet() 
    * uses the sandtile() method to fill in the outtermost 3 spaces with water and the two next outtermost spaces with sand to make an "island" shape
* openify()
    * uses the openable() method to check for any points that seem to bottleneck a hallway or entryway to a room
        * uses the validplacement() in several places to verify rooms and other structures can be placed
* randomopenify() 
    * uses randomopenable() to find places where trees (walls) aren't touching barriers, i.e. can block movement randomly, removing some quota of random tree tiles
* addrandomroom() both implementations
    * uses some amount of information to generate a random room
        * utilizes the TERoom class for most actual tile placement
* cleanup()
    * fills in all space inside the "island" that isn't rooms or hallways with one tile
* findhallway()
    * randomly searches for valid places to put hallways and then delegates successful finds
        * uses hallwayable() to decide if random spots are valid for hallway building, and then delegates work to either newverthallway() or newhorhallway() depending on orientation
* makeAvatar()
    * randomly searches for valid placements for the player character's start
    * delegates most creation work to the Avatar class
* makeTile()
    * randomly searches for valid placement for any tile. Used by:
        - makeProfessor() 
            * dependent on Professor.whichProfTile()
        - makeStairs()
        - makeKey()
        - makeAxe()
* populateworld()
    * function that brings together all of the functionality of the worldgenerator.java class, utilizing a seed provided by input to call all of the above generation functions and build a world
* other helper methods:
    * randomx()
        * function to generate a random x based on Engine.WIDTH
    * randomy()
        * function to generate a random y based on Engine.HEIGHT

Menus.java 
-
* mainMenu()
    * leverages basicsetupblk() to StdDraw a main menu screen
* seedEntryMenu()
    * leverages basicsetupwht() to StdDraw a seed input menu that updates as the seed is typed
* nextFloorMenu()
    * leverages basicsetupblk() and loadCurrentStats() to StdDraw a bar that loads in real time and displays player stats
* shutdownmenu()
    * leverages basicsetupblk() and loadCurrentStats() to StdDraw stats and a goodbye message after the player saves and leaves
* avatarcustommenu()
    * leverages basicsetupwht() to display a menu to allow the player character to change their skin to one of the unlocked options
* successfulSkinChange() 
    * displays the Avatar's new skin after exiting the avatarcustommenu()
* UIPopup() 
    * adds a popup of the inputted text to the game screen that disappears when the player presses any key
* encounterUI()
    * leverages basicsetupwht() and is called by Avatar.takeInput to take in the nearby professor tile and render an encounter between the current skin and current professor
    * includes the following functionalities relevant to this UI:
        - professorSpeech()
            * Leverages clearProfSpeech() and displays a box of given speech over the current UI
        - encounterUIPopup()
            * Displays non-spoken text for encounters
        - answeredRight() 
            * Displays a congratulatory message
        - answeredRightAgain()
            * Displays a congratulatory message acknowledging the player has already finished this riddle
        - answeredWrong()
            * Displays an encouraging negatory message
        - countDown()
            * Displays a 10 second countdown that corresponds with how long the player has to answer
* validMouseTile()
    * returns true if the mouse is over a world TETile, false if not
* currMouseTile()
    * returns the current tile the mouse is over
* currTileText()
    * returns the relevant flavor text to the tile the mouse is over
* changeCustomizationMenu()
    * inverts the boolean representing if the player is in the customization menu
* inCustomizationMenu()
    * returns the boolean representing if the player is in the customization menu
* gameOverSuccessful()
    * the final game screen, depicts all professors congratulating you and then leverages shutDownMenu() to show final stats

TERoom.java
-
* newroom()
    * Generates a single new room from a given startx, starty, width and height
        * uses edgetile() to decide whether a tile is a tree or grass tile (i.e. wall or floor of room)
* newhorhallway()
    * creates a new horizontally running hallway (to the +x) of specified length or until it reaches another wall, in which case it will open the wall and be connected
* newverthallway()
    * exact same functionality as newhorhallway() except it generates a hallway running vertically (up)

Testgeneration.java
-
- a collection of tests that together build up to worldgenerator.populateworld(), utilizes all of the functionality of both TERoom.java and worldgenerator.java to understand seeding as well as what bounds for certain criteria are reasonable
    * generationtest1() creates a world as populateworld() would, with some random number of elements in bounds that we decided were reasonable (unseeded)
    * generationstringtest() uses Engine.interactwithstring() to generate a world consistent to a seed, has no added functionality

Avatar.java
- 
* Avatar()
    * constructor, sets skin and upon initial creation stores several important data within the class
    * calls initializeInventoryHashMapAndSkins() to allow for accurate inventory display later
* getInventoryMap()
    * used for inventory display in the UI
    
* xPos(), yPos(), getInventory(), getSkin()
    * delegate access to Avatar.java class attributes for use in a variety of functions
* dropFromInventory() / addToInventory()
    * delegates access to the inventory arraylist to other classes and functions
    * also has functionality in updating durability for items that the player can hold more than one of
* takeInput()
    * a generic function to delegate what the Avatar does with a given char input
* interactWith()
    * a generic function response to keyboard press 'e', defines character response relevant to what tiles are around using helper methods
    * uses findItem() to tell where an item is relative to the player for removal / use
    * uses interactWithProf(), interactWithItem(), goUpStairs() or other based on surroundings
* interactWithProf()
    * opens a professor dialogue screen and subsequent challenge based on current floor
* interactWithItem()
    * picks up an item if possible
* goUpStairs()
    * if allowable, generates a loading screen and then the next floor of the level
    * if on the final floor, triggers Menus.gameOverSuccessful()
* moveAvatar()
    * checks to see if possible to move in a given direction, and if possible, does so
    * leverages lookUp(), lookDown(), lookLeft(), lookRight(), moveUp(), moveDown(), moveLeft(), moveRight()
* customizeSkin() and changeSkin()
    * opens UI popup and functions to change the user's skin, respectively
* moneyNeeded()
    * calculates how much money the professor on the current floor requires to begin the riddle
* getSkins()
    * returns all available skins as an arrayList
    
* rerenderWorld()
    * if keyboard input is being taken, is used to render all actions in real time

Ax.java
-
* getDurability(), getMaxdurability()
    * helper methods to pull class attributes for use by other classes
    
* doAction()
    * delegates work to chop()

* chop()
    * checks for valid chopping in any direction and chops trees in ALL valid directions, consuming one durability per
    * leverages lookLeft(), lookRight(), lookUp(), and lookDown() 
    * leverages chopLeft(), chopRight(), chopUp(), and chopDown()

Professor.java
-
* interactWithProf()
    * sets boolean inencounter and delegates rendering to whichEncounter(), which delegates to one of the following:
        - johnDeneroEncounter();
        - paulHilfingerEncounter();
        - joshHugEncounter();
        - davidWagnerEncounter();
        - danGarciaEncounter();
        - nicholasWeaverEncounter();
        - anantSahaicounter();
        - aniAdhikariEncounter();
    * all of which leverage Menus.encounterUI, Menus.encounterUIPopup(), Menus.professorSpeech(), and Menus.countDown to simulate an encounter
    * functionality for input is taken in Avatar.takeInput, which leverages 
        - finishedRiddle() to check if the riddle has been completed before 
        - resetRiddle() to reset on going up stairs
        - correctAnswer() to check against the current right answer
        - endEncounter() to return to the overworld
        - inEncounter() to check if in an encounter for taking input accurately
* whichProf()
    * two versions, both return the String name of a professor given either an int (floor number) or no arguments, which uses the current floor number
* whichProfTile()
    * same as above, two versions returning the TETile of a given professor based off an int, or if no argument, current floor
* getMoney()  and currFloor()
    * used to return the amount of money the player has collected and what floor they've reached
* addMoney() and incrementFloor()
    * used to outwardly change the amount of money the player has and what floor they've reached
    
Additions to TERenderer.java
- 
* In renderFrame(), added
    * renderUI()
        - function that displays current floor, axe durability, inventory icons, and coin count, leveraging Professor.currFloor(), Professor.getMoney(), Ax.getDurability()
        - uses renderInventory(), which uses Avatar.getInventory() and Avatar.getInventoryMap() to see what the player has and convert it to tiles for display in the UI
    * renderCurrentTile()
        - leverages Menus.validMouseTile() and Menus.currMouseTile() and Menus.currTileText() to display flavor text for the tile the mouse is over in the lower right of the UI
    * renderCustomReminder()
        - displays a popup while in the overworld to remind the player they can change skin at any time
        


## Algorithms
* the openify() - style check all tiles for a condition algorithm
    * cleanup() and waterplanet() are simpler versions of this theta(width * height) algorithm
* the randomopenify() - style check a random number of tiles for a condition until a quota is hit or enough tiles have been tried that a timeout is reached
* the addrandomroom() and findhallway() style functions that attempt to generate N rooms or hallways in a random place on the map, limited only by the validplacement() function to check if placement is allowable


## Persistence
* 11/14 - we began experimenting with methods for how to add rooms, thinking through several implementations before coding one we were happy with. Our largest problems were not knowing how to implement hallways and needing more functionality with a random seed.
* 11/15 - we thought through several hallway concepts, deleted our original code and wrote a rather pretty recursive method to add hallways. We also figured out how to read input from a string and standardized seed functionality as well as general bounds for how many elements were generated using the new populateworld() method.
* 11/19 - we added the player avatar and keyboard interactivity as well as began building UI and movement. We also fully implemented saving and loading by use of scanners and writers to a specific file and leveraged interactwithstring to make loading particularly easy
* 11/22 - we added multi-floor generation, between floor loading screens, UI popups, inventory tracking in real time in UI, interactivity with keys, stairs, professors, and items.
* 11/23 - we added coins and professors now have coin counts before they can help you, more coins for successive floor clears, and added UI popups and encounterUI
* 11/26 - we finished professor interaction UI, added hover over tile functionality, fixed functionality for picking up professors' keys after saving, i.e. save and load works across floors
* 11/27 - added all professor encounters, character customization menu, different player skins, and unique professor skins per floor as well as an end menu and functionality to play through the entire game
* total hours: ~20