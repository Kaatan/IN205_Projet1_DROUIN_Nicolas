package ensta;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        Board field = new Board("TestField", 10);
        field.print(); //Should print a whole table


    }
}
