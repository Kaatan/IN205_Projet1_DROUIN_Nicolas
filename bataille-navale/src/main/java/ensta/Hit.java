package ensta;
import java.util.NoSuchElementException;

public enum Hit {
    MISS(-1, "Manqué"),
    STRIKE(-2, "Touché"),
    DESTROYER(2, "Destroyer"),
    SUBMARINE(3, "Sous-marin"),
    BATTLESHIP(4, "Cuirassé"),
    CARRIER(5, "Porte-avion"),
    ALREADYHIT(6, "Déjà touché");

    /* ***
     * Attributs
     */
    private int value;
    private String label;

    /* ***
     * Constructeur
     */
    Hit(int value, String label) {
        this.value = value;
        this.label = label;
    }

    /* ***
     * Méthodes
     */

    /**
     *
     * @param value
     * @return un Hit en fonction du code value associé
     */
    public static Hit fromInt(int value) {
        for (Hit hit : Hit.values()) {
            if (hit.value == value) {
                return hit;
            }
        }
        throw new NoSuchElementException("No enum for value " + value);
    }

    /**
     *
     * @return la valeur d'un Hit donné
     */
    public int getValue(){
        return value;
    }

    /**
     *
     * @return le label du hit pour l'affichage.
     */
    public String toString() {
        return this.label;
    }
};
