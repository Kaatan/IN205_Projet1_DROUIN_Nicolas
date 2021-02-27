package ensta;
import java.util.Arrays;
import java.util.Scanner;

public final class InputHelper {

    /* **
     * Constructeur
     */
    private InputHelper() {}

    /* **
     * Classe ShipInput, interne à InputHelper
     */
    public static class ShipInput {
        public char orientation;
        public int x;
        public int y;
    }

    /* **
     * Classe CoordInput, interne à InputHelper
     */
    public static class CoordInput {
        public int x;
        public int y;
    }

    /* **
     * Méthodes de la classe InputHelper
     */
    public static ShipInput readShipInput() {
        @SuppressWarnings("resource")
        Scanner sin = new Scanner(System.in); //réception de l'input
        ShipInput res = new ShipInput(); //création de l'objet dans lequel on stocke les inputs
        String[] validOrientations = {"n", "s", "e", "w"}; // North, South, East, West, détermination des oritentations valides
        boolean done = false;

        do {
            try {
                String[] in = sin.nextLine().toLowerCase().split(" "); //séparation de l'entrée en un tableau de string, la séparation étant faite à l'espace
                //System.out.println(in[0] + "\n" + in[1] + "\n");
                if (in.length == 2) {
                    String coord = in[0]; //le premier string de in correspond au code en deux lettres de la position
                    //System.out.println("First if done");
                    if (Arrays.asList(validOrientations).contains(in[1])) {
                        res.orientation = in[1].charAt(0);
                        res.x = coord.charAt(0) - 'a';

                        res.y = Integer.parseInt(coord.substring(1, coord.length())) - 1;
                        done = true;
                        //System.out.println("Second if done");
                    }
                }
            } catch (Exception e) {
                // nop
            }

            if (!done) {
                System.err.println("Format incorrect! Entrez la position sous forme 'A1 n'");
            }
        } while (!done && sin.hasNextLine());

        return res;
    }

    public static CoordInput readCoordInput() {
        @SuppressWarnings("resource")
        Scanner sin = new Scanner(System.in);
        CoordInput res = new CoordInput();
        boolean done = false;

        do {
            try {
                String coord = sin.nextLine().toLowerCase();
                res.x = coord.charAt(0) - 'a';
                res.y = Integer.parseInt(coord.substring(1, coord.length())) - 1;
                done = true;
            } catch (Exception e) {
                // nop
            }

            if (!done) {
                System.err.println("Format incorrect! Entrez la position sous forme 'A0'");
            }
        } while (!done && sin.hasNextLine());

        return res;
    }
}
