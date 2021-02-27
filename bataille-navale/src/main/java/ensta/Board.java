package ensta;




public class Board implements IBoard { //like that ?
    private String boardName;
    private boolean boardStrikes[];
    private Character boardBoats[];
    private int boardSize;

    public int getSize(){
         return boardSize*boardSize;

    }

    private void Board_Maker(){ /** Initialise les tableaux de l'objet **/
        //System.out.println("Entered Board Maker\n");
        boardStrikes = new boolean[boardSize*boardSize];
        for (int i =0; i<boardSize*boardSize; i++){
            boardStrikes[i]=false;
        }
        boardBoats = new Character[boardSize*boardSize];
        for (int i =0; i<boardSize*boardSize; i++){
            boardBoats[i]='0';
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
        String exit = "";
        exit+= numLine+1 + " ";
        if (numLine<9){ // car à 10 il y a un char de plus
            exit+=" ";
        }

        for (int i = 0; i<boardSize; i++) { //prtie pour les bateaux
            if (boardBoats[i + boardSize * numLine] != '0') {
                exit += boardBoats[i + boardSize * numLine] + " ";
            } else {
                exit += ". ";
            }
        }

        exit+="    ";//Middleoffset

        for (int i = boardSize; i<2*boardSize; i++){ //partie pour les touchés
            exit += ". "; //temporaire
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
        if (boardBoats[x+boardSize*y] != '0'){
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

    public void validShip(AbstractShip ship, int x, int y){

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
                boardBoats[x0 + y0*boardSize] = shipLabel;

                //System.out.println("Ship put at " + x0 + " and " + y0 + " placement number " + i + " and ship size is " + shipSize + "\n");


            }
            return 1;
        }
        return 0;

    }

    public void setHit(boolean hit, int x, int y){
        if (inBounds(x, y)){
            boardStrikes[x + y * boardSize] = hit;
        }

    }

    public Boolean getHit(int x, int y){
        if (inBounds(x, y) && boardBoats[x + boardSize * y]!='0'){
            return true;
        }
        return false;
    }

}



