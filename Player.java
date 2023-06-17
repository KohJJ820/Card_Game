import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable{
    private String playerNo;
    private int score = 0;
    private ArrayList<Card> handDeck;

    Player(String n){
        handDeck = new ArrayList<>();
        this.playerNo = n;
    }

    public String getPlayerNo() {
        return playerNo;
    }

    @Override
    public String toString() {
        return "Player" + playerNo + ": " + handDeck;
    }

    public void addCard(Card card){
        handDeck.add(card);
    }

    public Card removeCard(String cardString) throws Exception{
        Card cardToRemove = null;
        for (Card card : handDeck) {
            if (card.toString().equals(cardString)) {
                cardToRemove = card;
                handDeck.remove(cardToRemove);
                break;
            }
        }

        if (cardToRemove == null) {
            throw new Exception("The desired card is not in your hand deck");
        }

        return cardToRemove;
    }



    public int getScore() {
        return score;
    }

    public int getSize(){
        return handDeck.size();
    }

    public ArrayList<Card> getHandDeck(){
        return handDeck;
    }
    
    public void setScore(int score) {
        this.score = score;
    }


}
