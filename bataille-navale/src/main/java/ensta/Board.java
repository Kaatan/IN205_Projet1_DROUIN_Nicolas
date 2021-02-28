package ensta;




public class Board implements IBoard {
    private String boardName;
    private Boolean boardStrikes[];
    private ShipState boardBoats[];
    private int boardSize;

    public int getSize(){
         return boardSize;

    }
    /**
     * Initialise les tableaux de l'objet
     * **/
    private void Board_Maker(){

        boardStrikes = new Boolean[boardSize*boardSize];
        for (int i =0; i<boardSize*boardSize; i++){
            boardStrikes[i]=null;
        }
        boardBoats = new ShipState[boardSize*boardSize];
        for (int i =0; i<boardSize*boardSize; i++){
            boardBoats[i]= new ShipState(); //pas correct
        }
    }

    /**
     * constructeur
     * @param name
     * @param size
     */
    public Board (String name, int size){
        /**Size correspond à la largeur du tableau**/

        boardName = name;
        boardSize = size;
        Board_Maker();

    }

    /**
     * Constructeur
     * @param name
     */
    public Board (String name){
        boardName = name;
        boardSize=10;
        Board_Maker();
    }

    /**
     * Sous fonction pour ajouter un nombre donné d'esapces
     * @param spaceNumber
     * @return un string contenant spaceNumber espaces
     */
    private static String addSpaces(int spaceNumber){
        String exit = "";
        for (int i=0; i< spaceNumber; i++){
            exit+=" ";
        }
        return exit;
    }

    /**
     * @return une ligne de lettres pour l'affichage du tableau
     */
    private String letterLine(){
        String exit = "";
        for (int i=0; i<boardSize; i++){
            exit+= " " + String.valueOf((char)(i + 'A'));
        }

        return exit;
    }

    /**
     *
     * @param numLine
     * @return un String contenant la ligne numLine du board
     */
    private String addBoardLine (int numLine){
        Boolean tile;
        String exit = "";
        exit+= numLine+1 + " ";

        if (numLine<9){
            exit+=" ";
        }

        for (int i = 0; i<boardSize; i++) {
            if (boardBoats[i + boardSize * numLine].getIfLinked() == true) {
                exit += boardBoats[i + boardSize * numLine].toString() + " ";

            } else {
                exit += ". ";
            }
        }

        exit+="    ";//Middleoffset

        for (int i = 0; i<boardSize; i++){
            tile = boardStrikes[i + boardSize * numLine];
            if (tile == null){
                exit += ". ";
            }
            else if (tile == true){
                exit += ColorUtil.colorize("X ", ColorUtil.Color.RED);

            }
            else if (tile == false){
                exit+= "X ";
            }

        }



        exit+="\n";
        return exit;
    }


    public String toString(){
        String exit;
        int length = boardSize*boardSize;
        int leftOffset = 3;
        int middleOffset = 5;

        exit = "\n\n       " + boardName + "\n\n";
        exit += "   Vessels";
        exit += addSpaces(boardSize*2+middleOffset-8);
        exit+="Strikes\n\n";

        exit+="  ";
        exit += letterLine() + addSpaces(middleOffset-1) + letterLine() + "\n";



        for (int i = 0; i<boardSize; i++){
            exit+=addBoardLine(i);
        }

        exit+="\r";

        return exit;


    }

    public void print(){
        System.out.println(toString());
    }





    public boolean hasShip(int x, int y){
        if (boardBoats[x+boardSize*y].getIfLinked() == true){
            System.err.println("Err : Occupied Case");
            return true;
        }

        return false;
    }

    public boolean inBounds(int x, int y){
        if (x < boardSize && x >= 0 && y < boardSize && y >= 0){
            return true;
        }
        System.err.println("Err : Out of Bounds");
        return false;
    }


    /**
     * Place un bateau sur le board
     * @param ship
     * @param x
     * @param y
     * @return 0 si échec, 1 si succès
     */
    public int putShip(AbstractShip ship, int x, int y){

        int x0;
        int y0;
        int i = 0;
        int stop=0;
        int shipSize = ship.getSize();
        Orientation shipOrientation = ship.getOrientation();
        char shipLabel = ship.getLabel();


        while (i<shipSize && stop == 0){
            x0 = x + i* AbstractShip.convertHorizDirec(shipOrientation);
            y0 = y + i* AbstractShip.convertVertDirec(shipOrientation);

            if (!(inBounds(x0, y0))){
                return 0;

            }
            if (hasShip(x0, y0)){
                return 0;

            }
            i++;
        }

        if (stop==0){
            for (i=0; i<shipSize; i++ ){
                x0 = x + i * AbstractShip.convertHorizDirec(shipOrientation);
                y0 = y + i*AbstractShip.convertVertDirec(shipOrientation);
                boardBoats[x0 + y0*boardSize].setShip(ship);

                //System.out.println("Ship put at " + x0 + " and " + y0 + " placement number " + i + " and ship size is " + shipSize + "\n");


            }
            return 1;
        }
        return 0;

    }

    /** *Gestion des dégâts**/

    /**
     * Marque le tableau comme touché
     * @param hit
     * @param x
     * @param y
     */
    public void setHit(boolean hit, int x, int y){
        if (inBounds(x, y)){
            boardStrikes[x + y * boardSize] = hit;
        }

    }

    /**
     *
     * @param x
     * @param y
     * @return la valeur de la case demandée : si elle a été touchée ou non
     */
    public Boolean getHit(int x, int y){
        if (inBounds(x, y)){
            return boardStrikes[x + y * boardSize];
        }
        throw new ArrayIndexOutOfBoundsException();
    }


    /**
     *
     * @param label
     * @return un Hit correspondant au label demandé
     */
    public Hit shipConversion(char label){
        Hit hit = Hit.MISS;
        if (label == 'D'){
            hit= Hit.DESTROYER;
        }
        if (label == 'S'){
            hit = Hit.SUBMARINE;
        }
        if (label == 'C'){
            hit = Hit.CARRIER;
        }
        if (label == 'B'){
            hit = Hit.BATTLESHIP;
        }
        return hit;
    }

    /**
     * Marque un tir sur le tableau à l'endroit indiqué
     * @param x
     * @param y
     * @return le hit correspondant
     */
    public Hit sendHit(int x, int y){
        Hit hit = null;
        if (!inBounds(x, y)){
            return null;
        }
        ShipState tile = boardBoats[x + boardSize * y];
        if (tile.getIfLinked() == true){
            if (tile.isStruck()){
                System.out.println("Case détruite touchée à nouveau en " + (char)('A'+x) + ", " + (y+1));
            }

            else {
                tile.addStrike();

                if (tile.isSunk()){
                    hit = Hit.fromInt(tile.getShip().getSize());
                }
                else{
                    hit = Hit.STRIKE;
                }
            }

        }
        else{
            hit = Hit.MISS;
        }
        return hit;
    }


}



