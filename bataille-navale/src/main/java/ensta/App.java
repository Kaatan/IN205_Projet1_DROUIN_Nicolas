package ensta;

public class App {


    /**
     * Main app
     * @param args
     */
    public static void main( String[] args ){


        Game game = new Game();
        game = game.init();
        game.run();



    }


}
