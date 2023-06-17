import java.io.Serializable;
import java.util.Collections;
import java.util.Stack;

public class Deck implements Serializable {
    protected Stack<Card> cards;

    public Deck() {
        String suits[] = { "c", "d", "h", "s" };
        String ranks[] = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K" };
        cards = new Stack<Card>();
        for (String x : suits) {
            for (String y : ranks) {
                Card card = new Card(x, y);
                cards.add(card);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCardTop() {
        Card card = cards.pop();
        return card;
    }

    public int getSize() {
        return cards.size();
    }

    @Override
    public String toString() {
        return "Deck: " + cards;
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public Card push(Card card) {
        cards.push(card);
        return card;

    }

    public Stack<Card> getCards() {
        return cards;
    }

    public boolean remove(Card card) {
        return cards.remove(card);
    }

    public Card remove(int index) {
        return cards.remove(index);
    }

    public void clearAndReset() {
        cards.clear();
        cards = new Stack<>();
    }

    public void sort() {
        Collections.sort(cards);
    }

}
