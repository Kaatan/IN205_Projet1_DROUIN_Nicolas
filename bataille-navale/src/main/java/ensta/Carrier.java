package ensta;



class Carrier extends AbstractShip{
    /**
     * Constructors for Carrier SubClass
     * @param name
     * @param orientation
     */
    public Carrier(String name, Orientation orientation){
        super(name, 'C', 5, orientation);
    }
    public Carrier(){
        super("Carrier", 'C', 5, Orientation.EAST);
    }
}