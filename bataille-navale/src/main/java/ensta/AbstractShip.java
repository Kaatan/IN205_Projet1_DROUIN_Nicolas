package ensta;



public abstract class AbstractShip{

    private String shipName;
    private char shipLabel; //correspond à la lattre affichée sur la grille. Ptet un char ?
    private int shipSize;
    private char shipDirection;

    public void setDirection(char newOrientation){
        if (checkDirection(newOrientation)){
            shipDirection = newOrientation;
        }
        else{

        }

    } //sert à changer l'orientation

    private boolean checkDirection(char direction){
        if (direction == 'n' || direction == 's' || direction == 'e' || direction == 'w'){
            return true;
        }
            System.out.println("Error : non valid orientation given.");
            return false;
        }


    public AbstractShip(String name, char label, int size, char orientation){
        shipName=name;
        shipLabel=label;
        shipSize=size;

        if(checkDirection(orientation)){
            shipDirection = orientation;
        }
        else{
            shipDirection = 'o'; //possiblement pas exact
        }

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

    public char getDirection(){
        return shipDirection;
    }

    public static int convertVertDirec(char direc){
        if (direc=='n'){
            return -1;
        }
        if (direc=='s'){
            return 1;
        }
        else{
            return 0;
        }
    }

    public static int convertHorizDirec(char direc){
        if (direc=='e'){
            return 1;
        }
        if (direc=='w'){
            return -1;
        }
        else{
            return 0;
        }
    }

}



//à mettre dans des fichiers dédiés
class Destroyer extends AbstractShip{
    public Destroyer(String name, char orientation){
        super(name, 'D', 2, orientation);
    }
}

class Submarine extends AbstractShip{
    public Submarine(String name, char orientation){
        super(name, 'S', 3, orientation);
    }
}

class BattleShip extends AbstractShip{
    public BattleShip(String name, char orientation){
        super(name, 'B', 4, orientation);
    }
}

class Carrier extends AbstractShip{
    public Carrier(String name, char orientation){
        super(name, 'C', 5, orientation);
    }
}
