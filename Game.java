/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Edward Pisco
 * @version 2016.04.04
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, atrium, security, library, bookstore, freddy , jason, zombie;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university", " No Items", 0);
        theater = new Room("in a lecture theater", " No Items", 0);
        pub = new Room("in the campus pub", "Piano", 1);
        lab = new Room("in a computing lab", "Lab Rats", 5);
        office = new Room("in the computing admin office", "Computers" , 10);
        atrium = new Room("In the Atrium","There is a MG3 Machine Gun, yoou'll need it!", 1);
        library = new Room("In the library", "No Items", 0);
        freddy = new Room("It's Freddy!, welcome to my nightmare", "No items", 0);
        jason = new Room("kill....kill...kill...now...now..now!...RUN!", "No items", 0);
        zombie = new Room("Zombie outbreak!", "Zombie virus antitode", 1);
        bookstore = new Room("In the bookstore", "No items", 0);
        security = new Room("In the security office", "No one is here", 0);
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("west", library);

        office.setExit("west", lab);
        
        // new room exits
        
        library.setExit("east", lab);
        library.setExit("north", pub);
        
        office.setExit("east", lab);
        office.setExit("south", atrium);
        atrium.setExit("north", office);
        
        atrium.setExit("west", bookstore);
        atrium.setExit("north", office);
        atrium.setExit("south", jason);
        
        bookstore.setExit("north", lab);
        bookstore.setExit("south", zombie);
        bookstore.setExit("north", lab);

        jason.setExit("north", atrium);
        jason.setExit("west", zombie);
        jason.setExit("south", freddy);
        
        zombie.setExit("north", bookstore);
        zombie.setExit("east", jason);
        zombie.setExit("south", security);
        
        freddy.setExit("north", jason);
        freddy.setExit("west", security);
       
        security.setExit("east", freddy);
        security.setExit("north", zombie);
       
        
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    private void printLocationInfo()
    {
        System.out.println("Your are " + currentRoom.getLongDescription());
        System.out.println("Exists: ");
        if(currentRoom.northExit != null)
        {
            System.out.println("north ");
        }
         if(currentRoom.eastExit != null)
        {
            System.out.println("east "); 
        }
            if(currentRoom.southExit != null)
        {
            System.out.println("south ");
        }
        if(currentRoom.westExit != null)
        {
            System.out.println("west ");
        }
    }
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    }
    private void look()
    {
        System.out.println("Here is your current location");
        System.out.println(currentRoom.getLongDescription());
    }
    private void eat()
    {
        System.out.println("You have eaten now and you are not hungry anymore");
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
