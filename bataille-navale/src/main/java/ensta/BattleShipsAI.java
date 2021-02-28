package ensta;
import java.io.Serializable;
import java.util.*;

public class BattleShipsAI implements Serializable {

    /* **
     * Attributs
     */

    /**
     * grid size.
     */
    private final int size;

    /**
     * My board. My ships have to be put on this one.
     */
    private final IBoard board;

    /**
     * My opponent's board. My hits go on this one to strike his ships.
     */
    private final IBoard opponent;

    /**
     * Coords of last known strike. Would be a good idea to target next hits around this point.
     */
    private int lastStrike[];

    /**
     * If last known strike lead me to think the underlying ship has vertical placement.
     */
    private Boolean lastVertical;

    /* **
     * Constructeur
     */

    /**
     *
     * @param myBoard board where ships will be put.
     * @param opponentBoard Opponent's board, where hits will be sent.
     */
    public BattleShipsAI(IBoard myBoard, IBoard opponentBoard) {
        this.board = myBoard;
        this.opponent = opponentBoard;
        size = board.getSize();
    }

    /* **
     * Méthodes publiques
     */

    /**
     * Put the ships on owned board.
     * @param ships the ships to put
     */
    public void putShips(AbstractShip ships[]) {
        int x, y, d, i;
        //AbstractShip.Orientation o; //Création d'un objet orientation
        Random rnd = new Random(); //Création d'un random seed
        //AbstractShip.Orientation[] orientations = AbstractShip.Orientation.values(); //récupérations des orientations possibles


        i = 1;
        for (AbstractShip ship : ships) { //pour chaqeu bateau dans ships
            do {
                x = rnd.nextInt(size);
                y = rnd.nextInt(size);
                d = rnd.nextInt(4);
                ship.setDirection(Orientation.fromInt(d));


            } while(board.putShip(ship, x, y) == 0);

            System.out.println("Put ship n° " + i);
            i++;



        }
    }

    /**
     *
     * @param coords array must be of size 2. Will hold the coord of the send hit.
     * @return the status of the hit.
     */
    public Hit sendHit(int[] coords) { //on stocke les données dans coords

        System.out.println("Entered AI sendHit function");

        int res[] = null;
        if (coords == null || coords.length < 2) {
            throw new IllegalArgumentException("must provide an initialized array of size 2");
        }

        System.out.println("passed error verification section");

        // already found strike & orientation?
        if (lastVertical != null) {
            if (lastVertical) {
                res = pickVCoord();
            } else {
                res = pickHCoord();
            }

            if (res == null) {
                // no suitable coord found... forget last strike.
                lastStrike = null;
                lastVertical = null;
            }
        } else if (lastStrike != null) {
            // if already found a strike, without orientation
            // try to guess orientation
            res = pickVCoord();
            if (res == null) {
                res = pickHCoord();
            }
            if (res == null) {
                // no suitable coord found... forget last strike.
                lastStrike = null;
            }
        }

        System.out.println("Passed memory verification section");

        if (lastStrike == null) {
            System.out.println("AI chose random coords");
            res = pickRandomCoord();

        }

        System.out.println("Ai chose to fire at target " + res[0] + ", " + res[1]);

        Hit hit = opponent.sendHit(res[0], res[1]);
        board.setHit(hit != Hit.MISS, res[0], res[1]);

        if (hit != Hit.MISS) {
            if (lastStrike != null) {
                lastVertical = guessOrientation(lastStrike, res);
            }
            lastStrike = res;
        }

        coords[0] = res[0];
        coords[1] = res[1];
        return hit;
    }

    /* ***
     * Méthodes privées
     */

    private boolean canPutShip(AbstractShip ship, int x, int y) {
        Orientation o = ship.getDirection();
        int dx = 0, dy = 0;
        if (o == Orientation.EAST) {
            if (x + ship.getSize() >= this.size) {
                return false;
            }
            dx = 1;
        } else if (o == Orientation.SOUTH) {
            if (y + ship.getSize() >= this.size) {
                return false;
            }
            dy = 1;
        } else if (o == Orientation.NORTH) {
            if (y + 1 - ship.getSize() < 0) {
                return false;
            }
            dy = -1;
        } else if (o == Orientation.WEST) {
            if (x + 1 - ship.getSize() < 0) {
                return false;
            }
            dx = -1;
        }

        int ix = x;
        int iy = y;

        for (int i = 0; i < ship.getSize(); ++i) {
            if ( board.hasShip(ix, iy)) {
                return false;
            }
            ix += dx;
            iy += dy;
        }

        return true;
    }

    private boolean guessOrientation(int[] c1, int[] c2) {
        return c1[0] == c2[0];
    }

    private boolean isUndiscovered(int x, int y) {
        return (x >= 0 && x < size && y >= 0 && y < size && board.getHit(x, y) == null);
        //renvoie true si inBounds et si la case ciblée n'a pas été tirée.
    }

    private int[] pickRandomCoord() {

        System.out.println("Entered random coordinates generation");

        Random rnd = new Random();
        int x;
        int y;

        do {
            x = rnd.nextInt(size);
            y = rnd.nextInt(size);
            System.out.println("Last chosen coordinates : " + x + ", " + y + " among " + size);
        } while (!isUndiscovered(x, y));

        return new int[] {x, y};
    }

    /**
     * pick a coord verically around last known strike
     * @return suitable coord, or null if none is suitable
     */
    private int[] pickVCoord() {
        int x = lastStrike[0];
        int y = lastStrike[1];

        for (int iy : new int[]{y - 1, y + 1}) {
            if (isUndiscovered(x, iy)) {
                return new int[]{x, iy};
            }
        }
        return null;
    }

    /**
     * pick a coord horizontally around last known strike
     * @return suitable coord, or null if none is suitable
     */
    private int[] pickHCoord() {
        int x = lastStrike[0];
        int y = lastStrike[1];

        for (int ix : new int[]{x - 1, x + 1}) {
            if (isUndiscovered(ix, y)) {
                return new int[]{ix, y};
            }
        }
        return null;
    }
}
