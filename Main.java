import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

class Main {

    public static String printScore(ArrayList<Player> players) {
        StringBuilder sb = new StringBuilder("Score: ");
        for (Player player : players) {
            sb.append("Player" + player.getPlayerNo() + " = " + player.getScore() + " | ");
        }
        sb.delete(sb.length() - 3, sb.length());
        return sb.toString();
    }

    public static void removeDiffSuit(Stack<Card> center) {
        for (int j = 1; j < center.size(); j++) {
            if (center.get(j).getSuit() != center.get(0).getSuit()) {
                center.remove(center.get(j));
            }
        }
    }
    
    

    public static Player determineWinner(Stack<Card> center, Map<Card, Player> map) {
        Collections.sort(center); // sort ascending (refer Card.java compareTo for info)
        Card largestRankCard = center.pop(); // take the last element of stack (greatest rank)
        Player highestRankPlayer = map.get(largestRankCard);
        // for illustration purpose, print out the key-value pair if the Card:Player

        // for (Card key : cardPlayer.keySet()) {
        // System.out.println(key + " = " + cardPlayer.get(key));
        // } // for checking purpose

        // prints the winner
        return highestRankPlayer;
    }

    public static void determinePlayerAccordance(Card card, Stack<Card> center, ArrayList<Player> pA,
            ArrayList<Player> players) {
        switch (center.get(0).getRank()) {
            case "A":
            case "5":
            case "9":
            case "K":
                for (int i = 0; i < players.size(); i++) {// Player1
                    pA.add(players.get(i));
                }
                break;
            case "2":
            case "6":
            case "X":// Player2
                for (int i = 1; i < players.size(); i++) {
                    pA.add(players.get(i));
                    if (i == 0) {
                        break;
                    }
                    if (i == 3) {
                        i = -1;
                    }
                }
                break;
            case "3":
            case "7":
            case "J":// Player3
                for (int i = 2; i < players.size(); i++) {
                    pA.add(players.get(i));
                    if (i == 1) {
                        break;
                    }
                    if (i == 3) {
                        i = -1;
                    }
                }
                break;
            case "4":
            case "8":
            case "Q":// Player4
                for (int i = 3; i < players.size(); i++) {
                    pA.add(players.get(i));
                    if (i == 2) {
                        break;
                    }
                    if (i == 3) {
                        i = -1;
                    }
                }
                break;
            default:
                System.out.println("Error assigning lead card");
                break;
        }

    }

    public static ArrayList<Player> newPlayerAcc(ArrayList<Player> players, ArrayList<Player> playerAcc, Player highestRankPlayer) {
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

    public static void main(String[] args) {
        outerloop: while (true) { // New game
            Deck deck = new Deck(); // new Deck is created
            Stack<Card> center = new Stack<>(); // new center is created
            Scanner input = new Scanner(System.in);
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
            for (int k = 1; players.get(0).getSize() != 0 ||
                    players.get(1).getSize() != 0 ||
                    players.get(2).getSize() != 0 ||
                    players.get(3).getSize() != 0; k++) {
                if (k > 1) {
                    center = new Stack<>();
                }

                // new Map created
                Map<Card, Player> cardPlayer = new HashMap<>();
                System.out.println();
                System.out.println("Trick" + k + "#");

                for (int i = 0; i < 4; i++) {
                    for (Player player : players) {
                        if (player.equals(playersAccordance.get(i))) {
                            System.out.println("--> " + player.toString());
                        } else {
                            System.out.println("    " + player.toString());
                        }

                    } // print player's deck

                    System.out.println("Center: " + center);
                    System.out.println(deck.toString()); // print deck
                    System.out.println("Turn: Player" + playersAccordance.get(i).getPlayerNo());
                    Card cardToRemove = null;
                    boolean error = false;
                    String choice;
                    do {
                        System.out.print("> ");
                        choice = input.nextLine();
                        // if input is draw, the player takes the card on top of the deck (see the error
                        // || choice.equals("draw") at the end of the do-while loop)
                        if (choice.equals("d")) {
                            Card drawCard = deck.drawCardTop();
                            playersAccordance.get(i).addCard(drawCard);
                            System.out.println("Trick" + k + "#");
                            for (Player player : players) {
                                System.out.println(player.toString());
                            } // print all players deck
                            System.out.println("Center: " + center);
                            System.out.println(deck.toString()); // print deck
                            System.out.println(printScore(players)); // print players score
                            System.out.println("Turn: Player" + playersAccordance.get(i).getPlayerNo());

                        } else if (choice.equals("s")) {
                            System.out.println("Would you like to start a new game? (y/n)");
                            System.out.print("choice: ");
                            String startNew = input.nextLine();
                            System.out.println();
                            if (startNew.equals("y")) {
                                continue outerloop;
                            } else {
                                error = true;
                            }
                        } else if (choice.equals("x")) {
                            System.out.println("Would you like to exit the game? (y/n)");
                            System.out.print("choice: ");
                            String exitGame = input.nextLine();
                            if (exitGame.equals("y")) {
                                break outerloop;
                            } else {
                                error = true;
                            }
                        } else {
                            try {
                                cardToRemove = playersAccordance.get(i).removeCard(choice); // Card to remove
                                error = false;
                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                                error = true;
                            }

                            if (error == false) { // Check if the card matches the suit or rank of the current card

                                if (center.isEmpty()) { // Check if the center is empty (trick 2 onwards so that the
                                                        // first player's card is the lead)
                                    center.push(cardToRemove);
                                    cardPlayer.put(cardToRemove, playersAccordance.get(i));
                                } else {
                                    if (center.get(0).getSuit().equals(cardToRemove.getSuit())
                                            || center.get(0).getRank().equals(cardToRemove.getRank())) {
                                        center.push(cardToRemove);
                                        cardPlayer.put(cardToRemove, playersAccordance.get(i));
                                    } else {
                                        error = true;
                                        System.out.println("Please match your suit or rank of the lead card ^^");
                                    }
                                }

                            }
                        }

                    } while (error || choice.equals("d"));

                    System.out.println(center);
                } // for loop

                // remove diff suit
                removeDiffSuit(center);

                if (k == 1) { // remove the first lead card. Only for trick 1
                    center.remove(0);

                }

                Player highestRankPlayer = determineWinner(center, cardPlayer);

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

            }
            // for loop
        }
    }
}