package ensta;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        Board field = new Board("TestField", 10);
        //field.print(); //Should print a whole table

        Destroyer firstDestroyer = new Destroyer("Destroyer 1", 'S');
        SubMarine firstSub = new SubMarine("Submarine 1", 'E');
        BattleShip firstBattleShip = new BattleShip("BattleShip 1", 'W');
        Carrier firstCarrier = new Carrier("Carrier 1", 'N');

        /*field.putShip(firstDestroyer, 0, 0);
        field.putShip(firstSub, 10, 5);
        field.putShip(firstBattleShip, 3, 7);
        field.putShip(firstCarrier, 0, 3);
        */

        field.putShip(firstDestroyer, 1, 1);
        field.putShip(firstSub, 5, 5);
        field.putShip(firstBattleShip, 7, 7);
        field.putShip(firstCarrier, 0, 9);

        field.print();




    }
}
