import java.util.List;

public class Player {
    private Colour colour;
    private List<Tile> playerTiles;
    private boolean checkOpponent;
    private boolean gotChecked;
    public Player(Colour colour) {
        this.colour = colour;
    }
}