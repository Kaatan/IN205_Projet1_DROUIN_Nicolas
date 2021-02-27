package ensta;

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

    public boolean getIfLinked(){
        return isLinked;
    }

    public boolean isStruck(){
        return struck;
    }

    public String toString(){
        if (mainShip == null && isLinked==true){
            System.out.println("ship is null but is linked is true");
        }

        if (isLinked && mainShip!=null){
            char label = mainShip.getLabel();
            String ret = Character.toString(label); //récupération du label
            if (struck){
                ret = ColorUtil.colorize(ret, ColorUtil.Color.RED); //coloration du label
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

    public void setShip(AbstractShip ship){
        System.out.println("Called method that sets isLinked to True");
        mainShip = ship;
        isLinked = true;
    }

    public ShipState(AbstractShip mainShip){
        System.out.println("Called constructor that sets isLinked to true");
        this.mainShip = mainShip;
        struck = false;
        isLinked = true;
    }

    public ShipState(){
        //System.out.println("Called constructor that sets isLinked to false");
        struck = false;
        isLinked = false;
    }
}