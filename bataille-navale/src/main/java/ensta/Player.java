package ensta;
import java.io.Serializable;
import java.util.List;

public class Player {
    /* **
     * Attributs
     */
    protected Board board;
    protected Board opponentBoard;
    protected int destroyedCount;
    protected AbstractShip[] ships;
    protected boolean lose;

    /* **
     * Constructeur
     */
    public Player(Board board, Board opponentBoard, AbstractShip[] ships) {
        this.board = board;
        this.ships = ships;
        this.opponentBoard = opponentBoard;
    }

    /* **
     * Méthodes
     */

    /**
     * Read keyboard input to get ships coordinates. Place ships on given coodrinates.
     */
    public void putShips(int numberShips) {
        boolean done = false;
        int i = 0;


        do {
                AbstractShip s = ships[i];
                String msg = String.format("Placement n° %d : %s (taille %d)", i + 1, s.getName(), s.getSize());
                System.out.println(msg);
                InputHelper.ShipInput res = InputHelper.readShipInput();
                s.setOrientation(Orientation.fromChar(res.orientation));

                //Equivalent d'un try catch
                while (board.putShip(s, res.x, res.y)==0){
                    System.out.println("Position incorrecte. Veuillez recommencer.");
                    res = InputHelper.readShipInput();
                    s.setOrientation(Orientation.fromChar(res.orientation));
                }
                ++i;
                done = i == numberShips;

                board.print();


        } while (!done);
    }

    /**
     * Envoie un hit aux coordonnées données sur le board de l'adversaire
     * Marque son propre board en fonction du résultat
     * @param coords
     * @return
     */
    public Hit sendHit(int[] coords) {
        boolean done = false;
        Hit hit = null;
        int x = 0, y = 0;

        do {
            System.out.println("Où frapper?");
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
            hit = opponentBoard.sendHit(hitInput.x, hitInput.y);


            if (hit!=null){

                if (hit.getValue() == -1){
                    board.setHit(false, hitInput.x, hitInput.y);
                }
                else{
                    board.setHit(true, hitInput.x, hitInput.y);
                }
                done = true;
                x = hitInput.x;
                y = hitInput.y;
            }
        } while (!done);

        coords[0] = x;
        coords[1] = y;

        return hit;
    }

    /**
     *
     * @return les bateaux du joueur
     */
    public AbstractShip[] getShips() {
        return ships;
    }

    /**
     * Fonction inutilisée permettant de mettre à jour les bateaux d'un joueur.
     * @param ships
     */
    public void setShips(AbstractShip[] ships) {
        this.ships = ships;
    }
}
