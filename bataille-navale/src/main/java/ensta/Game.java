package ensta;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

    /* ***
     * Constante
     */
    public static final File SAVE_FILE = new File("savegame.dat");

    /* ***
     * Attributs
     */
    private Player player1;
    private Player player2;
    private Scanner sin;

    private int gameSize;
    private int playerNumber;
    private int playerShotNumber;
    private int ennemyShotNumber;
    private boolean isUnfair; //non utilisé

    /* ***
     * Constructeurs
     */
    public Game() {}

    public Game init() {
        if (!loadSave()) {
            // init attributes

            String msg = "Bienvenue dans ce petit jeu de Bataille Navale !\n\nVoici les règles : \n";
            msg += "Tu vas d'abord choisir ta taille de partie. La taille de la partie détermine le nombre de bâteaux que chaque joueur possède, ainsi que la taille de la grille de jeu.\n";
            msg+= "Si tu joue seul, tu peux choisir la difficulté de la partie.";
            msg+= "Tu vas d'abord devoir placer tes navires un par un. Pour palcer un navire, tu devras entrer la coordonnée de départ ainsi que la direction dans laquelle il sera placé. La coordonnée de départ est indiquée par une lettre majuscule et un nombre, puis la direction par une lettre parmi e (east), n (north), s (south) et w (west).\n";
            msg+= "Tu devras ainsi entrer tes bateaux sous la forme A1 e, B9 w ou F5 s.\n";
            msg+= "Tes bateaux seront placés sur la grille gauche de ta partie.";
            msg+= "A chaque tour, tu pourras effefctuer un tir. Tu ne vois pas les bateaux de ton adversaire, mais tu sauras si tu as touché ou non le bateau. Le résultat de tes frappes sera donné sur la grille droite de l'écran de jeu. Une croix blanche indique un tir raté, une croix rouge un tir réussi. ";
            msg+= "Si tu réussis un tir, tu peux immédiatement retenter ta chance avec un autre tir, et ainsi de suite !\n Bon jeu !";

            System.out.println(msg);

            System.out.println("Entre ton nom:");

            // TODO use a scanner to read player name
            //sin = Scanner(System.in);

            playerShotNumber = 0;
            ennemyShotNumber = 0;


            int size = chooseGameSize();
            while (size==0){
                System.out.println("Choix non valide. Veuillez faire un choix compris entre 1 et 4 inclus.");

                size = chooseGameSize();
            }

            ennemyShotNumber = playerShotNumber;

            AbstractShip[] ships = makeShips();
            AbstractShip[] ships2 = null;

            // TODO init boards
            Board b1 = new Board("Board 1", size);
            Board b2 = new Board("Board 2", size);





            // TODO init this.player1 & this.player2
            System.out.println("1 ou 2 joueurs ?");
            Scanner sin2 = new Scanner(System.in);
            int stop = 0;

            do{
                try{
                    playerNumber = Integer.parseInt(sin2.nextLine()); //bien vérifier si le nombre est valide
                    stop=1;
                }catch (Exception e) {
                    System.out.println("Réponse non valide, choisissez 1 ou 2");
                }
            }while (stop==0);


            if (playerNumber == 1){
                this.player1 = new Player(b1, b2, ships);
                ships2 = setDifficulty();
                this.player2 = new AIPlayer(b2, b1, ships2); //potentiellement un IA player, l'adversaire


            }
            else{
                this.player1 = new Player(b1, b2, ships);
                ships2 = makeShips();
                this.player2 = new Player(b2, b1, ships2);

            }


            b1.print();
            // place player ships
            player1.putShips(ships.length);
            if (playerNumber==2){
                sleep(1000);

                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                sleep(1000);
                b2.print();
            }
            player2.putShips(ships2.length);
            if (playerNumber==2){
                sleep(1000);

                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                sleep(1000);
                b1.print();
            }
        }
        return this;
    }

    /* ***
     * Méthodes
     */

    private AbstractShip[] makeShips(){ //dépend de la taille de la partie
        AbstractShip[] ships = null;

        if (gameSize==1){
            ships = new AbstractShip[3];
            ships[0]=new Destroyer();
            ships[1]=new Submarine();
            ships[2]=new BattleShip();
        }
        if (gameSize == 2){
            ships = new AbstractShip[5];
            ships[0]=new Destroyer();
            ships[1]=new Submarine();
            ships[2]=new Submarine();
            ships[3]=new BattleShip();
            ships[4]=new Carrier();
        }
        if (gameSize == 3){
            ships = new AbstractShip[8];
            ships[0]=new Destroyer();
            ships[1]=new Destroyer();
            ships[3]=new Submarine();
            ships[4]=new Submarine();
            ships[5]=new BattleShip();
            ships[6]=new BattleShip();
            ships[7]=new Carrier();
        }
        if (gameSize == 4){
            ships = new AbstractShip[12];
            ships[0]=new Destroyer();
            ships[1]=new Destroyer();
            ships[2]=new Destroyer();
            ships[3]=new Submarine();
            ships[4]=new Submarine();
            ships[5]=new Submarine();
            ships[6]=new Submarine();
            ships[7]=new BattleShip();
            ships[8]=new BattleShip();
            ships[9]=new BattleShip();
            ships[10]=new Carrier();
            ships[11]=new Carrier();
        }

        return ships;

    }

    private AbstractShip[] setDifficulty(){

        String msg = "Choisis ta diffficulté  : \n";
        msg+= "1 : Facile : Tu peux tirer une fois de plus par tour.\n";
        msg+= "2 : Moyenne : Chaque adversaire a le même nombre de tirs.\n";
        msg+= "3 : Difficile : L'adversaire peut tirer une fois de plus par tour.\n";
        msg+= "4 : Très difficile : L'adversaire peut tirer une fois de plus par tour et dispose d'un Destroyer en plus.\n";
        msg+= "5 : Injuste : L'adversaire peut tirer deux fois de plus par tour. Il dispose de deux Destroyer en plus. Vous ne pouvez pas tirer plus de fois que votre nombre de tirs maximum, même si vous touchez un navire. (On vous aura prévenu !)\n ";

        isUnfair = false;
        int diff = 0;
        Scanner sin2 = null;

        System.out.println(msg);
        diff = 0;

        AbstractShip[] ships = makeShips();


        AbstractShip[] ships2 = ships;


        while (diff > 5 || diff <= 0){
            sin2 = new Scanner(System.in);
            try{
                diff = Integer.parseInt(sin2.nextLine());
            }catch (Exception e) {
                System.out.println("Choix incorrect, veuillez réessayer.");
            }
            System.out.println("Difficulté choisie : " + diff);

            if (diff==1 || diff == 2 || diff == 3){


                if (diff == 1){
                    System.out.println("Difficulté : Facile !");
                    playerShotNumber+= 1;
                }
                else if(diff == 3){
                    System.out.println("Difficulté : Difficile !");
                    ennemyShotNumber+= 1;
                }
                else{
                    System.out.println("Difficulté : Moyenne !");
                }
            }

            if (diff == 4){


                ships2 = new AbstractShip[ships.length+1];
                for (int i = 0; i < ships.length; i++){
                    ships2[i] = ships[i];
                }


                ships2[ships.length] = new Destroyer();


                ennemyShotNumber += 1;

                System.out.println("Difficulté : Très Difficile !");

            }

            if (diff == 5){

                ships2 = new AbstractShip[ships.length+2];

                for (int i = 0; i < ships.length; i++){
                    ships2[i] = ships[i];
                }

                ships2[ships.length] = new Destroyer();
                ships2[ships.length + 1] = new Destroyer();
                ennemyShotNumber += 2;

                isUnfair = true; //a changer pour un bool unfair sans doute.
                System.out.println("Difficulté  : Injuste ! Bon courage ! (vous en aurez besoin)");

            }

        }

        System.out.println("Nombre de bâteaux dans ships 2 " + ships2.length + " nombre de bateaux dans ships : " + ships.length);

        return ships2;

    }




    private int chooseGameSize(){


        String msg = "Choisis ta partie :\n1 : Partie courte. Chaque joueur a seulement un Destroyer, un Sous-Marin et un Cuirassé, sur une carte de taille 7 * 7.\n";
        msg += "2 : Partie moyenne : Chaque joueur a un Destroyer, deux Sous-Marins, un Cuirassé et un Porte-Avions, sur une carte de taille 10 * 10.\n";
        msg+= "3 : Partie grande : Chaque joueur a deux Destroyer, deux Sous-Marins, deux Cuirassé et un Porte-Avions, sur une carte de taille 15*15, chaque joueur peut tirer deux fois par tour minimum.\n";
        msg+= "4 : Partie Geante : Chaque joueur a trois Destroyer, quatre Sous-Marins, trois Cuirassés et deux Porte-Avions, sur une carte de taille 20*20, et chaque joueur peut tirer trois fois par tour minimum.\n";

        System.out.println(msg);
        int size = 0;


        Scanner sin2 = new Scanner(System.in);
        try{
            size = Integer.parseInt(sin2.nextLine());
        }catch (Exception e) {
            // nop
        }

        gameSize = size;



        if (size==1){
            return 7;
        }
        if (size==2){
            return 10;
        }
        if (size == 3){
            playerShotNumber = 1;
            return 13;
        }
        if (size==4){
            playerShotNumber = 2;
            return 16;
        }
        return 0;
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        int[] coords = new int[2];
        Board b1 = player1.board;
        Board b2 = player2.board;
        Hit hit;
        int numberShot;
        boolean fullStrike = false; //true lorsque l'entité a fini tous ses tirs

        // main loop

        boolean done;
        boolean strike = false;


        do {

            numberShot = 0;


            do{
                hit = player1.sendHit(coords); // TODO player1 send a hit
                strike = (hit != Hit.MISS); // TODO set this hit on his board (b1)
                b1.setHit(strike, coords[0], coords[1]);




                b1.print();
                System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));

                done = updateScore();
                if(done){
                    System.out.println("Match should be over");
                }
                else{
                    System.out.println("Match continues");
                }

                numberShot++; //un tir, échoué ou non, a été réalisé.
                //System.out.println("Tirs effectués : " + numberShot + " Tirs totaux : " +playerShotNumber + " Validation du FullStrike ? ");

                fullStrike = ((!strike & numberShot > playerShotNumber) || (isUnfair && numberShot > playerShotNumber)); //si le tir a échoué et que le joueur a réalisé tous ses tirs, ou que la difficulté est injuste et que le joueur a tiré son max :




            }while (!done && !fullStrike);



            save();
            numberShot = 0;

            sleep(1000);
            if (playerNumber==2 && !done){
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                sleep(1500);
                b2.print();
            }



                if(!done) {


                    do {
                        fullStrike = false;


                        hit = player2.sendHit(coords); // TODO player2 send a hit.

                        strike = (hit != Hit.MISS);
                        if (playerNumber == 2) {
                            b2.print();
                        }

                        System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
                        done = updateScore();


                        numberShot++;

                        fullStrike = (!strike & numberShot > ennemyShotNumber); //si le tir a échoué et que l'ennemi a réalisé tous ses tirs :


                        done = updateScore();
                        if (done) {
                            System.out.println("Match should be over");
                        } else {
                            System.out.println("Match continues");
                        }

                        if (!done) {
                            save();
                        }

                        if (playerNumber == 1) {
                            sleep(1000);
                            b1.print();
                        }


                    } while (!fullStrike && !done);

                    if (playerNumber == 2 && !done) {
                        sleep(1000);

                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                        sleep(1500);
                        b1.print();
                    }


                }


        } while (!done);

        SAVE_FILE.delete();
        System.out.println(String.format("Le joueur %d gagne ! ", player1.lose ? 2 : 1));
        //sin.close();
    }


    private void save() {
        /*try {
            // TODO bonus 2 : uncomment
            //  if (!SAVE_FILE.exists()) {
            //      SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
            //  }

            // TODO bonus 2 : serialize players

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private boolean loadSave() {
        /*if (SAVE_FILE.exists()) {
            try {
                // TODO bonus 2 : deserialize players

                return true;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }*/
        return false;
    }

    private boolean updateScore() {
        for (Player player : new Player[]{player1, player2}) {
            int destroyed = 0;
            for (AbstractShip ship : player.getShips()) {
                if (ship.isSunk()) {
                    destroyed++;
                    System.out.println(destroyed + " ships destroyed.");
                }
            }

            player.destroyedCount = destroyed;
            System.out.println(player.destroyedCount + " ships destroyed. (player.destroyedcount) on " + player.getShips().length);
            player.lose = (destroyed == player.getShips().length);


            if (player.lose) {
                System.out.println("Player lost");
                return true;
            }
        }

        return false;
    }

    private String makeHitMessage(boolean incoming, int[] coords, Hit hit) {
        String msg;
        ColorUtil.Color color = ColorUtil.Color.RESET;
        switch (hit) {
            case MISS:
                msg = hit.toString();
                break;
            case STRIKE:
                msg = hit.toString();
                System.out.println("Touché");
                color = ColorUtil.Color.RED;
                break;
            default:
                msg = hit.toString() + " coulé";
                color = ColorUtil.Color.RED;
        }
        msg = String.format("%s Frappe en %c%d : %s", incoming ? "<=" : "=>",
                ((char) ('A' + coords[0])),
                (coords[1] + 1), msg);
        return ColorUtil.colorize(msg, color);
    }

    private static List<AbstractShip> createDefaultShips() {
        return Arrays.asList(new AbstractShip[]{new Destroyer(), new Submarine(), new Submarine(), new BattleShip(), new Carrier()});
    }

    public static void main(String args[]) {
        new Game().init().run();
    }
}
