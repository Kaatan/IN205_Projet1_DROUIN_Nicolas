package ensta;
import java.io.Serializable;
import java.util.List;

public class AIPlayer extends Player {
    /**
     * Attribut
     */
    private BattleShipsAI ai;

    /**
     * Constructeur
     */
    public AIPlayer(Board ownBoard, Board opponentBoard, AbstractShip[] ships) {
        super(ownBoard, opponentBoard, ships);
        ai = new BattleShipsAI(ownBoard, opponentBoard);
    }

    /**
     * puts ship on the board. number of ships is useless but needed to conserve the form of the function.
     * @param numberShips
     */
    public void putShips(int numberShips){
        ai.putShips(ships);
    }

    /**
     * Sends hit to coordinates
     * @param coords
     * @return type of hit inflicted.
     */
    public Hit sendHit(int[] coords){
        return ai.sendHit(coords);
    }


}
