package ensta;
import java.util.NoSuchElementException;


public enum Orientation {
    WEST('w', 0),
    EAST('e', 1),
    NORTH('n', 2),
    SOUTH('s', 3);

    private char orLabel;
    private int orValue;

    Orientation(char label, int value){
        this.orLabel = label;
        this.orValue = value;
    }

    public char getLabel(){
        return orLabel;
    }

    public static Orientation fromInt(int value){
        for (Orientation orient : Orientation.values()) {
            if (orient.orValue == value) {
                return orient;
            }
        }
        throw new NoSuchElementException("No enum for value " + value);
    }

    public static Orientation fromChar(char label){
        for (Orientation orient : Orientation.values()) {
            if (orient.orLabel == label) {
                return orient;
            }
        }
        throw new NoSuchElementException("No enum for label " + label);
    }

}