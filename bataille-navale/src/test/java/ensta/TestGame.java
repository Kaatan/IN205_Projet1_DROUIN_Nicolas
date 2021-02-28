package ensta;

public class TestGame {
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main( String[] args ){

        //Initialisation du board et affichage
        Board field1 = new Board("TestField", 10);

        field1.print();

        //Initialisation d'une liste de navires
        AbstractShip[] ships = new AbstractShip[5];
        ships[0]=new Destroyer();
        ships[1]=new Submarine();
        ships[2]=new Submarine();
        ships[3]=new BattleShip();
        ships[4]=new Carrier();

        BattleShipsAI ai = new BattleShipsAI(field1, field1);

        int counter = 0;
        int[] coords = {0, 0};
        Hit hit = null;

        while (counter<5){



            hit = ai.sendHit(coords);
            if (hit.getValue() > 0){ //un navire a été coulé
                counter++;
                System.out.println(hit.toString() + " a été coulé.");
            }


        }
        field1.print();



    }


}
