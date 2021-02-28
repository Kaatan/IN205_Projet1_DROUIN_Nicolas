package ensta;

class BattleShip extends AbstractShip{
    /**
     * Constructor for battleship SubClass
     * @param name
     * @param orientation
     */
    public BattleShip(String name, Orientation orientation){
        super(name, 'B', 4, orientation);
    }
    public BattleShip(){
        super("Battleship", 'B', 4, Orientation.EAST);
    }
}