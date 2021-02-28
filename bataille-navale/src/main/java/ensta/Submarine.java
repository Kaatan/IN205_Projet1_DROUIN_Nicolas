package ensta;


class Submarine extends AbstractShip{
    /**
     * Constructor for Submarine SubClass
     * @param name
     * @param orientation
     */
    public Submarine(String name, Orientation orientation){
        super(name, 'S', 3, orientation);
    }

    public Submarine(){
        super("Submarine", 'S', 3, Orientation.EAST);
    }
}