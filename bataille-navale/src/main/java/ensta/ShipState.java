package ensta;

/**
 * Classe intermédiaire entre le ship et le board
 */
public class ShipState{
    protected AbstractShip mainShip;
    protected boolean struck;
    private boolean isLinked;


    public void addStrike(){
        if (struck == false) {
            struck = true;
            mainShip.addStrike();
        }
    }

    /**
     *
     * @return true si le ShipState (= case) est lié à un bateau.
     */
    public boolean getIfLinked(){
        return isLinked;
    }

    /**
     *
     * @return true si la case a été touchée
     */
    public boolean isStruck(){
        return struck;
    }


    public String toString(){
        if (mainShip == null && isLinked==true){
            System.out.println("ship is null but is linked is true");
        }

        if (isLinked && mainShip!=null){
            char label = mainShip.getLabel();
            String ret = Character.toString(label);
            if (struck){
                ret = ColorUtil.colorize(ret, ColorUtil.Color.RED);
            }


            return ret;
        }

        return ".";
    }

    public boolean isSunk(){
        return mainShip.isSunk();
    }

    public AbstractShip getShip(){
        return mainShip;
    }

    /**
     * Associe la ase à un bateau existant
     * @param ship
     */
    public void setShip(AbstractShip ship){

        mainShip = ship;
        isLinked = true;
    }

    /**
     * Constructeur valué
     * @param mainShip
     */
    public ShipState(AbstractShip mainShip){

        this.mainShip = mainShip;
        struck = false;
        isLinked = true;
    }

    /**
     * Constructeur par défaut
     */
    public ShipState(){

        struck = false;
        isLinked = false;
    }
}