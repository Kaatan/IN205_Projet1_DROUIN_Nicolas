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
    public void putShips() {
        boolean done = false;
        int i = 0;


        do {
            //try{
                AbstractShip s = ships[i]; //liste des bâteaux à placer, à déterminer en avance dans une initialisation d'une autre méthode

                String msg = String.format("Placement n° %d : %s (taille %d)", i + 1, s.getName(), s.getSize());
                System.out.println(msg); //affichage du message contenant le numéro du bateau à placer, son nom et sa taille
                InputHelper.ShipInput res = InputHelper.readShipInput();//récupération des inputs joueur. La validation de l'input est effectuée dans cette fonction/
                s.setDirection(res.orientation); //mise à jour de l'orientation

                while (board.putShip(s, res.x, res.y)==0){ //boucle de vérification du placement.
                    System.out.println("Position incorrecte. Veuillez recommencer.");
                    res = InputHelper.readShipInput(); //On recommence, il y a eu une erreur.
                    //Traitement des inputs nécessaire
                    s.setDirection(res.orientation); //mise à jour de l'orientation


                }

                ++i;
                done = i == 5; //on place 5 bateaux

                board.print();
            /*} catch (Exception e) {
                System.err.println("Problem Occured");
            }*/

        } while (!done);
    }

    public Hit sendHit(int[] coords) {
        boolean done = true;
        Hit hit = null;

        do {
            System.out.println("où frapper?");
            InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
            hit = opponentBoard.sendHit(hitInput.x, hitInput.y);
            // TODO call sendHit on this.opponentBoard : Done

            // TODO : Game expects sendHit to return BOTH hit result & hit coords.
            // return hit is obvious. But how to return coords at the same time ?
            //Answer : no need to. Just update playerboard here with SetHit.
            if (hit.getValue() == -1){
                board.setHit(false, hitInput.x, hitInput.y);
            }
            else{
                board.setHit(true, hitInput.x, hitInput.y);
            }
        } while (!done);

        return hit;
    }

    public AbstractShip[] getShips() {
        return ships;
    }

    public void setShips(AbstractShip[] ships) { //sert à mettre à jour les bâteaux d'un joueur : à faire dans le main.
        this.ships = ships;
    }
}
