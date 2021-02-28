package ensta;


class Destroyer extends AbstractShip{
    /**
     * Destroyer for battleship SubClass
     * @param name
     * @param orientation
     */
    public Destroyer(String name, Orientation orientation){
        super(name, 'D', 2, orientation);
    }
    public Destroyer(){
        super("Destroyer", 'D', 2, Orientation.EAST);
    }
}