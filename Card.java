import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Card implements Comparable<Card>,Serializable{
    private String suit;
    private String rank;

    public Card(String suit, String rank){
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit(){
        return suit;
    }

    public String getRank(){
        return rank;
    }

    @Override
    public String toString() {
        return suit + rank;
    }

    @Override
    public int compareTo(Card that) {
        List<String> accordance= Arrays.asList("2","3","4","5","6","7","8","9","X","J","Q","K","A");
        int thisIndex = accordance.indexOf(this.rank);
        int thatIndex = accordance.indexOf(that.rank);
        return Integer.compare(thisIndex,thatIndex);
    }



}
