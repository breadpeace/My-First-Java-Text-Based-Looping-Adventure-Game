/**
 * Description:
 * Shotgun roulette is a game where you play russian roulette with a shotgun.
 * 
 * Version: Alpha build 1.4: The STYLE update [WARNING BUGGY]
 * - added double or nothing mode, which is the exact same as round 3 but the dealer goes first each time
 * - new player menu makes the game a lot more ergonomic
 * - dealer no longer uses the handcuffs when one is already applied
 * - added more stuff to playerTurn
 * - balanced the amount of blank and live shells
 * - working on the dealer's turn
 * - added a debug mode
 * - the bullets get consumed now (i used the wrong varaible oops)
 * - the dealer no longer uses the handcuffs and magnifying glass when theres only one bullet left
 * 
 * to do:
 * - although round 4 is a BUG! this would be great for a boss fight!!
 * - make it so it says what items you and the dealer received
 * - make it so you dont get extra items when you already have full
 * - make a menu for the player to make choices during the players turn
 * - make the game start from round 2 if firstTime is false
 * - the style update
 * - ADD A DEBUG MODE!!
 * - make the dealer not use the magnifying glass if 100 percent of the bullets are the same
 * - make sure to add a way to end the game if the bullets run out
 * - eventually replace shellsLeft with blank + live 
 * - get rid of placeholders and make it better looking later
 * - state how inspecting items wont use them immediatly
 * - add dealer dialogue and reactions
 * -if you're ambitious, re add the mysterious phone
 *
 * bugs:
 * - after inspecting an item, it says invalid please enter a number between 1 and 3 [FIXED: I ACCIDENTALLY PUT SCAN.NEXTLINE IN DEBUG WAHHh]
 * - pressing shoot in the turnMenu doesent work [FIXED, the logic with the else and else ifs didnt account for if it was gameround > 1 and it wasnt first time]
 * - dealer knowledge doesent change after a shot [FIXED]
 * - dealer is not using items for some reason? dealeritemuse() is not being called [FIXED: accidentally used variable round instead of gameRound]
 * - the  dealer shot me twice for some reason, the is within dealerShoot() [FIXED: should have used else if instead of if statement]
 * - some lines wouldnt go to the next line [FIXED: used println instead of print]
 */
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
public class ShotgunRoulette
{
    public static Scanner scan = new Scanner(System.in);
    public static int playerLives, dealerLives, turn, maxLives, liveShells, blankShells, shellsLeft, choice, gameRound, dealerKnowledge, round;
    public static boolean doubleDamage, skipDealerTurn, skipPlayerTurn, playerAlive, debugMode, firstTime, dealerDialogue, easyMode;
    public static String playerName;
    public static String[] shells = new String[1];
    public static String[] playerItems = new String[8] ; 
    public static String[] dealerItems = new String[8];
    public static void main (String[] args)
    {
        easyMode = false;
        firstTime = true;
        debugMode = false;
        dealerDialogue = true;
        menu(); 
    }
    private static void menu()
    {
        System.out.println("============================================================" + "\n" + "SHOTGUN ROULETTE" + "\n" + "a game created by Jeffrey Xu" + "\n" + "Alpha 1.3 Copyright 2025");
        if(firstTime)
        {
            System.out.println("Options:" + "\n" + "1. new game" + "\n" + "2. settings" + "\n" + "3. leave" + "\n" + "============================================================" + "\n" + "(1/2/3)");
        }
        else
        {
            System.out.println("Options:" + "\n" + "1. continue" + "\n" + "2. settings" + "\n" + "3. leave" + "\n" + "============================================================" + "\n" + "(1/2/3)");
        }
        String selection = scan.nextLine();
        if(selection.equals("1"))
        {
            if(firstTime)
            {
                intro();
            }
            game();
        }
        else if(selection.equals("2"))
        {
            settings();
        }
        else
        {
            System.exit(0);
        }
    }
    private static void settings()
    {
        System.out.println("============================================================" + "\n" + "SETTINGS" + "\n" + "1. Toggle Debug Mode" + "\n" + "2. Toggle Dealer Dialogue" + "\n" + "3. Toggle difficulty" + "\n" + "4. Back" + "\n" + "============================================================" + "\n" + "(1/2/3/4)");
        String selection = scan.nextLine();
        if(selection.equals("1"))
        {
            if(debugMode)
            {
                debugMode = false;
                System.out.println("Debug mode has been disabled.");
            }
            else
            {
                debugMode = true;
                System.out.println("Debug mode has been enabled.");
            }
            settings();
        }
        if(selection.equals("2"))
        {
            if(dealerDialogue)
            {
                dealerDialogue = false;
                System.out.println("dealer dialogue has been disabled");
            }
            else
            {
                dealerDialogue = true;
                System.out.println("dealer dialogue has been enabled");
            }
            settings();
        }
        else if(selection.equals("3"))
        {
            if(easyMode)
            {
                easyMode = false;
                System.out.println("Difficulty set to normal");
            }
            else
            {
                easyMode = true;
                System.out.println("Difficulty set to easy");
            }
            settings();
        }
        else if(selection.equals("4"))
        {
            menu();
        }
        
        else
        {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
            settings();
        }
    }
    private static void intro() //introduction to the game
    {
        if(dealerDialogue)
        {
            try {
            Thread.sleep(1000); // Pause for the given time in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle the interruption
        }
            System.out.println("It was a dark and stormy night that saturday morning");
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
            System.out.println("[Dealer]: I'm gonna need you to sign this waiver, it's just a formality, you know, in case you die. giggle");
        }
        System.out.println("(Please enter your name.)");
        playerName = scan.nextLine();
        if(dealerDialogue)
        {
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
            System.out.println("[Dealer]: Interesting name, " + playerName + ", have you played this game before?");
        }
        System.out.println("(yes/no)");
        String response = scan.nextLine();
        if(response.toUpperCase().charAt(0) == 'Y')
        {
            if(dealerDialogue)
            {
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                System.out.println("[Dealer]: well I've never seen you before, i guess you won against the last guy, but you know the rules, so lets begin.");
            }
            firstTime = false;
        }
        else
        {
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }System.out.println("[Dealer]: so what brought you here today?");
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
            System.out.println("[" + playerName + "]: medical bills");
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
            System.out.println("[Dealer]: Well for me personally,  I need this. My mom is kind of homeless. I live with my dad. I wanna help her out.");
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
            System.out.println("[Dealer]: Anyways, since you're new, i'll explain the rules as we play.");
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
            System.out.println("[Dealer]: Lets begin.");
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
        }   
    }
    private static void game() //the gameplay, consisting of 3 rounds
    {
        System.out.println("============================================================" + "\n" + "Game Start" + "\n" + "============================================================");
        try {
            Thread.sleep(1000); // Pause for the given time in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle the interruption
        }
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, game()");
        }
        //setup before the rounds
        if(firstTime)
        {
            gameRound = 1;
        }
        else
        {
            gameRound = 2;
        }
        playerAlive = true;
        
        //the 3 rounds
        for(int i = 0; i < 3; i++) 
        {
            //setup before new round
            playerItems = new String [] {"n/a", "n/a", "n/a", "n/a", "n/a", "n/a", "n/a", "n/a"}; 
            dealerItems = new String [] {"n/a", "n/a", "n/a", "n/a", "n/a", "n/a", "n/a", "n/a"};
            round();
            gameRound++;
            if(playerLives < 1)
            {
                playerAlive = false;
                if(debugMode == true) //debug
                {
                    System.out.println("DEBUG, within game(), since playerLives = " + playerLives + ", playerAlive = " + playerAlive);
                }
                break; 
            }
            if(gameRound == 4) //or 3 ill check later 
            {
                if(dealerDialogue)
                {
                    mysteriousCallerYap();
                }
                System.out.println("Would you like to participate in double or nothing? (yes/no)");
                String response = scan.nextLine();
                if(response.toUpperCase().charAt(0) != 'Y')
                {
                    break;
                }
                
            }
        }
        
        //game over
        if(playerAlive)
        {
            if(debugMode == true) //debug
            {
                System.out.println("DEBUG, within game(), since playerAlive = " + playerAlive + ", the player has won the game");
            }
            blackScreen();
            System.out.println("aw3cd"); 
            firstTime = true;
        }
        else
        {
            System.out.println(""); //placeholder
            blackScreen();
            if(debugMode) //debug
            {
                System.out.println("DEBUG, within game(), since playerAlive is = " + playerAlive + ", the player has lost the game");
            }
            if(firstTime)
            {
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                System.out.println("[Dealer]: that was too soon...");
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                blackScreen();
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                System.out.println("The dealer defribilates you");
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                System.out.println("[Dealer]: come on, let's try again.");
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                game();
            }
            else if(gameRound == 2)
            {
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                System.out.println("[Dealer]: see you soon... " + playerName);
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                blackScreen();
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                System.out.println("Someone defribilates you");
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                hopital();
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                System.out.println("[Doctor]: Thank god you're alive, " + playerName + ", don't think we'll let you die without paying the hospital bill!");
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                System.out.println("[" + playerName + "]: fuck.");
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                blackScreen();
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                menu();
            }
        }
        
        //play again
        menu();
    }
    private static void round() //resets the bullets and distributes items, then gameplay until seomone dies. 
    {
        System.out.println("============================================================" + "\n" + "Round " + gameRound + "\n" + "============================================================");
        
        if(gameRound == 4)
        {
            System.out.println("DOUBLE OR NOTHING" + "\n" + "============================================================");
        }
        try {
            Thread.sleep(1000); // Pause for the given time in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle the interruption
        }
        if(debugMode) //debug
        {
            System.out.println("DEBUG, round()");
        }
        setLives();
        while(playerLives > 0 && dealerLives > 0)
        {
            skipDealerTurn = false;
            skipPlayerTurn = false;
            turn = 1;
            reload();
            if(gameRound > 1)
            {
                giveItems();
            }
            while(shellsLeft > 0) //gameplay until bullets run out or death
            {
                if(gameRound == 4)
                {
                    if(!skipDealerTurn && dealerLives > 0)
                    {   
                        if(debugMode == true) //debug
                        {
                            System.out.println("DEBUG, within round(), skipPlayerTurn = " + skipPlayerTurn + ", skip dealerTurn = " + skipDealerTurn + ", dealer lives = " + dealerLives);
                        }
                        dealerTurn();
                        turn++;
                    }
                }
                else if(!skipPlayerTurn && playerLives > 0)
                {
                    if(debugMode == true) //debug
                    {
                        System.out.println("DEBUG, within round(), skipDealerTurn = " + skipDealerTurn + ", skip playerTurn = " + skipPlayerTurn + ", player lives = " + playerLives);
                    }
                    
                    playerTurn();
                    turn++;
                    if(firstTime)
                    {
                        if(gameRound > 1)
                        {
                            firstTime = false;
                        }
                    }
                }
                if(dealerLives < 1 || shellsLeft < 1 || playerLives < 1)
                {
                    if(debugMode == true) //debug
                    {
                        System.out.println("DEBUG, within round(), dealerLives = " + dealerLives + " shellsLeft = " + shellsLeft + " playerLives = " + playerLives + ", so loop breaks"); //start off from here later
                    }
            
                    break; 
                }
                if(gameRound == 4)
                {
                        
                }
                else if (!skipDealerTurn && dealerLives > 0)
                {   
                    if(debugMode == true) //debug
                    {
                        System.out.println("DEBUG, within round(), skipPlayerTurn = " + skipPlayerTurn + ", skip dealerTurn = " + skipDealerTurn + ", dealer lives = " + dealerLives);
                    }
            
                    dealerTurn();
                    turn++;
                }
                if(playerLives < 1 || shellsLeft < 1 || dealerLives < 1)
                {
                    if(debugMode == true) //debug
                    {
                        System.out.println("DEBUG, within round(), dealerLives = " + dealerLives + " shellsLeft = " + shellsLeft + " playerLives = " + playerLives + ", so loop breaks"); //start off from here later
                    }
            
                    break; 
                }
            
            }
        }     
    }
    private static void setLives() //sets the lives of the player and the dealer
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, setLives()");
        }
        
        if(gameRound == 1)
        {
            maxLives = 2;
        }
        else if(gameRound == 2)
        {
            maxLives = 4;
        }
        else
        {
            maxLives = 5;
        } 
        
        if(dealerDialogue)
        {
            System.out.println("[Dealer]: we get " + maxLives + " hearts this round");
        }
        playerLives = maxLives;
        dealerLives = maxLives;
    }
    private static void reload() //resets the bullets in the chamber at the start of each round 
    {
        System.out.println("============================================================" + "\n" + "SHOTGUN RELOAD");
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, reload()");
        }
        
        //amount of blank and live shells are randomized but fair relative to the amount of each other, epic game design! 
        liveShells = (int) (Math.random() * 4) + 1;
        if(liveShells == 1)
        {
            blankShells = (int) (Math.random() * 2) + 1;
        }
        else if(liveShells == 2)
        {
            blankShells = (int) (Math.random() * 3) + 1;
        }
        else if(liveShells >= 3)
        {
            blankShells = (int) (Math.random() * 4) + 2;
        }
        shellsLeft = blankShells + liveShells; //maybe redundant
        
        if(dealerDialogue)
        {
            System.out.println("[Dealer]: Time to load the chamber with bullets");
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
            System.out.println("[Dealer]: " + blankShells + " blank and " + liveShells + " live shells have been loaded into the chamber.");
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
        }
        
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, within reload(), liveShells = " + liveShells + " blankShells = " + blankShells + " shellsLeft = " + shellsLeft);
        }
        
        //blank and live shells added to the array
        shells = new String[blankShells + liveShells];
        for(int i = 0; i < blankShells; i++) 
        {
            shells[i] = "blank";
        }
        for(int i = 0; i < liveShells; i++) 
        {
            shells[blankShells + i] = "live";
        }
        
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, within reload(), array shells before randomization = " + Arrays.toString(shells));
       
        }
        //randomizes the sequence of the shells
        List<String> shellList = Arrays.asList(shells);
        Collections.shuffle(shellList);
        shells = shellList.toArray(new String[0]);

        if(dealerDialogue)
        {
            System.out.println("[Dealer]: they are inserted into a random order");
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
        }

        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, within reload(), array shells after randomization = " + Arrays.toString(shells));
       
        }
        System.out.println("============================================================");
        try {
            Thread.sleep(1000); // Pause for the given time in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle the interruption
        }
    }

    private static void giveItems() //distributes the items to the player and the dealer
    {
        if(dealerDialogue) //brb
        {
            if(firstTime)
            {
                System.out.println("[Dealer]: lets spice things up, ill distribute some items before each reload, feel free to inspect them to see what they do");
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
            }
        }
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, giveItems()");
        }
        
        int numItemsToGive = 0;
        if(gameRound == 2)
        {
            numItemsToGive = 2;
        }
        else if(gameRound == 3)
        {
            numItemsToGive = 4;
        }
        
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, within giveItems(), gameRound = " + gameRound + " so numItemsToGive = " + numItemsToGive);
        }
        //player items received
        for(int i = 0; i < numItemsToGive; i++) //im going to have to seperate these... SIGHHHH
        {
            int availablePlayerSlot = emptySlotSearch("player");
            
            if(availablePlayerSlot == -1)
            {
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                System.out.println("Your inventory is full, you will not receive anymore items");
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                if(dealerDialogue)
                {
                    System.out.println("[Dealer]: sorry man I don't make the rules, no items for you until you have an empty inventory slot.");
                    try {
                        Thread.sleep(1000); // Pause for the given time in milliseconds
                    } catch (InterruptedException e) {
                        e.printStackTrace(); // Handle the interruption
                    }
                }
                break;
            }
            else
            {
                playerItems[availablePlayerSlot] = itemRandom();
                System.out.println("You have received a " + playerItems[availablePlayerSlot]);
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
            }
            
        }
        //dealer items received
        for(int i = 0; i < numItemsToGive; i++)
        {
            int availableDealerSlot = emptySlotSearch("dealer");
            if(availableDealerSlot == -1)
            {
                System.out.println("The Dealer's inventory is full, they will not receive anymore items");
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                if(dealerDialogue)
                {
                    System.out.println("[Dealer]: aw man");
                    try {
                        Thread.sleep(1000); // Pause for the given time in milliseconds
                    } catch (InterruptedException e) {
                        e.printStackTrace(); // Handle the interruption
                    }
                }
                break;
            }
            else
            {
                dealerItems[availableDealerSlot] = itemRandom();
                System.out.println("The dealer has received a " + dealerItems[availableDealerSlot]);
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
            }
        }
    
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, within giveItems()," + "\n" +  "playerItems = " + Arrays.toString(playerItems) + "\n" + "dealerItems = " + Arrays.toString(dealerItems));
        }

    }
    private static int emptySlotSearch(String guy) //DOESENT ACCOUNT FOR IF INVENTORY IS FULL, FIX LATER
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, emptySlotSearch()");
        }
        
        int index = -1;
        if(guy.equals("player"))
        {
            for(int i = 0; i < playerItems.length; i++)
            {
                if(playerItems[i].equals("n/a"))
                {
                    return i;
                }
            }
        }
        if(guy.equals("dealer"))
        {
            for(int i = 0; i < dealerItems.length; i++)
            {
                if(dealerItems[i].equals("n/a"))
                {
                    return i;
                }
            }
        }
        
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, within emptySlotSearch(), for the " + guy + ", index = " + index);
        }

        return index;
    }
    private static String itemRandom()
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, itemRandom()");
        }
        
        int itemType = (int)(Math.random() * 5) + 1;
        String item = "placeholder";
        if(itemType == 1)
        {
            item = "cigarette";
        }
        else if(itemType == 2)
        {
            item = "magnifying glass";         
        }
        else if(itemType == 3)
        {
            item = "hand saw";
        }
        else if(itemType == 4)
        {
            item = "pair of handcuffs";
        }
        else if(itemType == 5)
        {
            item = "beer can";
        }
        
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, within itemRandom(), " + "itemType = " + itemType + " so item = " + item);
        }

        return item;
    }
    private static void playerTurn() //the player's turn
    { 
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, playerTurn()");
        }
        skipDealerTurn = false;
        System.out.println("============================================================" + "\n" + "YOUR TURN" + "\n" + "============================================================");
        turnMenu();
        if(shellsLeft < 1)
        {
            try {
                Thread.sleep(1000); // Pause for the given time in milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the interruption
            }
            System.out.println("The shotgun has ran out of shells");
        }
    }
    private static void turnMenu()
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, turnMenu()");
        }
        System.out.println("============================================================" + "\n" + "OPTIONS");
        String response;
        System.out.println("1. Turn information" + "\n" + "2. Shoot");
        if(gameRound > 1)
        {
            System.out.println("3. Inspect Items");
            System.out.println("(1/2/3)");
            
        }
        else
        {
            System.out.println("(1/2)");
        }
        System.out.println("============================================================");
        response = scan.nextLine();
        if(response.equals("1"))
        {
            turnInformation();
            System.out.println("press ENTER to continue");
            String entertocontinue = scan.nextLine();
            if(entertocontinue.equals("we hate boyan wei"))
            {
                turnMenu();
            }
            else
            {
                turnMenu();
            }
        }
        else if(response.equals("2"))
        {
            if(gameRound > 1)
            {
                if(firstTime) //brb
                {
                    System.out.println("Are you sure you want to shoot? You will need to wait until your next turn to use your items.");
                    System.out.println("(yes/no)");
                    String confirmation = scan.nextLine();
                    if(confirmation.toUpperCase().charAt(0) == 'Y')
                    {
                        shootPrompt();
                    }
                    else
                    {
                        turnMenu();
                    }
                }
                else
                {
                    shootPrompt();
                }
            }
            else
            {
                shootPrompt();
            }
        }
        else if(response.equals("3"))
        {
            if(gameRound > 1)
            {
                itemInspectPrompt();
                turnMenu();
            }
            else
            {
                System.out.println("Invalid. Please enter a number between 1 and 2");
                turnMenu();
            }
        }
        else
        {
            System.out.println("Invalid. Please enter a number between 1 and 3");
            turnMenu();
        }
    }
    private static void itemInspectPrompt()
    {   
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, itemInspectPrompt()");
        }
        inventoryDisplay();
        while(!playerItemsNone())
        {
            if(debugMode == true) //debug
            {
                System.out.println("DEBUG, within itemInspectPrompt(), item inspect prompt accepted");
            }
            if(firstTime)
            {
                System.out.println("You can inspect an Item before using it, and multiple items can be used before your turn ends.");
            }
            System.out.println("input the number for the item slot coresponding to the item you would like to inspect" + "\n" + "or input \"leave\" to quit item inspection");
            try 
            {
                choice = (int)scan.nextInt();
                scan.nextLine();
                //empty item slot
                if(playerItems[(choice - 1)].equals("n/a"))
                {
                    if(debugMode == true) //debug
                    {
                        System.out.println("DEBUG, withing itemInspectPormpt(), item choice " + playerItems[(choice - 1)] + " is empty");
                    }
                    
                    System.out.println("that slot is empty, please select another");

                }
                //valid item slot
                else if(choice < 9 && choice > 0)
                {
                    if(debugMode == true) //debug
                    {
                        System.out.println("DEBUG, withing itemInspectPormpt(), item choice " + playerItems[(choice - 1)] + " is valid");
                    }
                    playerItemInfo(playerItems[(choice - 1)]);
                    System.out.println("Would you like to use this item? (yes/no)");
                    String answer = scan.nextLine();
                    if(answer.toUpperCase().charAt(0) == 'Y')
                    {
                        playerItemUse(playerItems[(choice - 1)]);
                        playerItems[(choice - 1)] = "n/a";
                    }
                }
                //invalid item slot
                else
                {
                    if(debugMode == true) //debug
                    {
                        System.out.println("DEBUG, within itemInspectPrompt(), choice " + choice + " is invalid");                        
                    }
                        
                    System.out.println("Invalid input. Please enter a number between 1 and 8.");
                }
            } 
            //invalid input but also quit as well
            catch (InputMismatchException e) 
            { 
                if(debugMode == true) //debug
                {
                    System.out.println("DEBUG, within itemInspectPormpt(), response " + choice + " is invalid");
                }
                scan.nextLine();
                break;
            }
                    
        }
        //no items found
        if(playerItemsNone())
        {
            System.out.println("You have no items."); //placeholder
        }
    }
    private static void playerItemInfo(String item)
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, playerItemInfo()");
        }
        
        if(item.equals("cigarette"))
        {
            System.out.println("The Cigarette heals 1 life ");
        }
        else if(item.equals("magnifying glass"))
        {
            System.out.println("The Magnifying Glass allows you to see the current shell in the chamber.");
        }
        else if(item.equals("hand saw"))
        {
            System.out.println("The Handsaw doubles the damage of the current shell");
        }
        else if(item.equals("pair of handcuffs"))
        {
            System.out.println("The Pair of Handcuffs skips the dealers turn");
        }
        else if(item.equals("beer can"))
        {
            System.out.println("Removes the current shell from the chamber and go to the next one, it will still remain your turn.");
        }
    }
    private static void playerItemUse(String item)
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, playerItemUse()");
        }
        
        if(item.equals("cigarette"))
        {
            if(playerLives < maxLives)
            {
                playerLives++;
                System.out.println("You have healed 1 life.");
            }
            else
            {
                System.out.println("You are already at full health. no you dont get it back");
            }
        }
        else if(item.equals("magnifying glass"))
        {
            System.out.println("The current shell is " + shells[turn - 1]);
        }
        else if(item.equals("hand saw"))
        {
            doubleDamage = true;
        }
        else if(item.equals("pair of handcuffs"))
        {
            if(skipDealerTurn == false)
            {
                skipDealerTurn = true;
            }
            else
            {
                System.out.println("The dealer has already been handcuffed");
            }
        }
        else if(item.equals("beer can"))
        {
            System.out.println("The shell you removed was " + shells[turn - 1]);
            if(shells[turn - 1].equals("live"))
            {
                liveShells--;
            }
            if(shells[turn - 1].equals("blank"))
            {
                blankShells--;
            }
            shellsLeft--;
            turn++;
        }
    }
    private static void shootPrompt() //unfinished
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, shootPrompt()");
        }
        
        System.out.println("Would you like to shoot yourself or the dealer?" + "\n" + "- Shoot yourself (shooting yourself with a blank does not end your turn)" + "\n" + "- Shoot dealer" + "\n" + "(yourself/dealer)");
        String response = scan.nextLine();
        System.out.println("click...");
        try {
            Thread.sleep(2000); // Pause for the given time in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle the interruption
        }
        if(response.toUpperCase().charAt(0) == 'Y')
        {
            if(shells[turn - 1].equals("live"))
            {
                System.out.println("BOOM");
                playerLives--;
                if(doubleDamage)
                {
                    playerLives--;
                    doubleDamage = false;
                    System.out.println("You have taken 2 damage.");
                }
                else
                {
                    System.out.println("You have taken 1 damage.");
                }
                liveShells--; 
                
            }
            else if(shells[turn - 1].equals("blank"))
            {
                System.out.println("...");
                skipDealerTurn = true;
                System.out.println("You have shot a blank.");
                doubleDamage = false;
                blankShells--;
            }
            
        }
        else
        {
            if(shells[turn - 1].equals("live"))
            {
                System.out.println("BOOM");
                dealerLives--;
                if(doubleDamage)
                {
                    dealerLives--;
                    doubleDamage = false;
                    System.out.println("The dealer has taken 2 damage");
                }
                else
                {
                    System.out.println("The dealer has taken 1 damage.");
                }
                liveShells--;
            }
            else if(shells[turn - 1].equals("blank"))
            {
                System.out.println("...");
                System.out.println("You have shot a blank.");
                doubleDamage = false;
                blankShells--;
            }
        }
        shellsLeft --;
    }
    private static void turnInformation() //displays the information for the player at the start of their turn
    {
        System.out.println("============================================================" + "\n" + "TURN INFORMATION"); //brb
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, turnInformation()");
        }
        
        System.out.println("Round " + gameRound);
        System.out.println("You have " + playerLives + " hearts left.");
        System.out.println("The dealer has " + dealerLives + " hearts left.");
        System.out.println("There are " + shellsLeft + " shells left in the chamber."); //make an easy mode that displays the amount of blank and live shells left later
        if(easyMode)
        {
            System.out.println("There are " + liveShells + " live and " + blankShells + " blank shells left");
        }
        System.out.println("\"============================================================\"");
    }
    private static boolean playerItemsNone() //checks if player has no items at all
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, playerItemsNone()");
        }
        
        String[] empty = {"n/a", "n/a", "n/a", "n/a", "n/a", "n/a", "n/a", "n/a"};
        if(Arrays.equals(empty, playerItems))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private static void inventoryDisplay()
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, inventoryDisplay()");
        }
        
        for(int i = 0; i < 8; i++)
        {
            System.out.println((i + 1) + ". " + playerItems[i]);
        }
    }
    private static void dealerTurn() //the dealer's turn
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, dealerTurn()");
        }
        
        skipPlayerTurn = false;
        if(gameRound > 1)
        {
            if(debugMode == true) //debug
            {
                System.out.println("DEBUG, within dealerTurn(), game round = " + gameRound + " so dealeritemuse() is called");
            }
            dealerItemUse();
        }
        else
        {
            if(debugMode == true) //debug
            {
                System.out.println("DEBUG, within dealerTurn(), game round = " + gameRound + " so dealeritemuse() is  not called");
            }
        }
        if(shellsLeft > 0)
        {
            dealerShoot();
        }
        else
        {
            System.out.println("There are no shells left in the chamber.");
        }
    }
    private static void dealerItemUse()
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, dealerItemUse()");
        }
        
        for(int i = 0; i < 8; i++)
        {
            //very smart dealer AI here giggle, does the most optimal choice
            if(!dealerItemsNone())
            {
                try {
                    Thread.sleep(1000); // Pause for the given time in milliseconds
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Handle the interruption
                }
                if(dealerItems[i].equals("cigarette"))
                {
                    if(dealerLives < maxLives)
                    {
                        dealerLives++;
                        System.out.println("The dealer used the ciggarete");
                        dealerItems[i] = "n/a";
                    }
                }
                else if(dealerItems[i].equals("magnifying glass"))
                {
                    if(dealerKnowledge == 0 && blankShells != 0 && liveShells != 0)
                    {
                        System.out.println("The dealer has used the magnifying glass.");
                        if(shells[turn - 1].equals("live"))
                        {
                            
                            dealerKnowledge = 2;
                        }
                        if(shells[turn - 1].equals("blank"))
                        {
                            
                            dealerKnowledge = 1;
                        }
                        dealerItems[i] = "n/a";
                    }
                }
                else if(dealerItems[i].equals("hand saw"))
                {
                    if(dealerKnowledge == 2 || liveShells > blankShells)
                    {
                        if(doubleDamage == false)
                        {
                            System.out.println("The dealer uses a hand saw");
                            doubleDamage = true;
                            dealerItems[i] = "n/a";
                        }
                    }
                }
                else if(dealerItems[i].equals("pair of handcuffs"))
                {
                    if((dealerKnowledge == 2 || liveShells > blankShells) && shellsLeft > 1 && skipPlayerTurn == false)
                    {
                        if(skipPlayerTurn == false)
                        {
                            System.out.println("The dealer uses a pair of handcuffs");
                            skipPlayerTurn = true;
                            dealerItems[i] = "n/a";
                        }
                    }
                }
                else if(dealerItems[i].equals("beer can"))
                {
                    if(dealerKnowledge == 0 && blankShells == liveShells)
                    {
                        System.out.println("The dealer uses the beer can, The current shell gets ejected, it appears to be " + shells[turn - 1]);
                        if(shells[turn - 1].equals("live"))
                        {
                            liveShells--;
                        }
                        if(shells[turn - 1].equals("blank"))
                        {
                            blankShells--;
                        }
                        shellsLeft--;
                        turn++; 
                        dealerItems[i] = "n/a";
                    }
                }
            }
            else
            {
                break;
            }
        }
    }
    private static boolean dealerItemsNone()
    {
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, dealerItemsNone()");
        }
        
        String[] empty = {"n/a", "n/a", "n/a", "n/a", "n/a", "n/a", "n/a", "n/a"};
        if(Arrays.equals(empty, dealerItems))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private static void dealerShoot()
    {
        
        if(debugMode == true) //debug
        {
            System.out.println("DEBUG, dealerShoot()");
        }
        
        if(dealerKnowledge == 1)
        {
            System.out.println("The dealer shoots himself"); //placeholder
            skipPlayerTurn = true;
            blankShells--;
            shellsLeft--;
            if(debugMode == true) //debug
            {
                System.out.println("DEBUG, the shell is " + shells[turn - 1]);
            }
        }
        else if(dealerKnowledge == 2)
        {
            System.out.println("The dealer shoots you "); //placeholder
            playerLives--;
            if(doubleDamage)
            {
                playerLives--;
                doubleDamage = false;
                System.out.println("You take 2 damage");
            }
            else
            {
                System.out.println("You take 1 damage");
            }
            liveShells--;   
            shellsLeft--;
            if(debugMode == true) //debug
            {
                System.out.println("DEBUG, the shells is " + shells[turn - 1]);
            }
        }
        else if(dealerKnowledge == 0)
        { //placeholders
            if(blankShells < liveShells)
            {
                System.out.println("The dealer shoots you");
                if(shells[turn - 1].equals("live"))
                {
                    
                    System.out.println("it was live");
                    playerLives--; 
                    if(doubleDamage)
                    {
                        playerLives--;
                        doubleDamage = false;
                        System.out.println("You take 2 damage");
                    }
                    else
                    {
                        System.out.println("You take 1 damage");
                    }
                    liveShells--;
                }
                else
                {
                    System.out.println("it was blank");
                    blankShells--;
                }
                shellsLeft--;
            }
            else if(blankShells > liveShells)
            {
                System.out.println("The dealer shoots himself");
                if(shells[turn - 1].equals("live"))
                {
                    System.out.println("it was live");
                    dealerLives--; 
                    liveShells--;
                }
                else
                {
                    System.out.println("it was blank");
                    skipPlayerTurn = true;
                    blankShells--;
                }
                shellsLeft--;
            }
            else if(blankShells == liveShells)
            {
                int guess = (int)(Math.random() * 2) + 1;
                if(guess == 1) //dealer shoots himself
                {
                    System.out.println("The dealer shoots himself");
                    if(shells[turn - 1].equals("live"))
                    {
                        System.out.println("it was live");
                        dealerLives--;
                        if(doubleDamage)
                        {
                            dealerLives--;
                            doubleDamage = false;
                        }
                        liveShells--;
                    }
                    else
                    {
                        System.out.println("it was blank");
                        skipPlayerTurn = true;
                        doubleDamage = false;
                        blankShells--;
                    }
                }
                else
                {
                    System.out.println("The dealer shoots you");
                    if(shells[turn - 1].equals("live"))
                    {
                        System.out.println("it was live");
                        playerLives--;
                        if(doubleDamage)
                        {
                            playerLives--;
                            doubleDamage = false;
                        }
                        liveShells--;
                    }
                    else
                    {
                        System.out.println("it was blank");
                        doubleDamage = false;
                        blankShells--;
                    }
                }
                shellsLeft--;    
            }
        }
        dealerKnowledge = 0;
        try {
            Thread.sleep(1000); // Pause for the given time in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle the interruption
        }
    }
    private static void blackScreen()
    {
        for(int i = 0; i < 28; i++)
        {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        }
    }
    private static void hopital() //brb
    {
        System.out.println(".................................................................................................." + "\n" + "  ..................................................................................................." + "\n" + "...................................................................            ..................." + "\n" +  ".................................................................   @@@* @@@@@.    ..............." + "\n" + "..............    ..............................................  #@          *@@@    ............" + "\n" + "......         @+: ............................................  @:  .........     @#   .........." + "\n" + "..    +@@@**@@-  @ ............................................ %@  ..............   @-  ........." + "\n" + " .. *@          . @  ........................................... @      .............  @# ........." + "\n" + " .. -@  ::        %* ..........................................  @  @@* ........    ..  @  ........ " + "\n" + "..  @  @@ @@@@@@ =@ .......................................... @       ........ @@ ... #@ ........ " + "\n" + "..  @@@= @@       @ .......................................... %#  ..   .......    ...  @ ........ " + "\n" + "..  @     @       @ ..........................................  @@  . @  .....    ...  @  ........ " + "\n" + "..  @ .=+@%@@@@@#   ...........................................   @    @       +@ ..  @- ......... " + "\n" + " ... @%   @@       ...............................................  +@    @@* ..@+    @   .........");
        System.out.println("...      @-: .....................................        ........    @@@*=      @@@ @  .......... " + "\n" + ".......  @ @ ......      ......................... @@@@@@ ........... @  :  @@@@   @  @: ........." + "\n" + ".......  @ @ .....  @=@= ..............                 @ ........... @  @    @@   @.  @  ........" + "\n" + "........ @ @  ...  @  #               :=**####*+%@@@    :  .......... @  @    @@@   @   @  ......." + "\n" + "........ @ -# ..   @@@@@#@@@@@@@@@@@@#:              @@  = .......... @  @ . @@@@@@  @#  @  ...... " + "\n" +  "........  @ @    @@                    .............   @   .......... @  @    @@@@@@  @- *@  ....." + "\n" +  "......... @ @  @    ..................................  @@  ......... @-  @   =@@@@    @  @= ..... " + "\n" +  "......... # @@%  ......................................  .@  ........ @+  @    %@@+ ..  @  @  .... " + "\n" +  "........  =@.   ........................................   @   ......  @   @    @@% ...  @  @  ... " + "\n" +  "......   @@   ............................................  @@ ....... @ . *        .... #@  @  .. " + "\n" + "....   @@   ...............................................  #  ...... @ . @@ ..........  @  @- .. " + "\n" + " ..   @@   ..................................................  @  ..... @ .  @ ........... @#  @ .." + "\n" + "@:   .....................................................  @  .... @  . @  ..........  :@ @  . ");
    }
    private static void mysteriousCallerYap()
    {
        System.out.println("*ring... ring...*");
        System.out.println("Your phone rings, you pick it up.");
        System.out.println("[Mysterious Caller]: Congratulations, this is a prerecorded message, if you're hearing this, you've won the game, but it looks like you have a bit of experience " + playerName);
        System.out.println("[Mysterious Caller]: So I have a proposition for you, if you really feel confident in your skills, you can play one more round right?");
        System.out.println("[Mysterious Caller]: There will be a catch though, for this entire time, you've had a huge advantage, you've been going first this entire time");
        System.out.println("[Mysterious Caller]: This time, I will be going first each round.");
        System.out.println("[Mysterious Caller]: This is double or nothing, if you want to participate, all you have to do is defribilate me, otherwise you can walk away with what you have.");
        System.out.println("[Mysterious Caller]: Make your choice... " + playerName);
    }

}