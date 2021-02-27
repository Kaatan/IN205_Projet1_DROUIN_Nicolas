package ensta;




public class Board implements IBoard { //like that ?
    private String boardName;
    private Boolean boardStrikes[];
    private ShipState boardBoats[];
    private int boardSize;

    public int getSize(){
         return boardSize*boardSize;

    }

    private void Board_Maker(){ /** Initialise les tableaux de l'objet **/
        //System.out.println("Entered Board Maker\n");
        boardStrikes = new Boolean[boardSize*boardSize];
        for (int i =0; i<boardSize*boardSize; i++){
            boardStrikes[i]=null;
        }
        boardBoats = new ShipState[boardSize*boardSize];
        for (int i =0; i<boardSize*boardSize; i++){
            boardBoats[i]= new ShipState(); //pas correct
        }
    }


    public Board (String name, int size){
        /**Size correspond à la largeur du tableau**/
        //System.out.println("Entered Constructor\n");
        boardName = name;
        boardSize = size;
        Board_Maker();

    }

    public Board (String name){
        boardName = name;
        boardSize=10;
        Board_Maker();
    }


    private static String addSpaces(int spaceNumber){
        String exit = "";
        for (int i=0; i< spaceNumber; i++){
            exit+=" ";
        }
        return exit;
    }

    private String letterLine(){
        String exit = "";
        for (int i=0; i<boardSize; i++){
            exit+= " " + String.valueOf((char)(i + 'A'));
        }

        return exit;
    }

    private String addBoardLine (int numLine){
        Boolean tile;
        String exit = "";
        exit+= numLine+1 + " ";
        if (numLine<9){ // car à 10 il y a un char de plus
            exit+=" ";
        }

        for (int i = 0; i<boardSize; i++) { //prtie pour les bateaux
            if (boardBoats[i + boardSize * numLine].getIfLinked() == true) {
                exit += boardBoats[i + boardSize * numLine].toString() + " ";
                //System.out.println("Is linked is true for tile (" + i +", "+numLine + ")");
            } else {
                exit += ". ";
            }
        }

        exit+="    ";//Middleoffset

        for (int i = 0; i<boardSize; i++){ //partie pour les touchés
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

        exit+="\r"; //permet de recouvrir la même ligne à chaque print

        return exit;


    }

    public void print(){
        System.out.println(toString());
    }





    public boolean hasShip(int x, int y){
        if (boardBoats[x+boardSize*y].getIfLinked() == true){
            System.out.println("Err : Occupied Case");
            return true;
        }

        return false;
    }

    public boolean inBounds(int x, int y){
        if (x < boardSize && x >= 0 && y < boardSize && y >= 0){
            return true;
        }
        System.out.println("Err : Out of Bounds");
        return false;
    }



    public int putShip(AbstractShip ship, int x, int y){ //renvoie 1 si succès, 0  si échec

        int x0;
        int y0;
        int i = 0;
        int stop=0;
        int shipSize = ship.getSize();
        char shipDirection = ship.getDirection();
        char shipLabel = ship.getLabel();


        while (i<shipSize && stop == 0){
            x0 = x + i* AbstractShip.convertHorizDirec(shipDirection); //pour les méthodes statics, mettre Class.method()
            y0 = y + i* AbstractShip.convertVertDirec(shipDirection);

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
                x0 = x + i * AbstractShip.convertHorizDirec(shipDirection);
                y0 = y + i*AbstractShip.convertVertDirec(shipDirection);
                boardBoats[x0 + y0*boardSize].setShip(ship);

                //System.out.println("Ship put at " + x0 + " and " + y0 + " placement number " + i + " and ship size is " + shipSize + "\n");


            }
            return 1;
        }
        return 0;

    }

    //Gestion des dégâts


    public void setHit(boolean hit, int x, int y){
        if (inBounds(x, y)){
            boardStrikes[x + y * boardSize] = hit;
        }

    }

    public Boolean getHit(int x, int y){ //vérifie si la case est touchée ou si elle sra touchée lors du tir ?
        if (inBounds(x, y) && boardBoats[x + boardSize * y].getIfLinked() == true){
            return true;
        }
        return false;
    }



    public Hit shipConversion(char label){ //on peut utiliser fromInt du fichier Hit qui convertit la taille du navire en enum
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

    public Hit sendHit(int x, int y){
        Hit hit = null;
        ShipState tile = boardBoats[x + boardSize * y];
        if ( tile.getIfLinked() == true){//on vérifie si on a bien un bateau en dessous
            if (tile.isStruck()){ //on vérifie si la case n'a pas déjjà été touchée

            }
            else { //sinon, on la détruit
                //on actualise d'abord le tableau des bâteaux :

                tile.addStrike();

                if (tile.isSunk()){ //si le bâteau a coulé à cause de ce tir,
                    hit = shipConversion(tile.getShip().getLabel());
                    System.out.println("Le navire de label " + tile.getShip().getLabel() + " a coulé");
                }
                else{ //le bâteau n'a pas coulé
                    hit = Hit.STRIKE;
                }
            }

        }
        else{ //s'il n'y a pas de bâteau :
            hit = Hit.MISS;
        }
        return hit;
    }


}



