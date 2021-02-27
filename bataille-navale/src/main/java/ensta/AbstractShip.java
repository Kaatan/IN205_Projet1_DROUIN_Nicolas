package ensta;



public class abstract AbstractShip{

    private String shipName;
    private char shipLabel; //correspond à la lattre affichée sur la grille. Ptet un char ?
    private int shipSize;
    private char shipDirection;

    public void mutator(char newOrientation){
        if (checkDirection(newOrientation)){ //à coder proprement
            shipDirection = newOrientation;
        }
        else{

        }

    } //sert à changer l'orientation

    private boolean checkDirection(char direction){
        if (direction == 'N' || direction == 'S' || direction == 'E' || direction == 'W'){
            return true
        }
            System.out.println("Error : non valid orientation given.");
            return false
        }


    public AbstractShip(String name, char label, int size, char orientation){
        shipName=name;
        shipLabel=label;
        shipSize=size;

        if(checkDirection(orientation)){
            shipDirection = orientation;
        }
        else{
            shipDirection == NULL; //possiblement pas exact
        }

    }


    public String getName(){
        return shipName;
    }

    public char getLabel(){
        return shipLabel;
    }

    public int getSize(){
        return shipSize;
    }

    public char getDirection(){
        return shipDirection;
    }

}

class AbstractShip extends Destroyer{
    public Destroyer(String name, char orientation){
        AbstractShip(name, 'D', 2, orientation)
    }
}

class AbstractShip extends SubMarine{
    public SubMarine(String name, char orientation){
        AbstractShip(name, 'S', 3, orientation)
    }
}

class AbstractShip extends BattleShip{
    public Destroyer(String name, char orientation){
        AbstractShip(name, 'B', 4, orientation)
    }
}

class AbstractShip extends Carrier{
    public Destroyer(String name, char orientation){
        AbstractShip(name, 'C', 5, orientation)
    }
}
