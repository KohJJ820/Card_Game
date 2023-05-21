import java.util.Collections;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards;
    String suits[] = {"c", "d", "h", "s"};
    String ranks[] = {"A","2","3","4","5","6","7","8","9","X","J","Q","K"};

    public Deck(){
        cards = new Stack<Card>();
            for (String x : suits) {
            for (String y:ranks) {
                Card card = new Card(x, y);
                cards.add(card);
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card drawCardTop(){
        Card card = cards.pop();
        return card;
    }

    public int getSize(){
        return cards.size();
    }

    @Override
    public String toString() {
        return "Deck: " + cards;
    }

}
