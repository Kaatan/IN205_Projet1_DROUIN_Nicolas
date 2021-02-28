package ensta;

public abstract class AbstractShip{

    private String shipName;
    private char shipLabel; //correspond à la lattre affichée sur la grille. Ptet un char ?
    private int shipSize;
    private Orientation shipDirection;
    private int strikeCount;

    public void setDirection(Orientation newOrientation){
        shipDirection = newOrientation;
    } //sert à changer l'orientation


    public void addStrike(){
        strikeCount++;
        //System.out.println("number of received strikes is " + strikeCount + " while total size is " + shipSize);
    }

    public boolean isSunk(){
        //System.out.println("From isSunk : number of received strikes is " + strikeCount + " while total size is " + shipSize + " and Label is " + shipLabel);
        if (strikeCount >= shipSize){
            return true;
        }
        return false;
    }



    public AbstractShip(String name, char label, int size, Orientation orientation){
        shipName=name;
        shipLabel=label;
        shipSize=size;
        shipDirection = orientation;
        strikeCount = 0;

    }

    //public abstract AbstractShip(String name, char orientation);


    public String getName(){
        return shipName;
    }

    public char getLabel(){
        return shipLabel;
    }

    public int getSize(){
        return shipSize;
    }

    public Orientation getDirection(){
        return shipDirection;
    }

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



//à mettre dans des fichiers dédiés
class Destroyer extends AbstractShip{
    public Destroyer(String name, Orientation orientation){
        super(name, 'D', 2, orientation);
    }
    public Destroyer(){
        super("Destroyer", 'D', 2, Orientation.EAST);
    }
}

class Submarine extends AbstractShip{
    public Submarine(String name, Orientation orientation){
        super(name, 'S', 3, orientation);
    }

    public Submarine(){
        super("Submarine", 'S', 3, Orientation.EAST);
    }
}

class BattleShip extends AbstractShip{
    public BattleShip(String name, Orientation orientation){
        super(name, 'B', 4, orientation);
    }
    public BattleShip(){
        super("Battleship", 'B', 4, Orientation.EAST);
    }
}

class Carrier extends AbstractShip{
    public Carrier(String name, Orientation orientation){
        super(name, 'C', 5, orientation);
    }
    public Carrier(){
        super("Carrier", 'C', 5, Orientation.EAST);
    }
}
