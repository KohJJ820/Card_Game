import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import java.util.HashSet;

class Main {
    public static void saveGame(ArrayList<Player> players, ArrayList<Player> playerAccordance, Deck deck,
        CenterDeck center, int k, int i, Map<Card, Player> cardPlayer, Scanner input) throws IOException {
        SaveData save = new SaveData(players, playerAccordance, deck, center, k, i, cardPlayer);
        System.out.print("Enter the filename to save: ");
        String file = input.nextLine();
        FileOutputStream fileOut = new FileOutputStream(file + ".txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(save);
        out.close();
    }

    public static SaveData loadGame(Scanner input) throws ClassNotFoundException, IOException {
        System.out.print("Enter the file name to load: ");
        String file = input.nextLine();
        FileInputStream fileIn = new FileInputStream(file + ".txt");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        SaveData savedData = (SaveData) in.readObject();
        return savedData;

    }

    public static boolean pause(Scanner scanner) {
        System.out.println("Press anything to continue...");
        scanner = new Scanner(System.in);
        scanner.nextLine();
        return true;
    }

    public static void removeDiffSuit(CenterDeck center) {
        for (int j = 1; j < center.getSize(); j++) {
            if (center.get(j).getSuit() != center.get(0).getSuit()) {
                center.remove(center.get(j));
            }
        }
    }

    public static Player determineWinner(CenterDeck center, Map<Card, Player> map) {
        center.sort(); // sort ascending (refer Card.java compareTo for info)
        Card largestRankCard = center.drawCardTop(); // take the last element of stack (greatest rank)
        Player highestRankPlayer = map.get(largestRankCard);
        return highestRankPlayer;
    }

    public static void determinePlayerAccordance(Card card, CenterDeck center, ArrayList<Player> pA,
            ArrayList<Player> players) {
        HashSet<String> setPlayer1 = new HashSet<String>(Arrays.asList("A", "5", "9", "K"));
        HashSet<String> setPlayer2 = new HashSet<String>(Arrays.asList("2", "6", "X"));
        HashSet<String> setPlayer3 = new HashSet<String>(Arrays.asList("3", "7", "J"));
    

        
        if (setPlayer1.contains(center.get(0).getRank())) {
            for (int i = 0; i < players.size(); i++) {// Player1
                pA.add(players.get(i));
            }
        }
        else if (setPlayer2.contains(center.get(0).getRank()))// Player2
        {
            for (int i = 1; i < players.size(); i++) {
                pA.add(players.get(i));
                if (i == 0) {
                    break;
                }
                if (i == 3) {
                    i = -1;
                }
            }
        }
        else if (setPlayer3.contains(center.get(0).getRank()))// Player3
        {
            for (int i = 2; i < players.size(); i++) {
                pA.add(players.get(i));
                if (i == 1) {
                    break;
                }
                if (i == 3) {
                    i = -1;
                }
            }
        }
        else // Player4
        {
            for (int i = 3; i < players.size(); i++) {
                pA.add(players.get(i));
                if (i == 2) {
                    break;
                }
                if (i == 3) {
                    i = -1;
                }
            }
        }
        

    }

    // Let winner become the first player of the next trick
    public static ArrayList<Player> newPlayerAcc(ArrayList<Player> players, ArrayList<Player> playerAcc,
            Player highestRankPlayer) {
        playerAcc.add(0, highestRankPlayer); // shift winner to the first index: [winner(player4), player1, player2, ..]

        Player currentPlayer = playerAcc.get(0); // Gets the first player
        int currentPlayerIndex = players.indexOf(currentPlayer); // Find index of current player in the players list
                                                                 // which will be used to re-order the list.
        ArrayList<Player> newPlayersAccordance = new ArrayList<>(); // Creates a new arraylist to store the updated
                                                                    // ordering of players

        for (int i = currentPlayerIndex; i < players.size(); i++) {
            newPlayersAccordance.add(players.get(i)); // iterate through starting from current player index then add
                                                      // each players to new players acc
        }

        for (int i = 0; i < currentPlayerIndex; i++) {
            newPlayersAccordance.add(players.get(i));
        }

        return newPlayersAccordance;
    }

    public static int checkHandDeckAndDeck(CenterDeck center, Player player) { // return cardCheck (check if got playable card)
        ArrayList<Card> currentHandDeck = player.getHandDeck();
        Card centerFirstCard = center.get(0);
        int cardCheck = 0;
        for (Card card : currentHandDeck) {
            if (!(card.getRank().equals(centerFirstCard.getRank()))
                    && !(card.getSuit().equals(centerFirstCard.getSuit()))) {
                cardCheck++;
            }
        }
        return cardCheck;

    }

    public static void countScore(ArrayList<Player> players) {
        for (Player player : players) {
            ArrayList<Card> currentPlayerCards = player.getHandDeck();
            int currentScore = player.getScore();
            for (Card card : currentPlayerCards) {
                String cardRank = card.getRank();
                switch (cardRank) {
                    case "A":
                        currentScore++;
                        break;
                    case "2":
                        currentScore += 2;
                        break;
                    case "3":
                        currentScore += 3;
                        break;
                    case "4":
                        currentScore += 4;
                        break;
                    case "5":
                        currentScore += 5;
                        break;
                    case "6":
                        currentScore += 6;
                        break;
                    case "7":
                        currentScore += 7;
                        break;
                    case "8":
                        currentScore += 8;
                        break;
                    case "9":
                        currentScore += 9;
                        break;
                    case "X":
                    case "J":
                    case "Q":
                    case "K":
                        currentScore += 10;
                        break;

                    default:
                        System.out.println("Got error in switch");
                        break;
                }
                player.setScore(currentScore);
            }
        }

    }

    public static void printScore(ArrayList<Player> players) {
        System.out.println("Player 1: " + players.get(0).getScore() + " | " + "Player 2: "
                + players.get(1).getScore() + " | " + "Player 3: " + players.get(2).getScore() + " | "
                + "Player 4: " + players.get(3).getScore() + " | ");
    }

    public static void printInfo(ArrayList<Player> players,Player currentPlayer, int k, boolean loadStatus, SaveData savedData, CenterDeck center, Deck deck) {
        System.out.println();
        System.out.println("Trick" + k + "#");
for (Player player : players) {
                        if (player.equals(currentPlayer)) {
                            System.out.println("--> " + player.toString());
                        } else {
                            System.out.println("    " + player.toString());
                        }
                    } // print player's deck

                    System.out.println(center);
                    System.out.println(deck.toString()); // print deckk
                    printScore(players);
                    System.out.println("Turn: Player" + currentPlayer.getPlayerNo());
     }

    public static void main(String[] args) {

        gameloop: while (true) { // New game
            SaveData savedData = new SaveData();
            Scanner input = new Scanner(System.in);
            //Print homepage (start, load and exit)
            System.out.println("*************************");
            System.out.println("         GO BOOM         ");
            System.out.println("*************************");
            System.out.println(" START     LOAD     EXIT ");
            System.out.println();
            System.out.print("Enter your choice: ");
            boolean loadStatus = false;
            //while loop for gameChoice
            gameChoice:
            while (true){
                String gameChoice = input.nextLine();
                if (gameChoice.equals("exit")) {
                    System.out.println("game exited");
                    break gameloop;
                } else if (gameChoice.equals("load")) {
                    while (true) {
                        try {
                            savedData = loadGame(input);
                            loadStatus = true;
                            break gameChoice;
                        } catch (Exception e) {
                            System.out.println("Load failed !!!!");
                            System.out.println("File does not found! Please enter a valid file name");
                        }
                    }
                    
                } else if (gameChoice.equals("start")) {
                    System.out.println();
                    break;
                } else {
                    System.out.println("Invalid input, please try again");
                }
            }

            Deck deck = new Deck(); // new Deck is created
            CenterDeck center = new CenterDeck(); // new center is created

            System.out.println("Unshuffled " + deck);
            deck.shuffle(); // shuffle the deck
            ArrayList<Player> players = new ArrayList<>();
            players.add(new Player("1"));
            players.add(new Player("2"));
            players.add(new Player("3"));
            players.add(new Player("4"));
            ArrayList<Player> playersAccordance = new ArrayList<>();
            Card firstLeadCard = deck.drawCardTop(); // draw the top card from deck
            center.push(firstLeadCard);
            // Assign the first turn to the corresponding player based on the first lead
            // card's rank
            determinePlayerAccordance(firstLeadCard, center, playersAccordance, players); // determine the accordance of
                                                                                          // the players

            // deals 7 card to Players

            for (Player player : players) {
                for (int i = 0; i < 7; i++) {
                    Card temp = deck.drawCardTop();
                    player.addCard(temp);
                }
            }

            // loop continues when players' deck size not equal 0
            trickloop: for (int k = 1; players.get(0).getSize() != 0 ||
                    players.get(1).getSize() != 0 ||
                    players.get(2).getSize() != 0 ||
                    players.get(3).getSize() != 0; k++) {

                // new Map created to track who is the winner
                Map<Card, Player> cardPlayer = new HashMap<>();
                if (k > 1) {
                    center.clearAndReset();
                }
                if (loadStatus == true) {
                    k = savedData.getK();
                    cardPlayer = savedData.getCardPlayer();
                    center = savedData.getCenter();
                    players = savedData.getPlayers();
                    playersAccordance = savedData.getPlayerAccordance();
                    deck = savedData.getDeck();
                }

                playerloop: for (int i = 0; i < 4; i++) {
                    if (loadStatus == true) {
                        i = savedData.getI();
                        loadStatus = false;
                    }
                    Player currentPlayer = playersAccordance.get(i);
                    printInfo(players, currentPlayer, k, loadStatus, savedData, center, deck);
                    Card cardToRemove = null;
                    boolean error = false;
                    String choice;
                    inputloop: do {
                        if (error == true) {
                            System.out.println();
                            printInfo(players, currentPlayer, k, loadStatus, savedData, center, deck);
                            error = false;
                        }
                        if (i > 0 || k == 1) {
                            int cardCheck = checkHandDeckAndDeck(center, currentPlayer); // if card check equals to
                                                                                         // number of hand deck, means
                                                                                         // all cards are checked and
                                                                                         // are not playable
                            int handDeckSize = currentPlayer.getSize();
                            // if remaining deck is exhausted, skip this player turn
                            if (cardCheck == handDeckSize) {
                                if (deck.getSize() == 0) {
                                    System.out.println("DECK IS EXHAUSTED. You dont have playable card ^^ ");
                                    System.out.println();
                                    pause(input);
                                    continue playerloop;
                                }

                                System.out.println("You don't have a playable card. Please draw a card ^^");
                            }
                        }

                        System.out.print("> ");
                        choice = input.nextLine();
                        // if input is draw, the player takes the card on top of the deck (see the error
                        // || choice.equals("draw") at the end of the do-while loop)
                        if (choice.equals("d")) {
                            try {
                                Card drawCard = deck.drawCardTop();
                                currentPlayer.addCard(drawCard);
                                error = false;
                            } catch (Exception e) {
                                System.out.println("The deck is exhausted. You cannot draw the card !");
                                pause(input);
                                error = true;
                            }
                            printInfo(players, currentPlayer, k, loadStatus, savedData, center, deck);
                        } else if (choice.equals("s")) {
                            System.out.print("Would you like to start a new game?(y/n) :");
                            String startNew = input.nextLine();
                            if (startNew.equals("y")) {
                                System.out.print("Do yo want to save the current progress?(y/n) :");
                                String toSave = input.nextLine();
                                if (toSave.equals("y"))
                                    try {
                                        saveGame(players, playersAccordance, deck, center, k, i, cardPlayer, input);
                                        System.out.println("Game saved successfully");
                                        error = false;
                                    } catch (IOException e) {
                                        System.out.println("Failed to save");
                                        error = true;
                                    }
                                continue gameloop;
                            } else {
                                error = true;
                            }
                        } else if (choice.equals("x")) {
                            System.out.print("Would you like to exit the game?(y/n): ");
                            String exitGame = input.nextLine();
                            if (exitGame.equals("y")) {
                                System.out.print("Do yo want to save the current progress?(y/n) :");
                                String toSave = input.nextLine();
                                if (toSave.equals("y"))
                                    try {
                                        saveGame(players, playersAccordance, deck, center, k, i, cardPlayer, input);
                                        System.out.println("Game saved successfully");
                                        error = false;
                                    } catch (IOException e) {
                                        System.out.println("Failed to save");
                                        error = true;
                                    }
                                break gameloop;
                            } else {
                                error = true;
                            }
                        } else if (choice.equals("save")) {
                            try {
                                saveGame(players, playersAccordance, deck, center, k, i, cardPlayer, input);
                                error = false;
                                printInfo(players, currentPlayer, k, loadStatus, savedData, center, deck);
                            } catch (IOException e) {
                                System.out.println("Failed to save");
                                error = true;
                            }
                        } else if (choice.equals("load")) {
                            while (true) {
                                try {
                                    savedData = loadGame(input);
                                    i = savedData.getI();
                                    k = savedData.getK();
                                    players = savedData.getPlayers();
                                    playersAccordance = savedData.getPlayerAccordance();
                                    currentPlayer = playersAccordance.get(i);
                                    center = savedData.getCenter();
                                    deck = savedData.getDeck();
                                    cardPlayer = savedData.getCardPlayer();
                                    printInfo(players, currentPlayer, k, loadStatus, savedData, center, deck);
                                    error = false;
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Load failed !!!!");
                                    System.out.println("File does not found! Please enter a valid file name");
                                    System.out.println(e.getMessage());
                                    error = true;
                                }
                            }
                        } else {
                            try {
                                cardToRemove = currentPlayer.removeCard(choice); // Card to remove
                                error = false;
                            } catch (Exception e) {
                                System.out.println("Card not found in handDeck, please choose a playable card");
                                error = true;
                            }

                            if (error == false) { // Check if the card matches the suit or rank of the current card

                                if (center.getCards().isEmpty()) { // Check if the center is empty (trick 2 onwards so
                                                                   // that the first player's card is the lead) 
                                    center.push(cardToRemove);
                                    cardPlayer.put(cardToRemove, currentPlayer);
                                } else { //when the center has lead card, check
                                    if (center.get(0).getSuit().equals(cardToRemove.getSuit())
                                            || center.get(0).getRank().equals(cardToRemove.getRank())) {
                                        center.push(cardToRemove);
                                        cardPlayer.put(cardToRemove, currentPlayer);
                                    } else {
                                        error = true;
                                        System.out.println("Please match your suit or rank of the lead card ^^");
                                    }
                                }
                            }
                        }
                    } while (error || choice.equals("d") || choice.equals("load") || choice.equals("save")); // end of
                                                                                                             // input
                                                                                                             // loop
                    if (currentPlayer.getSize() == 0) { // end the round
                        countScore(players);
                        System.out.println("This is the final score for this round. Well Done ^^");
                        printScore(players);
                        System.out.println();
                        System.out.print("Do you want to start a new game ? (y/n) : ");
                        String newGame = input.nextLine();
                        if (newGame.equals("y")) {
                            System.out.println();
                            continue gameloop;
                        } else {
                            break gameloop;
                        }
                    } // end the round

                } // end of player loop

                // remove diff suit
                Stack<Card> centerCardsToPrintOnly = new Stack<Card>();
                centerCardsToPrintOnly = center.getCards();
                removeDiffSuit(center);

                if (k == 1) { // remove the first lead card. Only for trick 1
                    center.remove(0);

                }

                Player highestRankPlayer = determineWinner(center, cardPlayer);
                System.out.println(centerCardsToPrintOnly);
                System.out.println("The winner for this trick is : player" + highestRankPlayer.getPlayerNo()); // print
                                                                                                               // out
                                                                                                               // winner

                // winner leads next trick
                playersAccordance = newPlayerAcc(players, playersAccordance, highestRankPlayer); // Replace existing
                                                                                                 // players acc list
                                                                                                 // with the updated
                                                                                                 // ordering of players
                                                                                                 // stored in new
                                                                                                 // players acc.
            } // end of trick loop
        }
    }
}