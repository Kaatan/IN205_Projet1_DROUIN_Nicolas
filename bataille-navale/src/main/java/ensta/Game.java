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
    private boolean isUnfair;

    /* ***
     * Constructeurs
     */
    public Game() {}

    /**
     * Initialise le jeu
     * Demande la taille de jeu
     * Demande la difficulté
     * Met en place la liste des bateaux
     * @return le jeu mis à jour.
     */
    public Game init() {
        if (!loadSave()) {

            String msg = "Bienvenue dans ce petit jeu de Bataille Navale !\n\nVoici les règles : \n";
            msg += "Tu vas d'abord choisir ta taille de partie. La taille de la partie détermine le nombre de bâteaux que chaque joueur possède, ainsi que la taille de la grille de jeu.\n";
            msg+= "Si tu joue seul, tu peux choisir la difficulté de la partie.";
            msg+= "Tu vas d'abord devoir placer tes navires un par un. Pour palcer un navire, tu devras entrer la coordonnée de départ ainsi que la direction dans laquelle il sera placé. La coordonnée de départ est indiquée par une lettre majuscule et un nombre, puis la direction par une lettre parmi e (east), n (north), s (south) et w (west).\n";
            msg+= "Tu devras ainsi entrer tes bateaux sous la forme A1 e, B9 w ou F5 s.\n";
            msg+= "Tes bateaux seront placés sur la grille gauche de ta partie.";
            msg+= "A chaque tour, tu pourras effefctuer un tir. Tu ne vois pas les bateaux de ton adversaire, mais tu sauras si tu as touché ou non le bateau. Le résultat de tes frappes sera donné sur la grille droite de l'écran de jeu. Une croix blanche indique un tir raté, une croix rouge un tir réussi. ";
            msg+= "Si tu réussis un tir, tu peux immédiatement retenter ta chance avec un autre tir, et ainsi de suite !\n Bon jeu !";

            System.out.println(msg);


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


            Board b1 = new Board("Board 1", size);
            Board b2 = new Board("Board 2", size);


            System.out.println("1 ou 2 joueurs ?");
            Scanner sin2 = new Scanner(System.in);
            int stop = 0;

            do{
                try{
                    playerNumber = Integer.parseInt(sin2.nextLine());
                    stop=1;
                }catch (Exception e) {
                    System.out.println("Réponse non valide, choisissez 1 ou 2.");
                }
            }while (stop==0);


            if (playerNumber == 1){
                this.player1 = new Player(b1, b2, ships);
                ships2 = setDifficulty();
                this.player2 = new AIPlayer(b2, b1, ships2);


            }
            else{
                this.player1 = new Player(b1, b2, ships);
                ships2 = makeShips();
                this.player2 = new Player(b2, b1, ships2);

            }


            b1.print();
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

    /**
     *
     * @return la liste des bateaux valide pour le mode de jeu sélectionné
     */
    private AbstractShip[] makeShips(){
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


    /**
     * Met à jour la difficulté du jeu et les paramètres associés (nombre de tirs autorisés, etc)
     * @return la liste mise à jour des bateaux pour l'IA
     */
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


            if (diff==1 || diff == 2 || diff == 3){


                if (diff == 1){
                    System.out.println("\nDifficulté : Facile !\n");
                    playerShotNumber+= 1;
                }
                else if(diff == 3){
                    System.out.println("\nDifficulté : Difficile !\n");
                    ennemyShotNumber+= 1;
                }
                else{
                    System.out.println("\nDifficulté : Moyenne !\n");
                }
            }

            if (diff == 4){


                ships2 = new AbstractShip[ships.length+1];
                for (int i = 0; i < ships.length; i++){
                    ships2[i] = ships[i];
                }

                ships2[ships.length] = new Destroyer();
                ennemyShotNumber += 1;
                System.out.println("\nDifficulté : Très Difficile !\n");
            }

            if (diff == 5){
                ships2 = new AbstractShip[ships.length+2];
                for (int i = 0; i < ships.length; i++){
                    ships2[i] = ships[i];
                }
                ships2[ships.length] = new Destroyer();
                ships2[ships.length + 1] = new Destroyer();
                ennemyShotNumber += 2;
                isUnfair = true;
                System.out.println("\nDifficulté  : Injuste ! Bon courage ! (vous en aurez besoin)\n");
            }

        }

        return ships2;

    }


    /**
     * Met à jour les paramètres du jeu pour la taille de jeu choisie (dans la fonction par un appel utilisateur)
     * @return un entier correspondant à la taille du tableau
     */
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
            return 14;
        }
        if (size==4){
            playerShotNumber = 2;
            return 18;
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

    /**
     * Boucle principale du jeu. Gère les tours de jeu, à un ou deux joueurs, les pauses et l'affichage.
     */
    public void run() {
        int[] coords = new int[2];
        Board b1 = player1.board;
        Board b2 = player2.board;
        Hit hit;
        int numberShot;
        boolean fullStrike = false;

        boolean done;
        boolean strike = false;

        do {
            numberShot = 0;
            do{
                hit = player1.sendHit(coords);
                strike = (hit != Hit.MISS);
                b1.setHit(strike, coords[0], coords[1]);
                b1.print();
                System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));
                done = updateScore();
                numberShot++;
                fullStrike = ((!strike & numberShot > playerShotNumber) || (isUnfair && numberShot > playerShotNumber));

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

                        hit = player2.sendHit(coords);

                        strike = (hit != Hit.MISS);
                        if (playerNumber == 2) {
                            b2.print();
                        }
                        System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
                        done = updateScore();
                        numberShot++;

                        fullStrike = (!strike & numberShot > ennemyShotNumber);
                        done = updateScore();
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
        System.out.println(String.format("\n\nLe joueur %d gagne ! ", player1.lose ? 2 : 1));

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

                }
            }

            player.destroyedCount = destroyed;
            player.lose = (destroyed == player.getShips().length);
            if (player.lose) {

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

    public static void main(String args[]) {
        new Game().init().run();
    }
}
