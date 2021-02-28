package ensta;
import java.io.Serializable;
import java.util.List;

public class AIPlayer extends Player {
    /* **
     * Attribut
     */
    private BattleShipsAI ai;

    /* **
     * Constructeur
     */
    public AIPlayer(Board ownBoard, Board opponentBoard, AbstractShip[] ships) {
        super(ownBoard, opponentBoard, ships);
        ai = new BattleShipsAI(ownBoard, opponentBoard);
    }


    public void putShips(int numberShips){
        ai.putShips(ships);
    }

    public Hit sendHit(int[] coords){
        return ai.sendHit(coords);
    }



    // TODO AIPlayer must not inherit "keyboard behavior" from player. Call the coded ai instead.
    //This class serves as the Ai for the game. Use what's in BattleShipsAI for the methods.
}
