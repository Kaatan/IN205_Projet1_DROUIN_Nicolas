package ensta;

public abstract class AbstractShip{

    private String shipName;
    private char shipLabel;
    private int shipSize;
    private Orientation shipOrientation;
    private int strikeCount;

    /**
     *
     * @param newOrientation
     */
    public void setOrientation(Orientation newOrientation){
        shipOrientation = newOrientation;
    }

    /**
     * Adds 1 to the number of strikes received bby the ship
     */
    public void addStrike(){
        strikeCount++;

    }

    /**
     * check if the number of strikes is equal (or more) to the length (and therefore max amount of strikes possible) of the ship
     * @return true if ship is sunk
     */
    public boolean isSunk(){

        if (strikeCount >= shipSize){
            return true;
        }
        return false;
    }


    /**
     * Valued Constructor for the ship
     * @param name
     * @param label
     * @param size
     * @param orientation
     */
    public AbstractShip(String name, char label, int size, Orientation orientation){
        shipName=name;
        shipLabel=label;
        shipSize=size;
        shipOrientation = orientation;
        strikeCount = 0;

    }


    /**
     * @return name of ship
     */
    public String getName(){
        return shipName;
    }

    /**
     * @return label of ship
     */
    public char getLabel(){
        return shipLabel;
    }

    /**
     * @return size of ship
     */
    public int getSize(){
        return shipSize;
    }

    /**
     *
     * @return orientation of the ship, in form of an object of the orientation class
     */
    public Orientation getOrientation(){
        return shipOrientation;
    }

    /**
     * receives an orientation and returns a non null value if the orientation is vertical, which sign depends on direction.
     * @param orientation,
     * @return value for calculus in other fonctions
     */
    public static int convertVertDirec(Orientation orientation){

        if (orientation == Orientation.NORTH){
            return -1;
        }
        if (orientation == Orientation.SOUTH )  {
            return 1;
        }
        else{
            return 0;
        }
    }

    /**
     * receives an orientation and returns a non null value if the orientation is horizontal, which sign depends on direction.
     * @param orientation,
     * @return value for calculus in other fonctions
     */
    public static int convertHorizDirec(Orientation orientation){

        if (orientation == Orientation.EAST){
            return 1;
        }
        if (orientation == Orientation.WEST){
            return -1;
        }
        else{
            return 0;
        }
    }
}



