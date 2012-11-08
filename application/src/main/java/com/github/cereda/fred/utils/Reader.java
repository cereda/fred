/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.cereda.fred.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXParser;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.Value;

/**
 *
 * @author Paulo
 */
public class Reader {
    
    private String filename;
    private String key;

    public Reader(String filename, String key) {
        this.filename = filename;
        this.key = key;
    }
    
    public BibTeXEntry getEntry() throws IOException, ParseException {
        BibTeXParser parser = new BibTeXParser();
        BibTeXDatabase database = parser.parse(new FileReader(filename));
        BibTeXEntry entry = database.resolveEntry(new Key(key));
        return entry;
    }
    
    
}
