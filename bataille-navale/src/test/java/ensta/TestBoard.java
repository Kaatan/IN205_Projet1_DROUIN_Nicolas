package ensta;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class TestBoard{

    int main(String args[]){ //référencer plutôt ce truc dans le vrai main dans App
        Board field = new Board("TestField", 10);
        field.print(); //Should print a whole table
        return 1;
    }

}