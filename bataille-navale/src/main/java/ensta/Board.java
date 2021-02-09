package ensta;



import java.lang.Math;

public class Board{
    private String boardName;
    private boolean boardStrikes[];
    private Character boardBoats[];
    private int boardSize;


    private void Board_Maker(){ /** Initialise les tableaux de l'objet **/
        boardStrikes = new boolean[boardSize*boardSize];
        boardBoats = new Character[boardSize*boardSize];
    }


    public Board (String name, int size){
        /**Size correspond à la largeur du tableau**/
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
        for (int i = 0; i<2*boardSize; i++){

            exit+= ". ";
            if (i == boardSize-1){
                exit+="    ";//Middleoffset
            }
        }//nécessité de couper le for en deux parce qu'on va afficher des données différentes par la suite.
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

        return exit;






    }

    public void print(){
        System.out.println(toString());
    }
}