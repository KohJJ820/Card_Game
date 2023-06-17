import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SaveData implements Serializable{
    private int i;
    private int k;
    private ArrayList<Player> players;
    private ArrayList<Player> playerAccordance;
    private Deck deck;
    private CenterDeck center;
    Map<Card, Player> cardPlayer;

    SaveData(){};

    SaveData(ArrayList<Player> players, ArrayList<Player> playerAccordance, Deck deck, CenterDeck center, int k, int i, Map<Card, Player> cardPlayer){
        this.i = i;
        this.k = k;
        this.players = players;
        this.playerAccordance = playerAccordance;
        this.deck = deck;
        this.center = center;
        this.cardPlayer = cardPlayer;
    }

    public Map<Card, Player> getCardPlayer() {
        return cardPlayer;
    }

    public CenterDeck getCenter() {
        return center;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getI() {
        return i;
    }

    public int getK() {
        return k;
    }

    public ArrayList<Player> getPlayerAccordance() {
        return playerAccordance;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }



}
