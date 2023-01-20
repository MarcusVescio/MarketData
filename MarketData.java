//Packages & Libs used
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
// -- Stock Class

class Stocks
{
    String name;
    String symbol;
    double price;
    int quantity;
 
    public Stocks(String name, String symbol, double price, int quantity) //Constructor
    {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }
}
 
// -- Compares (How we compare and sort the data) 

class nameCompare implements Comparator<Stocks> //Name compare. We sort the names in descending order (A-Z). Not used in this particular program but can be useful if you want to add more stocks with differing names thus why its added.
{
    @Override
    public int compare(Stocks s1, Stocks s2)
    {
        return s1.name.compareTo(s2.name);
    }
}

class symbolCompare implements Comparator<Stocks> //Symbol Compare. Compares the symbols in the same fashion we compared the names. Again not used but if there are more options other than B or S we can use this function to sort each symbol in our output.
{
    @Override
    public int compare(Stocks s1, Stocks s2)
    {
        return s1.symbol.compareTo(s2.symbol);
    }
}
 
class quantityCompare implements Comparator<Stocks> //Quantity Compare. Compares and sorts the quantities in an ASCENDING ORDER.
{
    @Override
    public int compare(Stocks s1, Stocks s2)
    {
        return s1.quantity - s2.quantity;
    }
}

class priceCompare implements Comparator<Stocks> //Price Compare. Compares and sorts the prices in a DESCENDING ORDER.
{
    @Override
    public int compare(Stocks s1, Stocks s2)
    {
        return Double.compare(s2.price, s1.price); //We need to parse it from INT to DBL
    }
}
 
// -- Market Data (Main)

public class MarketData 
{
    public static void main(String[] args) throws IOException
    {
        //Buffered Reader allows us to read the lines from an external file.
        BufferedReader reader = new BufferedReader(new FileReader("C:\\ReadingFile\\input.txt")); //Change this if file location is modified
 
        //Create a Sells ArrayList and Buys ArrayList to hold Stocks objects
        ArrayList<Stocks> sellRecords = new ArrayList<Stocks>(); //Sells Table ArrayList
        ArrayList<Stocks> buyRecords = new ArrayList<Stocks>(); //Buys Table ArrayList
        String currentLine = reader.readLine(); 
 
        while (currentLine != null) //While our currentLine is not empty we go through our while loop until an empty line is reached (then we stop).
        {
            String[] StocksLine = currentLine.split(" "); //Allows us to separate the data using split. (Symbol buyselltype Price qty) Each space " " represents a split in the array in which the next element follows after the space
            String name = StocksLine[0];
            String symbol = StocksLine[1]; //Parse from string to character
            double price = Double.valueOf(StocksLine[2]); //Parse from string to double
            int quantity = Integer.valueOf(StocksLine[3]); //Parse from string to int

            //Create a stock object for every new stock and add it to our Buy and Sell ArrayLists
            if (name.equals("AAPL")) //Only display AAPL stocks. Change for each stock name
            {
                if(symbol.equals("S")) //Sells stocks added to sellRecords ArrayList
                {
                    sellRecords.add(new Stocks(name, symbol, price, quantity));
                }
                if(symbol.equals("B")) //Buys stocks added to buysRecords ArrayList
                {
                    buyRecords.add(new Stocks(name, symbol, price, quantity));
                }
            }
            currentLine = reader.readLine(); //Current line is set to the line the reader is on
        }
 
        //Created BufferedWriter object to write the results of the ArrayLists into the output text file
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\ReadingFile\\output.txt")); //Change this if file location is modified
        Collections.sort(sellRecords, new quantityCompare()); //Where we physically sort the data using our comparing functions
        Collections.sort(buyRecords, new quantityCompare());    
        //Collections.sort(sellRecords, new priceCompare());       //Uncomment for descending price sort 
        //Collections.sort(buyRecords, new priceCompare()); 
        writer.write("Sells");
        writer.newLine();
        writer.write("------");
        writer.newLine();
        for (Stocks Stocks : sellRecords)  //Writes our sellRecords table
        {
            //writer.write(Stocks.name);         //Uncomment to display Name column
            //writer.write(" | "+Stocks.symbol); //Uncomment to display Symbol column 
            writer.write(""+Stocks.price); 
            writer.write(" | "+Stocks.quantity);
            writer.newLine();
        }
        writer.newLine();
        writer.write("Buys");
        writer.newLine();
        writer.write("------");
        writer.newLine();
        for (Stocks Stocks : buyRecords) //Writes our buyRecords table
        {
            //writer.write(Stocks.name);         //Uncomment to display Name column
            //writer.write(" | "+Stocks.symbol); //Uncomment to display Symbol column 
            writer.write(""+Stocks.price);
            writer.write(" | "+Stocks.quantity);
            writer.newLine();
        }
        //Need to close our BufferedReader and BufferedWriter once we finish using them
        reader.close();
        writer.close();
    }
}