/**
 * Fred.
 */
package com.github.cereda.fred;

import com.github.cereda.fred.utils.Formatter;
import com.github.cereda.fred.utils.Reader;
import java.io.IOException;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.ParseException;

/**
 * Main class.
 *
 * @author Paulo Roberto Massa Cereda
 */
public class Fred {

    public static void main(String[] args) {
        
        System.out.println(" ____  ___   ____  ___  ");
        System.out.println("| |_  | |_) | |_  | | \\ ");
        System.out.println("|_|   |_| \\ |_|__ |_|_/ \n");
        
        if (args.length != 2) {
            System.err.println("I'm sorry, fred requires two arguments.\n");
            System.err.println("Usage: fred <bibfile> <key>");
            System.exit(1);
        }
        
        try {
            Reader reader = new Reader(args[0], args[1]);
            BibTeXEntry entry = reader.getEntry();
            if (entry == null) {
                System.out.println("The entry '".concat(args[1]).concat("' was not found."));
            }
            else {
                Formatter formatter = new Formatter();
                formatter.format(entry);
            }
        }
        catch (IOException ioException) {
            System.err.println("File '".concat(args[0]).concat("' was not found."));
        }
        catch (ParseException parseException) {
            System.err.println("An error happened when parsing the BibTeX file.");
        }
    }
}
