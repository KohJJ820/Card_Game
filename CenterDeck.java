import java.util.Stack;

public class CenterDeck extends Deck{
    public CenterDeck(){
        cards = new Stack<Card>();
    }

    @Override
    public String toString() {
        return "Center: " + cards;
    }

}